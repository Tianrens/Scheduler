package group8.scheduler;

import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Node;
import group8.models.ProcessorException;
import group8.models.Schedule;
import group8.scheduler.IScheduler;

import java.util.*;

public class AStarScheduler implements IScheduler {
    private PriorityQueue<Schedule> _openState = new PriorityQueue<>();
    private List<Schedule> _closedState = new ArrayList<>();
    private Graph _graph;

    @Override
    public Schedule generateValidSchedule(Graph graph) throws ProcessorException, AppConfigException {

        return null;
    }

    private boolean checkCompleteSchedule(Schedule state) {
        HashMap<String, Node> allNodesOfGraph = _graph.getAllNodes();
        Set<String> nodeIdList = allNodesOfGraph.keySet();

        Set<String> taskIdList = state.getTasks().keySet();
        taskIdList.removeAll(nodeIdList);

        if (taskIdList.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    private int calculateCostFunction(Schedule state) {
        return -1;
    }
}
