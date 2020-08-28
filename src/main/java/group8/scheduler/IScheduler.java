package group8.scheduler;

import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Schedule;

/**
 * Interface for the different scheduling algorithms
 */
public interface IScheduler {


    Schedule generateValidSchedule(Graph graph) throws AppConfigException;

}
