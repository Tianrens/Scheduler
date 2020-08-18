package group8.algorithm;

import group8.models.Schedule;

import java.util.List;

public interface IStateExpander {
    public List<Schedule> getNewStates(Schedule schedule);

}
