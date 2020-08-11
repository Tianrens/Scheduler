package group8.parser;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import group8.models.Graph;
import group8.models.TaskNode;
import java.util.Map;

public class GraphExternalParserGenerator implements IGraphGenerator {
    DOTExternalParser _parser;

    public GraphExternalParserGenerator(DOTExternalParser externalParser) {
        _parser = externalParser;
    }

    @Override
    public Graph generate() {
        Graph graph = new Graph();
        Map<String, GraphNode> nodes = _parser.getNodes();
        Map<String, GraphEdge> edges = _parser.getEdges();

        for (String nodeId : nodes.keySet()) {
            GraphNode node = nodes.get(nodeId);

            Integer weight = (Integer) node.getAttribute(DOTFileConstants.WEIGHTATTR);

            graph.addNode(new TaskNode(weight, nodeId));
        }

        // The .dot file input can be assumed to be sequential. Therefore all nodes
        // will have been previously initialised before they are referenced as an edge
        for (String edgeId : edges.keySet()) {
            GraphEdge edge = edges.get(edgeId);

            TaskNode src = graph.getNode(edge.getNode1().getId());
            TaskNode dst = graph.getNode(edge.getNode1().getId());
            Integer weight = (Integer) edge.getAttribute(DOTFileConstants.WEIGHTATTR);

            src.addDestination(dst, weight);
            dst.addParentNode(src);
        }

        return graph;
    }
}
