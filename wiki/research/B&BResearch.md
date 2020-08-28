# Client Specification/Requirements
* Input needs to just be a graph that is up to 20 nodes.
* Just one solution is needed, the point is not to enumerate 
through all possible solutions.
* ~4GB RAM is given for the software to run.
* <30min runtime.
* Up to 10 cores will be specified.

# Search Space Traversal Solutions

## DFS

We need a path cost to begin with.
“If the search encounters a path p such that cost(p) + h(p) > bound, path p can be pruned”
“We assume h(p) is less than or equal to the cost of a lowest-cost path from p to a goal node”
“If a none-pruned path to a goal is found, it must be better than the previous best path” [1].

Depth first search helps save memory space
However, A* proves to be faster because our solution search tree is a dense tree.


## A*

Best First Search algorithm - explores the best options first, until destination is reached.

NOTE: DO NOT CONFUSE SCHEDULING PROBLEM GRAPH WITH THE A* SEARCH TREE.

The A* search tree differs from the Scheduling problem Graph because in the search tree we explore the different 
orders + processors each node in our scheduling problem can be assigned. In layman's terms, A* is a tree of all 
possibilities that can be explored.

Path is selected according to the smallest value of F = G + H
G: is the cost to move from the starting point to the given node
H: is the estimated cost to move from the given node to the final destination 

Determining H will need to be discussed further 

##### INITIAL IDEAS:
* Sum of all remaining task times
* Sum of all remaining task times Plus remote processor times

#### Building the search tree
* Hash of all current leaves - Within each leaf we need:
* Hash of assigned and unassigned tasks
* Estimated heuristic cost for each reachable node
* Cost from starting node
* Need to store the path somehow - reference of parent node? Or array of path

From all current leaves, we scan through all reachable nodes. Pick the least cost topologically valid path according 
to Heuristic estimation - basically dijkstras. Once destination is reached algorithm finishes.

#### Memory problems
 there is a possibility of a memory problem because no options are ever ruled out, they are only not explored, so 
 all previous paths/branches must be retained - NO PRUNING. The tree will also grow exponentially with the number of 
 processors, because of a large increase in permutations and combinations. Similarly with nodes and branches
With a good enough heuristic, less unnecessarily high cost nodes will be expanded.


## AO*
A* algorithm and AO* algorithm are used for AI. A* algorithm: OR graph while  AO* algorithm: AND-OR graph. A* algorithm 
guarantees to give optimal solution. AO* doesn't gurantee because once receiving a solution, it doesn't explore all other 
solutions.


# The f function

#### F = g + h
H = heuristic, lower bound

A* is a popular brand-and bound algorithm (using bestfirst search approach) that is "optimally efficient" using the same
*cost function f*. The cost function MUST be an underestimate:  f(s) ≤ f ∗ (s), where f ∗ (s) is the true lowest cost of
a complete schedule (solution) at root node.

##### Heuristics: see [heuristics research](Heuristics.md)


# Pruning and Duplication Reduction Methods

### ELS and AO Model
States = partial schedules.

##### ELS Model
This means that some subset of tasks have been assigned to processors + have received a start time.
"At each branching step, successors are created by putting every possible ready task (tasks for which all parents 
are already scheduled) on every possible processor at the earliest possible start time. In this way, the search space 
demonstrates every possible sequence of decisions that a list scheduling algorithm could make." [2]

##### Two Types of Duplication

|P|! Equivalent complete schedules in the state space. Since the processors are homogeneous, any permutation of the 
processors in a schedule represents an entirely equivalent schedule.

"When tasks are independent of each other, the order in which they are selected for scheduling can be 
changed without affecting the resulting schedule. This means there is more than one path to the corresponding state, 
and therefore a potential duplicate. The only way to avoid these duplicates is to enforce a particular sequence onto 
these scheduling decisions." [2]

There is talk about an AO model. It performs better (generating state wise) than ELS model.
* **Allocation**: decide for each task the processor to which it will be assigned. We refer to this as the allocation phase
* **Ordering**: beginning after all tasks are allocated, decides the start times of each task. Given that each 
processor has a known set of tasks allocated to it, this is equivalent to deciding on an ordering for each set. 

### Pruning Methods [3]

##### Identical Tasks
 You insert a “virtual edge”[4]  between the identical tasks, and when states are expanded only ONE of the 
 identical tasks become free at a time. Identical tasks/nodes are identified by the following:
* Task weight
* Parents
* Children
* Incoming and outgoing weights (costs have to be the same as well).

##### Use a Heuristic to Generate an UPPER BOUND

Use a heuristic H to produce a valid schedule with length slH -- **UPPER BOUND**. All states that have a higher f function 
value than OR EQUAL the slH will automatically be DELETED. This solution can be used if nothing gets inserted into the 
queue. Why do we use A* then? A* needs to be used to prove that this solution is optimal.
Examplar heuristics
* Critical path
* max(crit path, perfect load balancing)

##### Partial Expansion -- not sure if we will ever use this.

Partially expand, so you do not resort to a complete exhaustive list scheduling expansion search. If a state s gets expanded and
assigned free nodes, in any of the resulting schedules do you get a f value *equal* to s's f value, then there is no need to 
expand the rest of the free nodes. **Note**: you do, however, need to expand ALL free nodes for that particular state, otherwise 
you cannot remove from the OPEN LIST.