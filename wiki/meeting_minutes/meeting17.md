## Meeting 17
27/08/2020 2:00pm

#### AGENDA
- We make a new expander object every loop, this can be avoided
- Also, in schedule class, I dont think we need to make a set for every processes when we initialise.
- Remove duplicate field in Schedule :(
    - Replace the hashmap, with the sets
    - For loops sad life
- Decide how to loop through closedStates
- Do more optimal testing!??? But we should receive an optimal schedule every time

_Raymond explained how he integrated elsmodel. He made various private methods to decrease overall runtime. In the end, we decided not to go with this because 
Do we need a closed list? It’s just for checking for duplicates. Do we save more memory by removing the closed list or having this closed list for duplicates?_
- This needs more testing

_Do we have other service classes that are being reinstated every time?_

**Data Structure Memory research**
- Hashmaps use the same amount of memory as sets.
- Treemap take quite a lot of memory.
    - http://java-performance.info/memory-consumption-of-java-data-types-2/
- Tianren brought up profiling to help us track memory
    - https://visualvm.github.io/
- Trove:
    - https://stackoverflow.com/questions/41841754/using-standard-java-hashmap-compared-to-trove-thashmap-causes-non-hashmap-code
    - Basically, it’s not much, we better save memory via eliminating useless temp objects

_A more meaningful visualisation?_

_Also think about reorganise package structure at the very end._

**Massive pruning**

_Dropped down to 3.3mil with upper bound, 1mil after processor normalisation. Down 900k after adding comparator: most nodes scheduled and earliest start time._

&nbsp;
#### ACTION ITEMS:
- Maybe start looking into report??? - this will depend on how the meeting goes
- Check over documentation and other admin stuff that might be required (WIKI) 
- Reset the expander object every time instead of creating a new expander object - Justin
    - Astar holds a list of expander objs
    - Reset just the state/schedule                   
- ScheduleComparator, create a for loop to compare classes instead of using sets. - David
- Minor pruning stuff except for: - Jennifer & Raymond
    - AStarScheduler, comparator classes
- Email: - Tianren
    - Processors start from 0 or 1?
    - Visualisation: more clarification on that line? Like num of tasks
	
- Unit Tests
    - Heuristic
        - Greedy - Tianren
        - Bottom line
        - DRT
        - Idle time
    - ScheduleQueue -> duplication thing
    - Use valid schedule tester - david
    - Finish identical node tests - Jennifer (10 nodes)
    - Expanded as expected (ELSModel)  - maybe
    - Speed test (<30min lol) - david
    - More tests to Algorithm Integration Tests - everyone
- Report - we all start our sections a little bit
- Wiki
    - Research - Jennifer
- Major code cleanup :( - Justin and Raymond
    - Method abstraction
    - Variable naming changes
    - Removal of unused classes
