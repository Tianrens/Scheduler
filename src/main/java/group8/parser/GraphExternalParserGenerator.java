package group8.parser;

import com.paypal.digraph.parser.GraphEdge;
import com.paypal.digraph.parser.GraphNode;
import com.sun.beans.WeakCache;
import group8.cli.AppConfigException;
import group8.cli.CLIException;
import group8.models.Graph;
import group8.models.Node;

import java.util.Map;
import static group8.parser.DOTFileConstants.*;

/**
 * This generates a graph from the input DOT file using a {@link DOTExternalParser}. It is essentially a converter
 * service between the external parser's implementation of edges and nodes into our code bases' implementation of edges and nodes.
 */
public class GraphExternalParserGenerator implements IGraphGenerator {
    private DOTExternalParser _parser;

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

        //calculate bottom level for all nodes - later used in heurisitc calculations
        for(Node node : graph.getAllNodes().values()){
            if(node.getBottomLevel()==-1){
                node.calculateBottomLevel();
            }
        }

        return graph;
    }

    private void addNodesToGraph(Graph graph, Map<String, GraphNode> nodes) {
        for (String nodeId : nodes.keySet()) {
            GraphNode node = nodes.get(nodeId);

            Map<String, Object> attrs = node.getAttributes();
            String weightValue = (String) attrs.entrySet().iterator().next().getValue();

            Integer weight = Integer.parseInt(weightValue); // Retrieve the relevant attribute (Cost)

            Node newNode = new Node(weight, nodeId);
            graph.addNode(newNode);
        }
    }

    private void addEdgesToGraph(Graph graph, Map<String, GraphEdge> edges) {
        for (String edgeId : edges.keySet()) {
            GraphEdge edge = edges.get(edgeId);

            Node src = graph.getNode(edge.getNode1().getId());
            Node dst = graph.getNode(edge.getNode2().getId());

            Map<String, Object> attrs = edge.getAttributes();
            String weightValue = (String) attrs.entrySet().iterator().next().getValue();

            Integer weight = Integer.parseInt(weightValue); // ASSUMPTION: each node and graph has one Weight attr.

            src.addDestination(dst, weight);
            dst.addParentNode(src);
        }
    }
}
