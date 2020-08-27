package group8.algorithm;

import group8.models.Node;
import group8.models.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class implementing a simple heuristic that calculates an estimate cost directly from a state to
 * the final schedule
 */
public class SimpleTopHeuristic implements IHeuristic {

    /**
     * Method for calculating an estimate based on the sum of all unassigned node costs
     * @param state state we are calculating the estimate for
     * @param allNodes list of all nodes in the graph
     * @return the cost from the state to the end
     */
    @Override
    public double calculateEstimate(Schedule state, HashMap<String, Node> allNodes) {
        int totalCost = 0;
        Map<String, int[]> stateNodes = state.getTasks();
        ArrayList<String> stateKeys = new ArrayList<String>(state.getTasks().keySet());
        ArrayList<String> allKeys = new ArrayList<String>(allNodes.keySet());
        int[] stateProcessors = state.getProcessors();
        TempTopologyFinder topologyFinder = new TempTopologyFinder();
        List<Node> statelessNodes = new ArrayList<>();
        List<Node> topology;

        // obtain all nodes outside of the state
        for (String nodeId: allKeys) {
            if(!stateKeys.contains(nodeId)){
                statelessNodes.add(allNodes.get(nodeId));
            }
        }

        topology = topologyFinder.generateTopology(statelessNodes);

        //Find latest starting processor time
        int index = 0;
        int maxStartTime = stateProcessors[index];
        for(int i = 0; i < stateProcessors.length; i++){
            if(maxStartTime < stateProcessors[i]){
                maxStartTime = stateProcessors[i];
                index = i;
            }
        }

        totalCost += maxStartTime;
        // Sum up the costs of all nodes not in the current state
        for(Node task: topology){
                int startTime = totalCost;
                int currentStartTime = totalCost;
                List<Node> parentList = task.getParentNodeList();

                // If node is not independent, check if parent effects the start time
                if(parentList != null){
                    for (Node parent: parentList) {
                        int[] parentDetails = stateNodes.get(parent.getId());

                        // If parent isn't in the state then it can start asap
                        if(parentDetails == null){
                            currentStartTime = totalCost;

                        }else if(parentDetails[1] - 1 != index){
                            // If parent in other processor, factor in remote cost
                            currentStartTime = parent.getEdgeList().get(task) + parent.getCost() + parentDetails[0];

                        }else{
                            //parent in same process already, start asap
                            currentStartTime = totalCost;
                        }

                        // If current start time exceeds the one previously set, then set it as the new
                        if(startTime < currentStartTime){
                            startTime = currentStartTime;
                        }
                    }
                }

                totalCost = startTime + task.getCost();
        }

        return totalCost;
    }
}
