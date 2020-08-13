package group8.parser;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.TaskNode;
import org.junit.Test;

import java.io.File;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExternalGraphGeneratorTests {

    @Test
    public void DefaultGraphTest() {
        Set<String> expectedOutput = new HashSet<>(Arrays.asList(
                "node a 2",
                "node b 3",
                "node c 3",
                "node d 2",
                "edge a b 1",
                "edge a c 10",
                "edge b d 2",
                "edge c d 1"
        ));

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

        Set<String> output = new HashSet<>();
        for (TaskNode node : arr) {
            output.add("node " + node.getId() + " " + node.getCost());

            for (TaskNode edge : node.getEdgeList().keySet()) {
                output.add("edge " + node.getId() + " " + edge.getId() + " " + node.getEdgeList().get(edge));
            }
        }

        assertEquals(expectedOutput, output);
    }

    @Test
    public void OneNodeGraph() {
        Set<String> expectedOutput = new HashSet<>(Arrays.asList(
                "node a 2"
        ));
        File inputFile = new File(this.getClass().getResource("OneNodeGraph.dot").getPath());
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

        Set<String> output = new HashSet<>();
        for (TaskNode node : arr) {
            output.add("node " + node.getId() + " " + node.getCost());

            for (TaskNode edge : node.getEdgeList().keySet()) {
                output.add("edge " + node.getId() + " " + edge.getId() + " " + node.getEdgeList().get(edge));
            }
        }

        assertEquals(expectedOutput, output);
    }

    @Test
    public void OneLevelGraph() {
        Set<String> expectedOutput = new HashSet<>(Arrays.asList(
                "node a 2",
                "node b 3",
                "node c 3",
                "edge a b 1",
                "edge a c 10"
        ));
        File inputFile = new File(this.getClass().getResource("OneLevelGraph.dot").getPath());
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

        Set<String> output = new HashSet<>();
        for (TaskNode node : arr) {
            output.add("node " + node.getId() + " " + node.getCost());

            for (TaskNode edge : node.getEdgeList().keySet()) {
                output.add("edge " + node.getId() + " " + edge.getId() + " " + node.getEdgeList().get(edge));
            }
        }

        assertEquals(expectedOutput, output);
    }

    @Test
    public void OneLineGraph() {
        Set<String> expectedOutput = new HashSet<>(Arrays.asList(
                "node a 2",
                "node b 3",
                "node c 3",
                "edge a b 1",
                "edge b c 10"
        ));
        File inputFile = new File(this.getClass().getResource("OneLineGraph.dot").getPath());
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

        Set<String> output = new HashSet<>();
        for (TaskNode node : arr) {
            output.add("node " + node.getId() + " " + node.getCost());

            for (TaskNode edge : node.getEdgeList().keySet()) {
                output.add("edge " + node.getId() + " " + edge.getId() + " " + node.getEdgeList().get(edge));
            }
        }

        assertEquals(expectedOutput, output);
    }

    @Test
    public void EmptyGraph() {
        Set<String> expectedOutput = new HashSet<>(Arrays.asList(
        ));
        File inputFile = new File(this.getClass().getResource("EmptyGraph.dot").getPath());
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

        Set<String> output = new HashSet<>();
        for (TaskNode node : arr) {
            output.add("node " + node.getId() + " " + node.getCost());

            for (TaskNode edge : node.getEdgeList().keySet()) {
                output.add("edge " + node.getId() + " " + edge.getId() + " " + node.getEdgeList().get(edge));
            }
        }

        assertEquals(expectedOutput, output);
    }

    @Test
    public void EdgeBeforeNodeGraph() {
        Set<String> expectedOutput = new HashSet<>(Arrays.asList(
                "node a 2",
                "node b 3",
                "edge a b 1"
        ));
        File inputFile = new File(this.getClass().getResource("EdgeBeforeNodeGraph.dot").getPath());
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

        Set<String> output = new HashSet<>();
        for (TaskNode node : arr) {
            output.add("node " + node.getId() + " " + node.getCost());

            for (TaskNode edge : node.getEdgeList().keySet()) {
                output.add("edge " + node.getId() + " " + edge.getId() + " " + node.getEdgeList().get(edge));
            }
        }

        assertEquals(expectedOutput, output);
    }
}
