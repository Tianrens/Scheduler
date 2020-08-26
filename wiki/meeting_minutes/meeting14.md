## Meeting 14
23/08/2020 4:00pm

#### AGENDA
- David’s stuff/experiment
- Presentation tomorrow

**Davids findings:**
_Heuristic seems to work_
- However memory leaks, garage collector operating 90% of CPU time but only returning 2% of the HEAP (threw this exception in 2-3 minutes). If you call it every loop, it would be really slow.
    - Duplication and pruning techniques
        - AO model and pruning techniques and identical states
    - Instantiating new variables ← full clean
    - Precalculate the bottom level and store it in the Graph object.
- Edited - ScheduleComparator to add an extra check
    - For small graphs it would not give optimal because not everything is explored
    - Priority queue sorting is a bit scuff
        - I wanted to add a second value to compare by, but priority queue can't sort like the alphabet -- Maybe a problem IDK????
        - Star graph caused this error. Second check doesn’t really fix it at the back of the queue.
    - Parallelising works, but currently cannot test whether it takes faster or not because of the heap/memory problem.

&nbsp;
#### ACTION ITEMS:
- Heuristic - everyone go over it
- Heuristic commenting - Raymond
- Comparator // Priority queue - Justin
- Visualisation - Tianren : we have to set these markers into the code base
    - UI Design - Jennifer
- Research for duplication - Jennifer
- Bottom level and put it into the Graph object - David
- Full clean - David and Raymond
