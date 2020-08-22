package group8.algorithm;

import group8.models.Node;
import group8.models.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        //Lmao 
        //TODO Jennifer
        return 0;
    }


    /**
     * HI My name is David, nice to meet you :D
     */
    private int calculateDrtHeuristic(Schedule state, HashMap<String, Node> allNodes){
        List<Integer> maxList = new ArrayList<>();
        Map<String,int[]> assignedTasks = state.getTasks();
        int[] processors = state.getProcessors();
        int heuristic = 0;

        for (Node node : allNodes.values()){
            //make sure node has not been assigned yet
            if(!assignedTasks.containsKey(node.getId())){
                //check if all its parents have been assigned or not
                if(checkParents(node.getParentNodeList(),state.getTasks())) {
                    int earliestStartTime = 0;

                    for (int i = 0; i < processors.length; i++) {

                        for (Node parent : node.getParentNodeList()) {
                            int startTime = 0;
                            if (assignedTasks.get(parent.getId())[1] != i) {
                                startTime = parent.getEdgeList().get(node.getId()) + parent.getCost() + assignedTasks.get(parent.getId())[0];
                                if (startTime < processors[i]) {
                                    startTime = processors[i];
                                }
                            } else {
                                startTime = processors[i];
                            }

                            if (startTime > earliestStartTime) {
                                earliestStartTime = startTime;
                            }
                        }
                    }
                    //record largest value
                    int bl=calculateBottomLevel(node);
                    if(heuristic<earliestStartTime+bl){
                        heuristic = earliestStartTime+bl;
                    }
                }
            }
        }
        return heuristic;
    }

    /**
     * THIS SEEMS VERY SIMILAR TO WHAT JENNIFER NEEDS TO IMPLEMENT, IDK IF I JUST DID IT FOR HER OR NOT, BUT I NEED IT SO I WROTE IT
     * @param node
     * @return
     */
    private int calculateBottomLevel(Node node){

        int longestCriticalPath = 0;

        for(Map.Entry<Node, Integer> dst: node.getEdgeList().entrySet()){
            int currentPathLength = 0;
            currentPathLength+=dst.getKey().getCost();
            currentPathLength+=calculateBottomLevel(dst.getKey());
            if(longestCriticalPath<currentPathLength){
                longestCriticalPath=currentPathLength;
            }
        }
        return longestCriticalPath;
    }


    /**
     * helper method to check if all parents have been assigned
     * @param parentList
     * @return
     */
    private boolean checkParents(List<Node> parentList, Map<String,int[]> scheduledNodes){
        boolean allParentsAdded = true;

        //Check parents one by one
        for (Node pNode: parentList) {
            if(scheduledNodes.get(pNode.getId())==null){
                allParentsAdded = false;
            }
        }

        //check if all parents have been added
        return allParentsAdded;
    }





}
