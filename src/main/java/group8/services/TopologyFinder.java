package group8.services;

import group8.models.Graph;
import group8.models.Node;
import group8.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TopologyFinder {

    public List<Node> generateTopology(Graph graph){
        int addedCount = 0;
        //add all of the nodes in Graph into a List
        List<Node> nodeList = new ArrayList<Node>(graph.getAllNodes().values());
        List<Node> topology = new ArrayList<>();

        while(!nodeList.isEmpty()){
            for (Node node: nodeList) {

            }
        }


        return topology;
    }

}
