package group8.visualisation;

import group8.algorithm.AlgorithmState;
import group8.models.Schedule;

/**
 * This class stores information on the current running algorithm.
 * The fields in this class is used to visualisation.
 */
public class AlgorithmStatus {
    private Schedule _currentBestSchedule;
    private long _numSchedulesGenerated = 0;
    private long _numTotalSchedules;
    private AlgorithmState _algoState = AlgorithmState.NOT_RUNNING;

    public Schedule getCurrentBestSchedule() {
        return _currentBestSchedule;
    }

    public void setCurrentBestSchedule(Schedule _currentBestSchedule) {
        this._currentBestSchedule = _currentBestSchedule;
    }

    public long getNumSchedulesGenerated() {
        return _numSchedulesGenerated;
    }

    public void incrementSchedulesGenerated() {
        _numSchedulesGenerated++;
    }

    public void setNumSchedulesGenerated(long _numSchedulesGenerated) {
        this._numSchedulesGenerated = _numSchedulesGenerated;
    }

    public long getNumTotalSchedules() {
        return _numTotalSchedules;
    }

    public void setNumTotalSchedules(long _numTotalSchedules) {
        this._numTotalSchedules = _numTotalSchedules;
    }

    public AlgorithmState getAlgoState() {
        return _algoState;
    }

    public void setAlgoState(AlgorithmState _algoState) {
        this._algoState = _algoState;
    }
}
