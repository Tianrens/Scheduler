package group8.parser;

import group8.models.Graph;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OutputGraphTests {
    IDOTDataParser _dataParser;
    Graph graph;
    Graph noEdgesGraph;
    Graph emptyGraph;

    @Before
    public void setUpParser() {
        _dataParser = new DOTDataParser();
        emptyGraph = new Graph();
        noEdgesGraph = new Graph();
        graph = new Graph();
    }

    @Before
    public void generateGraph() {

    }

    @Before
    public void generateNoEdgesGraph() {

    }

    @Test
    public void ValidOutputDOTSyntaxTest() {

    }

    @Test
    public void OutputDOTFileMatchGraphTest() {

    }

    @Test
    public void NoEdgesTest() {

    }

    @Test
    public void EmptyGraphTest() {

    }
}
