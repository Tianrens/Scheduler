package group8.services;

import group8.models.Graph;
import group8.models.Node;
import group8.models.Task;

import java.util.ArrayList;
import java.util.List;

public class TopologyFinder {
    private List<Node> _nodeList;
    private List<Node> _topology;

    /**
     * Method used to generate a topology from a given graph
     * @param graph graph to generate a topology from
     * @return the topology as a sequential list of Nodes
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
                    break;
                }

                //If node has parent, check if they're already added
                if(checkParents(parentNodes)){
                    removeList.add(node);
                    break;
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
    private void moveList(List<Node> removeList){
        for (Node node: removeList) {
            _nodeList.remove(node);
            _topology.add(node);
        }
    }

    /**
     * Helper function for checking if parent list of a node has all bee added to topology
     * @param parentList parent nodes to check
     * @return true if all parents already added, false if at least one parent is not already n the topology
     */
    private boolean checkParents(List<Node> parentList){
        int addedParents = 0;

        //Check parents one by one
        for (Node pNode: parentList) {
            if(_topology.contains(pNode)){
                addedParents++;
            }
        }

        //check if all parents have been added
        return addedParents == parentList.size();
    }

}
