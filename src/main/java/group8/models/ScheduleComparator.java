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

        if (cost1 == cost2) {
            return 0;
        } else if (cost1 < cost2){
            return -1;
        } else {
            return 1;
        }
    }
}
