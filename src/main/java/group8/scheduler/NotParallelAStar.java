package group8.scheduler;

import group8.algorithm.ELSModelStateExpander;
import group8.algorithm.SimpleHeuristic;
import group8.cli.AppConfigException;
import group8.models.*;

import java.util.*;

/**
 * This class is currently only used for testing purposes
 */
public class NotParallelAStar implements IScheduler {
    private PriorityQueue<Schedule> _openState;
    private List<Schedule> _closedState = new ArrayList<>();
    private Graph _graph;
    private HashMap<String, Node> _allNodesOfGraph;
    private Set<String> _nodeIdList;
    private int scheduleCount = 0;

    /**
     * This method is an implementation of the A* algorithm
     * @param graph
     * @return
     * @throws AppConfigException
     */
    @Override
    public Schedule generateValidSchedule(Graph graph) throws AppConfigException {
        _graph = graph;
        _openState = new PriorityQueue<>(new ScheduleComparator(graph));
        _allNodesOfGraph = _graph.getAllNodes();
        _nodeIdList = _allNodesOfGraph.keySet();

        //initialises the helper classes as objects to use their methods
        Schedule schedule = new Schedule();
        ELSModelStateExpander elsModelStateExpander = new ELSModelStateExpander(_graph);
        List<Schedule> newFoundStates;
        _openState.add(schedule); //add the empty schedule to get the algorithm started
        scheduleCount++;

        //continue with the algorithm while there are still states in the priority queue
        while (!_openState.isEmpty()) {
            schedule = _openState.poll(); //pop out the most promising state

            //run checkCompleteSchedule helper method to check if state is complete,
            //meaning that the schedule is valid
            if (checkCompleteSchedule(schedule)) {
                System.out.println(scheduleCount);
                return schedule;
            }

            //obtain a new set of states expanding from the most promising state
            newFoundStates = elsModelStateExpander.getNewStates(schedule);
            scheduleCount+=newFoundStates.size();
            _closedState.add(schedule);


            //add the newly found states into thee priority queue
            newFoundStates.forEach(state -> _openState.add(state));
        }
        return null;
    }

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