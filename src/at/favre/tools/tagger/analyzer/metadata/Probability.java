package at.favre.tools.tagger.analyzer.metadata;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public class Probability {
	private static Map<Double,Probability> cache = new ConcurrentHashMap<Double, Probability>();

	private final double probability;

	public static Probability getInstance(double probability) {
		if(cache.containsKey(probability)) {
			return cache.get(probability);
		} else {
			Probability p = new Probability(probability);
			cache.put(probability,p);
			return p;
		}
	}

	private Probability(double probability) {
		if(probability > 1.0 || probability < 0.0) {
			throw new IllegalArgumentException("Probability must be between 0.0 and 1.0");
		}
		this.probability = probability;
	}

	public double getProbability() {
		return probability;
	}

	public int getPossibilityAsPrecentage() {
		return (int) Math.round(probability * 100);
	}

	@Override
	public String toString() {
		return String.valueOf(probability);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Probability that = (Probability) o;

		if (Double.compare(that.probability, probability) != 0) return false;

		return true;
	}

	@Override
	public int hashCode() {
		long temp = probability != +0.0d ? Double.doubleToLongBits(probability) : 0L;
		return (int) (temp ^ (temp >>> 32));
	}
}
