package group8.services;

import group8.models.Graph;
import group8.models.TaskNode;

import java.util.List;

public interface ITopologyFinder {

    List<TaskNode> generateTopology(Graph graph);
}
