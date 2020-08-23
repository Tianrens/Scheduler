package group8.visualisation;

import group8.cli.AppConfig;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.Test;

public class VisualisationTest extends Application {
    @Test
    public void visualTest() {
        AppConfig config = AppConfig.getInstance();
        config.setNumProcessors(4);
        launch();
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
