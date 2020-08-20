package group8.scheduler;

import group8.algorithm.ELSModelStateExpander;
import group8.algorithm.SimpleHeuristic;
import group8.cli.AppConfigException;
import group8.models.*;

import java.util.*;

public class AStarScheduler implements IScheduler {
    private PriorityQueue<Schedule> _openState = new PriorityQueue<>(new ScheduleComparator());
    private List<Schedule> _closedState = new ArrayList<>();
    private Graph _graph;
    private HashMap<String, Node> _allNodesOfGraph;
    private Set<String> _nodeIdList;

    @Override
    public Schedule generateValidSchedule(Graph graph) throws ProcessorException, AppConfigException {
        _graph = graph;
        _allNodesOfGraph = _graph.getAllNodes();
        _nodeIdList = _allNodesOfGraph.keySet();

        Schedule schedule = new Schedule();
        ELSModelStateExpander elsModelStateExpander = new ELSModelStateExpander();
        SimpleHeuristic simpleHeuristic = new SimpleHeuristic();
        List<Schedule> newFoundStates;
        _openState.add(schedule);
        while (!_openState.isEmpty()) {
            schedule = _openState.poll();

            if (checkCompleteSchedule(schedule)) {
                return schedule;
            }

            newFoundStates = elsModelStateExpander.getNewStates(schedule);
            newFoundStates.forEach(state -> state.setHeuristicCost(simpleHeuristic.calculateEstimate(state, _allNodesOfGraph)));
            _closedState.add(schedule);

            newFoundStates.forEach(state -> _openState.add(state));
        }
        return null;
    }

    private boolean checkCompleteSchedule(Schedule state) {
        Set<String> taskIdList = state.getTasks().keySet();
        Set<String> nodeIdList = _nodeIdList;
        nodeIdList.removeAll(taskIdList);

        if (nodeIdList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private int calculateCostFunction(Schedule state) {
        return -1;
    }
}
