package at.favre.tools.tagger.analyzer.worker;

import at.favre.tools.tagger.analyzer.metadata.FileInfo;
import at.favre.tools.tagger.analyzer.metadata.FolderInfo;
import at.favre.tools.tagger.analyzer.metadata.Guess;
import at.favre.tools.tagger.analyzer.metadata.Probability;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class TitlePathAnalyzer implements IAnalyzer{

	@Override
	public List<Guess> analyze(FileInfo fileInfo) {
		List<Guess> guessList = new ArrayList<Guess>();
		FolderInfo folder = fileInfo.getParentFolder();

		while(folder  != null) {
			if(folder.getFolderName().length() >= 3) {
				Matcher m = Pattern.compile(folder.getFolderName(),Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)
						.matcher(fileInfo.getCleanedFileName());
				if(m.find()) {
					guessList.add(new Guess(Guess.Type.TITLE, Probability.getInstance(0.75),folder.getFolderName(), this.getClass().getSimpleName()));
				}
			}
			folder = folder.getParentFolder();
		}

		return guessList;
	}

	public void close() {}
}
