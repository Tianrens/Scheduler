package group8.algorithm;

import group8.models.Schedule;

import java.util.List;

public interface IStateExpander {

    /**
     * Takes in a state/schedule, and finds all next possible states/schedules. Returns all next possible states/schedules in an arrayList
     * @param state
     * @return
     */
    public List<Schedule> getNewStates(Schedule state);

}
