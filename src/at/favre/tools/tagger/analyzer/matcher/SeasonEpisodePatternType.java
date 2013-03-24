package at.favre.tools.tagger.analyzer.matcher;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public enum SeasonEpisodePatternType {

	SXX_EXX_PATTERN("s\\d{1,2}(\\.?|_?|-?|\\s?)e\\d{1,2}"), //e.g. S01E02,S01.E02,S01_E02,S01 E02,S1E02
	DXDD_PATTERN( "\\d{1,2}x\\d{1,2}"), //e.g. 01x02,1x02
	BRACKETED_SEASON_PATTERN("\\[\\d{1,2}\\]e\\d{1,2}"), //e.g. [2]e15
	FULL_WORDS_PATTERN("season\\s?\\d{1,2}(\\s| - )episode\\s?\\d{1,2}"), //e.g. season 03 - episode 14
	NO_PREFIX_PATTERN("[^\\d]\\d{1,2}(_|-)\\d{1,2}[^\\d]"), //e.g. 01-02,01_02
	TRIPLE_DIGITS_PATTERN("(1[0-9]|0[1-9]|[1-9])[0-3][0-9][^\\d\\w]"); //e.g. 101, 415,

	private final String pattern;

	private SeasonEpisodePatternType(String pattern) {
		this.pattern = pattern;
	}

	public String getPattern() {
		return pattern;
	}

}
