package group8.parser;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import group8.models.Graph;
import group8.models.TaskNode;
import java.util.Map;
import static group8.parser.DOTFileConstants.*;

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

        addNodesToGraph(graph, nodes);
        addEdgesToGraph(graph, edges);

        return graph;
    }

    private void addNodesToGraph(Graph graph, Map<String, GraphNode> nodes) {
        for (String nodeId : nodes.keySet()) {
            GraphNode node = nodes.get(nodeId);

            Integer weight = (Integer) node.getAttribute(WEIGHTATTR);

            graph.addNode(new TaskNode(weight, nodeId));
        }
    }

    private void addEdgesToGraph(Graph graph, Map<String, GraphEdge> edges) {
        for (String edgeId : edges.keySet()) {
            GraphEdge edge = edges.get(edgeId);

            TaskNode src = graph.getNode(edge.getNode1().getId());
            TaskNode dst = graph.getNode(edge.getNode1().getId());
            Integer weight = (Integer) edge.getAttribute(WEIGHTATTR);

            src.addDestination(dst, weight);
            dst.addParentNode(src);
        }
    }
}
