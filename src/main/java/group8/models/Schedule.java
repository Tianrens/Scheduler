package group8.models;

import group8.cli.AppConfig;

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
            _processors[i] = 0;
        }
    }

    /**
     * Sets a node in the _nodes map.
     * @param nodeId the key
     * @param startTime the first value of array
     * @param processor the second value of array
     */
    public void setNode(String nodeId, int startTime, int processor) {
        int[] value = new int[]{startTime, processor};
        _nodes.put(nodeId, value);
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

    public Map<String, int[]> getNodes() {
        return _nodes;
    }

    private void setNodes(Map<String, int[]> _nodes) {
        this._nodes = _nodes;
    }

    public int[] getProcessors() {
        return _processors;
    }

    private void setProcessors(int[] _processors) {
        this._processors = _processors;
    }
}
