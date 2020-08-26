package group8.models;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;

import java.util.*;

/**
 * This class contains the methods and fields to mimic a real world schedule and to also represent a state of the state
 * space tree.
 */
public class Schedule {

    /**
     *  Initial cost is -1.
     *  F = G + H
     */
    private int _heuristicCost = -1;
    /**
     * HashMap storing nodes in the schedule.
     * Key is nodeID.
     * Value is a int array, first element is start time, second element is processor.
     */
    private Map<String, int[]> _tasks = new HashMap<>();
    /**
     * Int array of processors. Where the arr[Index] = earliest start time.
     * Processors start at 0.
     */
    private int[] _processors = new int[AppConfig.getInstance().getNumProcessors()];

    /**
     * Creates schedule object.
     * Sets all processors start time to -1.
     */
    public Schedule() throws AppConfigException {
        if (AppConfig.getInstance().getNumProcessors() == 0) {
            throw new AppConfigException();
        }
        for (int i = 0; i < AppConfig.getInstance().getNumProcessors(); i++) {
            _processors[i] = -1;
        }
    }

    /**
     * Schedules a task in this {@link Schedule}.
     * @param nodeId the node id of the task
     * @param startTime start time of the task in this schedule
     * @param processor the processor the task has been assigned to. (processor starts from ZERO)
     */
    public void scheduleTask(String nodeId, int startTime, int processor) {
        int[] value = new int[]{startTime, processor};
        _tasks.put(nodeId, value);
    }

    /**
     * Set the start time of a processor
     * @param processor the processor. Processors start at 0 index!
     * @param startTime the start time of the processor.
     */
    public void setProcessorStartTime(int processor, int startTime) {
        _processors[processor] = startTime;
    }


    public int getHeuristicCost() {
        return _heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        _heuristicCost = heuristicCost;
    }

    public Map<String, int[]> getTasks() {
        return _tasks;
    }

    public void setTasks(Map<String, int[]> _nodes) {
        this._tasks = _nodes;
    }

    public int[] getProcessors() {
        return _processors;
    }

    public void setProcessors(int[] _processors) {
        this._processors = _processors;
    }

    /**
     * This is a calculation of a heuristic. The heuristic is
     * the earliest start time of the schedule
     * @return earliestStartTime
     */
    public int getEarliestStartTime() {
        int shortestLength = -1;

        for(int i = 0; i <_processors.length;i++){
            int length = _processors[i];
            if(shortestLength==-1 || length>shortestLength){
                shortestLength=length;
            }
        }
        return shortestLength;
    }
}
