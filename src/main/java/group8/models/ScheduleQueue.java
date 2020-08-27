package group8.models;

import group8.cli.AppConfig;

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



    @Override
    public boolean add(Schedule schedule){

//        Map<String, int[]> m1 = schedule.getTasks();
//        Map<String, int[]> m2;
//
//        if(_closedStates.size()>schedule.getTasks().size()){
//            for(Schedule s2 :_closedStates.get(schedule.getTasks().size())) {
//                m2 = s2.getTasks();
//
//                for (int i = 0; i < schedule.getProcessors().length; i++) {
//                    Map<String, Integer> same = new HashMap<>();
//                    for (Map.Entry<String, int[]> me1 : m1.entrySet()) {
//
//                        //puts all nodes on one processor into map
//                        if (me1.getValue()[1] == i) {
//                            same.put(me1.getKey(), me1.getValue()[0]);
//                        }
//                    }
//
//                    int processor = -1;
//
//                    //checks if other schedule has a processor with similar nodes
//                    for (Map.Entry<String, Integer> node : same.entrySet()) {
//
//                        if (!m2.containsKey(node.getKey())) {
//                            return false;
//                        }
//
//                        if (m2.get(node.getKey())[0] != node.getValue().intValue()) {
//                            return false;
//                        }
//
//                        int newProcessor = m2.get(node.getKey())[1];
//
//                        if (processor == -1) {
//                            processor = newProcessor;
//                            int size = 0;
//                            for(int[] n : m2.values()){
//                                if(n[1]==newProcessor){
//                                    size++;
//                                }
//                            }
//                            if(size!=same.size()){
//                                return false;
//                            }
//
//                        } else if (processor != newProcessor) {
//                            return false;
//                        }
//                    }
//                }
//            }
//        }
//


        return super.add(schedule);
    }


    /**
     * Method for adding unoptimal schedules to the CLOSED list
     * @param state
     */
    public void addClosedState(Schedule state){


        if(_closedStates.size()>state.getTasks().size()){
            _closedStates.get(state.getTasks().size()).add(state);
        }else{
            _closedStates.add(new ArrayList<>());
            _closedStates.get(state.getTasks().size()).add(state);
        }
    }
}
