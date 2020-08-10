package group8.services;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Processor;
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
        schedule.setUnassignedTaskList(topology);

        Processor processor = schedule.getProcessors().get(0); // Get first processor
        int startTime;
        for (TaskNode taskNode : topology) {
            startTime = processor.getFirstAvailableTime();
            schedule.scheduleTask(processor, taskNode, startTime);
            processor.setFirstAvailableTime(startTime + taskNode.getCost());
        }

        return schedule;
    }
}
