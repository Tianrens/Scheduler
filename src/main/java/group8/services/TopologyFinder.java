package group8.services;

import group8.models.Graph;
import group8.models.TaskNode;

import java.util.ArrayList;
import java.util.List;

public class TopologyFinder {

    //A list of all unassigned nodes in the graph
    private List<TaskNode> _nodeList;

    //A List of all nodes in a topological order
    private List<TaskNode> _topology;

    /**
     * Method used to generate a topology from a given graph
     * @param graph graph to generate a topology from
     * @return the topology as a sequential list of Nodes
     */
    public List<TaskNode> generateTopology(Graph graph){

        //add all of the nodes in Graph into a List
        _nodeList = new ArrayList<TaskNode>(graph.getAllNodes().values());
        _topology = new ArrayList<>();

        //Keep looping through until the nodeList is empty
        while(!_nodeList.isEmpty()){

            List<TaskNode> removeList = new ArrayList<>();
            for (TaskNode node: _nodeList) {
                List<TaskNode> parentNodes = node.getParentNodeList();

                //Check if node already has no parents
                if(parentNodes == null){
                    removeList.add(node);
                    continue;
                }
                //If node has parent, check if they're already added
                if(checkParents(parentNodes)){
                    removeList.add(node);
                    continue;
                }
            }

            //move any nodes that have been checked to be removable
            moveList(removeList);
        }

        return _topology;

    }

    /**
     * Helper function for moving nodes from Node List to topology list
     * @param removeList list of nodes to move
     */
    private void moveList(List<TaskNode> removeList){
        for (TaskNode node: removeList) {
            _nodeList.remove(node);
            _topology.add(node);
        }
    }

    /**
     * Helper function for checking if parent list of a node has all bee added to topology
     * @param parentList parent nodes to check
     * @return true if all parents already added, false if at least one parent is not already n the topology
     */
    private boolean checkParents(List<TaskNode> parentList){
        boolean allParentsAdded = true;

        //Check parents one by one
        for (TaskNode pNode: parentList) {
            if(_nodeList.contains(pNode)){
                allParentsAdded = false;
            }
        }

        //check if all parents have been added
        return allParentsAdded;
    }

}