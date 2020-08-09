package group8.services;

import group8.cli.AppConfig;
import group8.models.Graph;
import group8.models.Node;

import java.io.*;
import java.util.List;

public class GraphBuilder {
    private final IDOTDataParser _dotParser;

    /**
     * Dependency inject a DOT data parser in
     */
    public GraphBuilder(IDOTDataParser dotDataParser){
        _dotParser = dotDataParser;
    }

    /**
     * @return Graph generated from .dot file
     */
    public Graph build() throws FileNotFoundException {
        File inputFile = AppConfig.getInstance().get_inputFile();

        if (inputFile == null) {
            throw new FileNotFoundException();
        }

        Graph graph = new Graph();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
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
