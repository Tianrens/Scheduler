package group8.parser;

import group8.cli.AppConfigException;
import group8.models.Schedule;

public interface IDOTFileWriter {


    void writeOutput(Schedule schedule) throws AppConfigException;

}
