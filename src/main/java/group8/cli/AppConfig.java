package group8.cli;

import java.io.File;

public class AppConfig {

    private static AppConfig _appConfig;

    private File _inputFile;
    private int _numProcessors;
    private int _numCores;
    private boolean _visualise;
    private File _outputFile;
    private String _graphName;

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

    public void setInputFile(File file) {
        _inputFile = file;
    }

    public void setNumProcessors(int numProcessors) {
        _numProcessors = numProcessors;
    }

    public void setNumCores(int numCores) {
        _numCores = numCores;
    }

    public void setVisualise(boolean visualise) {
        _visualise = visualise;
    }

    public void setOutputFile(File file) {
        _outputFile = file;
    }

    public File getInputFile() {
        return _inputFile;
    }

    public int getNumProcessors() {
        return _numProcessors;
    }

    public int getNumCores() {
        return _numCores;
    }

    public boolean isVisualise() {
        return _visualise;
    }

    public File getOutputFile() {
        return _outputFile;
    }

    public String getGraphName() {
        return _graphName;
    }

    public void setGraphName(String graphName) {
        this._graphName = graphName;
    }
}
