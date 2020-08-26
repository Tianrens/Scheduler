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
import group8.scheduler.NotParallelAStar;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.fail;

public class AlgorithmIntegrationTests {


//    @Test
//    public void firstTest() throws Exception{
//
//        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("ThirteenNodeGraph.dot").getPath()));
//        AppConfig.getInstance().setNumProcessors(2);
//        AppConfig.getInstance().setNumCores(7);
//        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));
//
//
//        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
//        IScheduler scheduler = new AStarScheduler();
//        Graph graph = externalGraphGenerator.generate();
//
//        Schedule schedule = scheduler.generateValidSchedule(graph);
//
//        IDOTFileWriter outputBuilder = new DOTFileWriter();
//        outputBuilder.writeOutputToConsole(schedule, graph);
//
//
//    }
//
//    @Test
//    public void secTest() throws Exception{
//
//        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("starGraph.dot").getPath()));
//        AppConfig.getInstance().setNumProcessors(4);
//        AppConfig.getInstance().setNumCores(2);
//        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));
//
//
//
//        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
//        IScheduler scheduler = new AStarScheduler();
//        Graph graph = externalGraphGenerator.generate();
//
//        Schedule schedule = scheduler.generateValidSchedule(graph);
//
//        IDOTFileWriter outputBuilder = new DOTFileWriter();
//        outputBuilder.writeOutputToConsole(schedule, graph);
//
//
//
//    }

//    @Test
//    public void thirdTest() throws Exception{
//
//
//        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("sixteenNodeOptimal.dot").getPath()));
//        AppConfig.getInstance().setNumProcessors(2);
//        AppConfig.getInstance().setNumCores(2);
//
//        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));
//
//
//
//        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
//        IScheduler scheduler = new AStarScheduler();
//        Graph graph = externalGraphGenerator.generate();
//
//        Schedule schedule = scheduler.generateValidSchedule(graph);
//
//        IDOTFileWriter outputBuilder = new DOTFileWriter();
//        outputBuilder.writeOutputToConsole(schedule, graph);
//
//
//
//    }

    @Test
    public void fourthTest() throws Exception{

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("test2Graph.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(4);
        AppConfig.getInstance().setNumCores(2);
        AppConfig.getInstance().setOutputFile(new File("defaultGraph-o.dot"));



        IGraphGenerator externalGraphGenerator = new GraphExternalParserGenerator(new DOTPaypalParser());
        IScheduler scheduler = new AStarScheduler();
        Graph graph = externalGraphGenerator.generate();

        Schedule schedule = scheduler.generateValidSchedule(graph);

        IDOTFileWriter outputBuilder = new DOTFileWriter();
        outputBuilder.writeOutputToConsole(schedule, graph);



    }


}

