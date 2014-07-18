package ftrippel.logger;

import java.math.BigDecimal;

/**
 * 
 * @author ftrippel
 * 
 */
public class NumberUtils {

	@SuppressWarnings("unchecked")
	public static <T extends Number> T add(T a, T b) {
		if (a instanceof Integer) {
			return (T) new Integer((Integer) a + (Integer) b);
		}
		if (a instanceof Long) {
			return (T) new Long((Long) a + (Long) b);
		}
		if (a instanceof Double) {
			return (T) new Double((Double) a + (Double) b);
		}
		if (a instanceof BigDecimal) {
			return (T) ((BigDecimal) a).add((BigDecimal) b);
		}
		throw new IllegalArgumentException("Type not supported: " + a.getClass().getSimpleName());
	}

	@SuppressWarnings("unchecked")
	public static <T extends Number> T subtract(T a, T b) {
		if (a instanceof Integer) {
			return (T) new Integer((Integer) a - (Integer) b);
		}
		if (a instanceof Long) {
			return (T) new Long((Long) a - (Long) b);
		}
		if (a instanceof Double) {
			return (T) new Double((Double) a - (Double) b);
		}
		if (a instanceof BigDecimal) {
			return (T) ((BigDecimal) a).subtract((BigDecimal) b);
		}
		throw new IllegalArgumentException("Type not supported: " + a.getClass().getSimpleName());
	}

	@SuppressWarnings("unchecked")
	public static <T extends Number> T initialValue(Class<T> clazz) {
		if (clazz.equals(Integer.class)) {
			return (T) new Integer(0);
		}
		if (clazz.equals(Long.class)) {
			return (T) new Long(0L);
		}
		if (clazz.equals(Double.class)) {
			return (T) new Double(0.0d);
		}
		if (clazz.equals(BigDecimal.class)) {
			return (T) new BigDecimal("0.0");
		}
		throw new IllegalArgumentException("Type not supported: " + clazz.getSimpleName());
	}

}
