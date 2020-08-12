package group8.parser;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.TaskNode;

import java.io.*;
import java.util.List;

public class GraphGenerator implements IGraphGenerator {
    private final IDOTDataParser _dotParser;
    private Graph _graph = new Graph();

    /**
     * Dependency inject a DOT data parser in
     */
    public GraphGenerator(IDOTDataParser dotDataParser){
        _dotParser = dotDataParser;
    }

    /**
     * @return Graph generated from .dot file
     */
    @Override
    public Graph generate() throws AppConfigException {
        File inputFile = AppConfig.getInstance().getInputFile();

        if (inputFile == null) {
            throw new AppConfigException();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            String line;

            while ((line = br.readLine()) != null) {
                List<String> graphData = _dotParser.parseStringLine(line);
                addData(graphData);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return _graph;
    }

    /**
     * This seperates logic and helps with testing, makes the GraphGenerator class more flexible
     * @param graphData
     */
    public void addData(List<String> graphData){
        if (graphData.size() == 1) {
            /** Graph name */
        } else if (graphData.size() == 2) {
            _graph.addNode(new TaskNode(Integer.parseInt(graphData.get(1)), graphData.get(0)));
        } else if (graphData.size() == 3) {
            // The .dot file input can be assumed to be sequential. Therefore all nodes
            // will have been previously initialised before they are referenced as an edge
            TaskNode src = _graph.getNode(graphData.get(0));
            TaskNode dst = _graph.getNode(graphData.get(1));
            src.addDestination(dst, Integer.parseInt(graphData.get(2)));
            dst.addParentNode(src);

        }
    }

    /**
     * @return returns the last generated graph object
     */
    public Graph getGraph(){
        return _graph;
    }

    /**
     * resets the existing graph object, user can build a new graph from scratch
     */
    public void newGraph(){
        _graph=new Graph();
    }

}
