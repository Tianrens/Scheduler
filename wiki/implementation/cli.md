# Command Line Interface
#### Options: 
```
java -jar scheduler.jar INPUT.dot P [OPTION]
```

```
INPUT.dot    a task graph with integer weights in dot format
P            number of processors to schedule the INPUT graph on
Optional:
-p N         use N cores for execution in parallel(default is sequential)
-v           visualise the search
-o OUTPUT    output file is named OUTPUT(default is INPUT-output.dot)
```
    
##### External Dependencies:
* Apache Commons Cli


In the Main class, parse in the String[] args to AppConfigBuilder.
AppConfigBuilder uses Apache Commons Cli to parse the input arguments.
A lot of error checking is done in this class, to make sure the AppConfig instance will be valid.
Apache Commons Cli requires a CommandLineParser object which parses the input String[] arguments with 
a set of Options. The Options object is a set of Option objects, each Option object determines the
tags/flags allowed in the command line input.
The CommandLineParser object returns a CommandLine object, which can be used to check if certain tags/options'
have been specified by the user, along with if a value is provided with tag/option, if it has any.
    