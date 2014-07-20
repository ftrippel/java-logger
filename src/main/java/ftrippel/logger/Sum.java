package ftrippel.logger;

import ftrippel.logger.Logger.AggregateLoggable;

/**
 * 
 * @author https://github.com/ftrippel
 * 
 */
public class Sum<T extends Number> extends AggregateLoggable {

	protected T value;

	public Sum(T value) {
		super();
		this.value = value;
	}

	public T getValue() {
		return this.value;
	}

	public synchronized void add(T zahl) {
		this.value = NumberUtils.add(this.value, zahl);
	}

	public synchronized void subtract(T zahl) {
		this.value = NumberUtils.subtract(this.value, zahl);
	}

	@Override
	public String toString() {
		return getName() + ": " + this.value;
	}

}
