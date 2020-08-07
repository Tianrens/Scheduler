package group8.cli;

import org.apache.commons.cli.*;

import java.io.File;

public class CLIParser {

    private static String[] _args;
    private static AppConfig _config;

    public CLIParser(String[] args) {
        _args = args;
    }

    public void printArgs() {
        for (String arg : _args) {
            System.out.println(arg);
        }
    }

    public AppConfig generateConfig() {
        _config = AppConfig.getInstance();

        Options options = getOptions();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;

        try {
            cmd = parser.parse(options, _args);
        } catch (ParseException e) {
            e.printStackTrace();
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

    private int getNumProcessors(String arg) {
        int numProcessors = Integer.parseInt(_args[1]);
        return numProcessors;
    }

    private Options getOptions() {
        Options options = new Options();
        options.addOption("p", true, "use N cores for execution in parallel (default is sequential)");
        options.addOption("v", false, "visualise the search");
        options.addOption("o", true, "output file is name OUTPUT (default is INPUT-output.dot)");

        return options;
    }

    private File getInputFile(String fileLoc) {
        File file = new File(fileLoc);

        return file;
    }

    private int getNumCores(CommandLine cmd) {
        int numCores = 1;
        if (cmd.hasOption("p")) {
            numCores = Integer.parseInt(cmd.getOptionValue("p"));
        }
        return numCores;
    }

    private boolean getUseVisualisation(CommandLine cmd) {
        boolean useVisualisation = false;
        if (cmd.hasOption("v")) {
            useVisualisation = true;
        }
        return useVisualisation;
    }

    private File getOutputFile(CommandLine cmd) {
        File file = new File(_args[0].substring(0,_args[0].length() - 4) + "-output.dot");
        if (cmd.hasOption("o")) {
            file = new File(cmd.getOptionValue("o"));
        }
        return file;
    }
}
