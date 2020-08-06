package group8.services;

import java.util.List;

public interface DOTParserInterface {



    /**
     *
     * @param path path to .dot file location
     * @return List<String[]> contains each line of the .dot file
     *         If it is a node (i.e. "a     |Weight=2|;"), String[].size = 2.
     *                          [0] contains the node id
     *                          [1] contains the weight
     *
     *         If it is an edge(i.e. "a -> b |Weight=1|;"), String[].size = 3.
     *                          [0] contains source node id
     *                          [1] contains destination node id
     *                          [2] contains remote weight
     *
     *
     */
    public List<String[]> getFileAsStringArray(String path);

}
