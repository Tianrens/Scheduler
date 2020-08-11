package group8.parser;

import group8.cli.AppConfig;
import group8.cli.AppConfigException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Map;

public abstract class DOTExternalParser<T, U> {

    abstract Map<String, T> getNodes();

    abstract Map<String, U> getEdges();

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
