# Input/Output Parsing

#### Input parsing
To be able to handle any valid format of the dot file type we decided that using an
external dependency to handle this would be the best use of our time.
After exploring the different options of parsing graphs,
we decided to use the [Paypal](../design/externalDependencies.md) parser external dependency
to parse input graphs and turn it into our own graph data structure. Apart from a few minor adjustments
to handling e.g. case sensitivity, the integration to our existing code base at that time was a relatively seamless experience. 

#### Output parsing
For output parsing we did not use an external parser due to the output needing to follow strict formatting
guidelines. We instead implemented our own output parser to write the generated schedule to file.
The output will print the list of tasks along with their weight, starting time, and the processor it is
running on. Our output parser will also print all edges along with their source and destination nodes and weight cost.