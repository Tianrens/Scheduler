package group8.scheduler;

import group8.models.Graph;
import group8.models.ProcessorException;
import group8.models.Schedule;

public interface IScheduler {


    Schedule generateValidSchedule(Graph graph) throws ProcessorException;

}
