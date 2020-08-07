package group8.services;

import group8.models.Graph;
import group8.models.ISchedule;

public interface Scheduler {


    public ISchedule generateValidSchedule(Graph graph);

}
