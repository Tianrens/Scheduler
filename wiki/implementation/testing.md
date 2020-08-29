# Testing
#### What was tested?
##### Algorithm
* Algorithm Integration Tests
    * Runs the algorithm with different inputs and graphs.
* Greedy Heuristic Test
    * Schedules were created and the heuristic cost was calculated on these schedules.
* Parallel Speed Tests
    * Runs same test graphs with different amount of cores.
* Simple Heuristic Tests
    * Schedules were created and the heuristic cost was calculated on these schedules.
* Valid Schedule Tests
    * Checks that the schedule is valid with different graphs and inputs.
* Identical Node Duplication Tests
    * Checks if identical nodes are detected and scheduled correctly.
    Identical tasks pruning technique was tested thoroughly to ensure that it would not interfere with the main algorithm
    The test suite includes:
    * Testing whether the initial Graph object identified correctly the individual identical node groupings
    * Whether the ESLScheduler service class correctly schedules the identical tasks that are free correctly
    
    A number of conditions were tested, such as when the num of identical tasks exceeded the num of processors, and whether two nodes would be identified as identical if they were exactly the same except for the id and the cost.

##### CLI
* CLI Tests
    * Check input is correctly parsed.
    * Correct Exceptions are thrown when the input arguments are invalid.
    * File/Path exists and is valid checks.

##### Parser
* External Graph Generator Tests
    * Output file is generated corrected.
* Output Graph Tests
    * Checks if output graph has correct syntax.

##### Visualisation
* Visualisation Test
    * Runs the visualisation, successfully without throwing errors/exceptions.
    

#### Optimality Test
Most optimality tests were done by hand. Reading the output and working out an optimal schedule by hand.
