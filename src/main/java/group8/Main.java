package group8;

import group8.cli.CLI;

public class Main {
    public static void main(String[] args) {
        CLI cli = new CLI(args);
        cli.printArgs();
    }
}
