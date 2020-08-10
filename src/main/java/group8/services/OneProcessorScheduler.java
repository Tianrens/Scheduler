package group8.services;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Schedule;
import group8.models.TaskNode;

import java.util.List;

public class OneProcessorScheduler implements IScheduler {
    private final TopologyFinder _topologyFinder;

    public OneProcessorScheduler(TopologyFinder topologyFinder) {
        _topologyFinder = topologyFinder;
    }

    @Override
    public Schedule generateValidSchedule(Graph graph) throws AppConfigException {
        List<TaskNode> topology = _topologyFinder.generateTopology(graph);
        int numProcessors = AppConfig.getInstance().get_numProcessors();

        if (numProcessors == 0) {
            throw new AppConfigException();
        }

        Schedule schedule = new Schedule(numProcessors);

        return null;
    }
}
