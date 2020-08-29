# Parallelisation

##### Parallelisation technique
* Java ExecutorService
* Callable interface

#### Implementation
* [ELSModelStateExpander](../../src/main/java/group8/algorithm/ELSModelStateExpander.java)
implements Callable interface so its methods can be multithreaded.
* More precisely the getNewStates() method which expands the search tree and generates new
 states is placed inside the call() method (overridden method from Callable interface).
* The AStarScheduler class holds a list of future objects as placeholders for the proper return value of list of schedules.
    * A future object represents the result of asynchronous computation (i.e. on different threads at the same times).
    * A for loop after all threads have been put to work calls the get() method on each future object to retrieve the proper result.
        * NOTE: Calling the get() method will block if result is not available yet.

##### Implications
* For loop reading future objects blocks the main thread because a thread has not finished computing their return values to replace the future object.
    * This can be bad since other threads can be done, but the main thread is blocked waiting for one thread to finish.

##### Challenges
* Did not find a way to not bulk load threads.
    * Implemented bulk loading and reading return value of the threads.
    * Cause problems with blocking.