package group8.scheduler;

import group8.models.Graph;
import group8.models.TaskNode;

import java.util.List;

/**
 * interface for Topology generation algorithms
 */
public interface ITopologyFinder {

    List<TaskNode> generateTopology(Graph graph);
}
