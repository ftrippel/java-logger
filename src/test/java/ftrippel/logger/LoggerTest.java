package ftrippel.logger;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Test;

import static ftrippel.logger.TestLog.*;

/**
 * 
 * @author https://github.com/ftrippel
 * 
 */
public class LoggerTest {

	protected TestLog log = new TestLog();

	@Test
	public void testAggregateLoggable() {
		Sum<BigDecimal> sum = log.getSum(SUM);
		sum.add(BigDecimal.ONE);

		Sum<BigDecimal> sum2 = log.getSum(SUM2);
		sum2.subtract(BigDecimal.ONE);

		Counter counter = log.getCounter(COUNTER);
		counter.increment();

		Text text = log.getText(TEXT);
		text.setValue("value");

		log.log("Hello World");

		Assert.assertEquals(COUNTER + ": 1\n" + SUM + ": 1.0\n", log.getOutput1());
		Assert.assertEquals("Header\n======\n" + COUNTER + ": 1\n" + SUM2 + ": -1.0\n", log.getOutput2());
		Assert.assertEquals("Hello World\n" + TEXT + ": value\n", log.getOutput3());

		// System.out.println(log.getOutput2());
	}

}
