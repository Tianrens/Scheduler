package group8.algorithm;

import group8.models.Node;
import group8.models.Schedule;

import java.util.HashMap;

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
    public double calculateEstimate(Schedule state, HashMap<String, Node> allNodes) {

        int sum = 0;
        for(Node node : allNodes.values()){
            sum+=node.getCost();
        }
        return sum;
    }

}
