package ftrippel.logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ftrippel.logger.Logger.AggregateLoggable;
import ftrippel.logger.Logger.Aggregator;
import ftrippel.logger.Logger.OutputChannel;

/**
 * 
 * @author ftrippel
 * 
 */
public class ConfigurableAggregator implements Aggregator {

	public static class Configuration {

		enum Sort {
			NONE, ASC, DESC
		}

		protected Sort sort = Sort.NONE;

		protected String header = null;

		public Configuration sort(Sort sort) {
			this.sort = sort;
			return this;
		}

		public Configuration header(String header) {
			this.header = header;
			return this;
		}

		public ConfigurableAggregator build() {
			ConfigurableAggregator ca = new ConfigurableAggregator();
			ca.config = this;
			return ca;
		}

	}

	protected Configuration config;

	public void aggregate(OutputChannel channel, List<AggregateLoggable> list) {
		// print header
		if (this.config.header != null) {
			channel.println(this.config.header);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < this.config.header.length(); ++i)
				sb.append("=");
			channel.println(sb.toString());
		}

		// sort
		if (!this.config.sort.equals(Configuration.Sort.NONE)) {
			final int i = this.config.sort.equals(Configuration.Sort.ASC) ? 1 : -1;
			Collections.sort(list, new Comparator<AggregateLoggable>() {
				public int compare(AggregateLoggable a, AggregateLoggable b) {
					return i * a.toString().compareTo(b.toString());
				}
			});
		}

		StringBuilder sb = new StringBuilder();
		for (AggregateLoggable l : list) {
			sb.append(l.toString()).append("\n");
		}

		channel.print(sb.toString());
	}

}
