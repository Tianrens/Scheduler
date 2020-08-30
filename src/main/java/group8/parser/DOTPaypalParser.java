package group8.parser;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.paypal.digraph.parser.GraphParser;
import group8.cli.AppConfig;
import group8.cli.AppConfigException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

/**
 * An intermediary between the external Paypal DOT parser and our code base. On construction, takes in an inputstream
 * provided by the parent class {@link DOTExternalParser}. This external parser does not have any methods for generating
 * a DOT file back.
 *
 * Paypal DOT parser: https://github.com/paypal/digraph-parser.
 */
public class DOTPaypalParser extends DOTExternalParser<GraphNode, GraphEdge> {
    private GraphParser _parser;

    public DOTPaypalParser() throws AppConfigException {
        FileInputStream inputStream = getFileInputStream();
        _parser = new GraphParser(inputStream); // Creates the external Paypal DOT parser

        try {
            inputStream.close(); // Input stream has to be manually closed, as we are borrowing the input stream from parent class.
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("File " + AppConfig.getInstance().getInputFile().toString() + " has been successfully read and parsed");
        System.out.println();
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
