package at.favre.tools.tagger.analyzer.matcher;

import at.favre.tools.tagger.analyzer.metadata.FileInfo;
import at.favre.tools.tagger.analyzer.metadata.FileMetaData;
import at.favre.tools.tagger.analyzer.metadata.Guess;
import at.favre.tools.tagger.analyzer.metadata.Probability;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public class FilesInPathTitleAnalyzer implements IAnalyzer{
	private static final int MIN_SUBSTRING_LENGTH = 4;

	private int skippedFiles;

	@Override
	public List<Guess> analyze(FileInfo fileInfo) {
		skippedFiles=0;
		List<Guess> guessList = new ArrayList<Guess>();

		String longestSubString = fileInfo.getCleanedFileName();

		for(FileMetaData file:fileInfo.getParentFolder().getFiles()) {
			String newSubString;
			if(!file.getFileInfo().equals(fileInfo)) {
				newSubString = longestSubstring(longestSubString,file.getFileInfo().getCleanedFileName()).trim();

				if(newSubString.length() >= MIN_SUBSTRING_LENGTH) {
					longestSubString = newSubString;
				} else {
					skippedFiles++;
				}
			}
		}

		if(!longestSubString.equals(fileInfo.getCleanedFileName())) {
			Probability p = Probability.getInstance(getLikelinessOfCorrectTitle(skippedFiles,fileInfo.getParentFolder().getFiles().size()));
			guessList.add(new Guess(Guess.Type.TITLE,p,longestSubString, this.getClass().getSimpleName()));
		}


		return guessList;
	}

	private static double getLikelinessOfCorrectTitle(int skippedFiles, int allFilesCount) {
		if(skippedFiles == 0)
			return 0.85;

		if(allFilesCount == 0 || skippedFiles >= allFilesCount)
			return 0.0;

		double percentage =  (((double) skippedFiles) / ((double) allFilesCount) * (-1)) +1;

		if(percentage > 0.2) {
			percentage -= 0.2;
		}

		return percentage;
	}

	private static String longestSubstring(String str1, String str2) {

		StringBuilder sb = new StringBuilder();
		if (str1 == null || str1.isEmpty() || str2 == null || str2.isEmpty())
			return "";

		// ignore case
		str1 = str1.toLowerCase();
		str2 = str2.toLowerCase();

		// java initializes them already with 0
		int[][] num = new int[str1.length()][str2.length()];
		int maxlen = 0;
		int lastSubsBegin = 0;

		for (int i = 0; i < str1.length(); i++) {
			for (int j = 0; j < str2.length(); j++) {
				if (str1.charAt(i) == str2.charAt(j)) {
					if ((i == 0) || (j == 0))
						num[i][j] = 1;
					else
						num[i][j] = 1 + num[i - 1][j - 1];

					if (num[i][j] > maxlen) {
						maxlen = num[i][j];
						// generate substring from str1 => i
						int thisSubsBegin = i - num[i][j] + 1;
						if (lastSubsBegin == thisSubsBegin) {
							//if the current LCS is the same as the last time this block ran
							sb.append(str1.charAt(i));
						} else {
							//this block resets the string builder if a different LCS is found
							lastSubsBegin = thisSubsBegin;
							sb = new StringBuilder();
							sb.append(str1.substring(lastSubsBegin, i + 1));
						}
					}
				}
			}}

		return sb.toString();
	}
}
