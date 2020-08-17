package group8.parser;

import group8.cli.AppConfigException;
import group8.models.Graph;

public interface IGraphGenerator {

    /**
     * Builds a {@link Graph} object from the input DOT file, using configurations based on {@link group8.cli.AppConfig}
     * and a parser that is dependency injected into this service.
     * @return Graph object
     * @throws AppConfigException
     */
    Graph generate() throws AppConfigException;
}
