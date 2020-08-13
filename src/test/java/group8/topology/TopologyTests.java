package group8.topology;

import group8.models.Graph;
import group8.models.TaskNode;
import group8.parser.DOTDataParser;
import group8.parser.GraphGenerator;
import group8.scheduler.TopologyFinder;
import org.junit.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


public class TopologyTests {


    private Graph _graph;
    private Graph _graph1;
    private Graph _graph2;
    private Graph _graph3;
    private Graph _graph4;

    /**
     * The graph for this test is a simple line of nodes
     * with edges between
     */
    @Test
    public void SimpleTopologyTest() {
        List<String> a = new ArrayList<String>();
        a.add("a");
        a.add("3");
        List<String> b = new ArrayList<String>();
        b.add("b");
        b.add("2");
        List<String> c = new ArrayList<String>();
        c.add("c");
        c.add("1");
        List<String> d = new ArrayList<String>();
        d.add("d");
        d.add("3");
        List<String> e = new ArrayList<String>();
        e.add("e");
        e.add("2");
        List<String> f = new ArrayList<String>();
        f.add("f");
        f.add("1");

        List<String> edge1 = new ArrayList<String>();
        edge1.add("a");
        edge1.add("b");
        edge1.add("1");
        List<String> edge2 = new ArrayList<String>();
        edge2.add("b");
        edge2.add("c");
        edge2.add("1");
        List<String> edge3 = new ArrayList<String>();
        edge3.add("c");
        edge3.add("d");
        edge3.add("1");
        List<String> edge4 = new ArrayList<String>();
        edge4.add("d");
        edge4.add("e");
        edge4.add("1");
        List<String> edge5 = new ArrayList<String>();
        edge5.add("e");
        edge5.add("f");
        edge5.add("1");

        GraphGenerator generator = new GraphGenerator(new DOTDataParser());
        generator.addData(a);
        generator.addData(b);
        generator.addData(c);
        generator.addData(d);
        generator.addData(e);
        generator.addData(f);
        generator.addData(edge1);
        generator.addData(edge2);
        generator.addData(edge3);
        generator.addData(edge4);
        generator.addData(edge5);

        _graph = generator.getGraph();

        TopologyFinder topologyFinder = new TopologyFinder();
        List<TaskNode> computedTopology = topologyFinder.generateTopology(_graph);
        TaskNode[] arrayOfTaskNodes = {
                new TaskNode(3, "a"),
                new TaskNode(2, "b"),
                new TaskNode(1, "c"),
                new TaskNode(3, "d"),
                new TaskNode(2, "e"),
                new TaskNode(1, "f")};


        List<TaskNode> validTopology = Arrays.asList(arrayOfTaskNodes);

        for (int i = 0; i < computedTopology.size(); i++) {
            assertEquals(computedTopology.get(i).getId(), validTopology.get(i).getId());
        }

    }

    @Test
    public void ComplexTopologyTest() {
        List<String> a = new ArrayList<String>();
        a.add("a");
        a.add("3");
        List<String> b = new ArrayList<String>();
        b.add("b");
        b.add("2");
        List<String> c = new ArrayList<String>();
        c.add("c");
        c.add("1");
        List<String> d = new ArrayList<String>();
        d.add("d");
        d.add("3");
        List<String> e = new ArrayList<String>();
        e.add("e");
        e.add("2");
        List<String> f = new ArrayList<String>();
        f.add("f");
        f.add("1");

        List<String> edge1 = new ArrayList<String>();
        edge1.add("a");
        edge1.add("b");
        edge1.add("1");
        List<String> edge2 = new ArrayList<String>();
        edge2.add("a");
        edge2.add("c");
        edge2.add("1");
        List<String> edge3 = new ArrayList<String>();
        edge3.add("c");
        edge3.add("f");
        edge3.add("1");
        List<String> edge4 = new ArrayList<String>();
        edge4.add("f");
        edge4.add("e");
        edge4.add("1");
        List<String> edge5 = new ArrayList<String>();
        edge5.add("b");
        edge5.add("e");
        edge5.add("1");
        List<String> edge6 = new ArrayList<String>();
        edge6.add("b");
        edge6.add("d");
        edge6.add("1");
        List<String> edge7 = new ArrayList<String>();
        edge7.add("e");
        edge7.add("d");
        edge7.add("1");
        List<String> edge8 = new ArrayList<String>();
        edge8.add("c");
        edge8.add("b");
        edge8.add("1");
        List<String> edge9 = new ArrayList<String>();
        edge9.add("f");
        edge9.add("b");
        edge9.add("1");

        GraphGenerator generator1 = new GraphGenerator(new DOTDataParser());
        generator1.addData(a);
        generator1.addData(b);
        generator1.addData(c);
        generator1.addData(d);
        generator1.addData(e);
        generator1.addData(f);
        generator1.addData(edge1);
        generator1.addData(edge2);
        generator1.addData(edge3);
        generator1.addData(edge4);
        generator1.addData(edge5);
        generator1.addData(edge6);
        generator1.addData(edge7);
        generator1.addData(edge8);
        generator1.addData(edge9);

        _graph1 = generator1.getGraph();

        TopologyFinder topologyFinder = new TopologyFinder();
        List<TaskNode> computedTopology = topologyFinder.generateTopology(_graph1);
        TaskNode[] arrayOfTaskNodes = {
                new TaskNode(3, "a"),
                new TaskNode(2, "c"),
                new TaskNode(1, "f"),
                new TaskNode(1, "b"),
                new TaskNode(2, "e"),
                new TaskNode(3, "d")};


        List<TaskNode> validTopology = Arrays.asList(arrayOfTaskNodes);

        for (int i = 0; i < computedTopology.size(); i++) {
            assertEquals(computedTopology.get(i).getId(), validTopology.get(i).getId());
        }

    }

