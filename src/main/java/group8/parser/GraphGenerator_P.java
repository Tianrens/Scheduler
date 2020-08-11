package group8.parser;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public class GraphGenerator_P implements IGraphGenerator {

    @Override
    public Graph generate() throws AppConfigException {
        File inputFile = AppConfig.getInstance().get_inputFile();

        if (inputFile == null) {
            throw new AppConfigException();
        }

        Graph graph = new Graph();

        try {
            GraphParser parser = new GraphParser(new FileInputStream(inputFile));
            Map<String, GraphNode> nodes = parser.getNodes();
            Map<String, GraphEdge> edges = parser.getEdges();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
