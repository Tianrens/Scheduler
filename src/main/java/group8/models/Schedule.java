package group8.models;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains the methods and fields to mimic
 * a real world schedule
 */
public class Schedule {

    private List<Processor> _processorList;
    private List<TaskNode> _taskNodeList;
    private List<TaskNode> _unassignedTaskList;
    private List<TaskNode> _assignedTaskList;

    /**
     * The constructor takes in number of processors that
     * the schedule can utilise and instantiates those.
     * @param numProcessors
     * @param tasksInOrder TaskNode in order (the topology)
     */
    public Schedule(int numProcessors, List<TaskNode> tasksInOrder) throws ProcessorException {
        _processorList = new ArrayList<>();
        _taskNodeList = new ArrayList<>();
        _unassignedTaskList = new ArrayList<>();
        _assignedTaskList = new ArrayList<>();

        for (int i = 1; i <= numProcessors; i++) {
            Processor processor = new Processor(i);
            _processorList.add(processor);
        }

        if (tasksInOrder != null) {
            _taskNodeList.addAll(tasksInOrder);
            _unassignedTaskList.addAll(tasksInOrder);
        }
    }

    public List<TaskNode> getTaskNodeList(){
        return _taskNodeList;
    }

    /**
     * To get the desired processor, please minus one for the index. E.g. to get processer one, you must .get(0) instead.
     * @return List of {@link Processor}
     */
    public List<Processor> getProcessors() {
        return _processorList;
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

        _unassignedTaskList.remove(task);
        _assignedTaskList.add(task);
    }
}
