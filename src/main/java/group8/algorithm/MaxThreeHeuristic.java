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

        int[] processors = state.getProcessors();
        int[] sumProcessors = new int[processors.length];
        int sumIdle = 0;
        int maxP = 0;

        // Find amount of time used in each processor by nodes
        for(Map.Entry<String, int[]> entry : state.getTasks().entrySet()){
            sumProcessors[entry.getValue()[1]]+=allNodes.get(entry.getKey()).getCost();
        }

        // subtract node times from processor times to find sum of all dile times
        for(int i = 0; i < processors.length ; i ++){


            if(processors[i]==-1){
                continue;
            }

            if(processors[i]>maxP){
                maxP=processors[i];
            }
            sumIdle+=processors[i]-sumProcessors[i];

        }

        //sumIdle+=maxP*processors.length;


        // Add total node costs
        for(Node node : allNodes.values()){
            sumIdle+=node.getCost();
        }

        // divide the total by num of processors and return
        return sumIdle/processors.length;
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
        int heuristic = 0;
        for (Map.Entry<String, int[]> nodeEntry : state.getTasks().entrySet()){

            heuristic = nodeEntry.getValue()[0]+allNodes.get(nodeEntry.getKey()).getBottomLevel();

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
        List<Integer> maxList = new ArrayList<>();
        Map<String,int[]> assignedTasks = state.getTasks();
        int[] processors = state.getProcessors();
        int maxHeuristic = 0;

        for (Node node : allNodes.values()){
            //make sure node has not been assigned yet
            if(!assignedTasks.containsKey(node.getId())){
                //check if all its parents have been assigned or not
                if(checkParents(node.getParentNodeList(),state.getTasks())) {

                    int earliestProcessorStartTime = Integer.MAX_VALUE;

                    // for every processor, check all of this node's parents
                    for (int i = 0; i < processors.length; i++) {
                        int earliestStartTime = 0;
                        for (Node parent : node.getParentNodeList()) {
                            int startTime = 0;

                            // If the parent is on another processor, factor in communication cost to
                            // find the required start time for this processor
                            if (assignedTasks.get(parent.getId())[1] != i) {
                                startTime = parent.getEdgeList().get(node) + parent.getCost() + assignedTasks.get(parent.getId())[0];

                                // Check if this remote time is lower than the current processor time, set it back
                                if (startTime < processors[i]) {
                                    startTime = processors[i];
                                }
                            } else {
                                // If parent is in same processor, start time is whenever the processor can start
                                startTime = processors[i];
                            }

                            // Check if parent remote dependancies have effected this processors earliest start time
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
