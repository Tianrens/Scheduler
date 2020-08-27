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

    public static void main(String[] args) throws AppConfigException {
        _appConfig = buildAppConfig(args);
        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        Graph graph = externalGraphGenerator.generate();

        _graph = graph;

        if (AppConfig.getInstance().isVisualise()) { // Using Visualisation
            // Run algorithm in another thread before launch.
            new Thread(Main::runAlgorithm).start();
            launch();
        } else {
            runAlgorithm();
        }

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

    /**
     * Set ups the primary stage. Load MainScreen.fxml. Initialize the main screen.
     * @param primaryStage
     * @throws Exception
     */
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
        primaryStage.setResizable(true);
        primaryStage.getIcons().add(new Image(this.getClass().getResource("visualisation/icons/8.png").toExternalForm()));
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
    }

    /**
     * Start the schedule application.
     */
    private static void runAlgorithm() {

        try {
            IScheduler scheduler = new AStarScheduler();
            Schedule schedule = scheduler.generateValidSchedule(_graph);

            IDOTFileWriter outputBuilder = new DOTFileWriter();
            outputBuilder.writeOutputToConsole(schedule, _graph);

        }catch(Exception e){
        e.printStackTrace();
        }
    }
}
