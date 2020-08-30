package group8.scheduler;

import group8.algorithm.AlgorithmState;
import group8.algorithm.TopologyFinder;
import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.*;
import group8.visualisation.AlgorithmStatus;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;


/**
 * This class is designed to schedule all tasks onto a single process, Was created for testing purposes, but is currently redundnat.
 */
public class OneProcessScheduler implements IScheduler {
    private final TopologyFinder _topologyFinder;

    public OneProcessScheduler(TopologyFinder topologyFinder) {
        _topologyFinder = topologyFinder;
    }


    @Override
    public Schedule generateValidSchedule(Graph graph) throws AppConfigException {
        List<Node> topology = _topologyFinder.generateTopology(graph.getAllNodes().values().stream().collect(Collectors.toList()));
        Schedule schedule = new Schedule();
        scheduleTopology(schedule, topology);

        AlgorithmStatus.getInstance().setCurrentBestSchedule(schedule);
        AlgorithmStatus.getInstance().setNumSchedulesGenerated(1);
        AlgorithmStatus.getInstance().setNumSchedulesOnCores(new long[]{1});
        AlgorithmStatus.getInstance().setAlgoState(AlgorithmState.FINISHED);
        return schedule;
    }

    /**
     * this method handles most of the processing and logic for the class. All tasks are scheduled onto a single process
     * @param schedule
     * @param topology
     */
    private void scheduleTopology(Schedule schedule, List<Node> topology) {

        int[] processor = new int[1];

        int startTime = 0;
        for (Node taskNode : topology) {
            schedule.getTasks().put(taskNode.getId(),new int[]{startTime,0});
            startTime += taskNode.getCost() ;
        }
    }
}
