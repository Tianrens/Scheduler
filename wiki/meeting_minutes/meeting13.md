## Meeting 13
22/08/2020 3:00pm

#### AGENDA
- Go over what we did

**Work Done**
- David implemented a valid schedule tester.
- Raymond added a parent checker, but it still ran the same results as last time. So either something is wrong with the heuristic.....we compared with some optimal schedules that we found online and working backwards, we found our algorithm wasn’t optimal.
    - Our heuristic isn’t what we thought we were taught in class for…
    - We need a tighter bound. It’s just the “bottom level”. So perhaps we need to review what he taught us in class.
    - So if we take into consideration the costs of the tasks then we can find a tighter bound.
- Justin tried parallelising things, particularly the expander class that David implemented which does a lot of the heavy lifting.
    - Extend callable for ESLexpander
    - executorserivce.fixedthreadpool(numcores)
    - Executorserver.submit might be blocking, also if we are looping through and getting all promise.get() (block) at a time, if one thread is really big it might be just linear in the end → actually shouldn’t affect it too much tbh
- Jennifer and Tianren set up Javafx and Gradle can create jar
    - “group8.Main” in Gradle, it wasn’t working with a backslash. Some reason Gradle mucks it up when creating a jar with dependencies with JavaFX
    - Extend main class with Application, implemented start() method with launch() and added empty controller with fxml file. Planning to only have one fxml file. One controller basically, doesn’t need too much of big changes. One GUI thread running at all time. 

_Parallelisation - get heuristic right first._

_We also went over a research paper and extracted a heuristic that we thought might be able to calculate the f function for us._

&nbsp;
#### ACTION ITEMS:
- Document gradle changes and also javafx changes. Put into wiki / meeting minutes / PR - Jennifer & Tianren
- Get most of class with “endpoints” done - Tianren & Justin
- Heuristic implementation
    - Idle time function - Raymond
    - Bottom level calculation - Jennifer
    - DRT (Data Ready Time) function - David
