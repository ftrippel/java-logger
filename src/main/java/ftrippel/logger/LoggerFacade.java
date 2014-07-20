package ftrippel.logger;

/**
 * Facade-style wrapper for the Logger object
 * 
 * @author https://github.com/ftrippel
 * 
 */
public abstract class LoggerFacade {

	protected Logger logger = new Logger();

	public LoggerFacade() {
		super();
		configure(this.logger);
	}

	protected abstract void configure(Logger logger);

	public Logger getLogger() {
		return logger;
	}

	public <T extends Number> Sum<T> getSum(String name, Class<T> clazz) {
		return getSum(name, NumberUtils.initialValue(clazz));
	}

	@SuppressWarnings("unchecked")
	public <T extends Number> Sum<T> getSum(String name, T o) {
		return logger.get(name, Sum.class, o);
	}

	public Counter getCounter(String name) {
		return getCounter(name, 0);
	}

	public Counter getCounter(String name, Integer o) {
		return logger.get(name, Counter.class, o);
	}

	public Text getText(String name) {
		return getText(name, "");
	}

	public Text getText(String name, String o) {
		return logger.get(name, Text.class, o);
	}

	public void log(final String text) {
		logger.log(text);
	}

}
