package group8.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the methods and fields to mimic
 * a real world schedule
 */
public class Schedule implements ISchedule {

    private List<Processor> _processorList;
    private List<Task> _taskList;
    private List<Task> _unassignedTaskList;
    private List<Task> _assignedTaskList;

    /**
     * The constructor takes in number of processors that
     * the schedule can utilise and instantiates those
     * @param numProcessors
     */
    public Schedule(int numProcessors) {

        for (int i = 0; i < numProcessors; i++) {
            Processor processor = new Processor();
            _processorList.add(processor);
        }
        _unassignedTaskList = new ArrayList<Task>();
        _assignedTaskList = new ArrayList<Task>();
    }

    @Override
    public void setNumberOfProcessors(int num) {

    }

    @Override
    public void setGraph(Graph graph) {

    }

    /**
     * Method is to initialise the whole set of unassigned tasks.
     * Call this method once at the beginning of schedule.
     * @param taskList
     */
    public void setUnassignedTaskList(List<Task> taskList) {
        _unassignedTaskList = taskList;
        _taskList = taskList;
    }

    /**
     * This method schedules the task on the processor
     * and assigns the processor to the task.
     * It also moves the task from the unassigned list to assigned list.
     * @param processor
     * @param task
     * @param timeScheduled
     */
    public void scheduleTask(Processor processor, Task task, int timeScheduled) {
        task.setProcessor(processor);
        task.setTimeScheduled(timeScheduled);

        processor.addTask(task, timeScheduled);

        _unassignedTaskList.remove(task);
        _assignedTaskList.add(task);
    }
}
