package group8.scheduler;

import group8.algorithm.*;
import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.*;
import group8.visualisation.AlgorithmStatus;

import java.util.*;
import java.util.concurrent.*;

/**
 * This class implements the A* algorithm
 */
public class AStarScheduler implements IScheduler {

    private Comparator<Schedule> _heuristicAndEarliestStartTimeComparator;
    private ScheduleQueue _openState;
    private Graph _graph;
    private int _scheduleCount = 0;
    private ExecutorService _executorService = Executors.newFixedThreadPool(AppConfig.getInstance().getNumCores());
    private int _numUsableThreads = AppConfig.getInstance().getNumCores();
    private List<ELSModelStateExpander> _expanderList = new ArrayList<>();
    //long array to hold schedules generated by each thread
    private long[] _scheduleCountList = new long[AppConfig.getInstance().getNumCores()];

    private int test = 0;

    /**
     * This method is an implementation of the A* algorithm
     * @param graph
     * @return
     * @throws AppConfigException
     */
    @Override
    public Schedule generateValidSchedule(Graph graph) throws AppConfigException {
        _graph = graph;

        //Make comparators which allow us to compare with one heuristic first then another
        //the program only compares with the second heuristic if first one returns zero
        Comparator<Schedule> heuristicComparator = Comparator.comparing((Schedule s) -> s.getHeuristicCost());
        Comparator<Schedule> nodesAssignedComparator = Comparator.comparing((Schedule s) ->s.getTasks().size());
        Comparator<Schedule> earliestStartTimeComparator = Comparator.comparing((Schedule s) ->s.getEarliestStartTime());
        _heuristicAndEarliestStartTimeComparator = heuristicComparator.thenComparing(nodesAssignedComparator).thenComparing(earliestStartTimeComparator).thenComparing(new ScheduleComparator(_graph));
        //make the priority queue use our own comparator by passing it into the priority queue
        _openState = new ScheduleQueue(_heuristicAndEarliestStartTimeComparator);

        Schedule schedule = new Schedule();
        _graph.setHeuristicCost(Math.min(new SimpleHeuristic().calculateEstimate(schedule, _graph.getAllNodes()),new GreedyHeuristic().calculateEstimate(schedule, _graph.getAllNodes())));

        IHeuristic heuristic = new MaxThreeHeuristic();
        //create a list of ELS expander objects for reuse
        for (int i = 0; i < _numUsableThreads; i++) {
            _expanderList.add(new ELSModelStateExpander(graph, heuristic));
        }

        // Set algo status to RUNNING.
        AlgorithmStatus algorithmStatus = AlgorithmStatus.getInstance();
        algorithmStatus.setAlgoState(AlgorithmState.RUNNING);

        List<Schedule> newFoundStates;
        _openState.add(schedule); //add the empty schedule to get the algorithm started
        _scheduleCount++;

        //continue with the algorithm while there are still states in the priority queue
        while (true) {

            //check if the size of the priority queue is less than number of threads we have available
            //if it is then we don't parallelise the expansion since we don't have enough schedules to assign
            if (_openState.size() < _numUsableThreads * 1000) {
                schedule = _openState.pollFirst(); //pop out the most promising state
                test = 1;
                //Set current best schedule.
                algorithmStatus.setCurrentBestSchedule(schedule);

                //run checkCompleteSchedule helper method to check if state is complete,
                //meaning that the schedule is valid
                if (checkCompleteSchedule(schedule)) {
                    System.out.println(_scheduleCount);
                    AlgorithmStatus.getInstance().setAlgoState(AlgorithmState.FINISHED);
                    return schedule;
                }

                //obtain a new set of states expanding from the most promising state
                _expanderList.get(0).setState(schedule);
                newFoundStates = _expanderList.get(0).getNewStates(schedule);
                _scheduleCount +=newFoundStates.size();
                _scheduleCountList[0] += newFoundStates.size(); //add number of schedules generated to the count of this thread
                _openState.addClosedState(schedule);
                //add the newly found states into the priority queue
                _openState.addAll(newFoundStates);

            } else {
                test = 2;
                //A list to contain the future list of states which each thread will return
                List<Future> allFutures = new ArrayList<>();
                for (int i = 0; i < _numUsableThreads; i++) { //perform actions for each thread
                    schedule = _openState.pollFirst(); //pop out the most promising state for each thread

                    //run checkCompleteSchedule helper method to check if state is complete,
                    //if schedule is complete then that the schedule is valid

                    algorithmStatus.setCurrentBestSchedule(schedule);
                    if (checkCompleteSchedule(schedule)) {
                        System.out.println(_scheduleCount);
                        AlgorithmStatus.getInstance().setAlgoState(AlgorithmState.FINISHED);
                        return schedule;
                    }
                    // assign each thread in the thread pool a state to expand
                    _expanderList.get(i).setState(schedule);
                    Future<List<Schedule>> future = _executorService.submit(_expanderList.get(i));
                    allFutures.add(future); //add future values to the list of future values
                    _openState.addClosedState(schedule); //add the explored schedule to another list
                }

                for (int i = 0; i < allFutures.size(); i++) { //for each of the futures
                    Future<List<Schedule>> future = allFutures.get(i); //retrieve the future object
                    try {
                        //main will block here when trying to get the real value of the future
                        newFoundStates = future.get(); //obtain a new set of states expanding from the most promising state
                        _scheduleCount +=newFoundStates.size(); //add the found schedules to the count
                        _scheduleCountList[i] += newFoundStates.size(); //add number of schedules generated to the count of this thread

                        //add the newly found states into the priority queue
                        _openState.addAll(newFoundStates);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
            algorithmStatus.setNumSchedulesGenerated(_scheduleCount);
            algorithmStatus.setNumSchedulesOnCores(_scheduleCountList);
        }
    }

    /**
     * A check to see if the state is complete, meaning that the schedule contains
     * all of the nodes of the graph.
     * @param state
     * @return
     */
    private boolean checkCompleteSchedule(Schedule state) {

        if(state==null){
            System.out.println(_scheduleCount);
            System.out.println(test);
        }
        Set<String> taskIdList = state.getTasks().keySet();
        Set<String> nodeIdListCopy = new TreeSet<>();
        nodeIdListCopy.addAll(_graph.getAllNodes().keySet());
        nodeIdListCopy.removeAll(taskIdList);

        if (nodeIdListCopy.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
