package group8.services;

import group8.models.Graph;
import group8.models.Node;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GraphGenerator {
    private final DOTParserInterface _dotParser;

    /**
     * Constructor
     */
    public GraphGenerator(DOTParserInterface dotParser){
        _dotParser = dotParser;
    }

    /**
     *
     * @param inputFilePath path to .dot file location
     * @return Graph generated from .dot file
     */
    public Graph getGraph(String inputFilePath) {

        Graph graph = new Graph();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<String> graphData = _dotParser.parseStringLine(line);

                if (graphData.size() == 1) {
                    /** Graph name */

                } else if (graphData.size() == 2) {

                      graph.addNode(new Node(Integer.parseInt(graphData.get(0)), graphData.get(1)));


                } else if (graphData.size() == 3) {
                    // The .dot file input can be assumed to be sequential. Therefore all nodes
                    // will have been previously initialised before they are referenced as an edge


                     Node src = graph.getNode(graphData.get(0));
                     Node dst = graph.getNode(graphData.get(1));
                     src.addDestination(dst, Integer.parseInt(graphData.get(2)));
                     dst.addParentNode(src);

                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return graph;
    }
}
