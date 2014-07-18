java-logger
===========

A simple Java logging facility to log aggregated statistical information (no replacement for log4j)

Primitives are
* Sum (with generic type T that extends Number)
* Counter
* Text

In a client project, we did batch processing and dispatched the batch input to multiple batch outputs. For the sake of flow control we wanted to log statistical information about this process. For that we built a Log class (spring singleton bean) that provided us with the primites above.
