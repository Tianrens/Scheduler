package group8.parser;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import group8.cli.AppConfigException;

import java.io.FileInputStream;
import java.util.Map;

public class DOTPaypalParser extends DOTExternalParser<GraphNode, GraphEdge> {
    GraphParser _parser;

    public DOTPaypalParser() throws AppConfigException {
        FileInputStream inputStream = getFileInputStream();
        _parser = new GraphParser(inputStream);
    }

    @Override
    public Map<String, GraphNode> getNodes() {
        return _parser.getNodes();
    }

    @Override
    public Map<String, GraphEdge> getEdges() {
        return _parser.getEdges();
    }
}
