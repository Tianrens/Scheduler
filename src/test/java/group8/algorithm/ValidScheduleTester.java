package group8.algorithm;

import group8.cli.AppConfig;
import group8.models.Graph;
import group8.models.Node;
import group8.models.Schedule;
import group8.parser.*;
import group8.scheduler.AStarScheduler;
import group8.scheduler.IScheduler;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

public class ValidScheduleTester {

    Graph _graph;
    HashMap<String,Node> _nodes;
    int _numProcessors = 4;

    @Test
    public void randomValidTest() throws Exception{
        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("Nodes_7_OutTree.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(8);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();
        _graph=graph;
        _nodes=graph.getAllNodes();
        Schedule schedule = scheduler.generateValidSchedule(graph);
        IDOTFileWriter outputBuilder = new DOTFileWriter();
        outputBuilder.writeOutputToConsole(schedule, graph);

        isValid(schedule);
    }

    @Test
    public void seriesParallelValidTest() throws Exception{
        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("Nodes_9_SeriesParallel.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(8);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();
        _graph=graph;
        _nodes=graph.getAllNodes();
        Schedule schedule = scheduler.generateValidSchedule(graph);
        IDOTFileWriter outputBuilder = new DOTFileWriter();
        outputBuilder.writeOutputToConsole(schedule, graph);

        isValid(schedule);
    }


    private void isValid(Schedule schedule){
        Map<String, int[]> tasks = schedule.getTasks();
        int[] processors = schedule.getProcessors();

        assertTrue(checkParents(tasks));
        assertTrue(checkProcessors(tasks, processors));
    }


    private boolean checkParents(Map<String, int[]> tasks){

        for(Map.Entry<String, int[]> entry : tasks.entrySet()){
            Node node = _nodes.get(entry.getKey());
            for(Node parent :node.getParentNodeList()){
                //checks if the start time is possible considering all of the nodes parents that are on different processors
                if(tasks.get(parent.getId())[1]!=entry.getValue()[1]) {
                    if (tasks.get(parent.getId())[0] + parent.getCost() + parent.getEdgeList().get(_nodes.get(entry.getKey())) > entry.getValue()[0]) {
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
            testProcessors[entry.getValue()[1]]+=node.getCost();

        }

        for(int i = 0 ; i< processors.length ; i++) {
            if(processors[i]==-1){
                processors[i]=0;
            }
            if(testProcessors[i]>processors[i]){
                return false;
            }
        }
        return true;
    }
}
