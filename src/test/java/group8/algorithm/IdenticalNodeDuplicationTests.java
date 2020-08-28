package group8.algorithm;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Node;
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

    @BeforeClass
    public static void setUp() throws AppConfigException {
        // First schedule test
        AppConfig.getInstance().setInputFile(new File(IdenticalNodeDuplicationTests.class.getResource("identicalNodes.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(3);

        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        _graph = externalGraphGenerator.generate();
                Schedule empty = new Schedule();
        _graph.setHeuristicCost(Math.min(new SimpleHeuristic().calculateEstimate(empty, _graph.getAllNodes()),new GreedyHeuristic().calculateEstimate(empty, _graph.getAllNodes())));

        _stateExpander = new ELSModelStateExpander(_graph);

        _schedule = new Schedule();
        _schedule.scheduleTask("a", 0, 0);
        _schedule.setProcessorStartTime(0, 2);

        //Second schedule test
        AppConfig.getInstance().setInputFile(new File(IdenticalNodeDuplicationTests.class.getResource("identicalNodes2.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(1);

        IGraphGenerator externalGraphGenerator2 = new GraphExternalParserGenerator(new DOTPaypalParser());
        _graph2 = externalGraphGenerator2.generate();
        Schedule empty2 = new Schedule();
        _graph2.setHeuristicCost(Math.min(new SimpleHeuristic().calculateEstimate(empty2, _graph2.getAllNodes()),new GreedyHeuristic().calculateEstimate(empty2, _graph2.getAllNodes())));

        _stateExpander2 = new ELSModelStateExpander(_graph2);

        _schedule2 = new Schedule();
        _schedule2.scheduleTask("a", 0, 0);
        _schedule2.setProcessorStartTime(0, 2);

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

        _correctSchedules.add(
                "\ta [Weight=2, Start=0, Processor=1];" + System.lineSeparator() +
                "\tc [Weight=4, Start=2, Processor=1];" + System.lineSeparator()
        );

        _correctSchedules.add(
                "\ta [Weight=2, Start=0, Processor=1];" + System.lineSeparator() +
                "\tc [Weight=4, Start=4, Processor=2];" + System.lineSeparator()
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

    @Test
    public void correctIdenticalGrouping() {
        assertEquals(_graph.getNode("b").getIdenticalNodeId(), 0);
        assertEquals(_graph.getNode("d").getIdenticalNodeId(), 0);

        assertEquals(_graph.getNode("a").getIdenticalNodeId(), -1);
        assertEquals(_graph.getNode("c").getIdenticalNodeId(), -1);
        assertEquals(_graph.getNode("e").getIdenticalNodeId(), -1);
    }

    @Test
    public void correctIdenticalGrouping2() {
        assertEquals(_graph2.getNode("b").getIdenticalNodeId(), 0);
        assertEquals(_graph2.getNode("g").getIdenticalNodeId(), 0);

        assertEquals(_graph2.getNode("a").getIdenticalNodeId(), -1);
        assertEquals(_graph2.getNode("c").getIdenticalNodeId(), -1);
        assertEquals(_graph2.getNode("d").getIdenticalNodeId(), -1);
        assertEquals(_graph2.getNode("e").getIdenticalNodeId(), -1);
        assertEquals(_graph2.getNode("f").getIdenticalNodeId(), -1);
    }

    @Test
    public void generateSchedule() throws AppConfigException {
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
    public void generateSchedule2() throws AppConfigException {
        List<Schedule> schedules = _stateExpander2.getNewStates(_schedule2);
        for (Schedule s : schedules) {
            String scheduleResult = _writer.writeOutputToString(s,_graph2);
            scheduleResult = scheduleResult.substring(24, scheduleResult.indexOf("->")-2);
            System.out.println(scheduleResult);
            if (_correctSchedules2.contains(scheduleResult)) {
                _correctSchedules2.remove(scheduleResult);
            }
        }

        if (_correctSchedules2.size() != 0) {
            fail();
        }
    }
}
