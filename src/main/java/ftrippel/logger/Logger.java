package ftrippel.logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * While log objects (text, counters, sums) are being shared, this class can write to multiple output channels.
 * 
 * E.g. you can write verbose exceptions to a text file, while writing basic statistical information to both console and text file.
 * 
 * @author ftrippel
 * 
 */
public class Logger {

	public interface Aggregator {

		void aggregate(OutputChannel channel, List<AggregateLoggable> list);

	}

	public static abstract class OutputChannel {

		/** For writing a multi-line string. E.g. StringBuilder. */
		abstract void print(String line);

		void println(String line) {
			print(line + "\n");
		}

	}

	/**
	 * Marker interface
	 * 
	 * toString() will be used for output
	 * 
	 */
	public interface Loggable {

	}

	public interface ImmediateLoggable extends Loggable {

	}

	public static abstract class AggregateLoggable implements Loggable {

		protected String name;

		public String getName() {
			return name;
		}

		/** name shall not be changed outside the Logger class */
		private void setName(String name) {
			this.name = name;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((name == null) ? 0 : name.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AggregateLoggable other = (AggregateLoggable) obj;
			if (name == null) {
				if (other.name != null)
					return false;
			} else if (!name.equals(other.name))
				return false;
			return true;
		}

	}

	protected Map<String, AggregateLoggable> aggregateLoggables = new HashMap<String, AggregateLoggable>();

	protected Map<OutputChannel, List<AggregateLoggable>> aggregateLoggablesByChannel = new HashMap<OutputChannel, List<AggregateLoggable>>();

	protected Map<Class<? extends Loggable>, List<OutputChannel>> channelsByLoggableClass = new HashMap<Class<? extends Loggable>, List<OutputChannel>>();

	protected List<OutputChannel> defaultOutputChannels = new ArrayList<OutputChannel>();

	/** set default OutputChannels */
	protected void bindDefault(OutputChannel... channels) {
		this.defaultOutputChannels = Arrays.asList(channels);
	}

	/** defaults to empty List */
	protected List<OutputChannel> getChannelsByClass(Class<? extends Loggable> clazz, boolean useDefaultChannels) {
		List<OutputChannel> channels = this.channelsByLoggableClass.get(clazz);
		if (channels == null) {
			channels = new ArrayList<OutputChannel>();
			if (useDefaultChannels) {
				channels.addAll(this.defaultOutputChannels);
			}
			this.channelsByLoggableClass.put(clazz, channels);
		}
		return channels;
	}

	/** defaults to empty List */
	protected List<AggregateLoggable> getAggregateLoggablesByChannel(OutputChannel channel) {
		List<AggregateLoggable> list = this.aggregateLoggablesByChannel.get(channel);
		if (list == null) {
			list = new ArrayList<AggregateLoggable>();
			this.aggregateLoggablesByChannel.put(channel, list);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public <T extends AggregateLoggable> T get(String name, Class<T> clazz, Object... params) {
		T l = (T) this.aggregateLoggables.get(name);
		if (l == null) {
			try {
				l = (T) clazz.getConstructors()[0].newInstance(params);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			set(name, l);
		}
		return l;
	}

	/** if you want to configure l yourself */
	public void set(String name, AggregateLoggable l) {
		l.setName(name);
		List<OutputChannel> channels = getChannelsByClass(l.getClass(), true);
		for (OutputChannel channel : channels) {
			List<AggregateLoggable> list = getAggregateLoggablesByChannel(channel);
			list.add(l);
		}
		this.aggregateLoggables.put(name, l);
	}

	/** adds OutputChannel binding for clazz */
	public void bind(Class<? extends AggregateLoggable> clazz, OutputChannel channel) {
		List<OutputChannel> list = getChannelsByClass(clazz, false);
		list.add(channel);
	}

	/** removes all OutputChannel bindings for clazz */
	public void unbind(Class<? extends AggregateLoggable> clazz) {
		List<OutputChannel> channels = getChannelsByClass(clazz, true);
		for (OutputChannel channel : channels) {
			getAggregateLoggablesByChannel(channel).clear();
		}
		channels.clear();
	}

	/** removes all bindings for l */
	public void unbind(AggregateLoggable l) {
		List<OutputChannel> channels = getChannelsByClass(l.getClass(), true);
		for (OutputChannel channel : channels) {
			getAggregateLoggablesByChannel(channel).remove(l);
		}
	}

	/** binds l to these channels exactly */
	public void bind(AggregateLoggable l, OutputChannel... channels) {
		unbind(l);
		for (OutputChannel channel : channels) {
			getAggregateLoggablesByChannel(channel).add(l);
		}
	}

	public void aggregate(Aggregator a) {
		for (OutputChannel channel : this.aggregateLoggablesByChannel.keySet()) {
			aggregate(channel, a);
		}
	}

	public void aggregate(OutputChannel channel, Aggregator a) {
		a.aggregate(channel, getAggregateLoggablesByChannel(channel));
	}

	public void log(ImmediateLoggable l) {
		List<OutputChannel> channels = getChannelsByClass(l.getClass(), true);
		for (OutputChannel channel : channels) {
			channel.println(l.toString());
		}
	}

	public void log(final String text) {
		log(new ImmediateLoggable() {
			@Override
			public String toString() {
				return text;
			}
		});
	}

}
