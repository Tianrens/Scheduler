package group8.cli;

import org.junit.Test;
import static org.junit.Assert.*;

public class CLITests {
    @Test
    public void SimpleCommandLineArguments() throws CLIException {
        String[] args = new String[] {"inputFileTest.dot", "4", "-p", "8", "-v", "-o", "someOUTputDOTFile"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        AppConfig config = cli.build();

        assertEquals("inputFileTest.dot", config.get_inputFile().toString());
        assertEquals(4, config.get_numProcessors());
        assertEquals(8, config.get_numCores());
        assertEquals(true, config.is_visualise());
        assertEquals("someOUTputDOTFile", config.get_outputFile().toString());

    }

    @Test
    public void DefaultCommandLineArguments() throws CLIException {
        String[] args = new String[] {"inputFileTest.dot", "4"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        AppConfig config = cli.build();

        assertEquals("inputFileTest.dot" ,config.get_inputFile().toString());
        assertEquals(4, config.get_numProcessors());
        assertEquals(1, config.get_numCores());
        assertEquals(false, config.is_visualise());
        assertEquals("inputFileTest-output.dot", config.get_outputFile().toString());

    }
}
