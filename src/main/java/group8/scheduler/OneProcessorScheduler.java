package group8.scheduler;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.*;

import java.util.List;

import static group8.scheduler.SchedulerConstants.*;

public class OneProcessorScheduler implements IScheduler {
    private final ITopologyFinder _topologyFinder;

    public OneProcessorScheduler(ITopologyFinder topologyFinder) {
        _topologyFinder = topologyFinder;
    }

    @Override
    public Schedule generateValidSchedule(Graph graph) throws ProcessorException, AppConfigException {
        List<TaskNode> topology = _topologyFinder.generateTopology(graph);
        int numProcessors = AppConfig.getInstance().getNumProcessors();
        if (numProcessors == 0) {
            throw new AppConfigException();
        }

        Schedule schedule = new Schedule(numProcessors, topology);

        scheduleTopology(schedule, topology);

        return schedule;
    }

    private void scheduleTopology(Schedule schedule, List<TaskNode> topology) {
        Processor processor = schedule.getProcessors().get(ONE_PROCESSOR_SCHEDULER_DEFAULT - 1); // Get default processor for this scheduler

        int startTime;
        for (TaskNode taskNode : topology) {
            startTime = processor.getFirstAvailableTime();
            schedule.scheduleTask(processor, taskNode, startTime);
            processor.setFirstAvailableTime(startTime + taskNode.getCost());
        }
    }
}
