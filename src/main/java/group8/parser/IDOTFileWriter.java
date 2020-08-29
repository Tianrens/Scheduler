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

    /**
     * Write output of {@link Schedule} to CLI output. Great for testing purposes.
     * @param schedule
     * @param graph
     */
    void writeOutputToConsole(Schedule schedule, Graph graph);

    /**
     * Write output of {@link Schedule} to a String. Great for testing purposes.
     * @param schedule
     * @param graph
     */
    String writeOutputToString(Schedule schedule, Graph graph);

}
