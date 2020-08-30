package group8.algorithm;
import group8.cli.AppConfig;
import group8.models.Graph;
import group8.models.Schedule;
import group8.parser.*;
import group8.scheduler.AStarScheduler;
import group8.scheduler.IScheduler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.File;

import static org.junit.Assert.assertEquals;

public class SystemTests {

    private static ValidScheduleTester _validScheduleTester;

    @BeforeClass
    public static void setUpValidChecker() {
        _validScheduleTester = new ValidScheduleTester();
    }

    @Test (timeout = 1800000)
    public void InTreeTest1() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("graphs/Nodes_7_OutTree.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);
        _validScheduleTester.isValid(schedule);

        assertEquals(28, schedule.getEarliestStartTime());
    }

    @Test (timeout = 1800000)
    public void InTreeTest2() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("graphs/Nodes_7_OutTree.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(4);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);
        _validScheduleTester.isValid(schedule);

        assertEquals(22, schedule.getEarliestStartTime());
    }

    @Test (timeout = 1800000)
    public void OutTreeTest1() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("graphs/Nodes_11_OutTree.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);
        _validScheduleTester.isValid(schedule);

        assertEquals(350, schedule.getEarliestStartTime());
    }

    @Test (timeout = 1800000)
    public void OutTreeTest2() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("graphs/Nodes_11_OutTree.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(4);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);
        _validScheduleTester.isValid(schedule);

        assertEquals(227, schedule.getEarliestStartTime());
    }

    @Test (timeout = 1800000)
    public void Random8TreeTest1() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("graphs/Nodes_8_Random.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);
        _validScheduleTester.isValid(schedule);

        assertEquals(581, schedule.getEarliestStartTime());
    }

    @Test (timeout = 1800000)
    public void Random8TreeTest2() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("graphs/Nodes_8_Random.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(4);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);
        _validScheduleTester.isValid(schedule);

        assertEquals(581, schedule.getEarliestStartTime());
    }

    @Test (timeout = 1800000)
    public void SeriesParallelTreeTest1() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("graphs/Nodes_9_SeriesParallel.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);
        _validScheduleTester.isValid(schedule);

        assertEquals(55, schedule.getEarliestStartTime());
    }

    @Test (timeout = 1800000)
    public void SeriesParallelTreeTest2() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("graphs/Nodes_9_SeriesParallel.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(4);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);
        _validScheduleTester.isValid(schedule);

        assertEquals(55, schedule.getEarliestStartTime());
    }

    @Test (timeout = 1800000)
    public void Random10TreeTest1() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("graphs/Nodes_10_Random.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);
        _validScheduleTester.isValid(schedule);

        assertEquals(50, schedule.getEarliestStartTime());
    }

    @Test (timeout = 1800000)
    public void Random10TreeTest2() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("graphs/Nodes_10_Random.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(4);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));


        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);
        _validScheduleTester.isValid(schedule);

        assertEquals(50, schedule.getEarliestStartTime());
    }

}
