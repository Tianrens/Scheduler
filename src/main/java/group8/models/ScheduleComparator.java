package group8.models;

import java.util.Comparator;

/**
 * This class compares two schedules heuristic costs.
 */
public class ScheduleComparator implements Comparator<Schedule> {


    @Override
    public int compare(Schedule s1, Schedule s2) {

        if (s1.getProcessorSet().equals(s2.getProcessorSet())) {
            // If a dupe is found, don't add
            return 0;
        }else if (s1.hashCode() < s2.hashCode()){
            return -1;
        } else {
            return 1;
        }

    }

}
