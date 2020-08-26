package group8.models;

import java.util.*;
import java.util.stream.Collectors;

/**
 * This class is a model of each individual node in the graph
 */
public class Node {

    private int _cost;
    private String _id;
    private int _bottomLevel = -1;

    private int _identicalGroupId = -1;

    private HashMap<Node, Integer> _edgeList;
    private List<Node> _parentNodeList;

    // unused for main algorithm
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

    /**
     * Helper method for calculating the bottom level of a node
     * @param
     * @return
     */
    public int calculateBottomLevel(){

        int longestCriticalPath = 0;

        // for every of the nodes dependees/pointed to nodes, recursively descend the various possible paths
        // to find the longest, Critical path
        for(Map.Entry<Node, Integer> dst: this.getEdgeList().entrySet()){
            int currentPathLength =0;
            currentPathLength+=dst.getKey().calculateBottomLevel();
            if(longestCriticalPath<currentPathLength){
                longestCriticalPath=currentPathLength;
            }
        }

        _bottomLevel = longestCriticalPath + getCost();
        // return the critical path cost for the node
        return _bottomLevel;
    }


    //getters and setters for mandatory fields
    public String getId() {
        return _id;
    }

    public int getCost() {
        return _cost;
    }

    public int getBottomLevel(){return _bottomLevel;}

    public int getIdenticalNodeId() { return _identicalGroupId; }

    public void setIdenticalNodeId(int id) { _identicalGroupId = id; }


    //getters and setters for other fields
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

    // unused in main algorithm
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
