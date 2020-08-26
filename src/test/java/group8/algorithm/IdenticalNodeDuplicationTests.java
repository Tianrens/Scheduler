package group8.algorithm;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Node;
import group8.models.Schedule;
import group8.parser.DOTFileWriter;
import group8.parser.IDOTFileWriter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class IdenticalNodeDuplicationTests {
    private Graph _graph;
    private IStateExpander _stateExpander;
    private Schedule _schedule;
    private IDOTFileWriter _writer;

    @Before
    public void setUp() throws AppConfigException {
        _writer = new DOTFileWriter();
        Node a = new Node(2, "a");
        Node b = new Node(3, "b");
        Node c = new Node(4, "c");
        Node d = new Node(3, "d");
        Node e = new Node(1, "e");

        a.addDestination(b, 2);
        a.addDestination(c, 2);
        a.addDestination(d, 2);
        b.addDestination(e, 2);
        c.addDestination(e, 2);
        d.addDestination(e, 2);

        b.addParentNode(a);
        c.addParentNode(a);
        d.addParentNode(a);
        e.addParentNode(b);
        e.addParentNode(c);
        e.addParentNode(d);

        _graph = new Graph();
        _graph.addNode(a);
        _graph.addNode(b);
        _graph.addNode(c);
        _graph.addNode(d);
        _graph.addNode(e);

        AppConfig.getInstance().setNumProcessors(3);
        _schedule = new Schedule();

        _schedule.scheduleTask("a", 0, 0);

        _stateExpander = new ELSModelStateExpander(_graph);
    }

    @Test
    public void generateSchedule() throws AppConfigException {
        List<Schedule> schedules = _stateExpander.getNewStates(_schedule);
        for (Schedule s : schedules) {
            _writer.writeOutputToConsole(s,_graph);
        }
    }
}
