package at.favre.tools.tagger.analyzer.matcher;

import at.favre.tools.tagger.analyzer.metadata.FileMetaData;
import at.favre.tools.tagger.analyzer.metadata.FolderMetaData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class PathAnalyzer {

	public static FileMetaData analyze(FileMetaData fileMetaData) {

		FolderMetaData folder = fileMetaData.getFolder();

		while(folder  != null) {
			if(folder.getFolderName().length() >= 3) {
				Matcher m = Pattern.compile(folder.getFolderName(),Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE).matcher(fileMetaData.getTitleChunks());
				if(m.find()) {
					fileMetaData.setGuessedTitle(folder.getFolderName());
					break;
				}
			}
			folder = folder.getParentFolder();
		}

		return fileMetaData;
	}
}
