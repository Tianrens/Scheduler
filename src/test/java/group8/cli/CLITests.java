package group8.cli;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class CLITests {

    @Before
    public void init() throws IOException {
        //new File("testResources").mkdirs();
        File testFile = new File("inputFileTest.dot");
        testFile.createNewFile();
    }

    @After
    public void cleanUp(){
        new File("inputFileTest.dot").delete();
    }

    @Test
    public void SimpleCommandLineArguments() throws CLIException {
        String[] args = new String[] {"inputFileTest.dot", "4", "-p", "8", "-v", "-o", "someOUTputDOTFile.dot"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        AppConfig config = cli.build();

        assertEquals("inputFileTest.dot", config.get_inputFile().toString());
        assertEquals(4, config.get_numProcessors());
        assertEquals(8, config.get_numCores());
        assertEquals(true, config.is_visualise());
        assertEquals("someOUTputDOTFile.dot", config.get_outputFile().toString());

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

    //========================= Exception Throw Tests =========================================

    //----------------------- General Input exception tests --------------------------------
    /**
     * Basic test to see if correct exception is thrown when given a null input
     */
    @Test
    public void nullInputTest() {
        AppConfigBuilder cli = new AppConfigBuilder(null);
        try {
            AppConfig config = cli.build();
            fail("Exception should be thrown due to null input");
        } catch (CLIException e) {
            assertEquals("CMD is null",e.getMessage());
        }
    }

    /**
     * Basic test to see if correct exception is thrown when given a no inputs
     */
    @Test
    public void emptyInputTest() {
        String[] args = new String[] {};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Exception should be thrown due to no inputs");
        } catch (CLIException e) {
            assertEquals("You must specify a path to the .dot file and the number of processors to use.",e.getMessage());
        }
    }

    /**
     * Test to see if correct exception is thrown when non-existent options are inputted
     */
    @Test
    public void nonExistentOptionsTest() {
        String[] args = new String[] {"inputFileTest.dot", "4", "-r", "8", "-s", "-o", "someOUTputDOTFile.dot"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Exception should be thrown due to invalid options");
        } catch (CLIException e) {
            assertEquals("Invalid Syntax.",e.getMessage());
        }
    }

    /**
     * Test to see if AppConfig is successfully built despite different options ordering
     */
    @Test
    public void disorderedOptionsTest() throws CLIException {
        String[] args = new String[] {"inputFileTest.dot", "7", "-v", "-o", "someOUTputDOTFile.dot", "-p", "5"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        AppConfig config = cli.build();

        assertEquals("inputFileTest.dot", config.get_inputFile().toString());
        assertEquals(7, config.get_numProcessors());
        assertEquals(5, config.get_numCores());
        assertEquals(true, config.is_visualise());
        assertEquals("someOUTputDOTFile.dot", config.get_outputFile().toString());
    }

    /**
     * Test to see if correct exception is thrown when non-existent options are inputted
     */
    @Test
    public void capitalLetteredOptionsTest() {
        String[] args = new String[] {"inputFileTest.dot", "4", "-P", "8", "-v", "-O", "someOUTputDOTFile.dot"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Exception should be thrown due to invalid options due to their capitalisation");
        } catch (CLIException e) {
            assertEquals("Invalid Syntax.",e.getMessage());
        }
    }

    //------------------- File input exception tests ----------------------------------------------

    /**
     * Basic test for checking if correct exception thrown when a non-existent file is inputted
     */
    @Test
    public void nonExistentFileInput(){
        String[] args = new String[] {"wrongFiletest.dot", "4"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("File not found Exception should be thrown due to no file having the name wrongFileTest.dot");
        } catch (CLIException e) {
            assertEquals("File not found. Please check the path specified.",e.getMessage());
        }
    }

    /**
     * Tests if correct exception is thrown when a non .dot file is inputted
     * @throws IOException
     */
    @Test
    public void inValidFileInput() throws IOException {
        File testFile = new File("inputFileTest.txt");
        testFile.createNewFile();

        String[] args = new String[] {"inputFileTest.txt", "5", "-p", "7", "-v", "-o", "someOUTputDOTFile.dot"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Invalid file Exception should be thrown due as only dot files are accepted");
        } catch (CLIException e) {
            assertEquals("Invalid file format, file must be a '.dot' file.",e.getMessage());
        }

        new File("inputFileTest.txt").delete();
    }

    /**
     * Test to check if cases matter in input file name
     */
    @Test
    public void lowerCaseFileInput(){
        String[] args = new String[] {"inputfiletest.dot", "4"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("File not found Exception should be thrown due to lower casing of inputfiletest.dot");
        } catch (CLIException e) {
            assertEquals("File not found. Please check the path specified.",e.getMessage());
        }
    }

    //------------------- processor argument exception checks -----------------------------------
    /**
     * Test to check non-number process number argument
     */
    @Test
    public void invalidProcessesInput(){
        String[] args = new String[] {"inputFileTest.dot", "one"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Invalid input for number of processes, exception should be thrown");
        } catch (CLIException e) {
            assertEquals("Invalid number of processors argument.",e.getMessage());
        }
    }

    /**
     * Test to check 0 process argument
     */
    @Test
    public void ZeroProcessesInput(){
        String[] args = new String[] {"inputFileTest.dot", "0"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Can't have no processes, exception should be thrown");
        } catch (CLIException e) {
            assertEquals("Invalid number of processors argument.",e.getMessage());
        }
    }

    /**
     * Test to check negative process arguments
     */
    @Test
    public void negativeProcessesInput(){
        String[] args = new String[] {"inputFileTest.dot", "-2"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Can't have negative number of processes, exception should be thrown");
        } catch (CLIException e) {
            assertEquals("Number of processors cannot be less than 0.",e.getMessage());
        }
    }

    /**
     * Test to check a double type process argument
     */
    @Test
    public void doubleArgProcessesInput(){
        String[] args = new String[] {"inputFileTest.dot", "2.0"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Can't non-int arguments for processes, exception should be thrown");
        } catch (CLIException e) {
            assertEquals("Invalid number of processors argument.",e.getMessage());
        }
    }

    //----------------------- Core/Parallelization argument exception checks -----------------
    /**
     * Test to check an invalid (non-int) core argument
     */
    @Test
    public void invalidCoreInput(){
        String[] args = new String[] {"inputFileTest.dot", "2", "-p", "p"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Can't non-int arguments for number of cores, exception should be thrown");
        } catch (CLIException e) {
            assertEquals("Invalid number of cores argument.",e.getMessage());
        }
    }

    /**
     * Test to check an invalid (non-int) core argument
     */
    @Test
    public void zeroCoreInput(){
        String[] args = new String[] {"inputFileTest.dot", "2", "-p", "0"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Can't have less than one number of cores, exception should be thrown");
        } catch (CLIException e) {
            assertEquals("Number of cores cannot be less than 1",e.getMessage());
        }
    }

    /**
     * Test to check an invalid (non-int) core argument
     */
    @Test
    public void negativeCoreInput(){
        String[] args = new String[] {"inputFileTest.dot", "2", "-p", "-1"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Can't have less than one number of cores, exception should be thrown");
        } catch (CLIException e) {
            assertEquals("Number of cores cannot be less than 1",e.getMessage());
        }
    }

    //-------------------- Output file name exception tests ---------------------------
    /**
     * Test for existing output file
     */
    @Test
    public void existingOutputFile() throws IOException, CLIException {
        File testFile = new File("outputTest.dot");
        testFile.createNewFile();
        String[] args = new String[] {"inputfiletest.dot", "2", "-o", "outputTest.dot"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        AppConfig config = cli.build();

        testFile.delete();
    }

    /**
     * Test to check an invalid output file name argument
     */
    @Test
    public void invalidOutputName(){
        String[] args = new String[] {"inputFileTest.dot", "2", "-o", "outputTest.txt"};
        AppConfigBuilder cli = new AppConfigBuilder(args);
        try {
            AppConfig config = cli.build();
            fail("Can't non-dot file output files, exception should be thrown");
        } catch (CLIException e) {
            assertEquals("Invalid output file format. Must end with '.dot'",e.getMessage());
        }
    }


}
