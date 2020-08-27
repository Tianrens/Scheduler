package group8.visualisation;

import group8.algorithm.AlgorithmState;
import group8.models.Schedule;

/**
 * This class stores information on the current running algorithm.
 * The fields in this class are used for visualisation.
 * This class is singleton.
 * Setters and getInstance are thread safe.
 */
public class AlgorithmStatus {
    private static AlgorithmStatus _instance = null;

    private Schedule _currentBestSchedule;
    private long _numSchedulesGenerated = 0;
    // AlgoState can be either NOT_RUNNING RUNNING or FINISHED
    private AlgorithmState _algoState = AlgorithmState.NOT_RUNNING;

    private AlgorithmStatus() {
    }

    // Singleton getInstance
    public static synchronized AlgorithmStatus getInstance() {
        if (_instance == null) {
            _instance = new AlgorithmStatus();
        }
        return _instance;
    }

    public Schedule getCurrentBestSchedule() {
        return _currentBestSchedule;
    }

    public synchronized void setCurrentBestSchedule(Schedule _currentBestSchedule) {
        this._currentBestSchedule = _currentBestSchedule;
    }

    public long getNumSchedulesGenerated() {
        return _numSchedulesGenerated;
    }

    public synchronized void incrementSchedulesGenerated() {
        _numSchedulesGenerated++;
    }

    public synchronized void setNumSchedulesGenerated(long _numSchedulesGenerated) {
        this._numSchedulesGenerated = _numSchedulesGenerated;
    }

    public AlgorithmState getAlgoState() {
        return _algoState;
    }

    public synchronized void setAlgoState(AlgorithmState _algoState) {
        this._algoState = _algoState;
    }
}
