package at.favre.tools.tagger.analyzer.matcher;

import at.favre.tools.tagger.analyzer.metadata.FileInfo;
import at.favre.tools.tagger.analyzer.metadata.Guess;
import at.favre.tools.tagger.analyzer.metadata.Probability;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class DateAnalyzer implements IAnalyzer{
	private static final String BRACKET_DATE_PATTERN = "(\\(\\s*(19[3-9][0-9]|20[0-9]{2})\\s*\\)|\\[\\s*(19[3-9][0-9]|20[0-9]{2})\\s*\\])"; //matches dates from 1930 to 2099 in brackets e.g. (1995)
	private static final String STANDALONE_DATE_PATTERN = "\\s+(19[7-9][0-9]|20[0-9]{2})\\s+"; //matches dates from 1970-2099

	private static Logger log = LogManager.getLogger(DateAnalyzer.class.getSimpleName());

	@Override
	public List<Guess> analyze(FileInfo fileInfo) {
		List<Guess> guessList = new ArrayList<Guess>();

		Matcher bracketPattern = Pattern.compile(BRACKET_DATE_PATTERN, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(fileInfo.getPureFileName());
		Matcher standAlonePattern = Pattern.compile(STANDALONE_DATE_PATTERN, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(fileInfo.getPureFileName());

		int yearBrackets=-1, standAloneYear=-1;

		if(bracketPattern.find()) {
			yearBrackets = Integer.valueOf(bracketPattern.group().replaceAll("[^\\d]",""));

		}

		if(standAlonePattern.find()) {
			standAloneYear = Integer.valueOf(standAlonePattern.group().replaceAll("[^\\d]",""));
		}

		if(yearBrackets != -1 || standAloneYear != -1) {
			if(yearBrackets == standAloneYear) {
				guessList.add(new Guess(Guess.Type.YEAR,Probability.getInstance(0.8),String.valueOf(yearBrackets), this.getClass().getSimpleName()));
			} else {
				if(yearBrackets != -1) {
					guessList.add(new Guess(Guess.Type.YEAR,Probability.getInstance(0.7),String.valueOf(yearBrackets), this.getClass().getSimpleName()));
				}
				if(standAloneYear != -1) {
					guessList.add(new Guess(Guess.Type.YEAR,Probability.getInstance(0.7),String.valueOf(yearBrackets), this.getClass().getSimpleName()));
				}
			}
		}
		return guessList;
	}

	public void close() {}
}
