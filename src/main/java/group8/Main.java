package group8;

import group8.algorithm.AlgorithmState;
import group8.cli.AppConfig;
import group8.cli.AppConfigBuilder;
import group8.cli.AppConfigException;
import group8.cli.CLIException;
import group8.models.Graph;
import group8.models.Node;
import group8.models.ProcessorException;
import group8.models.Schedule;
import group8.parser.*;
import group8.scheduler.*;
import group8.visualisation.AlgorithmStatus;
import group8.visualisation.MainScreenController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {

    private static AppConfig _appConfig;
    private static Graph _graph;

    public static void main(String[] args) throws AppConfigException, ProcessorException {
        _appConfig = buildAppConfig(args);
        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        _graph = graph;

        if (AppConfig.getInstance().isVisualise()) { // Using Visualisation
            // Run algorithm in another thread before launch.
            new Thread(Main::runAlgorithm).start();
            launch();
        } else {
            runAlgorithm();
        }



//        Schedule schedule = scheduler.generateValidSchedule(graph);
//
//        IDOTFileWriter outputBuilder = new DOTFileWriter();
//        outputBuilder.writeOutput(schedule, graph);
//
//        _graph = new Graph();
//        _graph.addNode(new Node(5, "A"));
//        _graph.addNode(new Node(3, "B"));
//
//        AppConfig config = AppConfig.getInstance();
//        AlgorithmStatus status = AlgorithmStatus.getInstance();
//
//        config.setInputFile(new File("Some-Test-File.file"));
//        config.setNumProcessors(3);
//        config.setGraphName("Good Graph");
//        config.setNumCores(8);
//        config.setOutputFile(new File("Output.file"));
//
//
//
//        Schedule schedule = new Schedule();
//        schedule.scheduleTask("A", 0, 0);
//        schedule.scheduleTask("B", 5, 1);
//
//        status.setCurrentBestSchedule(schedule);
//
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                int startTime = 100;
//                while(true) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    status.incrementSchedulesGenerated();
//                    Schedule schedule = null;
//                    try {
//                        schedule = new Schedule();
//                    } catch (AppConfigException e) {
//                        e.printStackTrace();
//                    }
//                    schedule.scheduleTask("A", startTime + 0, 0);
//                    schedule.scheduleTask("B", startTime + 100, 1);
//                    startTime = startTime + 10;
//                    status.setCurrentBestSchedule(schedule);
//
//                }
//
//
//            }
//        });
//        thread.start();
//
//        launch();
    }

    private static AppConfig buildAppConfig(String[] args) {
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            return cli.build();
        } catch (CLIException e) {
            String getHelp = "java -jar scheduler.jar INPUT.dot P [OPTION]" + System.lineSeparator()
                    + "INPUT.dot    a task graph with integer weights in dot format" + System.lineSeparator()
                    + "P            number of processors to schedule the INPUT graph on" + System.lineSeparator()
                    + "Optional:" + System.lineSeparator()
                    + "-p N         use N cores for execution in parallel(default is sequential)" + System.lineSeparator()
                    + "-v           visualise the search" + System.lineSeparator()
                    + "-o OUTPUT    output file is named OUTPUT(default is INPUT-output.dot)";
            System.out.println(e.getMessage() + System.lineSeparator() + getHelp);
            System.exit(-1);
            return null;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("visualisation/MainScreen.fxml"));
        MainScreenController controller = new MainScreenController();
        loader.setController(controller);
        Parent layout = loader.load();

        controller.setGraph(_graph);
        controller.start();

        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("visualisation/MainScreen.css").toExternalForm());

        primaryStage.setTitle("Team 8: GR8 B8 M8");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image(this.getClass().getResource("visualisation/icons/8.png").toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    private static void runAlgorithm() {

    }
}
