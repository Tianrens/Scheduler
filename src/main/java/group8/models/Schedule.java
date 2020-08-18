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
     */
    private int _heuristicCost = -1;
    /**
     * HashMap storing nodes in the schedule.
     * Key is nodeID.
     * Value is a String array, first element is start time, second element is processor.
     */
    private Map<String, String[]> _nodes = new HashMap<>();
    /**
     * Int array of processors. Where the arr[Index] = earliest start time.
     * Processors start at 0.
     */
    private int[] _processors = new int[AppConfig.getInstance().getNumProcessors()];

    public Schedule() {
        for (int i = 0; i < AppConfig.getInstance().getNumProcessors(); i++) {
            _processors[i] = -1;
        }
    }

    public void setNode(String nodeId, String startTime, String processor) throws ScheduleException {
        String[] value = {startTime, processor};
        if (_nodes.containsKey(nodeId)) {
            throw new ScheduleException("NodeId already in map.");
        }
        _nodes.put(nodeId, value);
    }

    public void setProcessorStartTime(int processor, int startTime) throws ScheduleException {
        int oldStartTime = _processors[processor];
        if (oldStartTime > startTime) {
            throw new ScheduleException("Invalid Start time of processor");
        }
        _processors[processor] = startTime;
    }

    protected void computeProcessorStartTimes() throws ScheduleException {
        for (String key : _nodes.keySet()) {
            if (_nodes.get(key) == null) {
                throw new ScheduleException("Null Value for this key, in Nodes HashMap");
            }
            String[] value = _nodes.get(key);
            int processorId = Integer.parseInt(value[0]);
            int startTime = Integer.parseInt((value[1]));

            setProcessorStartTime(processorId, startTime);
        }
    }


    public int getHeuristicCost() {
        return _heuristicCost;
    }

    public void setHeuristicCost(int heuristicCost) {
        _heuristicCost = heuristicCost;
    }

    public Map<String, String[]> get_nodes() {
        return _nodes;
    }

    private void set_nodes(Map<String, String[]> _nodes) {
        this._nodes = _nodes;
    }

    public int[] get_processors() {
        return _processors;
    }

    private void set_processors(int[] _processors) {
        this._processors = _processors;
    }
}
