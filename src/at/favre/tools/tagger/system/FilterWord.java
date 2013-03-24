package at.favre.tools.tagger.system;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class FilterWord {
	public enum CaseSensitivity {IGNORE_CASE, KEEP_CASE}
	private final String word;
	private final CaseSensitivity caseSensitivity;

	public FilterWord(String word, CaseSensitivity caseSensitivity) {
		this.word = word;
		this.caseSensitivity = caseSensitivity;
	}

	public String getWord() {
		return word;
	}

	public boolean isIgnoreCase() {
		return caseSensitivity.equals(CaseSensitivity.IGNORE_CASE);
	}
}
