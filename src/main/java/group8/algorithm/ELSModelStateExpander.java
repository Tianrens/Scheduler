package group8.algorithm;

import group8.models.Graph;
import group8.models.Node;
import group8.models.Schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ELSModelStateExpander implements IStateExpander {

    /**
     * This is a constant store of all nodes in the graph, This should never be written to!!
     */
    private HashMap<String,Node> _nodeList;

    public ELSModelStateExpander(Graph graph){
        _nodeList=graph.getAllNodes();
    }


    @Override
    public List<Schedule> getNewStates(Schedule state){

        Map<String, int[]> scheduledNodes = state.get_nodes();
        List<Schedule> newSchedules = new ArrayList<>();
        int[] processors = state.get_processors();
        //loops through all nodes in the graph
        for(Map.Entry<String,Node> nodeEntry : _nodeList.entrySet()){

            //if schedule does not contain the node then the node has not been assigned yet
            if(!scheduledNodes.containsKey(nodeEntry.getKey())){
                List<Node> parentNodes = nodeEntry.getValue().getParentNodeList();

                //if no parents then node has no dependencies and can be assigned to the schedule
                if(parentNodes == null){
                    //checks for duplicate states, where a node sis assigned to an empty process
                    boolean emptyAssign = false;

                    for(int i = 0 ; i < processors.length ; i++) {
                        int[] newProcessors = processors;
                        Map<String, int[]> newScheduledNodes = new HashMap<>();
                        int[] nodeInfo = new int[2];

                        if(!emptyAssign && newProcessors[i]==-1){
                            emptyAssign=true;
                        }else if(emptyAssign && newProcessors[i]==-1){
                            //This might need reconsideration, because I dont think there will be a case where after
                            // reading an empty process will we come across a process that is not empty
                            continue;
                        }

                        nodeInfo[0] = newProcessors[i];
                        nodeInfo[1]=i;
                        newProcessors[i]=newProcessors[i]+nodeEntry.getValue().getCost();

                        newScheduledNodes.putAll(scheduledNodes);
                        newScheduledNodes.put(nodeEntry.getKey(), nodeInfo);

                        //TODO assignSchedule
                    }
                }else if(checkParents(parentNodes,scheduledNodes)){

                    //checks for duplicate states, where a node sis assigned to an empty process
                    boolean emptyAssign = false;

                    for(int i = 0 ; i < processors.length ; i++){

                        if(!emptyAssign && processors[i]==-1){
                            emptyAssign=true;
                        }else if(emptyAssign && processors[i]==-1){
                            //This might need reconsideration, because I dont think there will be a case where after
                            // reading an empty process will we come across a process that is not empty
                            continue;
                        }

                        int startTime;
                        int earliestStartTime = 0;

                        for(Node parent : parentNodes) {
                            if(scheduledNodes.get(parent.getId())[1]!=i){
                                startTime = parent.getEdgeList().get(parent)+parent.getCost()+scheduledNodes.get(parent.getId())[0];
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


                    }

                }

            }

        }
        return null;
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
            if(scheduledNodes.get(pNode.getId())!=null){
                allParentsAdded = false;
            }
        }

        //check if all parents have been added
        return allParentsAdded;
    }



    private Schedule assignSchedule(int[] processors, Map<String, int[]> scheduledNodes ){

        Schedule newSchdule = new Schedule();



        return null;
    }
}
