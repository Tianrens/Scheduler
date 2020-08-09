package group8;

import group8.cli.AppConfig;
import group8.cli.AppConfigBuilder;
import group8.cli.CLIException;

public class Main {

    private static AppConfig _appConfig;

    public static void main(String[] args) {
       _appConfig = buildAppConfig(args);
    }

    private static AppConfig buildAppConfig(String[] args) {
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            return cli.build();
        } catch (CLIException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            System.exit(-1);
            return null;
        }
    }
}
