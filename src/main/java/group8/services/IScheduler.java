package group8.services;

import group8.models.Graph;
import group8.models.ISchedule;
import group8.models.Schedule;

public interface IScheduler {


    Schedule generateValidSchedule(Graph graph);

}