    /**
     * The graph for this test is node "a" is dependent on every other node
     * no other dependencies are in place
     */
    @Test
    public void DependencyTopologyTest() {
        List<String> a = new ArrayList<String>();
        a.add("a");
        a.add("3");
        List<String> b = new ArrayList<String>();
        b.add("b");
        b.add("2");
        List<String> c = new ArrayList<String>();
        c.add("c");
        c.add("1");
        List<String> d = new ArrayList<String>();
        d.add("d");
        d.add("3");
        List<String> e = new ArrayList<String>();
        e.add("e");
        e.add("2");
        List<String> f = new ArrayList<String>();
        f.add("f");
        f.add("1");

        List<String> edge1 = new ArrayList<String>();
        edge1.add("b");
        edge1.add("a");
        edge1.add("1");
        List<String> edge2 = new ArrayList<String>();
        edge2.add("c");
        edge2.add("a");
        edge2.add("1");
        List<String> edge3 = new ArrayList<String>();
        edge3.add("d");
        edge3.add("a");
        edge3.add("1");
        List<String> edge4 = new ArrayList<String>();
        edge4.add("e");
        edge4.add("a");
        edge4.add("1");
        List<String> edge5 = new ArrayList<String>();
        edge5.add("f");
        edge5.add("a");
        edge5.add("1");

        GraphGenerator generator1 = new GraphGenerator(new DOTDataParser());
        generator1.addData(a);
        generator1.addData(b);
        generator1.addData(c);
        generator1.addData(d);
        generator1.addData(e);
        generator1.addData(f);
        generator1.addData(edge1);
        generator1.addData(edge2);
        generator1.addData(edge3);
        generator1.addData(edge4);
        generator1.addData(edge5);

        _graph2 = generator1.getGraph();

        TopologyFinder topologyFinder = new TopologyFinder();
        List<TaskNode> computedTopology = topologyFinder.generateTopology(_graph2);
        TaskNode[] arrayOfTaskNodes = {
                new TaskNode(3, "b"),
                new TaskNode(2, "c"),
                new TaskNode(1, "d"),
                new TaskNode(1, "e"),
                new TaskNode(2, "f"),
                new TaskNode(3, "a")};


        List<TaskNode> validTopology = Arrays.asList(arrayOfTaskNodes);

        for (int i = 0; i < computedTopology.size(); i++) {
            assertEquals(computedTopology.get(i).getId(), validTopology.get(i).getId());
        }
    }

    /**
     * The graph for this test is just one node
     */
    @Test
    public void OneNodeTopologyTest() {
        List<String> a = new ArrayList<String>();
        a.add("a");
        a.add("3");

        GraphGenerator generator1 = new GraphGenerator(new DOTDataParser());
        generator1.addData(a);

        _graph3 = generator1.getGraph();

        TopologyFinder topologyFinder = new TopologyFinder();
        List<TaskNode> computedTopology = topologyFinder.generateTopology(_graph3);
        TaskNode[] arrayOfTaskNodes = {
                new TaskNode(3, "a"),
        };

        List<TaskNode> validTopology = Arrays.asList(arrayOfTaskNodes);

        for (int i = 0; i < computedTopology.size(); i++) {
            assertEquals(computedTopology.get(i).getId(), validTopology.get(i).getId());
        }
    }

    /**
     * The graph for this test has no edges
     */
    @Test
    public void NoEdgesTopologyTest() {
        List<String> a = new ArrayList<String>();
        a.add("a");
        a.add("3");
        List<String> b = new ArrayList<String>();
        a.add("b");
        a.add("3");
        List<String> c = new ArrayList<String>();
        a.add("c");
        a.add("3");

        GraphGenerator generator1 = new GraphGenerator(new DOTDataParser());
        generator1.addData(a);
        generator1.addData(b);
        generator1.addData(c);

        _graph4 = generator1.getGraph();

        TopologyFinder topologyFinder = new TopologyFinder();
        List<TaskNode> computedTopology = topologyFinder.generateTopology(_graph4);
        TaskNode[] arrayOfTaskNodes = {
                new TaskNode(3, "a"),
                new TaskNode(3, "b"),
                new TaskNode(3, "c")
        };

        List<TaskNode> validTopology = Arrays.asList(arrayOfTaskNodes);

        for (int i = 0; i < computedTopology.size(); i++) {
            assertEquals(computedTopology.get(i).getId(), validTopology.get(i).getId());
        }
    }
}
