package group8.models;

import java.util.*;

/**
 * Sub class of Priority queue used specially to handle schedules for the A* algorithm
 */
public class ScheduleQueue extends PriorityQueue<Schedule> {
    private List<Schedule> _closedState = new ArrayList<>();
    private Object[] _openQueue;

    /**
     * Override the add() from priority queue to include duplicate state check
     * @param state
     * @return
     */
    @Override
    public boolean add(Schedule state){
        boolean maybeDupe = false;
        Set<Set<String>> stateProcessorSet = state.getProcessorSet();
        Schedule dupeState = null;

        if(state == null){
            throw new NullPointerException();
        }
        // Obtain current OPEN list
        _openQueue = this.toArray();

        // Perform processor sets check on CLOSED list
        for (Schedule cState: _closedState) {
            if(stateProcessorSet.equals(cState.getProcessorSet())){
                maybeDupe = true;
                dupeState = cState;
            }
        }

        // Check OPEN list
        for(Object oState: _openQueue){
            Schedule oStateCompare = (Schedule)oState;
            if(stateProcessorSet.equals(oStateCompare.getProcessorSet())){
                maybeDupe = true;
                dupeState = oStateCompare;
            }
        }

        // If equals to any sets, check processor start times
        if(maybeDupe){
            // Loop through processor sets from each state
            for (Set<String> processor: dupeState.getProcessorSet()) {

                for (Set<String> StateProcessor: stateProcessorSet) {

                    // If you find the corresponding processor set, check their start times
                    if(processor.equals(StateProcessor)){
                        // Obtain a node id from each processor
                        String dupeTask = processor.iterator().next();
                        String stateTask = StateProcessor.iterator().next();

                        // Find node -> find node's processor
                        int dupePNum = dupeState.getTasks().get(dupeTask)[1];
                        int statePNum = state.getTasks().get(stateTask)[1];

                        // Obtain processor -> get start time
                        int dupeStart = dupeState.getProcessors()[dupePNum - 1];
                        int stateStart = state.getProcessors()[statePNum - 1];

                        // If start times are not the same, means they are not duplicates, thus add to queue
                        if(dupeStart != stateStart){
                            return super.add(state);
                        }
                    }
                }
            }

        } else {
            // If the processor sets dont match, not a duplicate
            return super.add(state);
        }

        // only reaches here if all dupe checks confirm duplication -> don't add to queue
        return false;
    }

    /**
     * Method for adding unoptimal schedules to the CLOSED list
     * @param state
     */
    public void addClosedState(Schedule state){
        _closedState.add(state);
    }
}
