package group8.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is a model of a processor
 */
public class Processor {

    private List<Task> _taskList; //the list of queued tasks
    //the time this processor will be available after completing its entire task list
    private int _firstAvailableTime;

    public Processor() {
        _taskList = new ArrayList<Task>();
        _firstAvailableTime = 0;
    }

    /**
     * This method adds tasks to its queue and
     * updates its available time to accommodate for the new task
     * @param task
     * @param timeScheduled
     */
    public void addTask(Task task, int timeScheduled) {
        _taskList.add(task);
        _firstAvailableTime += timeScheduled + task.getCost();
    }

    //generic getters and setters below
    public int getFirstAvailableTime() {
        return _firstAvailableTime;
    }

    public void setFirstAvailableTime(int firstAvailableTime) {
        _firstAvailableTime = firstAvailableTime;
    }
}
