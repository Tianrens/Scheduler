package group8.algorithm;

import group8.models.Node;
import group8.models.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class uses three heuristic calculations:
 * 1. Idle Time Heuristic
 * 2. Bottom-level Heuristic
 * 3. Data Ready Time Heuristic
 * From these three, the largest calculated estimate is used by the A* algorithm
 */
public class MaxThreeHeuristic implements IHeuristic{


    /**
     * This method performs all three calculations and picks the maximum of the three estimates
     * @param state
     * @param allNodes
     * @return
     */
    @Override
    public int calculateEstimate(Schedule state, HashMap<String, Node> allNodes) {
        return Math.max(calculateBlHeuristic(state, allNodes),Math.max(calculateIdleHeuristics(state, allNodes),calculateDrtHeuristic(state, allNodes)));
    }


    /**
     * This method calculates the idle heuristic based on (total node costs + total idle time)/no. of processors
     * @param state
     * @param allNodes
     * @return
     */
    private int calculateIdleHeuristics(Schedule state, HashMap<String, Node> allNodes){

        int[] sumProcessors = new int[state.getProcessors().length];
        int sumIdle = 0;

        // Find amount of time used in each processor by nodes
        for(String key : state.getTasks().keySet()){
            sumProcessors[state.getTasks().get(key)[1]]+=allNodes.get(key).getCost();
        }

        // subtract node times from processor times to find sum of all idle times
        for(int i = 0; i < state.getProcessors().length ; i++){


            if(state.getProcessors()[i]==-1){
                continue;
            }

            sumIdle+=state.getProcessors()[i]-sumProcessors[i];

        }

        // Add total node costs
        for(Node node : allNodes.values()){
            sumIdle+=node.getCost();
        }

        // divide the total by num of processors and return
        return sumIdle/state.getProcessors().length;
    }

    /**
     * This method calculates the Bottom-Level Heuristic by finding the largest
     * critical path from the nodes already in the state
     * @param state
     * @param allNodes
     * @return
     */
    private int calculateBlHeuristic(Schedule state, HashMap<String, Node> allNodes){

        // Iterate through every node already in the partial state and calculate their bottom levels
        // return only the largest bottom level / critical path
        int maxHeuristic = 0;
        int heuristic;
        for (String key : state.getTasks().keySet()){

            heuristic = state.getTasks().get(key)[0]+allNodes.get(key).getBottomLevel();

            if(heuristic>maxHeuristic){
                maxHeuristic=heuristic;
            }
        }

        return maxHeuristic;
    }


    /**
     *  This method calculates the Data Read Time Heuristic by going through all
     *  the unassigned nodes and finding max(earliest start time + bottom level)
     *  where the parent node dependancies are factored in when findin gthe earliest start times.
     * @param state
     * @param allNodes
     * @return
     */
    private int calculateDrtHeuristic(Schedule state, HashMap<String, Node> allNodes){
        int maxHeuristic = 0;

        for (Node node : allNodes.values()){
            //make sure node has not been assigned yet
            if(! state.getTasks().containsKey(node.getId())){
                //check if all its parents have been assigned or not
                if(checkParents(node.getParentNodeList(),state.getTasks())) {
                    int earliestProcessorStartTime = Integer.MAX_VALUE;

                    // for every processor, check all of this node's parents
                    for (int i = 0; i < state.getProcessors().length; i++) {
                        int earliestStartTime = 0;
                        for (Node parent : node.getParentNodeList()) {
                            int startTime = 0;

                            // If the parent is on another processor, factor in communication cost to
                            // find the required start time for this processor
                            if (state.getTasks().get(parent.getId())[1] != i) {
                                startTime = parent.getEdgeList().get(node) + parent.getCost() + state.getTasks().get(parent.getId())[0];

                                // Check if this remote time is lower than the current processor time, set it back
                                if (startTime < state.getProcessors()[i]) {
                                    startTime = state.getProcessors()[i];
                                }
                            } else {
                                // If parent is in same processor, start time is whenever the processor can start
                                startTime = state.getProcessors()[i];
                            }

                            // Max time between the parent nodes (tf(parent) + cost) for the node.
                            if (startTime > earliestStartTime) {
                                earliestStartTime = startTime;
                            }
                        }

                        // Find the time of the earliest starting processor
                        if (earliestProcessorStartTime > earliestStartTime) {
                            earliestProcessorStartTime = earliestStartTime;
                        }

                    }
                    //record largest value
                    if(maxHeuristic<earliestProcessorStartTime+node.getBottomLevel()){
                        maxHeuristic = earliestProcessorStartTime+node.getBottomLevel();
                    }
                }
            }
        }
        return maxHeuristic;
    }



    /**
     * helper method to check if all parents have been assigned
     * @param parentList
     * @return
     */
    private boolean checkParents(List<Node> parentList, Map<String,int[]> scheduledNodes){

        //Check parents one by one, if any aren't already scheduled, that means a parent is unassigned
        for (Node pNode: parentList) {
            if(scheduledNodes.get(pNode.getId())==null){
                return false;
            }
        }

        //otherwise all parents have been scheduled
        return true;
    }

}
