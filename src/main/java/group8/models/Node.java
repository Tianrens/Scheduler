package group8.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is a model of each individual node in the graph
 */
public class Node {

    private HashMap<Node, Integer> _edgeList;
    private List<Node> _parentNodeList;
    private int _cost;
    private String _id;
    private Processor _processor;
    private int _timeScheduled;

    /**
     * Constructs a new node with empty _edgeList and
     * _parentNodeList for later population. _cost and _id
     * is supplied upon construction for identification purposes.
     * @param cost
     * @param id
     */
    public Node(int cost, String id) {
        _edgeList = new HashMap<Node, Integer>();
        _parentNodeList = new ArrayList<Node>();
        _cost = cost;
        _id = id;
    }

    /**
     * This method adds destination nodes to a given node
     * so that there is a record of possible outward paths
     * @param destNode
     * @param edgeWeight
     */
    public void addDestination(Node destNode, int edgeWeight) {
        _edgeList.put(destNode, edgeWeight);
    }

    /**
     * This method adds a parent node to a given node
     * so that dependencies can be checked easily
     * @param parentNode
     */
    public void addParentNode(Node parentNode) {
        _parentNodeList.add(parentNode);
    }


    //below are generic getters and setters
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public List<Node> getParentNodeList() {
        return _parentNodeList;
    }

    public void setParentNodeList(List<Node> parentNodeList) {
        _parentNodeList = parentNodeList;
    }

    public HashMap<Node, Integer> getEdgeList() {
        return _edgeList;
    }

    public void setEdgeList(HashMap<Node, Integer> edgeList) {
        _edgeList = edgeList;
    }

    public int getCost() {
        return _cost;
    }

    public Processor getProcessor() {
        return _processor;
    }

    public void setProcessor(Processor processor) {
        _processor = processor;
    }

    public int getTimeScheduled(){
        return _timeScheduled;
    }

    public void setTimeScheduled(int timeScheduled) {
        _timeScheduled = timeScheduled;
    }

}
