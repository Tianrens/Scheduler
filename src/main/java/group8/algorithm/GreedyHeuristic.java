package group8.algorithm;

import group8.models.Node;
import group8.models.Schedule;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The purpose of this class is to calculate the greedy heuristic of the provided schedule
 * The greedy implementation is finding the selecting earliest starting processor
 */
public class GreedyHeuristic implements IHeuristic {

    /**
     * Method used to calculate a heuristic for the schedule based on a greedy algorithm
     * Used to find a heuristic cost used for heuristic schedules pruning
     * Greedy rule: place node in the earliest starting processor as of current
     * @param state
     * @param allNodes
     * @return
     */
    @Override
    public double calculateEstimate(Schedule state, HashMap<String, Node> allNodes) {
        Map<String, int[]> stateNodes = new HashMap<>();
        stateNodes.putAll(state.getTasks()); // Get all nodes in the schedule. Put into a new map
        ArrayList<String> stateKeys = new ArrayList<>(state.getTasks().keySet()); // Keys of the tasks in schedule
        ArrayList<String> allKeys = new ArrayList<>(allNodes.keySet()); // All task keys
        List<Node> statelessNodes = new ArrayList<>(); // Nodes not in schedule
        List<Node> topology;
        int[] stateProcessors = state.getProcessors().clone(); // Get processors from the schedule.
        Schedule currentState = state;
        TempTopologyFinder topologyFinder = new TempTopologyFinder();

        // obtain all nodes outside of the state
        for (String nodeId: allKeys) {
            if(!stateKeys.contains(nodeId)){
                statelessNodes.add(allNodes.get(nodeId));
            }
        }

        // Create topology from unassigned nodes, then assign them one by one
        topology = topologyFinder.generateTopology(statelessNodes);
        for (Node node:topology) {
            int earliestStartTime = 0; // Earliest start time the node on all processes.
            List<Node> parentList = node.getParentNodeList(); // For Remote Costs.
            //Keep list of start time costs available for each process
            int[] processSelection = new int[stateProcessors.length]; // Store the costs of start time for this singular node. Select lowest cost processor from this list, to add onto.

            for (int i = 0; i < stateProcessors.length; i++) { // For each processor in the state
                int startTime;

                int processorStartTime = stateProcessors[i];
                // Since processors initialised to -1, make sure to re-adjust their start times
                if(processorStartTime==-1){
                    processorStartTime=0;
                }

                earliestStartTime = processorStartTime;

                for (Node parent:parentList) {
                    //if parent is not on the same processor, remote costs have to be considered
                    int[] parentDetails = stateNodes.get(parent.getId());

                    // Get parents processor number.
                    if(parentDetails[1] != i){

                        // If parents processor does not match current processor calculate start time with remote cost factored in.
                        startTime = parentDetails[0] + parent.getCost() + parent.getEdgeList().get(node);
                        // If start time is smaller than current processor start time, ignore remote cost, as start time already longer than processor start time.
                        if(startTime < processorStartTime){
                            startTime = processorStartTime;
                        }
                    }else{
                        // If parent is on the processor. No need for remote cost.
                        startTime = processorStartTime;
                    }

                    // Checks if there any dependencies that might delay the scheduling of the task
                    // For this one processor, which every parent. account for the latest costing parent.
                    if(startTime>earliestStartTime){
                        earliestStartTime=startTime;
                    }
                }

                //Set earliest start time for this process
                processSelection[i] = earliestStartTime;
            }

            int index = 0;
            int min = processSelection[index];
            // Choose earliest start time process to allocate this node to
            for (int j = 0; j < stateProcessors.length; j++) {
                if(processSelection[j] < min){
                    min = processSelection[j];
                    index = j;
                }
            }

            //Now processor index is chosen, add new start time with new node.
            stateProcessors[index] = processSelection[index] + node.getCost();
            int[] newNodeDetails = {stateProcessors[index], index};
            stateNodes.put(node.getId(), newNodeDetails);
        }

        // Find the latest ending time to output as the estimate
        // Max start time of the processors.
        int maxStartTime = stateProcessors[0];
        for(int i = 0; i < stateProcessors.length; i++){
            if(maxStartTime < stateProcessors[i]){
                maxStartTime = stateProcessors[i];
            }
        }
        return maxStartTime;
    }

}
