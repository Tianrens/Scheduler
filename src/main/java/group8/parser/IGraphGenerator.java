package group8.parser;

import group8.cli.AppConfigException;
import group8.models.Graph;

public interface IGraphGenerator {

    /**
     * @return Graph object
     * @throws AppConfigException
     */
    Graph generate() throws AppConfigException;
}
