package group8.algorithm;

import group8.models.Node;
import group8.models.Schedule;

import java.util.HashMap;

public class MaxThreeHeuristic implements IHeuristic{


    @Override
    public int calculateEstimate(Schedule state, HashMap<String, Node> allNodes) {
        return Math.max(calculateBlHeuristic(state, allNodes),Math.max(calculateIdleHeuristics(state, allNodes),calculateDrtHeuristic(state, allNodes)));
    }


    private int calculateIdleHeuristics(Schedule state, HashMap<String, Node> allNodes){

        //TODO Raymond
        return 0;
    }


    private int calculateBlHeuristic(Schedule state, HashMap<String, Node> allNodes){

        //TODO Jennifer
        return 0;
    }


    private int calculateDrtHeuristic(Schedule state, HashMap<String, Node> allNodes){

        
        return 0;
    }





}
