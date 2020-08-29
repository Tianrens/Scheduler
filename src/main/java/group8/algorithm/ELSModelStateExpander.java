package group8.algorithm;

import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Node;
import group8.models.Schedule;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class ELSModelStateExpander implements IStateExpander, Callable<List<Schedule>> {

    /**
     * This is a constant store of all nodes in the graph, This should never be written to!!
     */
    private HashMap<String,Node> _nodeList;
    private Graph _graph;
    private Schedule _state;
    private double _graphHeuristicCost;

    private Queue<Node> _fixedOrder;

    public ELSModelStateExpander(Graph graph) throws AppConfigException {
        _nodeList=graph.getAllNodes();
        _graph = graph;
        _state = new Schedule();
        _graphHeuristicCost = graph.getHeuristicCost();
    }

    /**
     * Calling method used to invoke the state expansion to obtain
     * the list of new partial states
     * @return
     * @throws AppConfigException
     */
    @Override
    public List<Schedule> call() throws AppConfigException {
        return getNewStates(_state);
    }

    /**
     * Method for branching out from partial state by producing all possible
     * "child" schedules when a node is added
     * @param state
     * @return
     * @throws AppConfigException
     */
    @Override
    public List<Schedule> getNewStates(Schedule state) throws AppConfigException {

        Map<String, int[]> scheduledNodes = state.getTasks();
        List<Schedule> newSchedules = new ArrayList<>();
        int[] processors = state.getProcessors();
        List<Integer> addedIdenticalIds = new ArrayList<>(); // All identified identical node groupings
        List<Node> ignorefixedOrderNodes = new ArrayList<>();

        /* FORK AND JOIN */
//        List<Node> freeTasks = _graph.getAllNodes().values().stream()
//                .filter(n -> !state.getTasks().containsKey(n.getId()))
//                .filter(n -> checkParents(n.getParentNodeList(), state.getTasks()))
//                .collect(Collectors.toList());
//
//        if (checkFreeTasksForFixedOrder(freeTasks, state)) {
//            taskSortingFixOrder(freeTasks, state);
//        }

        /* END OF FORK AND JOIN */


        for(Node node : _nodeList.values()){
            // If schedule contains node then it has already been assigned.
            if(scheduledNodes.containsKey(node.getId())) {
                continue;
            }


            if(node.getFixedOrderParent() == null){

            }else if(!scheduledNodes.containsKey(node.getFixedOrderParent().getId())){
                continue;
            }

            // If the identical group has already been assigned
            if (addedIdenticalIds.contains(node.getIdenticalNodeId())) {
                continue;
            }
            //Pass this point, looking at nodes which have not been assigned

            // Skip nodes associated with the identical group next time around
            if (node.getIdenticalNodeId() != -1) {
                if (! addedIdenticalIds.contains(node.getIdenticalNodeId())) {
                    addedIdenticalIds.add(node.getIdenticalNodeId());
                }
            }

            //checks for duplicate states, where a node is assigned to an empty process
            boolean emptyAssign = false;

            // Try add node to every processor
            for(int i = 0 ; i < processors.length ; i++) {
                if (node.getIdenticalNodeId() != -1) {
                    node = _graph.getFixedOrderNode(node.getIdenticalNodeId()); // will always schedule all nodes no matter what
                }

                // Skip if the node from the identical group has already been assigned.
                if (scheduledNodes.containsKey(node.getId())) {
                    continue;
                }

                int[] newProcessors = processors.clone();

                if(!emptyAssign && newProcessors[i]==-1) {
                    emptyAssign=true;
                    newProcessors[i]=0;
                }else if (emptyAssign && newProcessors[i]==-1) {
                    //This might need reconsideration, because I dont think there will be a case where after
                    // reading an empty process will we come across a process that is not empty
                    continue;
                }

                // If node has no parents, just add into processor
                if (node.getParentNodeList().size() == 0) {
                    Map<String, int[]> newScheduledNodes = new HashMap<>();
                    int[] nodeInfo = new int[2];
                    nodeInfo[0] = newProcessors[i];
                    nodeInfo[1] = i;
                    newProcessors[i] = newProcessors[i] + node.getCost();

                    newScheduledNodes.putAll(scheduledNodes);
                    newScheduledNodes.put(node.getId(), nodeInfo);

                    //Only if a schedule has a lower heuristic than the baseline graph heuristic
                    //we add it to the new schedules
                    Schedule schedule = assignSchedule(newProcessors,newScheduledNodes);
                    if (schedule.getHeuristicCost() <= _graphHeuristicCost) {
                    newSchedules.add(schedule);
                    }

                } else if (checkParents(node.getParentNodeList(),scheduledNodes)) {
                    // Otherwise, if node has parents, take into account possible remote costs
                    Map<String, int[]> newScheduledNodes = new HashMap<>();
                    int[] nodeInfo = new int[2];
                    int startTime;
                    int earliestStartTime = 0;

                    for(Node parent : node.getParentNodeList()) {
                        if(scheduledNodes.get(parent.getId())[1]!=i){ //if parent is scheduled on a different processor
                            //Have to take into account remote cost
                            startTime = parent.getEdgeList().get(node)+parent.getCost()+scheduledNodes.get(parent.getId())[0];
                            if(startTime < processors[i]){ //if the processor start time is more than the remote cost calculation, ignore remote cost
                                startTime = processors[i];
                            }
                        }else { //if parent is scheduled on the same processor then start time is the start time of the processor
                            startTime = processors[i];
                        }

                        // Checks if there any dependencies that might delay the scheduling of the task
                        // For this one processor, which every parent. account for the latest costing parent.
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
                    if (schedule.getHeuristicCost() <= _graphHeuristicCost) {
                    newSchedules.add(schedule);
                    }
                }
            }
        }
        return newSchedules; //return the list of possible next states
    }

    /**
     * Helper method to check if all parents have been assigned
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

    /**
     * Helper method for creating a new schedule to assign the given attributes/fields
     * @param processors
     * @param scheduledNodes
     * @return
     * @throws AppConfigException
     */
    private Schedule assignSchedule(int[] processors, Map<String, int[]> scheduledNodes) throws AppConfigException {

        Schedule newSchedule = new Schedule();
        IHeuristic goodHeuristic = new MaxThreeHeuristic();
        newSchedule.setTasks(scheduledNodes);
        newSchedule.setProcessors(processors);
        newSchedule.setHeuristicCost(goodHeuristic.calculateEstimate(newSchedule, _nodeList));
        newSchedule.setEarliestStartTime(newSchedule.calculateEarliestStartTime());

        return newSchedule;
    }

    public void setState(Schedule state) {
        _state = state;
    }

    private void taskSortingFixOrder(List<Node> freeTasks, Schedule state) {
        // collections.sort the tasks by increasing drt. BUT if freetask doesnt have parent, set to zero
        // then break any ties with sorting according to decreaseing outedge costs. no outedge? set the cost to zero.
        // vertify that all free tasks are in decreaseing outedge cost order.
        // then set _fixedOrder to this ordering (NOTE IT IS A QUEUE).

        // THEN.... in another method or something
        // for all these free tasks, pop off the first one and assign across all processors like normal (back in main method)
        // keep doing that until another free task NOT of the list appears...then recheck using checkFreeTasksForFixedOrder
        // once this list clears, keep going.
    }

    private double drt(Schedule state, Node node) { // FOR ONE NODE, drt.
        int earliestProcessorStartTime = Integer.MAX_VALUE;

        Node parent = node.getParentNodeList().get(0);
        // for every processor, check all of this node's parents
        for (int i = 0; i < state.getProcessors().length; i++) {
            if ( i == state.getTasks().get(parent.getId())[1]) {
                continue; // IGNORE LOCAL, WE ASSUME ALL REMOTE COMMUNICATION FROM THE PARENT PROCESSOR Pp. !!!!! this line is different from before.
            }

            int earliestStartTime = 0;
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

            // Find the time of the earliest starting processor
            if (earliestProcessorStartTime > earliestStartTime) {
                earliestProcessorStartTime = earliestStartTime;
            }
        }
        return earliestProcessorStartTime;
    }

    private boolean checkFreeTasksForFixedOrder(List<Node> freeTasks, Schedule state) {
        Node child = null;
        int parentProcessor = -1;
        for (Node freeTask : freeTasks) {
            if (freeTask.getParentNodeList().size() > 1) {
                return false; // If even one free task has >1 parent, then these free tasks cannot be fixed ordered.
            }

            if (freeTask.getParentNodeList().size() == 1) { // ONE parent
                if (parentProcessor == -1) {
                    parentProcessor = state.getTasks().get(freeTask.getParentNodeList().get(0).getId())[1]; // Get parent processor
                } else if (parentProcessor != state.getTasks().get(freeTask.getParentNodeList().get(0).getId())[1]) {
                    return false; // Not all parent processors of all free tasks are allocated to the same processor.
                }
            }

            if (freeTask.getEdgeList().keySet().size() > 1) {
                return false; // If even one free task has >1 child, then these free tasks cannot be fixed ordered.
            }

            if (freeTask.getEdgeList().keySet().size() == 1) {
                if (child == null) {
                    child = freeTask.getEdgeList().keySet().iterator().next();
                } else if (child != freeTask.getEdgeList().keySet().iterator().next()) {
                    return false; // Not all child node is the same.
                }
            }
        }

        return true;
    }
}
