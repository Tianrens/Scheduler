package group8.models;

import group8.cli.AppConfig;

import java.util.*;

/**
 * This class compares two schedules heuristic costs.
 */
public class ScheduleComparator implements Comparator<Schedule> {


    private Graph _graph;
    private int pCount = AppConfig.getInstance().getNumProcessors();

    public ScheduleComparator(Graph graph){
        super();
        _graph=graph;
    }


    @Override
    public int compare(Schedule s1, Schedule s2) {


        Map<String, int[]> m1 =  s1.getTasks();
        Map<String, int[]> m2 =  s2.getTasks();

        boolean isSame = true;

        for(int i = 0 ; i < pCount ; i++){
            Map<String, Integer> same = new HashMap<>();
            for(Map.Entry<String, int[]> me1 : m1.entrySet()){
                if(me1.getValue()[1]==i){
                    same.put(me1.getKey(),me1.getValue()[0]);
                }
            }

            int processor = -1;
            for(Map.Entry<String, Integer> node : same.entrySet()){
                if(!m2.containsKey(node.getKey())){
                    isSame = false;
                    break;
                }

                int newProcessor = m2.get(node.getKey())[1];
                if(m2.get(node.getKey())[0]!=node.getValue().intValue()){
                    isSame = false;
                    break;
                }

                if(processor==-1){
                    processor=newProcessor;
                }else if(processor!=newProcessor){
                    isSame = false;
                    break;
                }

            }

            if(!isSame){
                break;
            }

        }

        isSame = false;

        if(isSame){
            return 0;
        }else if (s1.hashCode() < s2.hashCode()){
            return -1;
        } else {
            return 1;
        }

    }

}
