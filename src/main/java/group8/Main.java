package group8;

import group8.models.Graph;
import group8.services.DOTParser;
import group8.services.GraphGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        GraphGenerator g = new GraphGenerator(new DOTParser());
        Graph gg = g.getGraph("graph.gv");
    }
}
