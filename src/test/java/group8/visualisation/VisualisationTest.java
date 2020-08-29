package group8.visualisation;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Node;
import group8.models.Schedule;
import group8.parser.GraphExternalParserGenerator;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

public class VisualisationTest extends Application {

    private static Graph _graph;

    @BeforeClass
    public static void setUpGraph() {
        _graph = new Graph();
        _graph.addNode(new Node(5, "A"));
        _graph.addNode(new Node(3, "B"));
    }

    @Test
    public void visualTest() throws AppConfigException {
        AppConfig config = AppConfig.getInstance();
        AlgorithmStatus status = AlgorithmStatus.getInstance();

        config.setInputFile(new File("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO"));
        config.setNumProcessors(8);
        config.setGraphName("Good Graph");
        config.setNumCores(8);
        config.setOutputFile(new File("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO"));

        Schedule schedule = new Schedule();
        schedule.scheduleTask("A", 0, 0);
        schedule.scheduleTask("B", 5, 1);

        status.setCurrentBestSchedule(schedule);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    status.incrementSchedulesGenerated();

                }


            }
        });
        thread.start();

        launch();


    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("MainScreen.fxml"));
        MainScreenController controller = new MainScreenController();
        loader.setController(controller);
        Parent layout = loader.load();

        controller.setGraph(_graph);
        controller.start();

        Scene scene = new Scene(layout);

        scene.getStylesheets().add(this.getClass().getResource("MainScreen.css").toExternalForm());

        primaryStage.setTitle("Team 8: GR8 B8 M8");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();
        Thread.sleep(1000);
        primaryStage.close();
    }
}
