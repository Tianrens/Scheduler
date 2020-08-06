package group8.cli;

public class CLI {

    private static CLI _cliConfig;
    private static CLIConfig _config;
    private static String[] _args;

    private CLI(String[] args) {
        _args = args;
    }

    public static CLI getInstance(String[] args) {
        if (_cliConfig == null) {
            _cliConfig = new CLI(args);
        }

        return _cliConfig;
    }

    public void printArgs() {
        for (String arg : _args) {
            System.out.println(arg);
        }
    }

    private CLIConfig generateConfig() {
        _config = new CLIConfig();
        
        return _config;
    }
    private void format() {

    }

}
