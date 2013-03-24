package at.favre.tools.tagger.analyzer.matcher;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class DateAnalyzer {
	private static final String BRACKET_DATE_PATTERN = "(\\(\\s*(19[3-9][0-9]|20[0-9]{2})\\s*\\)|\\[\\s*(19[3-9][0-9]|20[0-9]{2})\\s*\\])"; //matches dates from 1930 to 2099 in brackets e.g. (1995)
	private static final String STANDALONE_DATE_PATTERN = "\\s+(19[7-9][0-9]|20[0-9]{2})\\s+"; //matches dates from 1970-2099

	private static Logger log = LogManager.getLogger(DateAnalyzer.class.getSimpleName());

	public static int analyse(String pureFilename) {
		pureFilename= TitleAnalyser.removeWhiteSpaceSubstitutes(pureFilename);

		Matcher bracketPattern = Pattern.compile(BRACKET_DATE_PATTERN, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(pureFilename);
		Matcher standAlonePattern = Pattern.compile(STANDALONE_DATE_PATTERN, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(pureFilename);

		int yearBrackets=-1, standAloneYear=-1;

		if(bracketPattern.find()) {
			yearBrackets = Integer.valueOf(bracketPattern.group().replaceAll("[^\\d]",""));
			return yearBrackets;
		}

		if(standAlonePattern.find()) {
			standAloneYear = Integer.valueOf(standAlonePattern.group().replaceAll("[^\\d]",""));
			return standAloneYear;
		}

		return -1;
	}
}
