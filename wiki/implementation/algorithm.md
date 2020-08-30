# Algorithms

## A* Algorithm

The Branch and Bound algorithm selected by our team was the A* algorithm. This algorithm is similar to Djikstra's algorithm in that each iteration, reachable nodes are scanned to find the next step.
However, the unique feature of this algorithm is its use of [heuristics](heuristics.md) to narrow down the search space such that the algorithm attempts to only branch towards the optimal schedule.
The advantage of A* is that due its heuristic estimates, it performs very well, however this comes at the cost of large memory usage which rely on [pruning](pruning.md) to reduce.

### Pseudo Code

````
Required: OPEN = Tree Set ordered by lowest to highest heuristic estimate

OPEN <- Initial Schedule
While OPEN not empty
    s <- lowest heuristic cost element from OPEN
    If s is complete then
        Return optimal schedule s
    Expand s into new partial schedules NEW
    For all partial schedules p in NEW
        Calculate heuristic for p
        Check for duplicates then insert into OPEN
    CLOSED <- s
````

## Single processor scheduler

If the program is told to schedule on a single the single processor scheduler algorithm
will be used instead of the A* Algorithm. The reason for this is because remote weights are
a non issue and that a valid topology is the only thing that matters. The single processor algorithm
therefore, finds a valid topology with the input graph and places that topology on a single processor,
thus finding the optimum schedule.

### Pseudo Code

```
If number of processors is one then
    find valid topology
    schedule that topology on a processor
else
    run A* algorithm
end

```









































