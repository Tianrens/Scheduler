package group8.algorithm;

import group8.cli.AppConfigException;
import group8.models.Schedule;

import java.util.List;

/**
 * Interface for state expanders used to find all possible states from a given partial schedule when adding a node
 */
public interface IStateExpander {

    /**
     * Takes in a state/schedule, and finds all next possible states/schedules. Returns all next possible states/schedules in an arrayList
     * @param state
     * @return
     */
    public List<Schedule> getNewStates(Schedule state) throws AppConfigException;

}
