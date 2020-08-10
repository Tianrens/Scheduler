package group8.services;

import group8.models.Schedule;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     *
     * @param filePath This is either specified by the user or the default value is output.dot
     * @param schedule More about the schedule sobject can be found in the documentation of the class
     */
    @Override
    public void parseOutput(String filePath, Schedule schedule) {
        try(BufferedWriter out=new BufferedWriter(new OutputStreamWriter(new FileOutputStream("g.dot")))){
            out.write("digraph {");
            out.newLine();
   
            out.write("}");
        }catch(Exception e){

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
