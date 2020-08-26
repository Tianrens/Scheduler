package group8.visualisation;

import group8.algorithm.AlgorithmState;
import group8.cli.AppConfig;
import group8.models.Graph;
import group8.models.Node;
import group8.models.Schedule;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.Map;

import static java.lang.Runtime.getRuntime;

/**
 * This is the Controller class for the Main Screen.
 * Color palette:
 * White: #f9f7f7
 * Light Blue: #dbe2ef
 * Blue: #3f72af
 * DarkBlue: #112d4e
 */
public class MainScreenController {

    private AppConfig _appConfig;
    private AlgorithmStatus _algoStatus;
    private long _startTime;
    private long _currentTime;
    private Graph _graph;

    private GanttChart<Number,String> _chart;
    private LineChart _lineChart;

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

    @FXML
    private Pane _scheduleGenGraph;


    public void start() {
        //Acquire app and algo instances
        _appConfig = AppConfig.getInstance();
        _algoStatus = AlgorithmStatus.getInstance();

        //_appStatusText.getStyleClass("text");

        // Set App Config values
        _numProcessorsText.setText("Number of Processors: " + _appConfig.getNumProcessors());
        _numCoresText.setText("Number of Cores: " + _appConfig.getNumCores());
        _inputFileText.setText("Input Graph: " + _appConfig.getInputFile().toString());
        _outputGraphText.setText("Output Graph: " + _appConfig.getOutputFile().toString());

        _startTime = System.currentTimeMillis();

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {

                    update();
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        setUpGanttChart();


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
                _appStatusText.setText("Not Running" );
                break;

            case RUNNING:
                _appStatusText.setText("Running" );
                break;

            case FINISHED:
                _appStatusText.setText("Done" );

            default:
                _appStatusText.setText("ERROR" );
        }
        updateGanttChart();
    }

    private void setUpGanttChart() {

        int numProcessors = _appConfig.getNumProcessors();

        String[] processors = new String[numProcessors];

        for (int i = 0; i < numProcessors; i++) {
            processors[i] = "Processor " + (i + 1);
        }

        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number,String> chart = new GanttChart<Number,String>(xAxis,yAxis);
        _chart = chart;

        chart.setPrefHeight(600);
        chart.setPrefWidth(700);
        xAxis.setLabel("Time");
        xAxis.setTickLabelFill(Color.BLACK);
        xAxis.setMinorTickCount(4);


        yAxis.setLabel("Processors");
        yAxis.setTickLabelFill(Color.BLACK);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(processors)));

        chart.setTitle("Current Best Schedule");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);

        chart.setStyle("-fx-background-color: #dbe2ef");

        chart.getStylesheets().add(getClass().getResource("GanttChart.css").toExternalForm());

        _ganttChartPane.getChildren().addAll(chart);

    }

    private void updateGanttChart() {
        _chart.getData().clear();

        if (_graph == null || _algoStatus.getCurrentBestSchedule() == null) {
            return;
        }

        int numProcessors = _appConfig.getNumProcessors();

        String[] processors = new String[numProcessors];

        for (int i = 0; i < numProcessors; i++) {
            processors[i] = "Processor " + (i + 1);
        }

        String processor;

        XYChart.Series[] series = new XYChart.Series[numProcessors];

        Schedule schedule = _algoStatus.getCurrentBestSchedule();
        Map<String, int[]> tasks = schedule.getTasks();

        // Loop through each processor, and assign the tasks in the schedule into each processor.
        for (int i = 0; i < numProcessors; i++) {
            processor = processors[i];
            XYChart.Series serie = new XYChart.Series();

            for (String key : tasks.keySet()) {
                    int[] value = tasks.get(key);
                    int startTime = value[0];
                    int processorNum = value[1];

                    if (processorNum == i) {
                        Node node = _graph.getNode(key);
                        serie.getData().add(new XYChart.Data(startTime, processor, new GanttChart.ExtraData( node.getCost(), "gantt-chart-bar")));
                    }
                }

            series[i] = serie;
        }

        _chart.getData().addAll(series);
    }

    private void setupLineGraph() {

    }

    public void setGraph(Graph graph) {
        _graph = graph;
    }



}
