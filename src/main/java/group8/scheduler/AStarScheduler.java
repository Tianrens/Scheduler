package group8.scheduler;

import group8.algorithm.ELSModelStateExpander;
import group8.algorithm.SimpleHeuristic;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Node;
import group8.models.ProcessorException;
import group8.models.Schedule;

import java.util.*;

public class AStarScheduler implements IScheduler {
    private PriorityQueue<Schedule> _openState = new PriorityQueue<>();
    private List<Schedule> _closedState = new ArrayList<>();
    private Graph _graph;
    private Set<String> _nodeIdList;

    @Override
    public Schedule generateValidSchedule(Graph graph) throws ProcessorException, AppConfigException {
        _graph = graph;
        HashMap<String, Node> allNodesOfGraph = _graph.getAllNodes();
        _nodeIdList = allNodesOfGraph.keySet();

        Schedule schedule = new Schedule();
        ELSModelStateExpander elsModelStateExpander = new ELSModelStateExpander();
        SimpleHeuristic simpleHeuristic = new SimpleHeuristic();
        List<Schedule> newFoundStates;
        _openState.add(schedule);
        while (!_openState.isEmpty()) {
            newFoundStates = elsModelStateExpander.getNewStates(_openState.peek());
//            newFoundStates.forEach(state -> state.setHeuristicCost(simpleHeuristic.findHeuristic(state)));



            newFoundStates.forEach(state -> _openState.add(state));

            schedule = _openState.peek();
            if (checkCompleteSchedule(schedule)) {
                return schedule;
            }
            _closedState.add(_openState.poll());
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
