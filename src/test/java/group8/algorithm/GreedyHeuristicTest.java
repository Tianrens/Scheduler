package group8.algorithm;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;
import group8.models.Node;
import group8.models.Schedule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class GreedyHeuristicTest {
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
}
