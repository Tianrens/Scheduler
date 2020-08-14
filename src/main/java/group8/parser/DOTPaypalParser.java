package group8.parser;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import group8.cli.AppConfigException;

import java.io.FileInputStream;
import java.util.Map;

/**
 * An intermediary between the external Paypay DOT parser and our code base. On construction, takes in an inputstream
 * provided by the parent class {@link DOTExternalParser}. This external parser does not have any methods for generating
 * a DOT file back.
 */
public class DOTPaypalParser extends DOTExternalParser<GraphNode, GraphEdge> {
    private GraphParser _parser;

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
