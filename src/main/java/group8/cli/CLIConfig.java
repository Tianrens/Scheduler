package group8.cli;

import java.io.File;

public class CLIConfig {
    private File _inputFile;
    private int _numProcessors;
    private int _numCores;
    private boolean _visualise;

    protected void setFile(String file) {
        _inputFile = new File(file);
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
}
