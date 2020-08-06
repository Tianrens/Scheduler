package group8.cli;

import org.apache.commons.cli.*;

import java.io.File;

public class CLI {

    private static CLI _cli;
    private static CLIConfig _config;
    private static String[] _args;

    private CLI(String[] args) {
        _args = args;
    }

    public static CLI getInstance(String[] args) {
        if (_cli == null) {
            _cli = new CLI(args);
        }

        return _cli;
    }

    public void printArgs() {
        for (String arg : _args) {
            System.out.println(arg);
        }
    }

    public CLIConfig generateConfig() {
        _config = new CLIConfig();

        File inputDOTFile = new File(_args[0]);
        int numProcessors = getNumProcessors();
        int numCores = 1;
        boolean useVisualisation = false;
        File outputDOTFile = new File(_args[0].substring(0,_args[0].length() - 3) + "-output.dot");

        Options options = new Options();
        options.addOption("p", true, "use N cores for execution in parallel (default is sequential)");
        options.addOption("v", false, "visualise the search");
        options.addOption("o", true, "output file is name OUTPUT (default is INPUT-output.dot)");

        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        try {
            cmd = parser.parse(options, _args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (cmd.hasOption("p")) {
            numCores = Integer.parseInt(cmd.getOptionValue("p"));
        }
        if (cmd.hasOption("v")) {
            useVisualisation = true;
        }
        if (cmd.hasOption("o")) {
            outputDOTFile = new File(cmd.getOptionValue("o"));
        }

        _config.setInputFile(inputDOTFile);
        _config.setNumCores(numCores);
        _config.setNumProcessors(numProcessors);
        _config.setVisualise(useVisualisation);
        _config.setOutputFile(outputDOTFile);
        return _config;
    }

    private int getNumProcessors() {
        int numProcessors = Integer.parseInt(_args[1]);
        return numProcessors;
    }

    private void getOptional() {

    }

}
