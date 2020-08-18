package group8.cli;

/**
 * This exception is thrown if the input command line arguments is invalid.
 */
public class CLIException extends Exception {
    public CLIException() {
        super();
    }

    public CLIException(String message) {
        super(message);
    }
}
