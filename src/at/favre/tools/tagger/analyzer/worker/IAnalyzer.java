package at.favre.tools.tagger.analyzer.worker;

import at.favre.tools.tagger.analyzer.metadata.FileInfo;
import at.favre.tools.tagger.analyzer.metadata.Guess;

import java.util.List;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public interface IAnalyzer {

	public List<Guess> analyze(FileInfo fileInfo);
	public void close();


	interface GuessCallback {
		public void onAnalyseComplete(List<Guess> guessList);
	}
}
