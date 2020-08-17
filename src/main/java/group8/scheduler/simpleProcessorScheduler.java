package group8.scheduler;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.*;

import java.util.List;

import static group8.scheduler.SchedulerConstants.ONE_PROCESSOR_SCHEDULER_DEFAULT;

public class simpleProcessorScheduler implements IScheduler {
    private final ITopologyFinder _topologyFinder;

    public simpleProcessorScheduler(ITopologyFinder topologyFinder) {
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

        System.out.println("Schedule generated");

        return schedule;
    }

    private void scheduleTopology(Schedule schedule, List<TaskNode> topology) {
        List<Processor> processors = schedule.getProcessors(); // Get default processor for this scheduler
        int processorCount = 0;



        for (TaskNode taskNode : topology) {
            int startTime;
            int earliestStartTime = 0;
            Processor processor = processors.get(processorCount);
            //get all parents
            List<TaskNode> parentList = taskNode.getParentNodeList();

            for(TaskNode parent : parentList){

                if(parent.getProcessor()!=processor){
                    startTime = parent.getEdgeList().get(taskNode)+parent.getCost()+parent.getTimeScheduled();
                    if(startTime < processor.getFirstAvailableTime()){
                        startTime = processor.getFirstAvailableTime();
                    }
                }else{
                    startTime = processor.getFirstAvailableTime();
                }

                if(startTime>earliestStartTime){
                    earliestStartTime=startTime;
                }
            }

            schedule.scheduleTask(processor, taskNode, earliestStartTime);
            processor.setFirstAvailableTime(earliestStartTime + taskNode.getCost());

            processorCount++;
            if(processorCount==processors.size()){
                processorCount = 0;
            }

        }
    }
}
