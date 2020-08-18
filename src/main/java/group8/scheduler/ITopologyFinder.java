package group8.scheduler;

import group8.models.Graph;
import group8.models.Node;

import java.util.List;

/**
 * interface for Topology generation algorithms
 */
public interface ITopologyFinder {

    List<Node> generateTopology(Graph graph);
}
