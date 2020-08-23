package group8.visualisation;

import group8.cli.AppConfig;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;

import java.io.File;

public class VisualisationTest extends Application {
    @Test
    public void visualTest() {
        AppConfig config = AppConfig.getInstance();
        AlgorithmStatus status = AlgorithmStatus.getInstance();

        config.setInputFile(new File("Some-Test-File.file"));
        config.setNumProcessors(4);
        config.setGraphName("Good Graph");
        config.setNumCores(8);
        config.setOutputFile(new File("Output.file"));

        launch();
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            status.incrementSchedulesGenerated();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("MainScreen.fxml"));
        Parent layout = loader.load();


        Scene scene = new Scene(layout);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });
        primaryStage.show();

    }
}
