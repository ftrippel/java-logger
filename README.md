java-logger
===========

A simple Java logging facility to log aggregated statistical information (no replacement for log4j)

Primitives are
* Sum (with generic type T that extends Number)
* Counter
* Text

In a client project we did batch processing and dispatched the batch input to multiple batch outputs. For the sake of flow control we wanted to log statistical information about this process. We built a Log class (spring singleton bean) that provided us with the primitives above.

Example Usage
=============

You create a custom Log class, e.g. `TestLog`, by inheriting from the `Log` class and configuring it. Note that you can change the generic type of the `Sum` class.

```java
@Autowired
TestLog log;

...

Sum<BigDecimal> sum = log.getSum("sum");
sum.subtract(BigDecimal.ONE);

Counter counter = log.getCounter("counter");
counter.increment();

log.log("text");
```
