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
    private IHeuristic _heuristic;


    /**
     * Create a state Expander
     * @param graph
     * @param heuristic Dependency inject a certain heuristic calculator
     * @throws AppConfigException
     */
    public ELSModelStateExpander(Graph graph, IHeuristic heuristic) throws AppConfigException {
        _nodeList=graph.getAllNodes();
        _graph = graph;
        _state = new Schedule();
        _graphHeuristicCost = graph.getHeuristicCost();
        _heuristic = heuristic;
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
        List<Node> freeTasks = _graph.getAllNodes().values().stream()
                .filter(n -> !state.getTasks().containsKey(n.getId()))
                .filter(n -> checkParents(n.getParentNodeList(), state.getTasks()))
                .collect(Collectors.toList());

        if (freeTasks.size()>2 && checkFreeTasksForFixedOrder(freeTasks, state)) {
            return assignFixOrderTasks(state,freeTasks,taskSortingFixOrder(freeTasks, state));
        }

        /* END OF FORK AND JOIN */

        for(Node node : _nodeList.values()){
            // If schedule contains node then it has already been assigned.
            if(scheduledNodes.containsKey(node.getId())) {
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

            // Now do the actual expanding of the current state s (create s+1 schedules)
            expandToAllProcessors(_state, node, newSchedules);

        }
        return newSchedules; //return the list of possible next states
    }

    /**
     * Given a state and a node to add, add to the newSchedules list all new possible states.
     * The list of states is also trimed by the upper bound heuristic cost calculated for the graph itself
     * @param state
     * @param node
     * @param newSchedules
     * @throws AppConfigException
     */
    private void expandToAllProcessors(Schedule state, Node node, List<Schedule> newSchedules) throws AppConfigException {
        //checks for duplicate states, where a node is assigned to an empty process
        boolean emptyAssign = false;
        int[] processors = state.getProcessors();
        Map<String,int[]> scheduledNodes = state.getTasks();


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
                        if(startTime < processors[i]){  //if the processor start time is more than the remote cost calculation, ignore remote cost
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
        newSchedule.setTasks(scheduledNodes);
        newSchedule.setProcessors(processors);
        newSchedule.setHeuristicCost(_heuristic.calculateEstimate(newSchedule, _nodeList));
        newSchedule.setEarliestStartTime(newSchedule.calculateEarliestStartTime());

        return newSchedule;
    }

    /**
     * sets the schedule state for the generator to expand on
     * @param state
     */
    public void setState(Schedule state) {
        _state = state;
    }

    /**
     *  Given a list of free nodes, sort the list according to increaseing DRT time given a schedule state, then connect each neighbouring node.
     *  Then disconnect any neighbours that have increaseing outgoing edge costs according to the same order.
     * @param freeTasks
     * @param state
     * @return
     */
    private HashMap<Node,Node> taskSortingFixOrder(List<Node> freeTasks, Schedule state) {

        // First entry is destination, second entry is the src
        HashMap<Node,Node> fixedOrder = new HashMap<Node,Node>();
        Map<Node, Integer> freeTasksDRTCosts = drt(state, freeTasks);

        //Sort according to DRT
        Collections.sort(freeTasks,Comparator.comparing((Node n) ->freeTasksDRTCosts.get(n)));

        //Loops through lists and adds all connections to the hashmap
        for(int i = 0 ; i < freeTasks.size()-1; i++){


            if(freeTasks.get(i).getEdgeList().isEmpty()){
                //if there are no outgoing edges there must also be no outgoing edges for the next node
                if(freeTasks.get(i+1).getEdgeList().isEmpty()){
                    fixedOrder.put(freeTasks.get(i + 1),freeTasks.get(i));
                }

            }else if(freeTasks.get(i+1).getEdgeList().isEmpty()){//if the next node has no out going edge then the value of the first node does not matter
                fixedOrder.put(freeTasks.get(i + 1),freeTasks.get(i));
            }else if(freeTasks.get(i).getEdgeList().values().iterator().next()>=freeTasks.get(i+1).getEdgeList().values().iterator().next()) {
                // add according to: destination, src
                fixedOrder.put(freeTasks.get(i + 1),freeTasks.get(i));
            }
        }
        return fixedOrder;
    }

    /**
     * Given a state, the list of freeNodes that can be added, and their fixed ordering, generate all schedules according to the specified fixed order
     * This function is in itself recursive.
     * @param state
     * @param freeNodes
     * @param fixedOrder
     * @return
     * @throws AppConfigException
     */
    private List<Schedule> assignFixOrderTasks(Schedule state, List<Node> freeNodes,HashMap<Node,Node> fixedOrder) throws AppConfigException{
        List<Schedule> newSchedules = new ArrayList<>();
        boolean allFreeNodesSchduled = true;

        //Scheudle all free nodes that are in the beginning of their fixed orderings
        for(Node node: freeNodes){

            if(state.getTasks().containsKey(node.getId())){
                continue;
            }else{
                allFreeNodesSchduled = false;
            }

            List<Schedule> fixOrderSchedules = new ArrayList<>();

            //if the freeNode does not have any fixed order edges approaching it
            if(!fixedOrder.containsKey(node)){
                expandToAllProcessors(state,node,fixOrderSchedules);
            }else if(state.getTasks().containsKey(fixedOrder.get(node).getId())){
                // if the previous node in the fixed order has been added then, the next node must also be able to be added
                expandToAllProcessors(state,node,fixOrderSchedules);
            }else{
                continue;
            }

            //if a node does not produce any new schdules i.e. heurisitc cost is too high, we move on.
            if(fixOrderSchedules.isEmpty()){
                continue;
            }

            //check if any new node has its parents freed up and can become a freeNode
            boolean newNodeAvaliable = true;
            //check all parents of the child of the node scheduled
            if(!node.getEdgeList().isEmpty()){
                for(Node parent : node.getEdgeList().keySet().iterator().next().getParentNodeList()){
                    //if a single parent is not scheduled then the node cannot be free
                    if(!fixOrderSchedules.get(0).getTasks().containsKey(parent.getId())){
                        newNodeAvaliable = false;
                    }
                }
            }else{
                newNodeAvaliable=false;
            }

            //if there is a new free node we need to check if it is able to be fix ordered
            if(newNodeAvaliable){
                List<Node> newFreeNodes = new ArrayList<>();
                newFreeNodes.addAll(freeNodes);
                newFreeNodes.remove(node);
                newFreeNodes.add(node.getEdgeList().keySet().iterator().next());

                for(Schedule s : fixOrderSchedules){
                    if(checkFreeTasksForFixedOrder(freeNodes,s)){
                        newSchedules.addAll(assignFixOrderTasks(s,newFreeNodes,taskSortingFixOrder(newFreeNodes,s)));
                    }else{
                        newSchedules.addAll(assignFixOrderTasks(s,freeNodes,fixedOrder));
                    }
                }
            }else{
                for(Schedule s : fixOrderSchedules){
                    newSchedules.addAll(assignFixOrderTasks(s,freeNodes,fixedOrder));
                }
            }
        }

        if(allFreeNodesSchduled){
            newSchedules.add(state);
        }
        return newSchedules;
    }



    private Map<Node, Integer> drt(Schedule state, List<Node> nodes) { // FOR ONE NODE, drt.
        Map<Node, Integer> result = new HashMap<>();

        int earliestProcessorStartTime;
        Node parent;
        for (Node node : nodes) {
            earliestProcessorStartTime = Integer.MAX_VALUE;

            if (node.getParentNodeList().size() == 0) {
                result.put(node, 0); // FREE TASK DOES NOT HAVE A PARENT.
                continue;
            }

            parent = node.getParentNodeList().get(0);

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

            result.put(node, earliestProcessorStartTime);
        }
        return result;
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
