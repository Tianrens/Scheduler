package group8.visualisation;

import group8.algorithm.AlgorithmState;
import group8.cli.AppConfig;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static java.lang.Runtime.getRuntime;

public class MainScreenController {

    private AppConfig _appConfig;
    private AlgorithmStatus _algoStatus;
    private long _startTime;
    private long _currentTime;

    @FXML
    private Text _numProcessorsText;

    @FXML
    private Text _numSchedulesGeneratedText;

    @FXML
    private Text _inputFileText;

    @FXML
    private Text _numCoresText;

    @FXML
    private Text _outputGraphText;

    @FXML
    private Text _timeElapsedText;

    @FXML
    private Text _RAMUsageText;

    @FXML
    private Text _appStatusText;

    @FXML
    private Pane _ganttChartPane;

    public void initialize() {


        _appConfig = AppConfig.getInstance();
        _algoStatus = AlgorithmStatus.getInstance();

        _numProcessorsText.setText("Number of Processors: " + _appConfig.getNumProcessors());
        _numCoresText.setText("Number of Cores: " + _appConfig.getNumCores());
        _inputFileText.setText("Input Graph: " + _appConfig.getInputFile().toString());
        _outputGraphText.setText("Output Graph: " + _appConfig.getOutputFile().toString());

        start();
        
    }

    private void start() {
        _startTime = System.currentTimeMillis();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0.5), e -> {

                    update();
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();


    }

    private void update() {
        _currentTime = System.currentTimeMillis();
        long time = (_currentTime - _startTime)/1000;
        long minutes = time / 60;
        long seconds = time % 60;
        String timeStr = String.format("%d:%02d", minutes, seconds);
        _timeElapsedText.setText("Time Elapsed: " + timeStr);
        _numSchedulesGeneratedText.setText("Schedules Generated: " + _algoStatus.getNumSchedulesGenerated());
        // RAM Usage in bytes. Converted to MegaBytes.
        long ramUsage = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000;
        _RAMUsageText.setText("RAM Usage: " + ramUsage + "MB");

        switch (_algoStatus.getAlgoState()) {
            case NOT_RUNNING:
                _appStatusText.setText("Application Status: Not Running" );
                break;

            case RUNNING:
                _appStatusText.setText("Application Status: Running" );
                break;

            case FINISHED:
                _appStatusText.setText("Application Status: Done" );

            default:
                _appStatusText.setText("Application Status: ERROR" );
        }
    }

    private void updateGanttChart() {

        int numProcessors = _appConfig.getNumProcessors();



    }

}
