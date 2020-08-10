package group8.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is a model of each individual node in the graph
 */
public class TaskNode {

    private HashMap<TaskNode, Integer> _edgeList;
    private List<TaskNode> _parentNodeList;
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
    public TaskNode(int cost, String id) {
        _edgeList = new HashMap<TaskNode, Integer>();
        _parentNodeList = new ArrayList<TaskNode>();
        _cost = cost;
        _id = id;
    }

    /**
     * This method adds destination nodes to a given node
     * so that there is a record of possible outward paths
     * @param destNode
     * @param edgeWeight
     */
    public void addDestination(TaskNode destNode, int edgeWeight) {
        _edgeList.put(destNode, edgeWeight);
    }

    /**
     * This method adds a parent node to a given node
     * so that dependencies can be checked easily
     * @param parentNode
     */
    public void addParentNode(TaskNode parentNode) {
        _parentNodeList.add(parentNode);
    }


    //below are generic getters and setters
    public String getId() {
        return _id;
    }

    public void setId(String id) {
        _id = id;
    }

    public List<TaskNode> getParentNodeList() {
        return _parentNodeList;
    }

    public void setParentNodeList(List<TaskNode> parentNodeList) {
        _parentNodeList = parentNodeList;
    }

    public HashMap<TaskNode, Integer> getEdgeList() {
        return _edgeList;
    }

    public void setEdgeList(HashMap<TaskNode, Integer> edgeList) {
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
