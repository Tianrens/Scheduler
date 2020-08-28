package group8.algorithm;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Node;
import group8.models.Schedule;
import org.junit.Test;
import java.util.HashMap;
import static org.junit.Assert.assertEquals;

/**
 * Tests suite to check for correct node cost totaling by the SimpleHeuristic Class
 */
public class SimpleHeuristicTests {

    /**
     * Basic test with normal node inputs
     * @throws AppConfigException
     */
    @Test
    public void BasicSimpleTest() throws AppConfigException {
        AppConfig.getInstance().setNumProcessors(2);
        SimpleHeuristic simple = new SimpleHeuristic();
        HashMap<String, Node> nodes = new HashMap<>();
        nodes.put("A", new Node(7, "A"));
        nodes.put("B", new Node(5, "B"));
        nodes.put("C", new Node(12, "C"));
        nodes.put("D", new Node(1, "D"));
        nodes.put("E", new Node(3, "E"));
        nodes.put("F", new Node(45, "F"));
        nodes.put("G", new Node(29, "G"));

        Schedule schedule = new Schedule();
        double result = simple.calculateEstimate(schedule, nodes);

        assertEquals(result, 102, 1);
    }

    /**
     * Test with all nodes having a cost of zero
     * @throws AppConfigException
     */
    @Test
    public void ZeroCostSimpleTest() throws AppConfigException {
        AppConfig.getInstance().setNumProcessors(3);
        SimpleHeuristic simple = new SimpleHeuristic();
        HashMap<String, Node> nodes = new HashMap<>();
        nodes.put("A", new Node(0, "A"));
        nodes.put("B", new Node(0, "B"));
        nodes.put("C", new Node(0, "C"));
        nodes.put("D", new Node(0, "D"));
        nodes.put("E", new Node(0, "E"));
        nodes.put("F", new Node(0, "F"));
        nodes.put("G", new Node(0, "G"));

        Schedule schedule = new Schedule();
        double result = simple.calculateEstimate(schedule, nodes);

        assertEquals(result, 0, 1);
    }

    /**
     * Test that large costs are all summed up correctly
     * @throws AppConfigException
     */
    @Test
    public void LargeCostSimpleTest() throws AppConfigException {
        AppConfig.getInstance().setNumProcessors(3);
        SimpleHeuristic simple = new SimpleHeuristic();
        HashMap<String, Node> nodes = new HashMap<>();
        nodes.put("A", new Node(123456789, "A"));
        nodes.put("B", new Node(7654321, "B"));
        nodes.put("C", new Node(234138, "C"));
        nodes.put("D", new Node(8765654, "D"));
        nodes.put("E", new Node(28371211, "E"));
        nodes.put("F", new Node(46573, "F"));
        nodes.put("G", new Node(23736688, "G"));

        Schedule schedule = new Schedule();
        double result = simple.calculateEstimate(schedule, nodes);

        assertEquals(result, 192265374, 1);
    }
}
