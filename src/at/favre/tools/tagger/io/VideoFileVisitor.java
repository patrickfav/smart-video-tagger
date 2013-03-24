package at.favre.tools.tagger.io;

import at.favre.tools.tagger.analyzer.FileNameAnalyser;
import at.favre.tools.tagger.analyzer.MetaData;
import at.favre.tools.tagger.system.ConfigManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class VideoFileVisitor extends SimpleFileVisitor<Path> {
	private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

	private FileNameAnalyser analyser;
	private int sumFileVisited;
	private int sumRecognizedFiles;

	public VideoFileVisitor() {
		analyser = new FileNameAnalyser();
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
		//log.trace("visiting file "+file+ " with extension "+getFileExtension(file.toString()));
		if(attr.isRegularFile() && hasCorrectExtension(getFileExtension(file.toString()))) {
			sumFileVisited++;
			MetaData data = analyser.analyzeFile(file.toString());

			if(data.isCouldReadSeasonEpisodeData()) {
				sumRecognizedFiles++;
			} else {
				log.trace("could not recognize "+file+ " with extension "+getFileExtension(file.toString()));
			}
		}
		return FileVisitResult.CONTINUE;
	}

	private static String getFileExtension(String fileName) {
		int lastIndexOfPoint = fileName.lastIndexOf('.');

		if(lastIndexOfPoint != -1) {
			return fileName.substring(lastIndexOfPoint+1);
		}

		return "";
	}

	private static boolean hasCorrectExtension(String currentExtension) {
		for(String extension : ConfigManager.getInstance().getExtensions()) {
			if(extension.equalsIgnoreCase(currentExtension)) {
				return true;
			}
		}
		return false;
	}

	public int getSumFileVisited() {
		return sumFileVisited;
	}

	public int getSumRecognizedFiles() {
		return sumRecognizedFiles;
	}
}
