package group8.parser;

import group8.models.Processor;
import group8.models.Schedule;
import group8.models.TaskNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

public class OutputGraphTests {
    IDOTDataParser _dataParser;
    GraphGenerator _graphGenerator;

    Schedule _schedule;
    Schedule _noEdgesSchedule;
    Schedule _emptySchedule;

    List<String> _expectedSchedule;
    List<String> _expectedNoEdgesSchedule;
    List<String> _expectedEmptySchedule;

    private final String _actualOutputSchedule = "actualOutputSchedule.dot";

    @Before
    public void setUpParser() {
        _dataParser = new DOTDataParser();
        _graphGenerator = new GraphGenerator(_dataParser);

        _emptySchedule = new Schedule(1);
    }

    @Before
    public void generateSchedule() {
        _schedule = new Schedule(2);

        TaskNode a = new TaskNode(2, "a");
        TaskNode b = new TaskNode(3, "b");
        TaskNode c = new TaskNode(2, "c");
        TaskNode d = new TaskNode(1, "d");
        TaskNode e = new TaskNode(10, "e");

        a.addDestination(b, 4);
        a.addDestination(c, 5);
        c.addDestination(e, 1);
        b.addDestination(d, 6);
        d.addDestination(e, 1);

        List<TaskNode> topology = new ArrayList<>();
        topology.add(a);
        topology.add(c);
        topology.add(b);
        topology.add(d);
        topology.add(e);
        _schedule.setUnassignedTaskList(topology);

        List<Processor> processors = _schedule.getProcessors();

        _schedule.scheduleTask(processors.get(0), a, 0);
        _schedule.scheduleTask(processors.get(0), c, 2);
        _schedule.scheduleTask(processors.get(0), b, 4);
        _schedule.scheduleTask(processors.get(0), d, 7);
        _schedule.scheduleTask(processors.get(1), e, 9);
    }

    @Before
    public void generateNoEdgesSchedule() {
        _noEdgesSchedule = new Schedule(1);
        TaskNode a = new TaskNode(2, "a");

        List<TaskNode> topology = new ArrayList<>();
        topology.add(a);
        _noEdgesSchedule.setUnassignedTaskList(topology);

        List<Processor> processors = _noEdgesSchedule.getProcessors();
        _noEdgesSchedule.scheduleTask(processors.get(0), a, 0);
    }

    @Before
    public void setUpExpectedSchedules() {
        _expectedSchedule = new ArrayList<String>() {{
            add("digraph output_graph {");
            add("a [Weight=2, Start=0, Processor=1];");
            add("c [Weight=2, Start=2, Processor=1];");
            add("b [Weight=3, Start=4, Processor=1];");
            add("d [Weight=1, Start=7, Processor=1];");
            add("e [Weight=10, Start=9, Processor=2];");
            add("a->b [Weight=4];");
            add("a->c [Weight=5];");
            add("c->e [Weight=1];");
            add("b->d [Weight=6];");
            add("d->e [Weight=1];");
            add("}");
        }};

        _expectedNoEdgesSchedule = new ArrayList<String>() {{
            add("digraph output_graph {");
            add("a [Weight=2, Start=0, Processor=1];");
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
    public void NormalScheduleTest() {
        String pathOfOutputTestSchedule = this.getClass().getResource(_actualOutputSchedule).getPath();
        _dataParser.parseOutput(pathOfOutputTestSchedule, _schedule);

        checkExpectedVsActual(_expectedSchedule);

    }

    /**
     * Test a schedule with no edges, just a singular node.
     */
    @Test
    public void NoEdgesTest() {
        String pathOfOutputTestSchedule = this.getClass().getResource(_actualOutputSchedule).getPath();
        _dataParser.parseOutput(pathOfOutputTestSchedule, _noEdgesSchedule);

        checkExpectedVsActual(_expectedNoEdgesSchedule);
    }

    /**
     * Test a schedule that has no edges nor nodes.
     */
    @Test
    public void EmptyScheduleTest() {
        String pathOfOutputTestSchedule = this.getClass().getResource(_actualOutputSchedule).getPath();
        _dataParser.parseOutput(pathOfOutputTestSchedule, _emptySchedule);

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
