## Meeting 18
28/08/2020 8pm

#### AGENDA
- We need some way to show between parallelisation and sequential mode -> done with the pie graph
- Parallelisation is not really faster…
    - Out-graph is faster
- Wiki: i did the research part (wanted to remove/add the places i straight copied and paste lmao)
    - References
    - Heuristics
    - We still need to put in:
        - Implementation plan // explanation of our modules
        - Class diagrams
        - Consistent file naming
        - Concept pictures for visualisation
        - Parallelisation
- Tests
- Going over the report to brainstorm stuff
- Graphs that we currently cannot process

_Tianren changed visualisation so that if in parallelisation mode the bar chart will change to a pie chart to show individual thread schedule count. Because of this AstarScheduler has an array which holds each individual thread schedule count._

_Someone’s group got marked down for not having tests cases, i.e. no junit tests classes. They also got marked down for having everything on one processor. They had no read me/run instructions and got marked down_

**Done:**
- Fixed bugs in schedulequeue
- Tests; schedule queue, greedy, speed test and used validschedulechecker
- Removing milestone1 classes

_Did stuff to speed up the closed list comparison; you don’t have to loop through every closed state. Check the closed state with the same heuristic cost and earliest start time. It sped up by a couple of minutes._

_When you throw an exception, there is no notification on the application. MOST RELEVANT EXAMPLE: garage collector._

**Current graphs that we cannot handle:**
- 20 node stencil
- Anything above 10 fork-join

&nbsp;
#### ACTION ITEMS:
- Scan through changelog for wiki - everyone
- Other tests. LINK TO: https://github.com/SoftEng306-2020/project-1-8-gr8-b8-m8/issues/118
    - Add more identical node tests - Jennifer
    - Simple - Raymond
    - BL - Justin
    - Idle - Justin
    - DRT - Justin
- Visualisation - Tianren
    - Green text to emphasis it was done
    - Change the numbers around for the graph
- Comments
- Wiki
    - Visualisation - Tianren
    - Heuristics
    - Implementation plan // explanation of our modules
        - Class diagrams
        - Pruning techniques
    - Consistent file naming
    - Parallelisation - Justin
- Refactoring - Raymond
    - Variable names
    - Refactor / abstract methods in elsexapnder
    - Make good heuristic object a global field (so it’s not being created every time) - dependency inject this
- Debugging/Testing - David
- Presentation?
    - Slides? Pure QnA? What does he expect from us?
    - Ask Oliver
