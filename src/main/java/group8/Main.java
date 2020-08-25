package group8;

import group8.cli.AppConfig;
import group8.cli.AppConfigBuilder;
import group8.cli.AppConfigException;
import group8.cli.CLIException;
import group8.models.Graph;
import group8.models.ProcessorException;
import group8.models.Schedule;
import group8.parser.*;
import group8.scheduler.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private static AppConfig _appConfig;

    public static void main(String[] args) throws AppConfigException, ProcessorException {
        _appConfig = buildAppConfig(args);
        if (AppConfig.getInstance().isVisualise()) { // Using Visualisation
            launch();
        } else {

        }

        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
//        IScheduler scheduler = new AStarScheduler();
//        Graph graph = externalGraphGenerator.generate();
//
//        Schedule schedule = scheduler.generateValidSchedule(graph);
//
//        IDOTFileWriter outputBuilder = new DOTFileWriter();
//        outputBuilder.writeOutput(schedule, graph);
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
