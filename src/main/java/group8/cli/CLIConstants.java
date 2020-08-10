package group8.cli;

public class CLIConstants {
    protected static final String PARALLEL_FLAG = "p";
    protected static final String VISUALISE_FLAG = "v";
    protected static final String OUTPUT_FLAG = "o";

    protected static final String PARALLEL_DESC = "use N cores for execution in parallel (default is sequential)";
    protected static final String VISUALISE_DESC = "visualise the search";
    protected static final String OUTPUT_DESC = "output file is name OUTPUT (default is INPUT-output.dot)";

    protected static final int DEFAULT_CORES = 1;
    protected static final boolean DEFAULT_VISUALISE = false;

    protected static final String DEFAULT_OUTPUT_SUFFIX = "-output.dot";

    protected static final int FAILURE_EXIT_CODE = -1;

}


