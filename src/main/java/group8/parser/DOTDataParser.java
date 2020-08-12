package group8.parser;

import group8.cli.AppConfig;
import group8.models.Schedule;
import group8.models.TaskNode;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static group8.parser.DOTFileConstants.*;

public class DOTDataParser implements IDOTDataParser {
    /**
     * This method ASSUMES elements of .dot file to be separated by ONE whitespace character.
     * @param line String to parse
     * @return List of extracted graph data.
     */
    @Override
    public List<String> parseStringLine(String line) {
        ArrayList<String> graphData = new ArrayList<>();

        String[] stringElements = line.split(" ");

        if (line.contains("{")) {
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
            out.write("digraph output_graph {");
            out.newLine();
            List<TaskNode> taskNodeList  = schedule.getTaskNodeList();

            //This string is written out last because all nodes must be declared before edges
            String edgeList = "";

            // The for loop cycles through all takesNodes and prints them out + their edges
            for(TaskNode task : taskNodeList){

                out.write(createNodeString(task)); // This prints out all nodes their weights, processor and start time
                out.newLine();

                for (Map.Entry edge : task.getEdgeList().entrySet()) { //This concats all edges a node has and adds then to print later
                    edgeList += createEdgeString(task, edge);
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

    private String createNodeString(TaskNode task) {
        StringBuffer sb = new StringBuffer(task.getId());
        sb.append(" [");
        sb.append(WEIGHTATTR);
        sb.append("=");
        sb.append(task.getCost());
        sb.append(", ");
        sb.append(STARTATTR);
        sb.append("=");
        sb.append(task.getTimeScheduled());
        sb.append(", ");
        sb.append(PROCESSORATTR);
        sb.append("=");
        sb.append(task.getProcessor().getId());
        sb.append("]");

        return sb.toString();
    }

    private String createEdgeString(TaskNode task, Map.Entry edge) {
        StringBuffer sb = new StringBuffer(task.getId());
        sb.append(" -> ");
        sb.append(edge.getKey());
        sb.append("[");
        sb.append(WEIGHTATTR);
        sb.append("=");
        sb.append(edge.getKey());
        sb.append("]");
        sb.append(System.lineSeparator());

        return sb.toString();
    }
}
