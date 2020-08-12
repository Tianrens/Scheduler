package group8.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the methods and fields to mimic
 * a real world schedule
 */
public class Schedule {

    private List<Processor> _processorList = new ArrayList<Processor>();
    private List<TaskNode> _taskNodeList;
    /**
     * _unassignedTaskList is problematic because we cannot loop through an array and edit at the same time, hard to loop and schedule tasks
     */
    //private List<TaskNode> _unassignedTaskList;
    private List<TaskNode> _assignedTaskList;

    /**
     * The constructor takes in number of processors that
     * the schedule can utilise and instantiates those
     * @param numProcessors
     */
    public Schedule(int numProcessors) {

        for (int i = 0; i < numProcessors; i++) {
            Processor processor = new Processor(i);
            _processorList.add(processor);
        }
        //_unassignedTaskList = new ArrayList<>();
        _assignedTaskList = new ArrayList<>();
    }

    public List<TaskNode> getTaskNodeList(){
        return _taskNodeList;
    }

    public List<Processor> getProcessors() {
        return _processorList;
    }

    /**
     * Method is to initialise the whole set of unassigned tasks.
     * Call this method once at the beginning of schedule.
     * @param taskList
     */
    public void setUnassignedTaskList(List<TaskNode> taskList) {
        //_unassignedTaskList = taskList;
        _taskNodeList = taskList;
    }

    /**
     * This method schedules the task on the processor
     * and assigns the processor to the task.
     * It also moves the task from the unassigned list to assigned list.
     * @param processor
     * @param task
     * @param timeScheduled
     */
    public void scheduleTask(Processor processor, TaskNode task, int timeScheduled) {
        task.setProcessor(processor);
        task.setTimeScheduled(timeScheduled);

        processor.addTask(task, timeScheduled);

        //_unassignedTaskList.remove(task);
        _assignedTaskList.add(task);
    }
}
