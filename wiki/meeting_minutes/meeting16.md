## Meeting 16
26/08/2020 3:00pm

#### AGENDA
- Go over our stuff
- A* or DFS?
- Any more duplication reduction methods

**Discussion**
- Do we need to do identical node check for identical tasks? No
- We fixed a bug in heuristic, and now it runs the 10 node graphs. However, when adding the set checks it runs out of heap space… USING SETS/DUPLICATION REDUCTION, it works after implementing identical nodes and processor normalisation
- When we set cores, do we put threads? YES
- If none is above the upper bound, we print out the schedule. NO
- Onelinegraph...nullpointerexception if heuristic is tighter bound -- exception -- FIXED
- We chose A* instead
- Empty graph for visualisation
- Optimal graph did not produce wanted results…

&nbsp;
#### ACTION ITEMS:
- Visuals - Tianren
    - Add comments to code
    - Transparent and border to differentiate between tasks on the Gantt chart
    - Window size????? >:(
    - Scroll pane
    - Make algorithm run on another thread
- ELSscheduler
    - Skip if heuristic is bigger than justins heuristic thing - Justin
- Merge setsetliststring stuff into ELSScheduler - Raymond
- Identical node finish - Jennifer
- AstarScheduler
    - Store earliest start time heuristic in newly made schedule instead of attaching the second comparator  - Justin 
    - AStarScheduler, put setstatus for visuals - Tianren & David
- TreeSet - David
    - Somebody Edit Schedule Comparator class to compare the Processor sets
    - If they are not equal compare by hashcode
- Remove duplicate field in Schedule :(
    - Replace the hashmap, with the sets
    - For loops sad life
- Fix Greedy - Tianren
- Research lmao + implementation plans
- More graphs
- More wiki
- Work out optimal schedule for thirteen node graph
