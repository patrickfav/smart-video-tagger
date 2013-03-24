package at.favre.tools.tagger.analyzer.matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class SeasonEpisodeAnalyser {
	private Logger log = LogManager.getLogger(this.getClass().getSimpleName());
	public static final int NON_FOUND = -1;

	private String source;
	private final Pattern pattern;
	private SeasonEpisodePatternType type;

	public SeasonEpisodeAnalyser(SeasonEpisodePatternType type) {
		log.debug("Create Analyser for " + type);
		this.type = type;
		this.pattern = Pattern.compile(type.getPattern(),Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
	}

	public boolean containsPattern() {
		checkSource();
		return pattern.matcher(getSourceString()).find();
	}

	public int getSeasonNumber() {
		return getIntFromStrippedDownString(0,0,1,0,2);
	}

	public int getEpisodeNumber() {
		return getIntFromStrippedDownString(1,1,3,2,4);
	}

	private int getIntFromStrippedDownString(int subStringStart2, int subStringStart3,int subStringEnd3, int subStringStart4, int subStringEnd4) {
		checkSource();
		if(containsPattern()) {
			Matcher matcher = pattern.matcher(getSourceString());
			matcher.find();
			String matchedString = matcher.group();
			matchedString= matchedString.replaceAll("[^\\d]","");

			if(matchedString.length() == 2) {
				return Integer.valueOf(matchedString.substring(subStringStart2,subStringStart2+1));
			} else if(matchedString.length() == 3) {
				return Integer.valueOf(matchedString.substring(subStringStart3,subStringEnd3));
			} else if (matchedString.length() == 4) {
				return Integer.valueOf(matchedString.substring(subStringStart4,subStringEnd4));
			} else {
				log.warn("Resulting string "+matchedString+" has strange length of "+matchedString.length());
			}
		}
		return NON_FOUND;
	}

	private void checkSource() {
		if (source == null) {
			throw new RuntimeException("You are using this class without an source string" +
					", call setSourceString(src), before calling any other method");
		}
	}

	public void setSourceString(String source) {
		this.source = source;
	}

	public String getSourceString() {
		return source;
	}

	public SeasonEpisodePatternType getType() {
		return type;
	}
}
