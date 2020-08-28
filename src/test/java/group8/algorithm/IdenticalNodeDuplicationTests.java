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
    private static Graph _graph;
    private static IStateExpander _stateExpander;
    private static IDOTFileWriter _writer;

    private static Schedule _schedule;
    private static List<String> _correctSchedules;

    @BeforeClass
    public static void setUp() throws AppConfigException {
        AppConfig.getInstance().setInputFile(new File(IdenticalNodeDuplicationTests.class.getResource("identicalNodes.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(3);
        _writer = new DOTFileWriter();

        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        _graph = externalGraphGenerator.generate();
                Schedule empty = new Schedule();
        _graph.setHeuristicCost(Math.min(new SimpleHeuristic().calculateEstimate(empty, _graph.getAllNodes()),new GreedyHeuristic().calculateEstimate(empty, _graph.getAllNodes())));

        _stateExpander = new ELSModelStateExpander(_graph);

        _schedule = new Schedule();
        _schedule.scheduleTask("a", 0, 0);
        _schedule.setProcessorStartTime(0, 2);
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

    @Test
    public void correctIdenticalGrouping() {
        assertEquals(_graph.getNode("b").getIdenticalNodeId(), 0);
        assertEquals(_graph.getNode("d").getIdenticalNodeId(), 0);

        assertEquals(_graph.getNode("a").getIdenticalNodeId(), -1);
        assertEquals(_graph.getNode("c").getIdenticalNodeId(), -1);
        assertEquals(_graph.getNode("e").getIdenticalNodeId(), -1);
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
}
