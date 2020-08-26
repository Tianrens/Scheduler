package group8.models;

import java.util.HashMap;
import java.util.List;

public class Graph {

    //Contains all the nodes of the graph
    //Key is the nodeId and value is the node itself
    private HashMap<String, Node> _nodes = new HashMap<>();

    //heuristicCost is the graph's initial heuristic cost
    //Acts as a baseline for comparision for schedules that spawn from this graph
    //Larger schedule heuristic costs are discarded
    private int heuristicCost ;

    /**
     * Method used by GraphGenerator to add a new node into the Graph
     * @param newNode node to add to the graph
     */
    public void addNode(Node newNode){
        String nodeID = newNode.getId();
        _nodes.put(nodeID, newNode);
    }


    /**
     * Method used to obtain a specific node given its ID
     * @param nodeID the node ID
     * @return the node whose ID was given
     */

    public Node getNode(String nodeID){
        return _nodes.get(nodeID);
    }

    /**
     * Method returns all of the graph's nodes
     * @return Hashmap of all nodes
     */
    public HashMap<String, Node> getAllNodes(){
        return _nodes;
    }

    public int getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    /**
     * This method is used to bypass having to create a Node and adding it to graph manually
     * @param graphData
     */
    public void addData(List<String> graphData) {
        if (graphData.size() == 1) {
            /** Graph name */
        } else if (graphData.size() == 2) {
            this.addNode(new Node(Integer.parseInt(graphData.get(1)), graphData.get(0)));
        } else if (graphData.size() == 3) {
            // The .dot file input can be assumed to be sequential. Therefore all nodes
            // will have been previously initialised before they are referenced as an edge
            Node src = this.getNode(graphData.get(0));
            Node dst = this.getNode(graphData.get(1));
            src.addDestination(dst, Integer.parseInt(graphData.get(2)));
            dst.addParentNode(src);
        }
    }
}
