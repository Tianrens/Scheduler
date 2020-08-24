## Meeting 10
17/08/2020 3:00pm

#### AGENDA
- SUBMIT MILESTONE 1 (JAR release) + release note
    - We fixed some README issues, talked about Oliver’s reply
    - Fix some input reader not closing
    - Change to spreading the nodes across the processors
- Start planning for Milestone 2:
    - Algorithm
    - Implementation plan
- Some file/IO stuff not closing properly

**Points to consider**
- Multi-threading
- Implementation of algorithm
- Visualisation
- Heuristic

_Considering the time we have left + our abilities, we’re not sure which algorithm to proceed with. DFS is easier but A* is better for our client._
- Multithreading / heuristic is the same for both
- Visualisation is potentially the same for both
- Implementation of A* algorithm will be harder and longer.

_In the end, we decided that A* is doable within our time limit (we will still have to look into heuristics and also multithreading), as it will only be slightly longer to implement than DFS. The difference between the two is traversing the search space tree._

_Possible split for the multi-threading: List<Leaves> / No. threads_

&nbsp;
#### ACTION ITEMS:
- Test David’s code
    - Add graphs to resources so we can use this later on
    - Graphs - Raymond and Justin
- Oliver:
    - Does the thread also include the GUI thread? -- Tianren
- Wiki - Raymond (& Jennifer)
    - Front page
    - Plan https://stackoverflow.com/questions/41604263/how-to-display-local-image-in-markdown
    - Coding standards
    - I’d imagine the wiki would split into packages like this: module/cli, module/parser (include Class diagram), etc. and also include external dependencies
    - Research
    - Implementation plan
- Comments
    - Cli - Tianren
    - Parser - Jennifer
    - Scheduler - David
- Research (for both algorithms)
    - Implementation
    - use a priority queue to perform the repeated selection of minimum (estimated) cost nodes to expand. This priority queue is known as the open set or fringe
    - Heuristics (for memory management A*, speed both) - Everyone
- Release note - Jennifer