
~4GB limit
Around 20 nodes apparently, not too big of a graph
Just one solution is needed, the point is not to enumerate through all possible solutions


# DFS

https://artint.info/html/ArtInt_63.html
We need a path cost to begin with.
“If the search encounters a path p such that cost(p) + h(p) > bound, path p can be pruned”
“We assume h(p) is less than or equal to the cost of a lowest-cost path from p to a goal node”
“If a none-pruned path to a goal is found, it must be better than the previous best path”

Depth first search helps save memory space
However, A* proves to be faster because our solution search tree is a dense tree.


# A*

Best First Search algorithm - explores the best options first, until destination is reached.

NOTE: DO NOT CONFUSE SCHEDULING PROBLEM GRAPH WITH THE A* SEARCH TREE.

The A* search tree differs from the Scheduling problem Graph because in the search tree we explore the different orders + processors each node in our scheduling problem can be assigned. In layman's terms, A* is a tree of all possibilities that can be explored.


Path is selected according to the smallest value of F = G + H
G: is the cost to move from the starting point to the given node
H: is the estimated cost to move from the given node to the final destination 

Determining H will need to be discussed further 
	IDEAS:
Sum of all remaining task times
Sum of all remaining task times Plus remote processor times


## Building the search tree:
Hash of all current leaves - Within each leaf we need:
Hash of assigned and unassigned tasks
Estimated heuristic cost for each reachable node
Cost from starting node
Need to store the path somehow - reference of parent node? Or array of path

From all current leaves, we scan through all reachable nodes
Pick the least cost topologically valid path according to Heuristic estimation - basically dijkstras

Once destination is reached algorithm finishes

## Memory problem 
 there is a possibility of a memory problem because no options are ever ruled out, they are only not explored, so all previous paths/branches must be retained - NO PRUNING.
The tree will also grow exponentially with the number of processors, because of a large increase in permutations and combinations. Similarly with nodes and branches
With a good enough heuristic, less unnecessarily high cost nodes will be expanded.

https://www.geeksforgeeks.org/a-search-algorithm/

https://en.wikipedia.org/wiki/A*_search_algorithm
As usually maybe unnecessarily complicated

https://www.youtube.com/watch?v=amlkE0g-YFU 
Good starting point
However, The actual explanation of A* i dont think is very good

# AO*
A* algorithm and AO* algorithm are used in the field of Artificial Intelligence. An A* algorithm is an OR graph algorithm while the AO* algorithm is an AND-OR graph algorithm. A* algorithm guarantees to give an optimal solution while AO* doesn't since AO* doesn't explore all other solutions once it got a solution.

The f function

F = g + h
H = heuristic, lower bound

A* is a particularly popular variant of branch-and-bound which uses a bestfirst search approach [5]. A* has the interesting property that it is optimally efficient; using the same cost function f, no search algorithm could find an optimal solution while examining fewer states. To achieve this property, it is necessary that the cost function f provides an underestimate. That is, it must be the case that f(s) ≤ f ∗ (s), where f ∗ (s) is the true lowest cost of a complete solution in the subtree rooted at s. A cost function with this property is said to be admissable.




## Duplication of States

Oliver Sinnen ( 2015): https://researchspace.auckland.ac.nz/handle/2292/30215?fbclid=IwAR2aHDFaUd30OAnFSuiVXpRBTx82T-_eAyJlAt5sSbo9KLyK5Msg24EjaI8

## ELS model:  
States are partial schedules in which some subset of the tasks in the problem instance have been assigned to a processor and given a start time. At each branching step, successors are created by putting every possible ready task (tasks for which all parents are already scheduled) on every possible processor at the earliest possible start time. In this way, the search space demonstrates every possible sequence of decisions that a list scheduling algorithm could make. This branch-and-bound strategy can therefore be described as exhaustive list scheduling.

|P|! Equivalent complete schedules in the state space.
Since the processors are homogeneous, any permutation of the processors in a schedule represents an entirely equivalent schedule.

When tasks are independent of each other, the order in which they are selected for scheduling can be changed without affecting the resulting schedule. This means there is more than one path to the corresponding state, and therefore a potential duplicate. The only way to avoid these duplicates is to enforce a particular sequence onto these scheduling decisions. Under the ELS (making of this state space tree) strategy, however, no method is apparent in which this could be achieved while also allowing all possible legitimate schedules to be produced.

This research paper talks about a duplicate-free state-space model: AO model. It performs better (generating state wise) than ELS model.
Allocation: decide for each task the processor to which it will be assigned. We refer to this as the allocation phase
Ordering: beginning after all tasks are allocated, decides the start times of each task. Given that each processor has a known set of tasks allocated to it, this is equivalent to deciding on an ordering for each set. 

Oliver (2014): https://researchspace.auckland.ac.nz/handle/2292/30213


