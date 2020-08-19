package group8.parser;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.fail;

public class OutputGraphTests {
    private IDOTFileWriter _dataParser;

    private Schedule _schedule;
    private Graph _graph;
    private Schedule _noEdgesSchedule;
    private Graph _noEdgesGraph;
    private Schedule _emptySchedule;
    private Graph _emptyGraph;

    private List<String> _expectedSchedule;
    private List<String> _expectedNoEdgesSchedule;
    private List<String> _expectedEmptySchedule;

    private final String _actualOutputSchedule = "actualOutputSchedule.dot";

    @Before
    public void setUpParser() {
        _dataParser = new DOTFileWriter();
    }

    @Before
    public void generateSchedule() throws AppConfigException {

        Node a = new Node(2, "a");
        Node b = new Node(3, "b");
        Node c = new Node(2, "c");
        Node d = new Node(1, "d");
        Node e = new Node(10, "e");

        a.addDestination(b, 4);
        a.addDestination(c, 5);
        c.addDestination(e, 1);
        b.addDestination(d, 6);
        d.addDestination(e, 1);

        _graph = new Graph();
        _graph.addNode(a);
        _graph.addNode(b);
        _graph.addNode(c);
        _graph.addNode(d);
        _graph.addNode(e);

        AppConfig.getInstance().setNumProcessors(3);
        _schedule = new Schedule();
        _schedule.scheduleTask("a", 0, 0);
        _schedule.scheduleTask("c", 2, 0);
        _schedule.scheduleTask("b", 4, 0);
        _schedule.scheduleTask("d", 7, 0);
        _schedule.scheduleTask("e", 9, 1);
    }

    @Before
    public void generateNoEdgesSchedule() throws ProcessorException, AppConfigException {
        Node a = new Node(2, "a");
        _noEdgesGraph = new Graph();
        _noEdgesGraph.addNode(a);

        AppConfig.getInstance().setNumProcessors(2);
        _noEdgesSchedule = new Schedule();
        _noEdgesSchedule.scheduleTask("a", 0, 0);
    }

    @Before
    public void generateEmptySchedule() throws AppConfigException {
        _emptyGraph = new Graph();

        AppConfig.getInstance().setNumProcessors(1);
        _emptySchedule = new Schedule();
    }

    @Before
    public void setUpExpectedSchedules() {
        _expectedSchedule = new ArrayList<String>() {{
            add("digraph output_graph {");
            add("\ta [Weight=2, Start=0, Processor=1];");
            add("\tc [Weight=2, Start=2, Processor=1];");
            add("\tb [Weight=3, Start=4, Processor=1];");
            add("\td [Weight=1, Start=7, Processor=1];");
            add("\te [Weight=10, Start=9, Processor=2];");
            add("\ta->b [Weight=4];");
            add("\ta->c [Weight=5];");
            add("\tc->e [Weight=1];");
            add("\tb->d [Weight=6];");
            add("\td->e [Weight=1];");
            add("}");
        }};

        _expectedNoEdgesSchedule = new ArrayList<String>() {{
            add("digraph output_graph {");
            add("\ta [Weight=2, Start=0, Processor=1];");
            add("}");
        }};

        _expectedEmptySchedule = new ArrayList<String>() {{
            add("digraph output_graph {");
            add("}");
        }};
    }

    /**
     * Test a normal schedule with nodes and edges, scheduled on TWO processors.
     */
    @Test
    public void NormalScheduleTest() throws AppConfigException {
        String pathOfOutputTestSchedule = this.getClass().getResource(_actualOutputSchedule).getPath();
        AppConfig.getInstance().setOutputFile(new File(pathOfOutputTestSchedule));
        _dataParser.writeOutput(_schedule, _graph);

        checkExpectedVsActual(_expectedSchedule);

    }

    /**
     * Test a schedule with no edges, just a singular node.
     */
    @Test
    public void NoEdgesTest() throws AppConfigException {
        String pathOfOutputTestSchedule = this.getClass().getResource(_actualOutputSchedule).getPath();
        AppConfig.getInstance().setOutputFile(new File(pathOfOutputTestSchedule));
        _dataParser.writeOutput(_noEdgesSchedule, _noEdgesGraph);

        checkExpectedVsActual(_expectedNoEdgesSchedule);
    }

    /**
     * Test a schedule that has no edges nor nodes.
     */
    @Test
    public void EmptyScheduleTest() throws AppConfigException {
        String pathOfOutputTestSchedule = this.getClass().getResource(_actualOutputSchedule).getPath();
        AppConfig.getInstance().setOutputFile(new File(pathOfOutputTestSchedule));
        _dataParser.writeOutput(_emptySchedule, null);

        checkExpectedVsActual(_expectedEmptySchedule);
    }

    private void checkExpectedVsActual(List<String> expectedList) {
        List<String> actual = readActualOutputSchedule();

        for (String expected : expectedList) {
            if (actual.contains(expected)) {
                actual.remove(expected); // Remove from the list so that the string that got matched up does not get compared again.
                continue;
            } else {
                fail();
            }
        }

        if (! actual.isEmpty()) { // Fail if there are extra components in the actual output that are NOT expected.
            fail();
        }
    }

    private List<String> readActualOutputSchedule() {
        List<String> output = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.getClass().getResource(_actualOutputSchedule).getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                output.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }
}
