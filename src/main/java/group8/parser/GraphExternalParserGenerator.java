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

public class GraphExternalParserGenerator implements IGraphGenerator {
    DOTExternalParser _parser;

    public GraphExternalParserGenerator(DOTExternalParser externalParser) {
        _parser = externalParser;
    }

    @Override
    public Graph generate() throws AppConfigException {
        Graph graph = new Graph();
        return graph;
    }
}
