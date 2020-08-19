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
public class SimpleHeuristic implements IHeuristic {

    /**
     * Method for calculating an estimate based on the sum of all unassigned node costs
     * @param state state we are calculating the estimate for
     * @param allNodes list of all nodes in the graph
     * @return the cost from the state to the end
     */
    @Override
    public int calculateEstimate(Schedule state, HashMap<String, Node> allNodes) {
        int totalCost = 0;
        //Map<String, int[]> stateNodes = state.get_nodes();
        ArrayList<String> stateKeys = new ArrayList<String>(state.getTasks().keySet());
        ArrayList<String> allKeys = new ArrayList<String>(allNodes.keySet());
        //List<Node> statelessNodes = new ArrayList<>();

        // Sum up the costs of all nodes not in the current state
        for (String nodeId: allKeys) {
            if(!stateKeys.contains(nodeId)){
                totalCost += allNodes.get(nodeId).getCost();
            }
        }

        return totalCost;
    }
}
