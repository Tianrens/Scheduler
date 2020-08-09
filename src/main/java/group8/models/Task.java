package group8.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The task class is a wrapper for the node class.
 * It has additional attributes as part of being a task.
 */
public class Task {

    private Processor _processor;
    private int _timeScheduled;
//    private HashMap<Task, Integer> _edgeList;
//    private List<Task> _parentTaskList;
    private int _cost;
    private String _id;
    private Node _node;

    private HashMap<Node, Integer> _edgeList;
    private List<Node> _parentNodeList;

    /**
     * Wrapping of the node is done in the constructor
     * @param node
     */
    public Task(Node node){
        _node = node;
//        _edgeList = new HashMap<Task, Integer>();
//        _parentTaskList = new ArrayList<Task>();

        _edgeList = node.getEdgeList();
        _parentNodeList = node.getParentNodeList();
        _cost = node.getCost();
        _id = node.getId();
    }

    //generic getters and setters below
    public int getTimeScheduled(){
        return _timeScheduled;
    }

    public void setTimeScheduled(int timeScheduled) {
        _timeScheduled = timeScheduled;
    }

    public Processor getProcessor() {
        return _processor;
    }

    public void setProcessor(Processor processor) {
        _processor = processor;
    }

    public int getCost() {
        return _cost;
    }

    private String getId() {
        return _id;
    }

    public HashMap<Node, Integer> getEdgeList() {
        return _edgeList;
    }

    public List<Node> getParentNodeList() {
        return _parentNodeList;
    }

    //    public HashMap<Task, Integer> getEdgeList() {
//        return _edgeList;
//    }
//
//    public List<Task> getParentTaskList() {
//        return _parentTaskList;
//    }

//    //1 way of setting task fields
//    //This way only works when all nodes have been turned into tasks
//    public void addParentTask(Task parentTask) {
//        _parentTaskList.add(parentTask);
//    }
//
//    public void addDestinationTask(Task destTask, int edgeWeight) {
//        _edgeList.put(destTask, edgeWeight);
//    }

}
