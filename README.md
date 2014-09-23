java-logger
===========

A simple Java logging facility to log aggregated statistical information (no replacement for log4j)

Log primitives are
* Sum (with generic type T that extends Number)
* Counter
* Text

Each instance of a primitive is associated with a name.

Multiple output channels are supported (think of it as a way to log to the console or a file). You can bind log primitives or just instances thereof to a particular output channel.

At the end of processing you can request the statistical information. You can customize the output by specifying an aggregator function.

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
