package group8.algorithm;

import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.ProcessorException;
import group8.models.Schedule;
import group8.scheduler.IScheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AStarScheduler implements IScheduler {
    private PriorityQueue<Schedule> _openState = new PriorityQueue<>();
    private List<Schedule> _closedState = new ArrayList<>();

    @Override
    public Schedule generateValidSchedule(Graph graph) throws ProcessorException, AppConfigException {
        return null;
    }

    public boolean checkCompleteSchedule() {
        return false;
    }
}
