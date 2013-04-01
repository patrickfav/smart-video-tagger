package at.favre.tools.tagger.analyzer.matcher;

import at.favre.tools.tagger.analyzer.metadata.FileInfo;
import at.favre.tools.tagger.analyzer.metadata.Guess;
import at.favre.tools.tagger.analyzer.metadata.Probability;
import at.favre.tools.tagger.io.ffmpeg.FfmpegProcess;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public class ReadMetaDataAnalyzer implements IAnalyzer{
	private FfmpegProcess ffmpegProcess;

	public ReadMetaDataAnalyzer(String pathToFfmpeg) {
		ffmpegProcess = new FfmpegProcess(pathToFfmpeg);
	}

	@Override
	public List<Guess> analyze(FileInfo fileInfo) {
		List<Guess> guessList = new ArrayList<Guess>();

		Properties properties = ffmpegProcess.getAllMetaData(fileInfo.getFullPath());

		if(properties != null) {
			if(properties.contains("title")) {
				guessList.add(new Guess(Guess.Type.TITLE, Probability.getInstance(0.85),(String) properties.get("title"),ReadMetaDataAnalyzer.class.getSimpleName()));
			}
		}

		return guessList;
	}

	public void close() {
		ffmpegProcess.cleanUp();
	}
}
