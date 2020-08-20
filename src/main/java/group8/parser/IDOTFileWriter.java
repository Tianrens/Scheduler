package group8.parser;

import group8.cli.AppConfigException;
import group8.models.Graph;
import group8.models.Schedule;

/**
 * Main interface for a service to write schedules to output DOT file.
 */
public interface IDOTFileWriter {

    /**
     * Main method which handles the writing of the DOT file from the {@link Schedule}.
     * @param schedule
     * @throws AppConfigException if the output file has not been configured, i.e. AppConfig has not been generated.
     */
    void writeOutput(Schedule schedule, Graph graph) throws AppConfigException;

    void writeOutputToConsole(Schedule schedule, Graph graph) throws AppConfigException;

}
