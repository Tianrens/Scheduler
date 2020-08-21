package group8.algorithm;

import group8.models.Node;
import group8.models.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GreedyHeuristic implements IHeuristic {

    @Override
    public int calculateEstimate(Schedule state, HashMap<String, Node> allNodes) {
        Map<String, int[]> stateNodes = new HashMap<String, int[]>();
        stateNodes.putAll(state.getTasks());
        ArrayList<String> stateKeys = new ArrayList<String>(state.getTasks().keySet());
        ArrayList<String> allKeys = new ArrayList<String>(allNodes.keySet());
        List<Node> statelessNodes = new ArrayList<>();
        List<Node> topology = new ArrayList<>();
        int[] stateProcessors = state.getProcessors().clone();
        Schedule currentState = state;
        TempTopologyFinder topologyFinder = new TempTopologyFinder();

        // obtain all nodes outside of the state
        for (String nodeId: allKeys) {
            if(!stateKeys.contains(nodeId)){
                statelessNodes.add(allNodes.get(nodeId));
            }
        }

        //Obtain topology for them
        topology = topologyFinder.generateTopology(statelessNodes);

        for (Node node:topology) {
            int earliestStartTime = 0;
            List<Node> parentList = node.getParentNodeList();
            //Keep list of start time costs available for each process
            int[] processSelection = new int[stateProcessors.length];

            for (int i = 0; i < stateProcessors.length; i++) {
                int startTime;
                int processorStartTime = stateProcessors[i];
                earliestStartTime = processorStartTime;

                for (Node parent:parentList) {
                    //if parent is not on the same processor, remote costs have to be considered
                    int[] parentDetails = stateNodes.get(parent.getId());

                    if(parentDetails[1] - 1 != i){
                        startTime = parentDetails[0] + parent.getCost() + parent.getEdgeList().get(node);
                        if(startTime < processorStartTime){
                            startTime = processorStartTime;
                        }
                    }else{
                        startTime = processorStartTime;
                    }

                    //checks if there any dependencies that might delay the scheduling of the task
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

            //Now processor index is chosen, add new start time with new node
            stateProcessors[index] = processSelection[index] + node.getCost();
            int[] newNodeDetails = {stateProcessors[index], index + 1};
            stateNodes.put(node.getId(), newNodeDetails);
        }

        // Find the latest ending time to output as the estimate
        int maxStartTime = stateProcessors[0];
        for(int i = 0; i < stateProcessors.length; i++){
            if(maxStartTime < stateProcessors[i]){
                maxStartTime = stateProcessors[i];
            }
        }
        return maxStartTime;
    }

}
