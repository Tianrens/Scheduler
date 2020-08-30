# Heuristics

## Algorithm Heuristics

### Idle Time
This is the (sum of all node of the graph costs + idle time of that schedule) / num of processors
There--fore this is the partial schedule’s idle-time lower bound.
idle time = time that the processor does not execute any task

**Assumptions:** 

Idle time of state will not increase
All processors will finish at the same time.
This only good for schedules where there are low communication costs, good load balancing

### Bottom Level
This is the maximum of ∑nodes of partial schedule(start time of node + bottom level of node) 
This heuristic is based on the already scheduled tasks cause you need to use the start time of the node

### Data Ready Time (DRT)
DRT of a node of a processor = maximum of ∑parent nodes(parent node finishing time + communication cost if need be).

So in conclusion: maximum of nodes not scheduled(min DRT of the node across all processors + bottom level of the node)
The minimum DRT of task n across all processors P is: 
This is also based on current scheduled nodes because this depends on the parent nodes and their processor placement.

### ALL TOGETHER:
So Oliver’s heuristic is therefore the maximum of all three equations. This is how he describes how to get the “optimal” heuristic to calculate f function for a partial schedule (state).

Difference between 2 and 3. Bl will contain the cost of the node in the partial schedule vs DRT containing the remote cost of the parents. Everything else is almost the same.

&nbsp;
## Pruning Heuristics
_These heuristics were used to calculate an upper bound to the initial graph. This allowed us to perform Heuristic Schedules Pruning by removing partial schedules whos heuristic costs exceed the graph's_

### Simple Heuristic
Basic heuristic estimate that totals the costs of all nodes

### Greedy Heuristic
A greedy algorithm that forms a temporary schedule by allocating nodes to the schedule based on the greedy rule: “earliest processor start time” in which nodes are added to the processors at the most optimal time at each step.
This provides us with a tighter bound estimate to allow for more reduction of generated partial schedules.