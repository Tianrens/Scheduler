package group8.algorithm;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Schedule;
import group8.parser.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class IdenticalNodeDuplicationTests {
    private static IDOTFileWriter _writer = new DOTFileWriter();

    private static Graph _graph;
    private static IStateExpander _stateExpander;
    private static Schedule _schedule;
    private static List<String> _correctSchedules;

    private static Graph _graph2;
    private static IStateExpander _stateExpander2;
    private static Schedule _schedule2;
    private static List<String> _correctSchedules2;

    private static Graph _graph3;
    private static IStateExpander _stateExpander3;
    private static Schedule _schedule3;
    private static List<String> _correctSchedules3;

    private static Graph _graph4;
    private static Graph _graph5;
    private static Graph _graph6;

    @BeforeClass
    public static void setUp() throws AppConfigException {
        // First schedule test
        AppConfig.getInstance().setInputFile(new File(IdenticalNodeDuplicationTests.class.getResource("identical-nodes/identicalNodes.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(3);

        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        _graph = externalGraphGenerator.generate();

                Schedule empty = new Schedule();
        _graph.setHeuristicCost(Math.min(new SimpleHeuristic().calculateEstimate(empty, _graph.getAllNodes()),new GreedyHeuristic().calculateEstimate(empty, _graph.getAllNodes())));

        _stateExpander = new ELSModelStateExpander(_graph, new MaxThreeHeuristic());


        _schedule = new Schedule();
        _schedule.scheduleTask("a", 0, 0);
        _schedule.setProcessorStartTime(0, 2);


        //Second schedule test
        AppConfig.getInstance().setInputFile(new File(IdenticalNodeDuplicationTests.class.getResource("identical-nodes/identicalNodes2.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(1);

        IGraphGenerator externalGraphGenerator2 = new GraphExternalParserGenerator(new DOTPaypalParser());
        _graph2 = externalGraphGenerator2.generate();
        Schedule empty2 = new Schedule();
        _graph2.setHeuristicCost(Math.min(new SimpleHeuristic().calculateEstimate(empty2, _graph2.getAllNodes()),new GreedyHeuristic().calculateEstimate(empty2, _graph2.getAllNodes())));

        _stateExpander2 = new ELSModelStateExpander(_graph2, new MaxThreeHeuristic());

        _schedule2 = new Schedule();
        _schedule2.scheduleTask("a", 0, 0);
        _schedule2.setProcessorStartTime(0, 2);

        //Third schedule test
        AppConfig.getInstance().setInputFile(new File(IdenticalNodeDuplicationTests.class.getResource("identical-nodes/identicalNodes3.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(3);

        IGraphGenerator externalGraphGenerator3 = new GraphExternalParserGenerator(new DOTPaypalParser());
        _graph3 = externalGraphGenerator3.generate();
        Schedule empty3 = new Schedule();
        _graph3.setHeuristicCost(Math.min(new SimpleHeuristic().calculateEstimate(empty3, _graph3.getAllNodes()),new GreedyHeuristic().calculateEstimate(empty3, _graph3.getAllNodes())));

        _stateExpander3 = new ELSModelStateExpander(_graph3, new MaxThreeHeuristic());

        _schedule3 = new Schedule();
        _schedule3.scheduleTask("a", 0, 0);
        _schedule3.setProcessorStartTime(0, 2);
        _schedule3.scheduleTask("f", 0, 2);
        _schedule3.setProcessorStartTime(2, 4);

        //Set up the rest of the graphs to test whether identical groupings have been correctly identified.
        AppConfig.getInstance().setInputFile(new File(IdenticalNodeDuplicationTests.class.getResource("identical-nodes/identicalNodes4.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(3);
        IGraphGenerator externalGraphGenerator4 = new GraphExternalParserGenerator(new DOTPaypalParser());
        _graph4 = externalGraphGenerator4.generate();

        AppConfig.getInstance().setInputFile(new File(IdenticalNodeDuplicationTests.class.getResource("identical-nodes/identicalNodes5.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(3);
        IGraphGenerator externalGraphGenerator5 = new GraphExternalParserGenerator(new DOTPaypalParser());
        _graph5 = externalGraphGenerator5.generate();

        AppConfig.getInstance().setInputFile(new File(IdenticalNodeDuplicationTests.class.getResource("identical-nodes/identicalNodes6.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(3);
        IGraphGenerator externalGraphGenerator6 = new GraphExternalParserGenerator(new DOTPaypalParser());
        _graph6 = externalGraphGenerator6.generate();
    }

    @BeforeClass
    public static void setUpCorrectSchedules() {
        _correctSchedules = new ArrayList<>();
        _correctSchedules.add(
                "\ta [Weight=2, Start=0, Processor=1];" + System.lineSeparator() +
                "\tb [Weight=3, Start=2, Processor=1];" + System.lineSeparator()
        );

        _correctSchedules.add(
                        "\td [Weight=3, Start=4, Processor=2];" + System.lineSeparator() +
                        "\ta [Weight=2, Start=0, Processor=1];" + System.lineSeparator()
        );
    }

    @BeforeClass public static void setUpCorrectSchedules2() {
        _correctSchedules2 = new ArrayList<>();
        _correctSchedules2.add(
                "\ta [Weight=2, Start=0, Processor=1];" + System.lineSeparator() +
                "\tb [Weight=300, Start=2, Processor=1];" + System.lineSeparator()
        );

        _correctSchedules2.add(
                "\ta [Weight=2, Start=0, Processor=1];" + System.lineSeparator() +
                "\tc [Weight=10, Start=2, Processor=1];" + System.lineSeparator()
        );

        _correctSchedules2.add(
                "\ta [Weight=2, Start=0, Processor=1];" + System.lineSeparator() +
                "\tf [Weight=1, Start=2, Processor=1];" + System.lineSeparator()
        );

        // Remember that there is no wrapping around with identical nodes, if num of nodes > num of processors
    }

    @BeforeClass public static void setUpCorrectSchedules3() {
        _correctSchedules3 = new ArrayList<>();
        _correctSchedules3.add(
                "\ta [Weight=2, Start=0, Processor=1];" + System.lineSeparator() +
                "\tf [Weight=4, Start=0, Processor=3];" + System.lineSeparator() +
                "\tb [Weight=3, Start=2, Processor=1];" + System.lineSeparator()
        );
        _correctSchedules3.add(
                "\td [Weight=3, Start=4, Processor=2];" + System.lineSeparator() +
                "\ta [Weight=2, Start=0, Processor=1];" + System.lineSeparator() +
                "\tf [Weight=4, Start=0, Processor=3];" + System.lineSeparator()
        );
        _correctSchedules3.add(
                "\ta [Weight=2, Start=0, Processor=1];" + System.lineSeparator() +
                "\tf [Weight=4, Start=0, Processor=3];" + System.lineSeparator() +
                "\tb [Weight=3, Start=4, Processor=3];" + System.lineSeparator()
        );
    }

    @Test
    public void correctIdenticalGrouping() { // Different cost
        assertEquals(_graph.getNode("b").getIdenticalNodeId(), 0);
        assertEquals(_graph.getNode("d").getIdenticalNodeId(), 0);

        assertEquals(_graph.getNode("a").getIdenticalNodeId(), -1);
        assertEquals(_graph.getNode("c").getIdenticalNodeId(), -1);
        assertEquals(_graph.getNode("e").getIdenticalNodeId(), -1);
    }

    @Test
    public void correctIdenticalGrouping2() { // Different child
        assertEquals(_graph2.getNode("b").getIdenticalNodeId(), 0);
        assertEquals(_graph2.getNode("g").getIdenticalNodeId(), 0);

        assertEquals(_graph2.getNode("a").getIdenticalNodeId(), -1);
        assertEquals(_graph2.getNode("c").getIdenticalNodeId(), -1);
        assertEquals(_graph2.getNode("d").getIdenticalNodeId(), -1);
        assertEquals(_graph2.getNode("e").getIdenticalNodeId(), -1);
        assertEquals(_graph2.getNode("f").getIdenticalNodeId(), -1);
    }

    @Test
    public void correctIdenticalGrouping3() { // Different cost
        assertEquals(_graph3.getNode("b").getIdenticalNodeId(), 0);
        assertEquals(_graph3.getNode("d").getIdenticalNodeId(), 0);

        assertEquals(_graph3.getNode("a").getIdenticalNodeId(), -1);
        assertEquals(_graph3.getNode("c").getIdenticalNodeId(), -1);
        assertEquals(_graph3.getNode("e").getIdenticalNodeId(), -1);
        assertEquals(_graph3.getNode("f").getIdenticalNodeId(), -1);
    }

    @Test
    public void correctIdenticalGrouping4() { // Different parent
        assertEquals(_graph4.getNode("b").getIdenticalNodeId(), -1);
        assertEquals(_graph4.getNode("d").getIdenticalNodeId(), -1);
        assertEquals(_graph4.getNode("a").getIdenticalNodeId(), -1);
        assertEquals(_graph4.getNode("c").getIdenticalNodeId(), -1);
        assertEquals(_graph4.getNode("e").getIdenticalNodeId(), -1);
    }

    @Test
    public void correctIdenticalGrouping5() { // Different parent cost
        assertEquals(_graph5.getNode("b").getIdenticalNodeId(), 0);
        assertEquals(_graph5.getNode("d").getIdenticalNodeId(), 0);

        assertEquals(_graph5.getNode("a").getIdenticalNodeId(), -1);
        assertEquals(_graph5.getNode("c").getIdenticalNodeId(), -1);
        assertEquals(_graph5.getNode("e").getIdenticalNodeId(), -1);
    }

    @Test
    public void correctIdenticalGrouping6() { // Different child cost
        assertEquals(_graph6.getNode("b").getIdenticalNodeId(), -1);
        assertEquals(_graph6.getNode("d").getIdenticalNodeId(), -1);
        assertEquals(_graph6.getNode("a").getIdenticalNodeId(), -1);
        assertEquals(_graph6.getNode("c").getIdenticalNodeId(), -1);
        assertEquals(_graph6.getNode("e").getIdenticalNodeId(), -1);
    }

    @Test
    public void identicalTasks() throws AppConfigException {
        List<Schedule> schedules = _stateExpander.getNewStates(_schedule);
        for (Schedule s : schedules) {
            String scheduleResult = _writer.writeOutputToString(s,_graph);
            scheduleResult = scheduleResult.substring(24, scheduleResult.indexOf("->")-2);
            if (_correctSchedules.contains(scheduleResult)) {
                _correctSchedules.remove(scheduleResult);
            }
        }

        if (_correctSchedules.size() != 0) {
            fail();
        }
    }

    @Test
    public void identicalTaskMoreThanProcessorNum() throws AppConfigException {
        List<Schedule> schedules = _stateExpander2.getNewStates(_schedule2);
        for (Schedule s : schedules) {
            String scheduleResult = _writer.writeOutputToString(s,_graph2);
            scheduleResult = scheduleResult.substring(24, scheduleResult.indexOf("->")-2);
            if (_correctSchedules2.contains(scheduleResult)) {
                _correctSchedules2.remove(scheduleResult);
            }
        }

        if (_correctSchedules2.size() != 0) {
            fail();
        }
    }

    @Test
    public void identicalTaskLessThanProcessorNum() throws AppConfigException {
        List<Schedule> schedules = _stateExpander3.getNewStates(_schedule3);
        for (Schedule s : schedules) {
            String scheduleResult = _writer.writeOutputToString(s,_graph3);
            scheduleResult = scheduleResult.substring(24, scheduleResult.indexOf("->")-2);
            if (_correctSchedules3.contains(scheduleResult)) {
                _correctSchedules3.remove(scheduleResult);
            }
        }

        if (_correctSchedules3.size() != 0) {
            fail();
        }
    }
}
