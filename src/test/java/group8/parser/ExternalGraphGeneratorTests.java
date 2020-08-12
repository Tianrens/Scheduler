package group8.parser;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.TaskNode;
import javafx.concurrent.Task;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

public class ExternalGraphGeneratorTests {

    @Test
    public void DefaultGraphTest() {
        File inputFile = new File(this.getClass().getResource("defaultGraph.dot").getPath());
        AppConfig config = AppConfig.getInstance();
        config.setInputFile(inputFile);

        DOTPaypalParser parser = null;
        try {
            parser = new DOTPaypalParser();
            parser.getFileInputStream();
        } catch (AppConfigException e) {
            fail();
        }
        GraphExternalParserGenerator graphGenerator = new GraphExternalParserGenerator(parser);
        Graph inputGraph = graphGenerator.generate();
        List<TaskNode> arr = new ArrayList<>(inputGraph.getAllNodes().values());

    }
}
