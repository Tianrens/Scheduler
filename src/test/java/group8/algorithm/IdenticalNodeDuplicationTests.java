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

import java.io.File;
import java.util.List;

public class IdenticalNodeDuplicationTests {
    private Graph _graph;
    private IStateExpander _stateExpander;
    private Schedule _schedule;
    private IDOTFileWriter _writer;

    @Before
    public void setUp() throws AppConfigException {
        _writer = new DOTFileWriter();
        _graph.setUpForIdenticalNodes();

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("identicalNodes.dot").getPath()));
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
