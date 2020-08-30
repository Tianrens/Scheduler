package group8.algorithm;
import group8.cli.AppConfig;
import group8.models.Graph;
import group8.models.Schedule;
import group8.parser.*;
import group8.scheduler.AStarScheduler;
import group8.scheduler.IScheduler;
import org.junit.Test;

import java.io.File;

public class SystemTests {


    @Test
    public void secTest() throws Exception {

        AppConfig.getInstance().setInputFile(new File(this.getClass().getResource("../../../resources/graphs/Nodes_7_OutTree.dot").getPath()));
        AppConfig.getInstance().setNumProcessors(2);
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
