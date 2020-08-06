package group8.services;

import group8.models.Graph;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GraphGenerator {
    /**
     * Constructor
     */
    GraphGenerator(){

    }

    /**
     *
     * @param inputFilePath path to .dot file location
     * @return Graph generated from .dot file
     */
    public Graph getGraph(String inputFilePath) {

        DOTParserInterface dotParser = new DOTParser();
        Graph graph = new Graph();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFilePath))) {
            String line = br.readLine();

            List<String> graphData = dotParser.parseStringLine(line);

            if (graphData.size() == 1) {
                /** Graph name */

            } else if (graphData.size() == 2) {
                /**WAITING ON IMPLEMENTATION
                 *  graph.addNode(new Node(graphData[0], graphData[1]))
                 */

            } else if (graphData.size() == 3) {
                // The .dot file input can be assumed to be sequential. Therefore all nodes
                // will have been previously initialised before they are referenced as an edge

                /**WAITING ON IMPLEMENTATION
                 *  Node src = graph.getNode(graphData[0]);
                 *  Node dst = graph.getNode(graphData[1]);
                 *  src.addDestination(dst, graphData[2])
                 *  dst.addParent(src)
                 */
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return graph;
    }
}
