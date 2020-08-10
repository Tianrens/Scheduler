package group8.cli;

import java.io.File;

public class AppConfig {

    private static AppConfig _appConfig;

    private File _inputFile;
    private int _numProcessors;
    private int _numCores;
    private boolean _visualise;
    private File _outputFile;

    private AppConfig() {}

    public static AppConfig getInstance() {
        if (_appConfig == null) {
            _appConfig = new AppConfig();
        }
        return _appConfig;
    }

    public static void clearConfig() {
        _appConfig = null;
    }

    @Override
    public String toString() {
        String output = "Input File: " + _inputFile + System.lineSeparator()
                + "Number of Processors: " + _numProcessors + System.lineSeparator()
                + "Number of Cores: " + _numCores + System.lineSeparator()
                + "Use Visualisation: " + _visualise + System.lineSeparator()
                + "Output File: " + _outputFile + System.lineSeparator();
        return output;
    }

    protected void setInputFile(File file) {
        _inputFile = file;
    }

    protected void setNumProcessors(int numProcessors) {
        _numProcessors = numProcessors;
    }

    protected void setNumCores(int numCores) {
        _numCores = numCores;
    }

    protected void setVisualise(boolean visualise) {
        _visualise = visualise;
    }

    protected void setOutputFile(File file) {
        _outputFile = file;
    }

    public File get_inputFile() {
        return _inputFile;
    }

    public int get_numProcessors() {
        return _numProcessors;
    }

    public int get_numCores() {
        return _numCores;
    }

    public boolean is_visualise() {
        return _visualise;
    }

    public File get_outputFile() {
        return _outputFile;
    }
}
