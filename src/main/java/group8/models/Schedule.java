package group8.models;

import group8.cli.AppConfig;

import java.util.*;

/**
 * This class contains the methods and fields to mimic
 * a real world schedule
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
    private Map<String, int[]> _nodes = new HashMap<>();
    /**
     * Int array of processors. Where the arr[Index] = earliest start time.
     * Processors start at 0.
     */
    private int[] _processors = new int[AppConfig.getInstance().getNumProcessors()];

    /**
     * Creates schedule object.
     * Sets all processors start time to -1.
     */
    public Schedule() {
        for (int i = 0; i < AppConfig.getInstance().getNumProcessors(); i++) {
            _processors[i] = -1;
        }
    }

    /**
     * Sets a node in the _nodes map.
     * @param nodeId the key
     * @param startTime the first value of array
     * @param processor the second value of array
     */
    public void setNode(String nodeId, int startTime, int processor) throws ScheduleException {
        int[] value = new int[]{startTime, processor};
//        if (_nodes.containsKey(nodeId)) {
//            throw new ScheduleException("NodeId already in map.");
//        }
        _nodes.put(nodeId, value);
    }

    /**
     * Set the start time of a processor
     * @param processor the processor. Processors start at 0 index!
     * @param startTime the start time of the processor.
     * @throws ScheduleException
     */
    public void setProcessorStartTime(int processor, int startTime) throws ScheduleException {
//        int oldStartTime = _processors[processor];
//        // Can remove for memory.
//        if (oldStartTime > startTime) {
//            throw new ScheduleException("Invalid Start time of processor");
//        }
        _processors[processor] = startTime;
    }

    /**
     * Calculates the start time of each processor in _processors.
     * Iterates through the _nodes map, and calcualtes the start time of each processor.
     * @throws ScheduleException
     */
    protected void computeProcessorStartTimes() throws ScheduleException {
        for (String key : _nodes.keySet()) {
//            // Can remove for memory.
//            if (_nodes.get(key) == null) {
//                throw new ScheduleException("Null Value for this key, in Nodes HashMap");
//            }
            int[] value = _nodes.get(key);
            int processorId = value[0];
            int startTime = value[1];

            setProcessorStartTime(processorId, startTime);
        }
    }


    public int getHeuristicCost() {
        return _heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        _heuristicCost = heuristicCost;
    }

    public Map<String, int[]> get_nodes() {
        return _nodes;
    }

    private void set_nodes(Map<String, int[]> _nodes) {
        this._nodes = _nodes;
    }

    public int[] get_processors() {
        return _processors;
    }

    private void set_processors(int[] _processors) {
        this._processors = _processors;
    }
}
