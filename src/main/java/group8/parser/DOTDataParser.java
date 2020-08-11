package group8.parser;

import group8.cli.AppConfig;
import group8.models.Schedule;
import group8.models.TaskNode;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DOTDataParser implements IDOTDataParser {

    // Some regex expressions for checking validity of input.
    private final String _idAcceptLang = "/(\\w)+/g";
    private final String _graphType = "/(?i)\\b(digraph)\\b/g";
    private final String _graphNameAcceptLang = "/\"(\\w)+\"/g";
    private final String _attrAcceptLang = "/(?i)\\b(Weight)\\b/g";
    private final String _startOfStatements = "{";
    private final String _endOfStatements = "}";

    /**
     * Accesses against DOT syntax loosely based on GraphViz DOT syntax. This method ASSUMES elements of .dot file to
     * be separated by ONE whitespace character.
     * @param line String to parse
     * @return List of extracted graph data.
     */
    @Override
    public List<String> parseStringLine(String line) {
        ArrayList<String> graphData = new ArrayList<>();

        String[] stringElements = line.split(" ");

        if (line.contains(_startOfStatements)) {
            graphData.add(stringElements[1].trim()); // [1] is the name
        } else {
            graphData.addAll(parseNodes(stringElements[0].trim())); // [0] are the nodes
            graphData.add(parseAttr(stringElements[1].trim())); // [1] is the weight attribute (key + value)
        }

        return graphData;
    }

    /**
     * This function is responsible for parsing the valid schedule as a dot file output
     * This function prints out all Nodes first, then edges are printed out to the dot file
     * @param filePath This is either specified by the user or the default value is output.dot
     * @param schedule More about the schedule sobject can be found in the documentation of the class
     */
    @Override
    public void parseOutput(String filePath, Schedule schedule) {
        File outputFile = AppConfig.getInstance().getOutputFile();
        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)))){
            out.write("digraph {");
            out.newLine();
            List<TaskNode> taskNodeList  = schedule.getTaskNodeList();

            //This string is written out last because all nodes must be declared before edges
            String edgeList = "";

            // The for loop cycles through all takesNodes and prints them out + their edges
            for(TaskNode task : taskNodeList){

                //This prints out all nodes their weights, processor and start time
                out.write(task.getId() + " " + "[ Weight=" + task.getCost() + ", Start=" + task.getTimeScheduled()
                        + ", Processor=" + task.getProcessor().getId());
                out.newLine();

                //This concats all edges a node has and adds then to print later
                for(Map.Entry edge : task.getEdgeList().entrySet()){
                    edgeList+= task.getId() + " -> " + edge.getKey() + "[ Weight=" + edge.getKey() + " ]\n";
                }
            }

            //all edges are written out to the dot file
            out.write(edgeList);
            out.write("}");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Helper method to parse nodes out of a string
     * @param processingString
     * @return List of nodes (id)
     */
    private List<String> parseNodes(String processingString) {
        String[] nodes;

        if (processingString.contains("->")) {
            nodes = processingString.split("->");
        } else {
            nodes = new String[] {
                    processingString
            };
        }

        return Arrays.asList(nodes);
    }

    /**
     * Helper method to parse the attribute value out of a string, in between [Weight= and ]
     * @param processingString
     * @return Attribute value (not the key)
     */
    private String parseAttr(String processingString) {
        int posEquals = processingString.indexOf("=");
        int posClosingBrace = processingString.indexOf("]");

        int start = posEquals + 1;
        int end = posClosingBrace;
        return processingString.substring(start, end);
    }
}
