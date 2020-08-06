package group8.models;

import java.util.HashMap;

public class Graph {
    private HashMap<String, Node> _nodeMap = new HashMap<String, Node>();

    /**
     * Method used by GraphGenerator to add a new node into the Graph
     * @param newNode node to add to the graph
     */

    public void addNode(Node newNode){
        String nodeID = newNode.getID();
        _nodeMap.put(nodeID, newNode);
    }

    /**
     * Method used to obtain a specific node given its ID
     * @param nodeID the node ID
     * @return the node whose ID was given
     */
    public Node getNode(String nodeID){
        return _nodeMap.get(nodeID);
    }
}
