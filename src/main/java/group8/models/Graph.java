package group8.models;

import java.util.HashMap;
import java.util.List;

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

    /**
     * The two following method is for testing purposes only
     * @return
     */
    public Graph createGraph() {
        Graph graph = new Graph();
        return graph;
    }

    public void addData(List<String> graphData) {
        if (graphData.size() == 1) {
            /** Graph name */
        } else if (graphData.size() == 2) {
            this.addNode(new TaskNode(Integer.parseInt(graphData.get(1)), graphData.get(0)));
        } else if (graphData.size() == 3) {
            // The .dot file input can be assumed to be sequential. Therefore all nodes
            // will have been previously initialised before they are referenced as an edge
            TaskNode src = this.getNode(graphData.get(0));
            TaskNode dst = this.getNode(graphData.get(1));
            src.addDestination(dst, Integer.parseInt(graphData.get(2)));
            dst.addParentNode(src);
        }
    }
}
