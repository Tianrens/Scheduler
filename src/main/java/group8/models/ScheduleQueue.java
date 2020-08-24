package group8.models;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ScheduleQueue extends PriorityQueue<Schedule> {
    private List<Schedule> _closedState = new ArrayList<>();
    private Object[] _openQueue;

    @Override
    public boolean add(Schedule state){
        boolean isDupe = false;
        Schedule dupeState;

        if(state == null){
            throw new NullPointerException();
        }
        // Obtain current OPEN list
        _openQueue = this.toArray();

        // Perform processor sets check on OPEN and CLOSED lists
        for (Schedule cState: _closedState) {
            if(state.equals(cState)){
                isDupe = true;
                dupeState = cState;
            }
        }

        for(Object oState: _openQueue){
            Schedule oStateCompare = (Schedule)oState;
            if(state.equals(oStateCompare)){
                isDupe = true;
                dupeState = oStateCompare;
            }
        }

        // If equals to any sets, check processor start times
        if(isDupe){
            
        }

        // If duplicate dont add

        // Otherwise add to priority queue normally
        //super.add(state);

        return true;
    }

    /**
     * Method for adding unoptimal schedules to the CLOSED list
     * @param state
     */
    public void addClosedState(Schedule state){
        _closedState.add(state);
    }
}
