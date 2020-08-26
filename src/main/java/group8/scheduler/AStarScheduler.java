package group8.scheduler;

import group8.algorithm.AlgorithmState;
import group8.algorithm.ELSModelStateExpander;
import group8.algorithm.GreedyHeuristic;
import group8.algorithm.SimpleHeuristic;
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

    //These comparators allow us to compare with one heuristic first then another one
    //the program only compares with the second heuristic if first one returns zero
    Comparator<Schedule> heuristicComparator = Comparator.comparing((Schedule s) -> s.getHeuristicCost());
    Comparator<Schedule> earliestStartTimeComparator = Comparator.comparing((Schedule s) -> s.getEarliestStartTime());
    Comparator<Schedule> heuristicAndEarliestStartTimeComparator = heuristicComparator.thenComparing(earliestStartTimeComparator);

    //make the priority queue use our own comparator by passing it into the priority queue
    private ScheduleQueue _openState = new ScheduleQueue(heuristicComparator);
    //private List<Schedule> _closedState = new ArrayList<>();

    private Graph _graph;
    private HashMap<String, Node> _allNodesOfGraph;
    private Set<String> _nodeIdList;
    private int _scheduleCount = 0;
    private ExecutorService _executorService = Executors.newFixedThreadPool(AppConfig.getInstance().getNumCores());
    private int _numUsableThreads = AppConfig.getInstance().getNumCores();

    /**
     * This method is an implementation of the A* algorithm
     * @param graph
     * @return
     * @throws AppConfigException
     */
    @Override
    public Schedule generateValidSchedule(Graph graph) throws AppConfigException {
        _graph = graph;
        _allNodesOfGraph = _graph.getAllNodes();
        _nodeIdList = _allNodesOfGraph.keySet();

        // Set algo status to RUNNING.
        AlgorithmStatus algorithmStatus = AlgorithmStatus.getInstance();
        algorithmStatus.setAlgoState(AlgorithmState.RUNNING);

        //initialises the helper classes as objects to use their methods
        Schedule schedule = new Schedule();
        _graph.setHeuristicCost(
                Math.min(new SimpleHeuristic().calculateEstimate(schedule, _graph.getAllNodes()),
                        new GreedyHeuristic().calculateEstimate(schedule, _graph.getAllNodes())));
        List<Schedule> newFoundStates;
        _openState.add(schedule); //add the empty schedule to get the algorithm started
        _scheduleCount++;


        //continue with the algorithm while there are still states in the priority queue
        while (true) {
            algorithmStatus.setNumSchedulesGenerated(_scheduleCount);

            //check if the size of the priority queue is less than number of threads we have available
            //if it is then we don't parallelise the expansion since we don't have enough schedules to assign
            if (_openState.size() < _numUsableThreads) {
                schedule = _openState.poll(); //pop out the most promising state

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
                newFoundStates = new ELSModelStateExpander(_graph, schedule).getNewStates(schedule);
                _scheduleCount +=newFoundStates.size();
                _openState.addClosedState(schedule);

                //add the newly found states into the priority queue only if their heuristic cost is smaller
                // than baseline heuristic cost of the whole graph
                newFoundStates.forEach(state -> {
                    if (_graph.getHeuristicCost() > state.getHeuristicCost()) {
                        _openState.add(state);
                    }
                });

            } else {



                //A list to contain the future list of states which each thread will return
                List<Future> allFutures = new ArrayList<>();
                for (int i = 0; i < _numUsableThreads; i++) { //perform actions for each thread
                    schedule = _openState.poll(); //pop out the most promising state for each thread

                    //run checkCompleteSchedule helper method to check if state is complete,
                    //if schedule is complete then that the schedule is valid

                    algorithmStatus.setCurrentBestSchedule(schedule);
                    if (checkCompleteSchedule(schedule)) {
                        System.out.println(_scheduleCount);
                        AlgorithmStatus.getInstance().setAlgoState(AlgorithmState.FINISHED);
                        return schedule;
                    }
                    // assign each thread in the thread pool a state to expand
                    Future<List<Schedule>> future = _executorService.submit(new ELSModelStateExpander(_graph, schedule));
                    allFutures.add(future); //add future values to the list of future values
                    _openState.addClosedState(schedule); //add the explored schedule to another list
                }

                for (int i = 0; i < allFutures.size(); i++) { //for each of the futures
                    Future<List<Schedule>> future = allFutures.get(i); //retrieve the future object
                    try {
                        //main will block here when trying to get the real value of the future
                        newFoundStates = future.get(); //obtain a new set of states expanding from the most promising state
                        _scheduleCount +=newFoundStates.size(); //add the found schedules to the count

                        //add the newly found states into the priority queue only if their heuristic cost is smaller
                        // than baseline heuri  stic cost of the whole graph

                        newFoundStates.forEach(state -> {
                            if (_graph.getHeuristicCost() >= state.getHeuristicCost()) {
                                _openState.add(state);
                            }
                        });


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * check if the state is complete, meaning that the schedule contains
     * all of the nodes of the graph.
     * @param state
     * @return
     */
    private boolean checkCompleteSchedule(Schedule state) {
        Set<String> taskIdList = state.getTasks().keySet();
        Set<String> nodeIdListCopy = new TreeSet<>();
        nodeIdListCopy.addAll(_nodeIdList);
        nodeIdListCopy.removeAll(taskIdList);

        if (nodeIdListCopy.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private int calculateCostFunction(Schedule state) {
        return -1;
    }
}
