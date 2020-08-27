package group8.algorithm;

import group8.models.Node;
import group8.models.Schedule;

import java.util.HashMap;

/**
 * Interface for heuristics used to calculate an estimate from a given state to the final complete schedule
 */
public interface IHeuristic {

    double calculateEstimate(Schedule state, HashMap<String, Node> allNodes);
}
