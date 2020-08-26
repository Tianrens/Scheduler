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

    public ELSModelStateExpander(Graph graph, Schedule state){
        _nodeList=graph.getAllNodes();
        _state = state;
    }
    public ELSModelStateExpander(Graph graph) throws AppConfigException {
        _nodeList=graph.getAllNodes();
        _state = new Schedule();
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

        List<String> identicalIds = new ArrayList<>(); // All identified identical node groupings

        for(Node node : _nodeList.values()){
            // If schedule contains node then it has already been assigned.
            if(scheduledNodes.containsKey(node.getId())) {
                continue;
            }

            // If identical group has already been assigned, then skip node to avoid duplication.
            if (identicalIds.contains(node.getIdenticalNodeId())) {
                continue;
            } else if (node.getIdenticalNodeId() != -1) {
                node = _graph.getFixedOrderNode(node.getIdenticalNodeId());
            }

            //if no parents then node has no dependencies and can be assigned to the schedule
            if(node.getParentNodeList().size()==0){
                noParentsSetUp(processors, node, scheduledNodes, newSchedules);
            } else if(checkParents(node.getParentNodeList(),scheduledNodes)){
                withParentsSetUp(processors, node, scheduledNodes, newSchedules);
            }

        }
        return newSchedules;
    }

    private void noParentsSetUp(int[] processors, Node node, Map<String, int[]> scheduledNodes, List<Schedule> newSchedules) throws AppConfigException {
        boolean emptyAssign = false; //checks for duplicate states, where a node sis assigned to an empty process

        for(int i = 0 ; i < processors.length ; i++) {
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

            nodeInfo[0] = newProcessors[i];
            nodeInfo[1]=i;
            newProcessors[i]=newProcessors[i]+node.getCost();

            newScheduledNodes.putAll(scheduledNodes);
            newScheduledNodes.put(node.getId(), nodeInfo);

            newSchedules.add(assignSchedule(newProcessors,newScheduledNodes));
        }
    }

    private void withParentsSetUp(int[] processors, Node node, Map<String, int[]> scheduledNodes, List<Schedule> newSchedules) throws AppConfigException {
        //checks for duplicate states, where a node sis assigned to an empty process
        boolean emptyAssign = false;

        for(int i = 0 ; i < processors.length ; i++){

            int[] newProcessors = makeProcessorList(processors);
            Map<String, int[]> newScheduledNodes = new HashMap<>();
            int[] nodeInfo = new int[2];

            if(!emptyAssign && processors[i]==-1){
                emptyAssign=true;
                newProcessors[i]=0;

            }else if(emptyAssign && processors[i]==-1){
                //This might need reconsideration, because I dont think there will be a case where after
                // reading an empty process will we come across a process that is not empty
                continue;
            }

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

            newSchedules.add(assignSchedule(newProcessors,newScheduledNodes));
        }
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


    private Schedule assignSchedule(int[] processors, Map<String, int[]> scheduledNodes) throws AppConfigException {

        Schedule newSchdule = new Schedule();
        IHeuristic goodHeuristic = new MaxThreeHeuristic();
        newSchdule.setTasks(scheduledNodes);
        newSchdule.setProcessors(processors);
        newSchdule.setHeuristicCost(goodHeuristic.calculateEstimate(newSchdule, _nodeList));

        return newSchdule;
    }
}
