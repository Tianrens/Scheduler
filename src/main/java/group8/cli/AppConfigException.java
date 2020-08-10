package group8.cli;

public class AppConfigException extends Exception {

    public AppConfigException() {
        super("AppConfig has not been built/initialised.");
    }

    public AppConfigException(String s) {
        super(s);
    }
}
