package group8.parser;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Schedule;
import group8.models.TaskNode;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import static group8.parser.DOTFileConstants.*;

/**
 * This class deals with output parsing
 */
public class DOTFileWriter implements IDOTFileWriter{

    /**
     * This method handles the writing of the schedule to a file
     * @param schedule
     * @throws AppConfigException
     */
    @Override
    public void writeOutput(Schedule schedule) throws AppConfigException {
        File outputFile = AppConfig.getInstance().getOutputFile();
        if (outputFile == null) {
            throw new AppConfigException();
        }

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

                for (Map.Entry<TaskNode, Integer> edge : task.getEdgeList().entrySet()) { //This concats all edges a node has and adds then to print later
                    edgeList += createEdgeString(task, edge);
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
     * Helper method to create a well defined string format for nodes of the graph
     * @param task
     * @return
     */
    private String createNodeString(TaskNode task) {
        StringBuffer sb = new StringBuffer("\t"+task.getId());
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
        sb.append("];");

        return sb.toString();
    }

    /**
     * Helper method to create a well defined string format for edges of the graph
     * @param task
     * @param edge
     * @return
     */
    private String createEdgeString(TaskNode task, Map.Entry<TaskNode, Integer> edge) {
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
