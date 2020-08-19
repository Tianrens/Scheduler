package group8.scheduler;

import group8.models.Graph;
import group8.models.Node;

import java.util.ArrayList;
import java.util.List;

public class TopologyFinder implements  ITopologyFinder {

    //A list of all unassigned nodes in the graph
    private List<Node> _nodeList;

    //A List of all nodes in a topological order
    private List<Node> _topology;

    /**
     * Method used to generate a scheduler from a given graph
     * @param graph graph to generate a scheduler from
     * @return the scheduler as a sequential list of Nodes
     */
    public List<Node> generateTopology(Graph graph){

        //add all of the nodes in Graph into a List
        _nodeList = new ArrayList<Node>(graph.getAllNodes().values());
        _topology = new ArrayList<>();

        //Keep looping through until the nodeList is empty
        while(!_nodeList.isEmpty()){

            List<Node> removeList = new ArrayList<>();
            for (Node node: _nodeList) {
                List<Node> parentNodes = node.getParentNodeList();

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
     * Helper function for moving nodes from Node List to scheduler list
     * @param removeList list of nodes to move
     */
    private void moveList(List<Node> removeList){
        for (Node node: removeList) {
            _nodeList.remove(node);
            _topology.add(node);
        }
    }

    /**
     * Helper function for checking if parent list of a node has all bee added to scheduler
     * @param parentList parent nodes to check
     * @return true if all parents already added, false if at least one parent is not already n the scheduler
     */
    private boolean checkParents(List<Node> parentList){
        boolean allParentsAdded = true;

        //Check parents one by one
        for (Node pNode: parentList) {
            if(_nodeList.contains(pNode)){
                allParentsAdded = false;
            }
        }

        //check if all parents have been added
        return allParentsAdded;
    }

}
