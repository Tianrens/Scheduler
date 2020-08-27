package group8.models;

import java.util.*;

/**
 * Sub class of Priority queue used specially to handle schedules for the A* algorithm
 */
public class ScheduleQueue extends TreeSet<Schedule> {
    private List<List<Schedule>> _closedStates = new ArrayList<>();

    /**
     * Constructor to handle comparator argument
     * @param comparator
     */
    public ScheduleQueue(Comparator<? super Schedule> comparator){
        super(comparator);
    }


    /**
     * Method for adding unoptimal schedules to the CLOSED list
     * @param state
     */
    public void addClosedState(Schedule state){

//        if(_closedStates.size()>state.getTasks().size()){
//            _closedStates.get(state.getTasks().size()-1).add(state);
//        }else{
//            _closedStates.add(new ArrayList<Schedule>());
//            _closedStates.get(state.getTasks().size()).add(state);
//        }

    }
}
