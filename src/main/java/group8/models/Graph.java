package group8.models;

import java.util.HashMap;

public class Graph {

    private HashMap<String, TaskNode> _nodes = new HashMap<>();


    /**
     * Method used by GraphGenerator to add a new node into the Graph
     * @param newNode node to add to the graph
     */


    public void addNode(TaskNode newNode){
        String nodeID = newNode.getId();
        _nodes.put(nodeID, newNode);
    }


    /**
     * Method used to obtain a specific node given its ID
     * @param nodeID the node ID
     * @return the node whose ID was given
     */

    public TaskNode getNode(String nodeID){
        return _nodes.get(nodeID);
    }

    /**
     * Method returns all of the graph's nodes
     * @return Hashmap of all nodes
     */
    public HashMap<String, TaskNode> getAllNodes(){
        return _nodes;
    }

}
