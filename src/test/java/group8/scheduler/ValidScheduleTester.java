package group8.scheduler;

import group8.models.Graph;
import group8.models.Node;
import group8.models.Schedule;
import org.junit.Before;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValidScheduleTester {

    Graph _graph;
    HashMap<String,Node> _nodes;
    int _numProcessors = 4;

    @Before
    public void setUp(){

    }


    private boolean isValid(Schedule schedule){
        Map<String, int[]> tasks = schedule.getTasks();
        int[] processors = schedule.getProcessors();

        return true;
    }


    private boolean checkParents(Map<String, int[]> tasks){

        for(Map.Entry<String, int[]> entry : tasks.entrySet()){
            Node node = _nodes.get(entry.getKey());
            for(Node parent :node.getParentNodeList()){
                //checks if the start time is possible considering all of the nodes parents that are on different processors
                if(tasks.get(parent.getId())[1]!=entry.getValue()[1]) {
                    if (tasks.get(parent.getId())[0] + parent.getCost() + parent.getEdgeList().get(entry.getKey()) > entry.getValue()[0]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    private boolean checkProcessors(Map<String, int[]> tasks, int[] processors){

        int[] testProcessors = new int[processors.length];


        for(Map.Entry<String, int[]> entry : tasks.entrySet()){
            Node node = _nodes.get(entry.getKey());
            testProcessors[entry.getValue()[1]]+=

        }
        return true;
    }
}
