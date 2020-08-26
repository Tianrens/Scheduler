package group8.models;

import java.util.*;

/**
 * Sub class of Priority queue used specially to handle schedules for the A* algorithm
 */
public class ScheduleQueue extends PriorityQueue<Schedule> {
    private List<Schedule> _closedState = new ArrayList<>();
    private Object[] _openQueue;

    /**
     * Constructor to handle comparator argument
     * @param comparator
     */
    public ScheduleQueue(Comparator<? super Schedule> comparator){
        super(comparator);
    }

    /**
     * Override the add() from priority queue to include duplicate state check
     * @param state
     * @return
     */
    @Override
    public boolean add(Schedule state){
        System.out.println(state);
        Set<Set<List<String>>> stateProcessorSet = state.getProcessorSet();
        // Obtain current OPEN list
        _openQueue = this.toArray();

        if(state == null){
            throw new NullPointerException();
        }

        // Perform processor sets check on CLOSED list
        for (Schedule cState: _closedState) {
            if(stateProcessorSet.equals(cState.getProcessorSet())){
                // If a dupe is found, don't add
                return false;
            }
        }

        // Check OPEN list
        for(Object oState: _openQueue){
            Schedule oStateCompare = (Schedule)oState;
            if(stateProcessorSet.equals(oStateCompare.getProcessorSet())){
                // If a dupe is found, don't add
                return false;
            }
        }

        // If passed all duplication checks, add onto queue
        return super.add(state);
    }

    /**
     * Method for adding unoptimal schedules to the CLOSED list
     * @param state
     */
    public void addClosedState(Schedule state){
        _closedState.add(state);
    }
}
