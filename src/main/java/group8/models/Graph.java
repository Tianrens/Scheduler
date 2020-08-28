package group8.models;

import java.util.*;

public class Graph {

    //Contains all the nodes of the graph
    //Key is the nodeId and value is the node itself
    private HashMap<String, Node> _nodes = new HashMap<>();

    private List<List<Node>> _identicalNodes = new ArrayList<>(); // Index represents the identical group id
    private List<Integer> _identicalNodeOrders = new ArrayList<>(); // Index represents the identical group id, value is the next order

    //heuristicCost is the graph's initial heuristic cost
    //Acts as a baseline for comparision for schedules that spawn from this graph
    //Larger schedule heuristic costs are discarded
    private double heuristicCost ;

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
            List<Node> queue = new ArrayList<>();

            if (skip.contains(node)) { // Skips if it is already part of a group. At all times it will only belong to ONE group
                continue;
            }

            for (Node nodeToCompare : _nodes.values()) { // Loops through all other nodes to compare

                if (node.getId().equals(nodeToCompare.getId())) {
                    continue;
                }

                if (node.getCost() == nodeToCompare.getCost()) { // Cost
                    if (node.getEdgeList().equals(nodeToCompare.getEdgeList())) { // Children
                        if (node.getParentNodeList().equals(nodeToCompare.getParentNodeList())) { // Parents
                            result = true;

                            if (node.getIdenticalNodeId() == -1) {
                                node.setIdenticalNodeId(id);
                                queue.add(node);
                                skip.add(node);
                            }
                            nodeToCompare.setIdenticalNodeId(id);
                            queue.add(nodeToCompare);
                            skip.add(nodeToCompare);
                        }
                    }
                }
            }
            if (! queue.isEmpty()) {
                _identicalNodes.add(id, queue);
                _identicalNodeOrders.add(id, 0); // Initialise to order 0
                id++;
            }
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
        Integer order = _identicalNodeOrders.get(identicalGroupId);
        Node node = _identicalNodes.get(identicalGroupId).get(order); // Get the node that is in the fixed order

        // Set next order for the identical nodes group
        if (order == _identicalNodes.get(identicalGroupId).size() - 1) {
            _identicalNodeOrders.set(identicalGroupId, 0); // wraps around
        } else {
            _identicalNodeOrders.set(identicalGroupId, order + 1);
        }

        return node;
    }

    public void setUpForkJoinOrdering(){

//        for(Node node : _nodes.values()){
//            List<Node> forkNodes = new ArrayList<>();
//            for(Node child :node.getEdgeList().keySet()){
//                if(child.getParentNodeList().size()==1){
//                    forkNodes.add(child);
//                }
//            }
//            System.out.println();
//            if(forkNodes.size()<2){
//                continue;
//            }else{
//
//                Collections.sort(forkNodes,Comparator.comparing((Node n) ->node.getEdgeList().get(n)));
//            }
//
//            for(int i = 1 ; i < forkNodes.size(); i++){
//                System.out.print(forkNodes.get(i).getId()+"\t");
//                //forkNodes.get(i-1).addDestination(forkNodes.get(i),0);
//                //forkNodes.get(i).addParentNode(forkNodes.get(i-1));
//                forkNodes.get(i-1).setFixedOrderEdge(forkNodes.get(i));
//            }
//        }
//        for(Node node : _nodes.values()) {
//            List<Node> joinNodes = new ArrayList<>();
//            for(Node child :node.getParentNodeList()){
//                if(child.getEdgeList().size()==1){
//                    joinNodes.add(child);
//                }
//            }
//
//            if(joinNodes.size()<2){
//                continue;
//            }else{
//                //orders by smallest outgoing edge first, so need ot check in the reverse
//                Collections.sort(joinNodes,Comparator.comparing((Node n) ->n.getEdgeList().get(node)+n.getCost()));
//            }
//
//            for(int i = 1; i <joinNodes.size()-1; i++){
//
//                if(joinNodes.get(i).getFixedOrderEdge()!=null){
//                    if(joinNodes.contains(joinNodes.get(i-1).getFixedOrderEdge())) {
//                        if (joinNodes.get(i).getEdgeList().get(node) < joinNodes.get(i).getFixedOrderEdge().getEdgeList().get(node)) {
//                            joinNodes.get(i).setFixedOrderEdge(null);
//                        }
//                    }
//                }else if(joinNodes.get(i).getFixedOrderEdge() == null){
//                    joinNodes.get(i).setFixedOrderEdge( joinNodes.get(i-1));
//                }
//
//            }
//        }

//        for(Node node : _nodes.values()) {
//            if(node.getFixedOrderEdge()!=null) {
//                Node zeroEdge = node.getFixedOrderEdge();
//                node.getEdgeList().put(zeroEdge, 0);
//                zeroEdge.addParentNode(node);
//                System.out.println(node.getId()+"->"+zeroEdge.getId());
//            }
//        }
    }

    public List<Node> getGroupOfIdenticalNodes(int identicalGroupId) {
        return _identicalNodes.get(identicalGroupId);
    }

    public double getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(double heuristicCost) {
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
