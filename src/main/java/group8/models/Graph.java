package group8.models;

import java.util.HashMap;

public class Graph {
    private HashMap<String, Node> _nodeMap = new HashMap<String, Node>();

    /**
     * Method used by GraphGenerator to add a new node into the Graph
     * @param newNode node to add to the graph
     */
    public void addGraphNode(Node newNode){
        String nodeID = newNode.getID();
        _nodeMap.put(nodeID, newNode);
    }
}
