package group8.models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Graph {

    private HashMap<String, Node> _nodes = new HashMap<>();
    private List<Queue<Node>> _identicalNodes = new LinkedList<>();

    /**
     * Method used by GraphGenerator to add a new node into the Graph
     * @param newNode node to add to the graph
     */
    public void addNode(Node newNode){
        String nodeID = newNode.getId();
        _nodes.put(nodeID, newNode);
    }

    /**
     * Check for any identical node groupings in the Graph. If there is, create a mapping of it in this {@link Graph}
     * class, and return true, else false.
     * @return {@link boolean} on whether there is an identical node grouping.
     */
    public boolean checkForIdenticalNodes() {
        return false;
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

    /**
     * Get the identical node group associated and poll for the NEXT fixed order node.
     * @param identicalGroupId
     * @return {@link Node} The next node to be scheduled for this identical group of nodes (FIXED ORDER).
     */
    public Node getFixedOrderNode(int identicalGroupId) {
        return _identicalNodes.get(identicalGroupId).poll();
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
