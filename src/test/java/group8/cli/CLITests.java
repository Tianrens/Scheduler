package group8.cli;

import org.junit.Test;
import static org.junit.Assert.*;

public class CLITests {
    @Test
    public void SimpleCommandLineArgument() {
        String[] args = new String[] {"inputFileTest.dot", "4", "-p", "8", "-v", "t", "-o", "someOUTputDOTFile"};
        CLI cli = CLI.getInstance(args);
        CLIConfig config = cli.generateConfig();

        assertEquals("inputFileTest.dot", config.get_inputFile().toString());
    }
}
