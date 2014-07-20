package ftrippel.logger;

import java.io.StringWriter;
import java.math.BigDecimal;

import ftrippel.logger.ConfigurableAggregator.Configuration.Sort;
import ftrippel.logger.Logger.OutputChannel;

/**
 * Facade pattern to provide and customize actual log capabilities.
 * 
 * This could a spring bean, for instance.
 * 
 * Wraps one Logger object that can write to multiple OutputChannels, e.g. console and file.
 * 
 * @author https://github.com/ftrippel
 * 
 */
class TestLog extends LoggerFacade {

	/** global statics guard against typos and make refactoring possible */
	public static final String SUM = "Sum", SUM2 = "Sum2", COUNTER = "Counter", TEXT = "Text";

	protected StringWriter w1, w2, w3;

	protected OutputChannel c1, c2, c3;

	/** run aggregate only once per channel */
	protected boolean run1, run2, run3;

	public TestLog() {
		super();
	}

	public Sum<BigDecimal> getSum(String name) {
		Sum<BigDecimal> sum = super.getSum(name, BigDecimal.class);
		if (name.equals(SUM2)) {
			getLogger().bind(sum, c2);
		}
		return sum;
	}

	public Sum<BigDecimal> getSum(String name, BigDecimal o) {
		Sum<BigDecimal> sum = super.getSum(name, o);
		if (name.equals(SUM2)) {
			getLogger().bind(sum, c2);
		}
		return sum;
	}

	@Override
	protected void configure(Logger l) {
		w1 = new StringWriter();
		c1 = new OutputChannel() {
			@Override
			public void print(String line) {
				w1.write(line);
			}
		};
		w2 = new StringWriter();
		c2 = new OutputChannel() {
			@Override
			public void print(String line) {
				w2.write(line);
				System.out.print(line);
			}
		};
		w3 = new StringWriter();
		c3 = new OutputChannel() {
			@Override
			public void print(String line) {
				w3.write(line);
			}
		};
		l.bindDefault(c3);
		l.bind(Sum.class, c1);
		l.bind(Counter.class, c1);
		l.bind(Counter.class, c2);
	}

	public String getOutput1() {
		if (!run1) {
			getLogger().aggregate(c1, new ConfigurableAggregator.Configuration().sort(Sort.ASC).build());
			run1 = true;
		}
		return w1.toString();
	}

	public String getOutput2() {
		if (!run2) {
			getLogger().aggregate(c2, new ConfigurableAggregator.Configuration().sort(Sort.ASC).header("Header").build());
			run2 = true;
		}
		return w2.toString();
	}

	public String getOutput3() {
		if (!run3) {
			getLogger().aggregate(c3, new ConfigurableAggregator.Configuration().sort(Sort.ASC).build());
			run3 = true;
		}
		return w3.toString();
	}

};
