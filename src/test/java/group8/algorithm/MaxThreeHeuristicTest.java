package group8.algorithm;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Node;
import group8.models.Schedule;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * This class tests the MaxThreeHeuristic Class.
 */
public class MaxThreeHeuristicTest {
    /**
     * Tests on an empty schedule
     * @throws AppConfigException
     */
    @Test
    public void MaxThreeTest1() throws AppConfigException {

        AppConfig.getInstance().setNumProcessors(1);
        MaxThreeHeuristic maxThreeHeuristic = new MaxThreeHeuristic();
        HashMap<String, Node> nodes = new HashMap<>();
        nodes.put("A", new Node(5, "A"));
        nodes.put("B", new Node(10, "B"));

        Schedule schedule = new Schedule();
        double result = maxThreeHeuristic.calculateEstimate(schedule, nodes);

        assertEquals(result, 15, 1);
    }

    /**
     * Tests empty schedule with 2 processors.
     * @throws AppConfigException
     */
    @Test
    public void MaxThreeTest2() throws AppConfigException {

        AppConfig.getInstance().setNumProcessors(2);
        MaxThreeHeuristic maxThreeHeuristic = new MaxThreeHeuristic();
        HashMap<String, Node> nodes = new HashMap<>();
        nodes.put("A", new Node(5, "A"));
        nodes.put("B", new Node(10, "B"));

        Schedule schedule = new Schedule();
        double result = maxThreeHeuristic.calculateEstimate(schedule, nodes);

        assertEquals(result, 7, 1);
    }
}
