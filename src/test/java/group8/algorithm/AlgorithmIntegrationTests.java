package group8.algorithm;
import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import group8.parser.*;
import group8.scheduler.AStarScheduler;
import group8.scheduler.IScheduler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.fail;

public class AlgorithmIntegrationTests {


    @Test
    public void firstTest() throws Exception{
        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("defaultGraph.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(4);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));



        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);

        IDOTFileWriter outputBuilder = new DOTFileWriter();
        outputBuilder.writeOutput(schedule, graph);

    }
}

