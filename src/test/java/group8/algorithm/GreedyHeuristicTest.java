package group8.algorithm;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Node;
import group8.models.Schedule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * This class tests the GreedyHeuristic Class.
 */
public class GreedyHeuristicTest {
    /**
     * Tests on an empty schedule
     * @throws AppConfigException
     */
    @Test
    public void GreedyTest() throws AppConfigException {
        AppConfig.getInstance().setNumProcessors(1);
        GreedyHeuristic greedy = new GreedyHeuristic();
        HashMap<String, Node> nodes = new HashMap<>();
        nodes.put("A", new Node(5, "A"));
        nodes.put("B", new Node(10, "B"));

        Schedule schedule = new Schedule();
        double result = greedy.calculateEstimate(schedule, nodes);

        assertEquals(result, 15, 1);
    }

    /**
     * Tests empty schedule with 2 processors.
     * @throws AppConfigException
     */
    @Test
    public void GreedyTest2() throws AppConfigException {
        AppConfig.getInstance().setNumProcessors(2);
        GreedyHeuristic greedy = new GreedyHeuristic();
        HashMap<String, Node> nodes = new HashMap<>();
        nodes.put("A", new Node(5, "A"));
        nodes.put("B", new Node(1, "B"));

        Schedule schedule = new Schedule();
        double result = greedy.calculateEstimate(schedule, nodes);

        assertEquals(result, 6, 1);
    }

    /**
     * Test non empty schedule.
     * @throws AppConfigException
     */
    @Test
    public void GreedyTest3() throws AppConfigException {
        AppConfig.getInstance().setNumProcessors(1);
        GreedyHeuristic greedy = new GreedyHeuristic();
        HashMap<String, Node> nodes = new HashMap<>();
        nodes.put("A", new Node(5, "A"));
        nodes.put("B", new Node(1, "B"));

        Schedule schedule = new Schedule();
        schedule.scheduleTask("A", 0, 0);
        double result = greedy.calculateEstimate(schedule, nodes);

        assertEquals(result, 1, 1);
    }
}
