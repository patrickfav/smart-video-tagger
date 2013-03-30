package at.favre.tools.tagger.analyzer.matcher;

import at.favre.tools.tagger.analyzer.GuessCallback;
import at.favre.tools.tagger.analyzer.metadata.FileInfo;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public class AnalyseRunner implements Runnable{
	private IAnalyzer analyzer;
	private FileInfo fileInfo;
	private GuessCallback callback;

	public AnalyseRunner(IAnalyzer analyzer, FileInfo fileInfo, GuessCallback callback) {
		this.analyzer = analyzer;
		this.fileInfo = fileInfo;
		this.callback = callback;
	}

	@Override
	public void run() {
		callback.onAnalyseComplete(analyzer.analyze(fileInfo));
	}
}
