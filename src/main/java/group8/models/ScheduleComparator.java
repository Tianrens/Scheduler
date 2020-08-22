package group8.models;

import java.util.Comparator;

/**
 * This class compares two schedules heuristic costs.
 */
public class ScheduleComparator implements Comparator<Schedule> {
    @Override
    public int compare(Schedule s1, Schedule s2) {
        // Heuristic cost
        int cost1 = s1.getHeuristicCost();
        int cost2 = s2.getHeuristicCost();

        int l1 = earliest(s1.getProcessors());
        int l2 = earliest(s2.getProcessors());

        if (cost1 == -1 || cost2 == -1) {
            try {
                throw new ScheduleException("Invalid Schedule Heuristic Cost");
            } catch (ScheduleException e) {
                e.printStackTrace();
            }
        }

        if (cost1 == cost2) {
            if(l1<l2){
                return -1;
            }else if(l1==l2){
                return 0;
            }else{
                return 1;
            }
        } else if (cost1 < cost2){
            return -1;
        } else {
            return 1;
        }
    }

    private int earliest(int[] processors){
        int shortestLength = -1;

        for(int i = 0; i <processors.length;i++){
            int length = processors[i];
            if(shortestLength==-1 || length>shortestLength){
                shortestLength=length;
            }
        }

        return shortestLength;
    }
}
