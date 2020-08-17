package group8.cli;

/**
 * This exception is thrown if other services or classes use AppConfig without it being fully built.
 */
public class AppConfigException extends Exception {

    public AppConfigException() {
        super("AppConfig has not been built/initialised.");
    }

    public AppConfigException(String s) {
        super(s);
    }
}
