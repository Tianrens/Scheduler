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
 * This class reads from AppConfig and AlgorithmStatus.
 * This class polls AlgorithmStatus every second and updates the GUI.
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
    private Timeline _timeLine;

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

    @FXML
    private Text _numTasksText;

    /**
     * Initialize this class.
     * Set Text elements and setup Graphs.
     * Cannot use build-in Initialize() method, getGraph must be called before start.
     */
    public void start() {
        //Acquire app and algo instances
        _appConfig = AppConfig.getInstance();
        _algoStatus = AlgorithmStatus.getInstance();

        //_appStatusText.getStyleClass("text");

        // Set App Config values
        _numProcessorsText.setText("Number of Processors: " + _appConfig.getNumProcessors());
        _numCoresText.setText("Number of Cores: " + _appConfig.getNumCores());
        _inputFileText.setText("Input Graph: " + _appConfig.getInputFile().toPath().getFileName().toString());
        _outputGraphText.setText("Output Graph: " + _appConfig.getOutputFile().toPath().getFileName().toString());
        _numTasksText.setText("Tasks: " + _graph.getAllNodes().size());

        _startTime = System.currentTimeMillis();

        setUpGanttChart();

        setupLineGraph();

        _timeLine = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    update();
                    if (_algoStatus.getAlgoState() == AlgorithmState.FINISHED) {
                        System.out.println("stop");
                        _timeLine.stop();


                    }
                })
        );
        _timeLine.setCycleCount(Animation.INDEFINITE);
        _timeLine.play();

    }

    /**
     * This method is run every second in the timeline.
     * Updates Time elapsed, RAM, Graphs and Algorithm Status.
     */
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
                break;
            default:
                _appStatusText.setText("ERROR" );
                break;
        }
        updateGanttChart();
        updateLineGraph();
    }

    /**
     * This method setup the GanttChart.
     * Sets the axis and
     */
    private void setUpGanttChart() {

        int numProcessors = _appConfig.getNumProcessors();

        String[] processors = new String[numProcessors];

        // Set up processors to be displayed in the Gantt Chart GUi.
        // Process of index 0, will be named Process 1 etc...
        for (int i = 0; i < numProcessors; i++) {
            processors[i] = "Processor " + (i + 1);
        }

        // Set up axises
        final NumberAxis xAxis = new NumberAxis();
        final CategoryAxis yAxis = new CategoryAxis();

        final GanttChart<Number,String> chart = new GanttChart<Number,String>(xAxis,yAxis);
        _chart = chart;

        chart.setPrefHeight(600);
        chart.setPrefWidth(700);
        xAxis.setLabel("Time (s)");
        xAxis.setTickLabelFill(Color.BLACK);
        xAxis.setMinorTickCount(5);


        yAxis.setLabel("Processors");
        yAxis.setTickLabelFill(Color.BLACK);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(processors)));

        chart.setTitle("Current Best Schedule");
        chart.setLegendVisible(false);
        chart.setBlockHeight(50);

        //chart.setStyle("-fx-background-color: #dbe2ef");

        chart.getStylesheets().add(getClass().getResource("GanttChart.css").toExternalForm());

        _ganttChartPane.getChildren().addAll(chart);

    }

    /**
     * Updates the gantt chart, clears the current Series/data. And adds a new set of Series to the gantt chart.
     */
    private void updateGanttChart() {
        _chart.getData().clear();

        if (_graph == null || _algoStatus.getCurrentBestSchedule() == null) {
            return;
        }

        int numProcessors = _appConfig.getNumProcessors();

        String[] processors = new String[numProcessors];

        // Name the processes to display. Processor of index 0, will be named Processor 1 etc...
        for (int i = 0; i < numProcessors; i++) {
            processors[i] = "Processor " + (i + 1);
        }

        String processor;

        XYChart.Series[] series = new XYChart.Series[numProcessors];

        Schedule schedule = _algoStatus.getCurrentBestSchedule();
        Map<String, int[]> tasks = schedule.getTasks();

        // Loop through each processor, and for each processor, loop through all tasks.
        // For each processor create a new serie.
        // If the task belongs on this processor, add a new s Data point to the processor serie.
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
        // Add all series to the gantt chart.
        _chart.getData().addAll(series);
    }

    /**
     * Set up the line graph to show schedules generated over time.
     */
    private void setupLineGraph() {
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Time (s)");
        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Schedules Generated");

        LineChart linechart = new LineChart(xAxis,yAxis);

        linechart.setPrefHeight(280);
        linechart.setPrefWidth(280);

        linechart.setCreateSymbols(false);

        XYChart.Series series = new XYChart.Series();
        series.setName("Number of Schedules Generated");

        linechart.getData().add(series);

        _lineChart = linechart;

        _scheduleGenGraph.getChildren().add(linechart);
    }

    /**
     * Add to the current series/data in the line graph, the new time and number of schedules generated.
     */
    private void updateLineGraph() {
        XYChart.Series series = (XYChart.Series) _lineChart.getData().get(0);
        //_lineChart.getData().clear();
        series.getData().add(new XYChart.Data(_currentTime - _startTime, _algoStatus.getNumSchedulesGenerated()));

        //_lineChart.getData().add(series);
    }

    public void setGraph(Graph graph) {
        _graph = graph;
    }



}
