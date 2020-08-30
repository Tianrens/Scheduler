

## DIFFERENCE BETWEEN PARSING AND LEXING https://dev.to/cad97/what-is-a-lexer-anyway-4kdo -- kind of a sidetrack thing I went into
Parsing: analyse string of symbols (CHARACTERS) whilst conforming to the rules of a formal grammar -- turning it into a data structure itself.
Lexing: turns the meaningless string into a flat list of things like “number literal” or “string literal”, recognising whitespace and reserved keywords. They recognize a set of regular language.
File bytestream → lexer → tokens → parser (via parse tree) → parsed language/data structure

http://zvtm.sourceforge.net/zgrviewer/doc/ajapad/dotParser.html → I pulled this lexer/parser out of there: https://www.antlr.org/. Then I found this thread: https://dzone.com/articles/antlr-and-javacc-parser-generators which lead me to this: https://javacc.github.io/javacc/. Both ANTLR and JAVACC are BSD licensed.

Anyway, Java parsers that implemented some of the lexers/parsers I talked about above. They generate Graph, Node and Edge objects to use when parsing in the .DOT files.
https://weka.sourceforge.io/doc.dev/weka/gui/graphvisualizer/DotParser.html Won't be able to deal with things like subgraphs and grouping of nodes.
https://github.com/nidi3/graphviz-java
However, the DOT syntax doesn’t have Weight for both node and edge? https://www.graphviz.org/doc/info/lang.html
Or this uses the GraphViz DOT syntax, using ANTLR https://github.com/paypal/digraph-parser/tree/master/src/main/java/com/paypal/digraph/parser.
Pretty good, it generates lists of nodes and edges. Can take this to generate our own Graph class.
Or we can just do something similar like this so we can create our own Graph, Edge and Node classes, and take in the custom attributes we have for our DOT syntax.

http://zvtm.sourceforge.net/zgrviewer/doc/ajapad/dotParser.html


Current method: just reading in using bufferedreader and dealing with each String line input. Assuming each element of the .dot file is separated neatly with a single whitespace. Loosely based on GraphViz DOT syntax
