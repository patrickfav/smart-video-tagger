package at.favre.tools.tagger.analyzer;

import at.favre.tools.tagger.analyzer.metadata.Guess;

import java.util.List;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public interface GuessCallback {
	public void onAnalyseComplete(List<Guess> guessList);
}
