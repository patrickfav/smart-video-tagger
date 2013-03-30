package at.favre.tools.tagger.analyzer.metadata;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public class Guess {
	public enum Type {SEASON_NO, EPISODE_NO, TITLE, EPISODE_TITLE, YEAR, MEDIA_TYPE}

	private final Type type;
	private final Probability probability;
	private final String value;

	public Guess(Type type, Probability probability, String value) {
		this.type = type;
		this.probability = probability;
		this.value = value;
	}

	public Type getType() {
		return type;
	}

	public Probability getProbability() {
		return probability;
	}

	public String getValue() {
		return value;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Guess guess = (Guess) o;

		if (!probability.equals(guess.probability)) return false;
		if (type != guess.type) return false;
		if (!value.equals(guess.value)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = type.hashCode();
		result = 31 * result + probability.hashCode();
		result = 31 * result + value.hashCode();
		return result;
	}
}
