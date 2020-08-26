package group8.models;

import java.util.*;

public class Graph {

    private HashMap<String, Node> _nodes = new HashMap<>();
    private List<Queue<Node>> _identicalNodes = new LinkedList<>(); // Index represents the identical group id

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
    public boolean setUpForIdenticalNodes() {
        boolean result = false;
        List<Node> skip = new ArrayList<>();
        int id = 0;
        for (Node node : _nodes.values()) {
            if (skip.contains(node)) { // Node has already been identified as an identical
                continue;
            }

            Queue<Node> queue = new LinkedList<>();
            Set<Node> intersection = node.getChildren();
            intersection.retainAll(node.getParentNodeList()); // Intersection of parents and children: same parents and children

            if (intersection.contains(node) && intersection.size() > 1) { // Potentially has one other node that is the same as this node being compared
                for (Node identical : intersection){
                    if (node.getCost() == identical.getCost() && node.getEdgeList().equals(identical.getEdgeList())) { // Edges and cost equal
                        queue.add(identical);
                        identical.setIdenticalNodeId(id);
                        skip.add(identical);
                    }
                }

                if (! queue.isEmpty()) { // If there were identical nodes to the node being compared, then add it to the queue
                    result = true;
                    queue.add(node);
                    node.setIdenticalNodeId(id);
                }
            }
            _identicalNodes.add(id, queue);
            id++;
        }

        return result;
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
