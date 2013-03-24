package at.favre.tools.tagger.analyzer.matcher;

import at.favre.tools.tagger.system.ConfigManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class TitleAnalyser {

	public static List<String> analyseTitle(String fileName) {
		String s = removeWhiteSpaceSubstitutes(fileName+" ");
		s=removeIgnoreWords(s);
		s=removeNonWords(s);
		s=removeBrackets(s);
		s=removeSpecialChars(s);

		return tryToExtractTitleFromChunks(Arrays.asList(s.split(" ")));
	}

	private static String removeWhiteSpaceSubstitutes(String s) {
		s = s.replaceAll("_"," ");
		s = s.replaceAll("\\."," ");
		s = s.replaceAll("%20"," ");
		return s;
	}

	private static String removeIgnoreWords(String s) {
		for(String ignoreWord : ConfigManager.getInstance().getIgnoreWords()) {
			s = s.replaceAll("(?i)\\s" + ignoreWord+"\\s", " ");
		}
		return s;
	}

	private static String removeBrackets(String s) {
		s = s.replaceAll("\\[.*\\]","");
		s = s.replaceAll("\\(.*\\)","");
		s = s.replaceAll("\\{.*\\}","");

		return s;
	}

	private static String removeNonWords(String s) {
		s = s.replaceAll("\\s\\W[^\\s]+\\w*","");
		s = s.replaceAll("\\w+([0-9]|-)+\\w*","");
		return s;
	}

	private static String removeSpecialChars(String s) {
		s = s.replaceAll("[^\\w\\s\\d]","");
		return s;
	}


	private static List<String> tryToExtractTitleFromChunks(List<String> chunks) {
		List<String> cleanedChunks = new ArrayList<String>();
		int emptyChunks = 0;

		for(int i=0,ii=chunks.size();i<ii;i++) {
			if(chunks.get(i).length() == 0) {
				emptyChunks++;
			} else {
				cleanedChunks.add(chunks.get(i).trim());
			}
		}
		return cleanedChunks;
	}
}
