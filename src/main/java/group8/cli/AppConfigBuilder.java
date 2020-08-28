package group8.cli;

import org.apache.commons.cli.*;

import java.io.File;

import static group8.cli.CLIConstants.*;

/**
 * Builds a AppConfig class. Parses input parameters from command line input and does error checking, before building
 * App Config instance.
 */
public class AppConfigBuilder {

    private static String[] _args;
    private static AppConfig _config;

    public AppConfigBuilder(String[] args) {
        _args = args;
    }

    /**
     * Debugging purposes
     * Prints out all arguments received from command prompt.
     */
    public void printArgs() {
        for (String arg : _args) {
            System.out.println(arg);
        }
    }

    /**
     * Builds an AppConfig instance with the given command prompt arguments.
     * @return the created AppConfig instance.
     */
    public AppConfig build() throws CLIException {
        AppConfig.clearConfig();
        _config = AppConfig.getInstance();

        Options options = getOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, _args);
        } catch (ParseException e) {
            throw new CLIException("Invalid Syntax.");
        }

        if (cmd == null) {
            throw new CLIException("CMD is null.");
        }

        if (cmd.getArgList().size() < 2) {
            throw new CLIException("You must specify a path to the .dot file and the number of processors to use.");
        }

        File inputDOTFile = getInputFile(_args[0]);
        int numProcessors = getNumProcessors(_args[1]);
        int numCores = getNumCores(cmd);
        boolean useVisualisation = getUseVisualisation(cmd);
        File outputDOTFile = getOutputFile(cmd);

        _config.setInputFile(inputDOTFile);
        _config.setNumCores(numCores);
        _config.setNumProcessors(numProcessors);
        _config.setVisualise(useVisualisation);
        _config.setOutputFile(outputDOTFile);
        return _config;
    }

    /**
     *  Gets the input file.
     *  Checks if input is in correct format.
     * @param filePath path to the file.
     * @return File object of the file.
     * @throws CLIException if the input file is invalid.
     */
    private File getInputFile(String filePath) throws CLIException {
        File file = new File(filePath);
        if (!isDOTExtension(file)) {
            throw new CLIException("Invalid file format, file must be a '.dot' file.");
        }
        if (!file.exists()) {
            throw new CLIException("File not found. Please check the path specified.");
        }

        return file;
    }

    /**
     * Setup options for CommandLine from apache.commons.cli
     * @return Options variable
     */
    private Options getOptions() {
        Options options = new Options();
        Option pOption = new Option(PARALLEL_FLAG, true, PARALLEL_DESC);
        pOption.setArgs(1);
        Option vOption = new Option(VISUALISE_FLAG, false, VISUALISE_DESC);
        Option oOption = new Option(OUTPUT_FLAG, true, OUTPUT_DESC);
        oOption.setArgs(1);
        options.addOption(pOption);
        options.addOption(vOption);
        options.addOption(oOption);

        return options;
    }

    /**
     * Checks if the file extention is '.dot'
     * @param file
     * @return true if it is else otherwise
     */
    private boolean isDOTExtension(File file) {
        if (!file.getName().endsWith(".dot")) {
            return false;
        }
        return true;
    }

    /**
     * Get the number of processors from command line argument.
     * @param arg the second argument passed in
     * @return number of processors.
     */
    private int getNumProcessors(String arg) throws CLIException {
        int numProcessors;
        try {
            numProcessors = Integer.parseInt(_args[1]);
        } catch (NumberFormatException e) {
            throw new CLIException("Invalid number of processors argument.");
        }
        if (numProcessors < 1) {
            throw new CLIException("Number of processors cannot be less than 1.");
        }

        return numProcessors;
    }

    /**
     * Get number of cores.
     * @param cmd
     * @return
     * @throws CLIException if number of cores is invalid.
     */
    private int getNumCores(CommandLine cmd) throws CLIException {
        int numCores = DEFAULT_CORES;
        if (cmd.hasOption(PARALLEL_FLAG)) {
            try {
                numCores = Integer.parseInt(cmd.getOptionValue(PARALLEL_FLAG));
            } catch (NumberFormatException e) {
                throw new CLIException("Invalid number of cores argument.");
            }
        }
        if (numCores < 1) {
            throw new CLIException("Number of cores cannot be less than 1");
        }
        return numCores;
    }

    /**
     * Check if visualisation flag is set
     * @param cmd
     * @return
     */
    private boolean getUseVisualisation(CommandLine cmd) {
        boolean useVisualisation = DEFAULT_VISUALISE;
        if (cmd.hasOption(VISUALISE_FLAG)) {
            useVisualisation = true;
        }
        return useVisualisation;
    }

    /**
     * Get output file
     * @param cmd
     * @return
     * @throws CLIException If output file format is invalid.
     */
    private File getOutputFile(CommandLine cmd) throws CLIException {
        File file = new File(_args[0].substring(0,_args[0].length() - 4) + DEFAULT_OUTPUT_SUFFIX);
        if (cmd.hasOption(OUTPUT_FLAG)) {
            file = new File(cmd.getOptionValue(OUTPUT_FLAG));
        }
        if (!file.getName().endsWith(".dot")) {
            throw new CLIException("Invalid output file format. Must end with '.dot'");
        }
        if (file.exists()) {
            System.out.println("File already exists. The old file will be overwritten.");
        }
        return file;
    }

}
