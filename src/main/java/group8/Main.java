package group8;

import group8.cli.AppConfig;
import group8.cli.AppConfigBuilder;
import group8.cli.AppConfigException;
import group8.cli.CLIException;
import group8.models.ProcessorException;
import group8.models.Schedule;
import group8.parser.*;
import group8.scheduler.IScheduler;
import group8.scheduler.ITopologyFinder;
import group8.scheduler.OneProcessorScheduler;
import group8.scheduler.TopologyFinder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Main {

    private static AppConfig _appConfig;

    public static void main(String[] args) throws AppConfigException, ProcessorException {
        _appConfig = buildAppConfig(args);

        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        ITopologyFinder topologyFinder = new TopologyFinder();
        IScheduler scheduler = new OneProcessorScheduler(topologyFinder);
        Schedule schedule = scheduler.generateValidSchedule(externalGraphGenerator.generate());
        IDOTFileWriter outputBuilder = new DOTFileWriter();
        outputBuilder.writeOutput(schedule);
    }

    private static AppConfig buildAppConfig(String[] args) {
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            return cli.build();
        } catch (CLIException e) {
            //e.printStackTrace();
            String getHelp = "java -jar scheduler.jar INPUT.jar P [OPTION]" + System.lineSeparator()
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
}
