package group8.scheduler;

import group8.models.Graph;
import group8.models.Node;
import group8.models.Schedule;

import java.util.List;
import java.util.Map;

public class ValidScheduleTester {

    Graph _graph;
    List<Node> _nodes;



    public boolean isValid(Schedule schedule){
        Map<String, int[]> tasks = schedule.getTasks();
        int[] processors = schedule.getProcessors();
        
        return true;
    }
}
