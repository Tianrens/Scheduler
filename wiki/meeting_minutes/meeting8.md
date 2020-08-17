##Meeting 8
13/08/2020 3:00pm

####AGENDA
- Go over our tasks, start testing for final so we can submit by perhaps this Saturday.
- Polishing up tasks
    - Tianren wants to add head node/ topologically available nodes.
- Assign someone to submit

&nbsp;
####ACTION ITEMS:
- Polishing up:
    - ScheduleTests
        - Move scheduletests out of cli folder (tests) - David
    - Processor
        - Id has to start from ONE / at least one processor test -- Jennifer
    - Make add methods to Graph instead of GraphGeneators -- Justin
        - Delete those addData methods from GraphGenerator
        - Move the output stuff out from DOTdataparser into a new class called DOTFileWriter, with a new interface IDOTFileWriter
        - Add methods to Graph, so that you can add nodes, etc. to create the Schedule and Topology tests
    - Oneprocessorscheduler -- Jennifer
        - Fix the for loop
        - Take in interface instead of ITopologyFinder
    - Outputgraph tests -- Jennifer
        - Change to appconfig usage always (no parameter) because you can use the appconfig public methods in outputgraphtests
    - SOUT for various steps of the process -- Raymond
        - Input file read - say the name of inputfile
        - Schedule generated
        - Output file - say the name of outputfile
- System tests on Linux -- everyone (ssh into)
- README -- Tianren
- Wiki -- everyone
    - Changelog
- JAR file + release on Github -- David (at the very end)
- Email Oliver about the one processor schedule + has to have at least ONE processor + cycles + wiki + are we getting marked on waterfall process? -- Jennifer