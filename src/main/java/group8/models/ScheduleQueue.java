package group8.models;

import group8.cli.AppConfig;
import java.util.*;

/**
 * Sub class of Priority queue used specially to handle schedules for the A* algorithm
 */
public class ScheduleQueue extends TreeSet<Schedule> {
    private List<HashMap<Double, List<Schedule>>> _closedStates = new ArrayList<>();

    /**
     * Constructor to handle comparator argument
     * @param comparator
     */
    public ScheduleQueue(Comparator<? super Schedule> comparator){
        super(comparator);
    }

    @Override
    public boolean add(Schedule schedule){

        Map<String, int[]> m1 = schedule.getTasks();
        Map<String, int[]> m2;

        //if number of scheduled nodes is large, then there are no existing schedules that big, so does not need to be checked
        if(_closedStates.size()>schedule.getTasks().size()){
            boolean isSame;

            if(_closedStates.get(schedule.getTasks().size()).get(schedule.getHeuristicCost()-schedule.getEarliestStartTime())!=null){



            //loops through all closed states, with the same task node number
            for(Schedule s2 :_closedStates.get(schedule.getTasks().size()).get(schedule.getHeuristicCost()-schedule.getEarliestStartTime())) {
                m2 = s2.getTasks();

                if(s2.getHeuristicCost()==schedule.getHeuristicCost()) {
                    if (s2.getEarliestStartTime() == schedule.getEarliestStartTime()) {

                            isSame = true;
                            //this for loop checks all processors, and stores all tasks stored on a single processor
                            for (int i = 0; i < AppConfig.getInstance().getNumProcessors(); i++) {

                                //stores all nodes that will be compared for equality
                                Map<String, Integer> same = new HashMap<>();
                                //loops through all nodes, and stores all nodes on the specified processor into map for checking later
                                for (Map.Entry<String, int[]> me1 : m1.entrySet()) {
                                    //puts all nodes on one processor into map
                                    if (me1.getValue()[1] == i) {
                                        same.put(me1.getKey(), me1.getValue()[0]);
                                    }
                                }

                                //checks if number of processors used is the same size
                                if (same.size() == 0) {
                                    for (Map.Entry<String, int[]> me2 : m2.entrySet()) {
                                        //puts all nodes on one processor into map
                                        if (me2.getValue()[1] == i) {
                                            isSame = false;
                                            break;
                                        }
                                    }
                                }

                                int processor = -1;

                                //checks if other schedule has a processor with similar nodes
                                for (Map.Entry<String, Integer> node : same.entrySet()) {

                                    //if second schedule does not contain node then it cant be same
                                    if (!m2.containsKey(node.getKey())) {
                                        isSame = false;
                                        break;
                                    }

                                    //if startTimes are differnt then they cannot be the same.
                                    if (m2.get(node.getKey())[0] != node.getValue().intValue()) {
                                        isSame = false;
                                        break;
                                    }

                                    int newProcessor = m2.get(node.getKey())[1];//finds the processor number of the same node in the other schedule

                                    //checks if first value in loop
                                    if (processor == -1) {
                                        //processor value should remain constant for all nodes
                                        processor = newProcessor;
                                        int size = 0;

                                        //counts how many nodes are on the processor in the other schedule
                                        for (int[] n : m2.values()) {
                                            if (n[1] == newProcessor) {
                                                size++;
                                            }
                                        }

                                        //there should be same number of nodes in total
                                        if (size != same.size()) {
                                            isSame = false;
                                            break;
                                        }

                                    } else if (processor != newProcessor) { //if processors do not equal then same nodes are not on the same processor
                                        isSame = false;
                                        break;
                                    }

                                }

                                if (!isSame) {
                                    break;
                                } else {
                                    return false;
                                }
                            }

                    }
                    }
                }
            }
        }
        return super.add(schedule);
    }

    /**
     * Method for adding non-optimal schedules to the CLOSED list
     * @param state
     */
    public void addClosedState(Schedule state){

        if(_closedStates.size()>state.getTasks().size()){

            if(_closedStates.get(state.getTasks().size()).get(state.getHeuristicCost()-state.getEarliestStartTime())!=null){
                _closedStates.get(state.getTasks().size()).get(state.getHeuristicCost()-state.getEarliestStartTime()).add(state);
            }else{
                _closedStates.get(state.getTasks().size()).put(state.getHeuristicCost()-state.getEarliestStartTime(),new ArrayList<>());
                _closedStates.get(state.getTasks().size()).get(state.getHeuristicCost()-state.getEarliestStartTime()).add(state);
            }
        }else{
            _closedStates.add(new HashMap<Double, List<Schedule>>());
            _closedStates.get(state.getTasks().size()).put(state.getHeuristicCost()-state.getEarliestStartTime(),new ArrayList<>());
            _closedStates.get(state.getTasks().size()).get(state.getHeuristicCost()-state.getEarliestStartTime()).add(state);
        }
    }
}
