package group8.scheduler;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.*;

import java.util.List;

/**
 * This class is designed to generate a simple schedule where as many processors are utilised as possible
 */
public class SimpleProcessorScheduler implements IScheduler {
    private final ITopologyFinder _topologyFinder;

    public SimpleProcessorScheduler(ITopologyFinder topologyFinder) {
        _topologyFinder = topologyFinder;
    }

    /**
     * Calling this function will generate a simple valid schedule to output
     * @param graph
     * @return
     * @throws ProcessorException
     * @throws AppConfigException
     */
    @Override
    public Schedule generateValidSchedule(Graph graph) throws ProcessorException, AppConfigException {
//        List<Node> scheduler = _topologyFinder.generateTopology(graph);
//        int numProcessors = AppConfig.getInstance().getNumProcessors();
//        if (numProcessors == 0) {
//            throw new AppConfigException();
//        }
//
//        Schedule schedule = new Schedule(numProcessors, scheduler);
//
//        scheduleTopology(schedule, scheduler);
//
//        System.out.println("Schedule generated");
//
//        return schedule;
        return null;
    }

    /**
     * This method handles most of the schduling and processing logic of the class
     * @param schedule
     * @param topology
     */
    private void scheduleTopology(Schedule schedule, List<Node> topology) {
//        List<Processor> processors = schedule.getProcessors(); // A list of all processors are obtained
//        int processorCount = 0;
//
//
//
//        for (Node node : scheduler) {
//            int startTime;
//            int earliestStartTime = 0;
//            Processor processor = processors.get(processorCount);
//            List<Node> parentList = node.getParentNodeList();
//
//            //checks all parents to determine earliest possible start time, by taking into account remote costs
//            for(Node parent : parentList){
//
//                //if parent is not on the same processor, remote costs have to be considered
//                if(parent.getProcessor()!=processor){
//                    startTime = parent.getEdgeList().get(node)+parent.getCost()+parent.getTimeScheduled();
//                    if(startTime < processor.getFirstAvailableTime()){
//                        startTime = processor.getFirstAvailableTime();
//                    }
//                }else{
//                    startTime = processor.getFirstAvailableTime();
//                }
//
//                //checks if there any dependencies that might delay the scheduling of the task
//                if(startTime>earliestStartTime){
//                    earliestStartTime=startTime;
//                }
//            }
//
//
//            schedule.scheduleTask(processor, node, earliestStartTime);
//            processor.setFirstAvailableTime(earliestStartTime + node.getCost());
//
//            processorCount++;
//            if(processorCount==processors.size()){
//                processorCount = 0;
//            }
//
//        }
    }
}
