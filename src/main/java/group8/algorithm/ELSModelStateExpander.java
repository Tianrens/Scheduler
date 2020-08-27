package group8.algorithm;

import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Node;
import group8.models.Schedule;

import java.util.*;
import java.util.concurrent.Callable;

public class ELSModelStateExpander implements IStateExpander, Callable<List<Schedule>> {

    /**
     * This is a constant store of all nodes in the graph, This should never be written to!!
     */
    private HashMap<String,Node> _nodeList;
    private Graph _graph;
    private Schedule _state;
    private int _graphHeuristicCost;

    public ELSModelStateExpander(Graph graph, Schedule state){
        _nodeList=graph.getAllNodes();
        _graph = graph;
        _state = state;
        _graphHeuristicCost = graph.getHeuristicCost();
    }
    public ELSModelStateExpander(Graph graph) throws AppConfigException {
        _nodeList=graph.getAllNodes();
        _graph = graph;
        _state = new Schedule();
        _graphHeuristicCost = graph.getHeuristicCost();
    }

    @Override
    public List<Schedule> call() throws AppConfigException {
        return getNewStates(_state);
    }

    @Override
    public List<Schedule> getNewStates(Schedule state) throws AppConfigException {

        Map<String, int[]> scheduledNodes = state.getTasks();
        List<Schedule> newSchedules = new ArrayList<>();
        int[] processors = state.getProcessors();

        List<Integer> addedIdenticalIds = new ArrayList<>(); // All identified identical node groupings

        for(Node node : _nodeList.values()){
            // If schedule contains node then it has already been assigned.
            if(scheduledNodes.containsKey(node.getId())) {
                continue;
            }

            // If the identical group has already been assigned
            if (addedIdenticalIds.contains(node.getIdenticalNodeId())) {
                continue;
            }

            // Skip nodes associated with the identical group next time around
            if (node.getIdenticalNodeId() != -1) {
                if (! addedIdenticalIds.contains(node.getIdenticalNodeId())) {
                    addedIdenticalIds.add(node.getIdenticalNodeId());
                }
            }

            //checks for duplicate states, where a node sis assigned to an empty process
            boolean emptyAssign = false;
            for(int i = 0 ; i < processors.length ; i++) {
                if (node.getIdenticalNodeId() != -1) {
                    node = _graph.getFixedOrderNode(node.getIdenticalNodeId()); // will always schedule all nodes no matter what
                }

                // Skip if the node from the identical group has already been assigned.
                if (scheduledNodes.containsKey(node.getId())) {
                    continue;
                }

                int[] newProcessors = makeProcessorList(processors);
                Map<String, int[]> newScheduledNodes = new HashMap<>();
                int[] nodeInfo = new int[2];

                if(!emptyAssign && newProcessors[i]==-1){
                    emptyAssign=true;
                    newProcessors[i]=0;
                }else if(emptyAssign && newProcessors[i]==-1){
                    //This might need reconsideration, because I dont think there will be a case where after
                    // reading an empty process will we come across a process that is not empty
                    continue;
                }

                if (node.getParentNodeList().size() == 0) {
                    nodeInfo[0] = newProcessors[i];
                    nodeInfo[1]=i;
                    newProcessors[i]=newProcessors[i]+node.getCost();

                    newScheduledNodes.putAll(scheduledNodes);
                    newScheduledNodes.put(node.getId(), nodeInfo);

                    //Only if a schedule has a lower heuristic than the baseline graph heuristic
                    //we add it to the new schedules
                    Schedule schedule = assignSchedule(newProcessors,newScheduledNodes);
                    if (schedule.getHeuristicCost() < _graphHeuristicCost) {
                        newSchedules.add(schedule);
                    }

                } else if (checkParents(node.getParentNodeList(),scheduledNodes)) {
                    int startTime;
                    int earliestStartTime = 0;

                    for(Node parent : node.getParentNodeList()) {
                        if(scheduledNodes.get(parent.getId())[1]!=i){
                            startTime = parent.getEdgeList().get(node)+parent.getCost()+scheduledNodes.get(parent.getId())[0];
                            if(startTime < processors[i]){
                                startTime = processors[i];
                            }
                        }else {
                            startTime = processors[i];
                        }

                        if(startTime>earliestStartTime){
                            earliestStartTime=startTime;
                        }
                    }

                    nodeInfo[0] = earliestStartTime;
                    nodeInfo[1]=i;
                    newProcessors[i] = earliestStartTime + node.getCost();
                    newScheduledNodes.putAll(scheduledNodes);
                    newScheduledNodes.put(node.getId(), nodeInfo);

                    //Only if a schedule has a lower heuristic than the baseline graph heuristic
                    //we add it to the new schedules
                    Schedule schedule = assignSchedule(newProcessors,newScheduledNodes);
                    if (schedule.getHeuristicCost() < _graphHeuristicCost) {
                        newSchedules.add(schedule);
                    }
                }
            }
        }
        return newSchedules;
    }

    /**
     * helper method to check if all parents have been assigned
     * @param parentList
     * @return
     */
    private boolean checkParents(List<Node> parentList, Map<String,int[]> scheduledNodes){

        //Check parents one by one
        for (Node pNode: parentList) {
            if(scheduledNodes.get(pNode.getId())==null){
                return false;
            }
        }

        return true;
    }

    private int[] makeProcessorList(int[] processors){
        int[] newProcessors = new int[processors.length];

        for (int i =0 ; i< processors.length ;i++){
            newProcessors[i]=processors[i];
        }
        return newProcessors;

    }

    /**
     * Helper method for generating a ProcessorSet
     * @param numOfProcessors
     * @param scheduledNodes
     * @return
     */
    private Set<Set<List<String>>> makeProcessorSet(int numOfProcessors, Map<String, int[]> scheduledNodes){

        Set<Set<List<String>>> newSet = new HashSet<>();

/*
        Set<String> scheduledNodeIds = scheduledNodes.keySet();

        // scan through the nodelist per processor to find that processors nodes
        for (int i = 0; i < numOfProcessors; i++) {
            Set<List<String>> newProcessorSet = new HashSet<>();

            for (String nodeId:scheduledNodeIds) {

                int nodeProcessor = scheduledNodes.get(nodeId)[1];
                // If the node processor number matches
                if(i == nodeProcessor){

                    // add details to a list and add to this processor's set
                    List<String> nodeDetails = new ArrayList<>();
                    nodeDetails.add(nodeId);
                    nodeDetails.add(Integer.toString(nodeProcessor));
                    newProcessorSet.add(nodeDetails);
                }
            }

            // If this processor isn't empty, add its set to the encompassing set
            if(!newProcessorSet.isEmpty()){
                newSet.add(newProcessorSet);
            }
        }

 */

        
        return newSet;
    }


    private Schedule assignSchedule(int[] processors, Map<String, int[]> scheduledNodes) throws AppConfigException {

        Schedule newSchedule = new Schedule();
        IHeuristic goodHeuristic = new MaxThreeHeuristic();
        newSchedule.setTasks(scheduledNodes);
        newSchedule.setProcessors(processors);
        newSchedule.setHeuristicCost(goodHeuristic.calculateEstimate(newSchedule, _nodeList));
        newSchedule.setProcessorSet(makeProcessorSet(processors.length, scheduledNodes));
        newSchedule.setEarliestStartTime(newSchedule.calculateEarliestStartTime());

        return newSchedule;
    }

    public void setState(Schedule state) {
        _state = state;
    }
}
