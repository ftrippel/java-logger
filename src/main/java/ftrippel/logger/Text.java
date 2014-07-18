package ftrippel.logger;

import ftrippel.logger.Logger.AggregateLoggable;

/**
 * 
 * @author ftrippel
 * 
 */
public class Text extends AggregateLoggable {

	protected String value;

	public Text(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public synchronized void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return getName() + ": " + this.value;
	}

}
