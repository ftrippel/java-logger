package ftrippel.logger;

import ftrippel.logger.Logger.AggregateLoggable;

/**
 * 
 * @author https://github.com/ftrippel
 * 
 */
public class Counter extends AggregateLoggable {

	protected long value;

	public Counter(long value) {
		super();
		this.value = value;
	}

	public long getValue() {
		return this.value;
	}

	public synchronized void increment() {
		this.value = this.value + 1;
	}

	public synchronized void increment(long i) {
		this.value = this.value + i;
	}

	public synchronized void decrement() {
		this.value = this.value - 1;
	}

	public synchronized void decrement(long i) {
		this.value = this.value - i;
	}

	@Override
	public String toString() {
		return getName() + ": " + this.value;
	}

}
