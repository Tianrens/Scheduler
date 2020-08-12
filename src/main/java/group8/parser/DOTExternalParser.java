package group8.parser;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * An intermediary between our code base and the external parser (dependency).
 * @param <T> External parser implementation of the Node class
 * @param <U> External parser implementation of the Edge class
 */
public abstract class DOTExternalParser<T, U> {

    /**
     * @return Get EXTERNAL implementation of nodes.
     */
    abstract Map<String, T> getNodes();

    /**
     * @return Get EXTERNAL implementation of edges.
     */
    abstract Map<String, U> getEdges();

    /**
     * Retrieve an input stream for reading in values via {@link AppConfig}'s input file.
     * @return input stream for reading in values.
     * @throws AppConfigException if file does not exist, which should have if {@link AppConfig} had been built correctly.
     */
    protected FileInputStream getFileInputStream() throws AppConfigException {
        File inputFile = AppConfig.getInstance().getInputFile();

        FileInputStream inputStream;
        try {
            inputStream = new FileInputStream(inputFile);
        } catch (FileNotFoundException | NullPointerException e) {
            throw new AppConfigException(); // File should exist if AppConfig had been built correctly
        }

        return inputStream;
    }
}
