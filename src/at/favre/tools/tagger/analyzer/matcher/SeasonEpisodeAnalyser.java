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
 * @since 23.03.13
 */
public class SeasonEpisodeAnalyser implements IAnalyzer{
	private Logger log = LogManager.getLogger(this.getClass().getSimpleName());
	private static final int SUSPECT_SEASON_THRESHOLD = 10;
	private static final int SUSPECT_EPISODE_THRESHOLD = 30;

	public static final int NON_FOUND = -1;

	@Override
	public List<Guess> analyze(FileInfo fileInfo) {
		List<Guess> guessList = new ArrayList<Guess>();

		for(SeasonEpisodePatternType type:SeasonEpisodePatternType.values()) {
			Matcher matcher = Pattern.compile(type.getPattern(),Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(fileInfo.getPureFileName());
			if(matcher.find()) {
				String matchedString = matcher.group();
				matchedString= matchedString.replaceAll("[^\\d]","");

				int season=NON_FOUND, episode=NON_FOUND;
				if(matchedString.length() == 2) {
					season = Integer.valueOf(matchedString.substring(0,1));
					episode = Integer.valueOf(matchedString.substring(1,2));
				} else if(matchedString.length() == 3) {
					season = Integer.valueOf(matchedString.substring(0,1));
					episode = Integer.valueOf(matchedString.substring(1,3));
				} else if (matchedString.length() == 4) {
					season = Integer.valueOf(matchedString.substring(0,2));
					episode = Integer.valueOf(matchedString.substring(2,4));
				} else {
					log.warn("Resulting string "+matchedString+" has strange length of "+matchedString.length());
				}

				if(season != NON_FOUND) {
					guessList.add(new Guess(Guess.Type.SEASON_NO, getDeductedSeasonProbability(type.getProbabilityOfPatternMeansSesonEpisode(),season),String.valueOf(season), this.getClass().getSimpleName()));
				}

				if(episode != NON_FOUND) {
					guessList.add(new Guess(Guess.Type.EPISODE_NO, getDeductedEpisodeProbability(type.getProbabilityOfPatternMeansSesonEpisode(),episode),String.valueOf(episode), this.getClass().getSimpleName()));
				}
			}
		}

		return guessList;
	}

	private Probability getDeductedSeasonProbability(Probability undeductedProbability, int season) {
		double prob = undeductedProbability.getProbability();

		if(season > SUSPECT_SEASON_THRESHOLD) {
			int deduction =  season / SUSPECT_SEASON_THRESHOLD;
			return Probability.getInstance(prob - (deduction * 0.1));
		}
		return undeductedProbability;
	}

	private Probability getDeductedEpisodeProbability(Probability undeductedProbability, int episode) {
		double prob = undeductedProbability.getProbability();

		if(episode > SUSPECT_EPISODE_THRESHOLD) {
			int deduction =  episode / SUSPECT_EPISODE_THRESHOLD;
			return Probability.getInstance(prob - (deduction * 0.1));
		}
		return undeductedProbability;
	}
}
