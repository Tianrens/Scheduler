package group8.scheduler;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.*;

import java.util.List;

import static group8.scheduler.SchedulerConstants.*;

/**
 * This class is designed to schedule all tasks onto a single process, Was created for testing purposes, but is currently redundnat.
 */
public class OneProcessorScheduler implements IScheduler {
    private final ITopologyFinder _topologyFinder;

    public OneProcessorScheduler(ITopologyFinder topologyFinder) {
        _topologyFinder = topologyFinder;
    }


    @Override
    public Schedule generateValidSchedule(Graph graph) throws ProcessorException, AppConfigException {
//        List<Node> topology = _topologyFinder.generateTopology(graph);
//        int numProcessors = AppConfig.getInstance().getNumProcessors();
//        if (numProcessors == 0) {
//            throw new AppConfigException();
//        }
//
//        Schedule schedule = new Schedule(numProcessors, topology);
//
//        scheduleTopology(schedule, topology);
//
//        System.out.println("Schedule generated");
//
//        return schedule;
        return null;
    }

    /**
     * this method handles most of the processing and logic for the class. All tasks are scheduled onto a single process
     * @param schedule
     * @param topology
     */
    private void scheduleTopology(Schedule schedule, List<Node> topology) {
//        Processor processor = schedule.getProcessors().get(ONE_PROCESSOR_SCHEDULER_DEFAULT - 1); // Get default processor for this scheduler
//
//        int startTime;
//        for (Node node : topology) {
//            startTime = processor.getFirstAvailableTime();
//            schedule.scheduleTask(processor, node, startTime);
//            processor.setFirstAvailableTime(startTime + node.getCost());
//        }
    }
}
