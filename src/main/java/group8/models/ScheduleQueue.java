package group8.models;

import java.util.*;

/**
 * Sub class of Priority queue used specially to handle schedules for the A* algorithm
 */
public class ScheduleQueue extends TreeSet<Schedule> {
    private List<List<Schedule>> _closedStates = new ArrayList<>();
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


        Set<Set<List<String>>> stateProcessorSet = state.getProcessorSet();

        /*
        // Obtain current OPEN list
        _openQueue = this.toArray();

        if(state == null){
            throw new NullPointerException();
        }


        // Perform processor sets check on CLOSED list
        if(_closedStates.size()>0) {
            for (Schedule cState : _closedStates.get(state.getTasks().size()-1)) {
                if (stateProcessorSet.equals(cState.getProcessorSet())) {
                    // If a dupe is found, don't add
                    //return false;
                }
            }
        }

        // Check OPEN list
        for(Object oState: _openQueue){
            Schedule oStateCompare = (Schedule)oState;
            if(stateProcessorSet.equals(oStateCompare.getProcessorSet())){
                // If a dupe is found, don't add
                //return false;
            }
        }

        // If passed all duplication checks, add onto queue

         */

        return super.add(state);
    }

    /**
     * Method for adding unoptimal schedules to the CLOSED list
     * @param state
     */
    public void addClosedState(Schedule state){

        if(_closedStates.size()>state.getTasks().size()){
            _closedStates.get(state.getTasks().size()-1).add(state);
        }else{
            _closedStates.add(new ArrayList<Schedule>());
            _closedStates.get(state.getTasks().size()).add(state);
        }

    }
}
