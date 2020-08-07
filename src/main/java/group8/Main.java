package group8;

import group8.cli.AppConfigBuilder;

public class Main {
    public static void main(String[] args) {
        AppConfigBuilder cli = new AppConfigBuilder(args);
        cli.build();
    }
}
