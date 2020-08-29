package group8.parser;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Schedule;
import group8.models.Node;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;

import static group8.models.ScheduleConstants.*;
import static group8.parser.DOTFileConstants.*;

/**
 * A service for writing the final schedule to the output DOT file.
 */
public class DOTFileWriter implements IDOTFileWriter{

    /**
     * Main method for writing the schedule to the output DOT file.
     * @param schedule
     * @throws AppConfigException
     */
    @Override
    public void writeOutput(Schedule schedule, Graph graph) throws AppConfigException {
        File outputFile = AppConfig.getInstance().getOutputFile();
        if (outputFile == null) {
            throw new AppConfigException();
        }

        try(BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile)))){
            out.write("digraph output_graph {");
            out.newLine();
            Map<String, int[]> tasks = schedule.getTasks();

            String edgeList = ""; // This string is written out last because all nodes must be declared before edges

            // The for loop cycles through all takesNodes and prints them out + their edges
            for(Map.Entry<String, int[]> task : tasks.entrySet()){
                Node node = graph.getNode(task.getKey());
                int[] taskScheduleInfo = task.getValue();

                out.write(createNodeString(node, taskScheduleInfo)); // This prints out all nodes their weights, processor and start time
                out.newLine();

                for (Map.Entry<Node, Integer> edge : node.getEdgeList().entrySet()) { //This concatenates all edges a node has and adds then to print later
                    edgeList += createEdgeString(node, edge);
                }
            }

            out.write(edgeList); // All edges are written out to the dot file
            out.write("}");
            out.flush();
            System.out.println(AppConfig.getInstance().getOutputFile().toString() + " has been generated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Prints the output to the console for easy debugging
     * @param schedule
     * @param graph
     * @throws AppConfigException
     */
    public void writeOutputToConsole(Schedule schedule, Graph graph) {
        System.out.println("digraph output_graph {");

        Map<String, int[]> tasks = schedule.getTasks();
        String edgeList = "";
        for(Map.Entry<String, int[]> task : tasks.entrySet()){
            Node node = graph.getNode(task.getKey());
            int[] taskScheduleInfo = task.getValue();

            System.out.println(createNodeString(node, taskScheduleInfo));

                for (Map.Entry<Node, Integer> edge : node.getEdgeList().entrySet()) { //This concatenates all edges a node has and adds then to print later
                    edgeList += createEdgeString(node, edge);
                }
            }
        System.out.println(edgeList);

        System.out.println("}");
    }

    public String writeOutputToString(Schedule schedule, Graph graph) {
        StringBuffer output = new StringBuffer("digraph output_graph {" + System.lineSeparator());

        Map<String, int[]> tasks = schedule.getTasks();
        String edgeList = "";
        for(Map.Entry<String, int[]> task : tasks.entrySet()){
            Node node = graph.getNode(task.getKey());
            int[] taskScheduleInfo = task.getValue();

            output.append(createNodeString(node, taskScheduleInfo));
            output.append(System.lineSeparator());

            for (Map.Entry<Node, Integer> edge : node.getEdgeList().entrySet()) {
                edgeList += createEdgeString(node, edge);
            }
        }
        output.append(edgeList);

        output.append("}");
        return output.toString();
    }

    /**
     * Helper method to create a well defined string format for nodes of the graph
     * @param task
     * @return
     */
    private String createNodeString(Node task, int[] taskScheduleInfo) {
        StringBuffer sb = new StringBuffer("\t"+task.getId());
        sb.append(" [");
        sb.append(WEIGHTATTR);
        sb.append("=");
        sb.append(task.getCost());
        sb.append(", ");
        sb.append(STARTATTR);
        sb.append("=");
        sb.append(taskScheduleInfo[STARTTIMEINDEX]);
        sb.append(", ");
        sb.append(PROCESSORATTR);
        sb.append("=");
        sb.append(taskScheduleInfo[PROCESSORINDEX] + 1); // +1 for original processor number
        sb.append("];");

        return sb.toString();
    }

    /**
     * Helper method to create a well defined string format for edges of the graph
     * @param task
     * @param edge
     * @return
     */
    private String createEdgeString(Node task, Map.Entry<Node, Integer> edge) {
        StringBuffer sb = new StringBuffer("\t" + task.getId());
        sb.append("->");
        sb.append(edge.getKey().getId());
        sb.append(" [");
        sb.append(WEIGHTATTR);
        sb.append("=");
        sb.append(edge.getValue().toString());
        sb.append("];");
        sb.append(System.lineSeparator());

        return sb.toString();
    }
}
