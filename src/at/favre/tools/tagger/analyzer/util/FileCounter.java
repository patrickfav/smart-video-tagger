package at.favre.tools.tagger.analyzer.util;

import java.io.File;
import java.util.List;

/**
 * @author PatrickF
 * @since 07.04.13
 */
public class FileCounter {

	public static int countAllFilesWithGivenExtensions(File root,List<String> extensions) {
		return countFillesWithGivenExtension(root,extensions,0);
	}


	private static int countFillesWithGivenExtension(File root,List<String> extensions,int countSoFar) {
		if(root.isDirectory()) {
			for(File child : root.listFiles()) {
				if(child.isDirectory()) {
					countSoFar = countFillesWithGivenExtension(child,extensions,countSoFar);
				} else {
					if(hasCorrectExtension(FileUtil.getFileExtension(child),extensions)) {
						countSoFar++;
					}
				}
			}
		}
		return countSoFar;
	}

	private static boolean hasCorrectExtension(String currentExtension, List<String> correctExtensionsRef) {
		for(String extension : correctExtensionsRef) {
			if(extension.equalsIgnoreCase(currentExtension)) {
				return true;
			}
		}
		return false;
	}

}
