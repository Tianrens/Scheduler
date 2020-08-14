package group8.cli;

import group8.models.Graph;
import group8.models.ProcessorException;
import group8.models.Schedule;
import group8.models.TaskNode;
import group8.parser.DOTDataParser;
import group8.parser.GraphGenerator;
import group8.scheduler.IScheduler;
import group8.scheduler.OneProcessorScheduler;
import static group8.scheduler.SchedulerConstants.*;
import group8.scheduler.TopologyFinder;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class ScheduleTests {


    private String[] _cliInput = {"INPUT.dot", "P", "3"};
    private Graph _graph;


    @Before
    public void buildGraph(){


        List<String> a = new ArrayList<String>();
        a.add("a");
        a.add("3");
        List<String> b = new ArrayList<String>();
        b.add("b");
        b.add("2");
        List<String> c = new ArrayList<String>();
        c.add("c");
        c.add("1");
        List<String> d = new ArrayList<String>();
        d.add("d");
        d.add("3");
        List<String> e = new ArrayList<String>();
        e.add("e");
        e.add("2");
        List<String> f = new ArrayList<String>();
        f.add("f");
        f.add("1");

        List<String> edge1 = new ArrayList<String>();
        edge1.add("a");
        edge1.add("b");
        edge1.add("1");
        List<String> edge2 = new ArrayList<String>();
        edge2.add("b");
        edge2.add("c");
        edge2.add("1");
        List<String> edge3 = new ArrayList<String>();
        edge3.add("c");
        edge3.add("d");
        edge3.add("1");
        List<String> edge4 = new ArrayList<String>();
        edge4.add("d");
        edge4.add("e");
        edge4.add("1");
        List<String> edge5 = new ArrayList<String>();
        edge5.add("e");
        edge5.add("f");
        edge5.add("1");
        List<String> edge6 = new ArrayList<String>();
        edge6.add("b");
        edge6.add("f");
        edge6.add("1");


        GraphGenerator generator = new GraphGenerator(new DOTDataParser());
        generator.addData(a);
        generator.addData(b);
        generator.addData(c);
        generator.addData(d);
        generator.addData(e);
        generator.addData(f);
        generator.addData(edge1);
        generator.addData(edge2);
        generator.addData(edge3);
        generator.addData(edge4);
        generator.addData(edge5);
        generator.addData(edge6);
        _graph = generator.getGraph();



    }

    /**
     * tests that all processors are on the single process
     */
    @Test
    public void OneProcessorTest() throws ProcessorException {
        IScheduler scheduler = new OneProcessorScheduler(new TopologyFinder());
        Schedule schedule = scheduler.generateValidSchedule(_graph);

        List<TaskNode> taskNodeList = schedule.getTaskNodeList();
        for (TaskNode tn : taskNodeList){
            assertEquals(ONE_PROCESSOR_SCHEDULER_DEFAULT, tn.getProcessor().getId());
        }

    }

    /**
     * test the correct start times are calculated
     */
    @Test
    public void timeScheduledTest() throws ProcessorException {
        IScheduler scheduler = new OneProcessorScheduler(new TopologyFinder());
        Schedule schedule = scheduler.generateValidSchedule(_graph);

        List<TaskNode> list = schedule.getTaskNodeList();
        int cost = 0;
        char node = 'a';

        for(int i = 0 ; i < list.size() ; i++){
            assertEquals(cost,list.get(i).getTimeScheduled());
            cost+= list.get(i).getCost();
        }

    }

}
