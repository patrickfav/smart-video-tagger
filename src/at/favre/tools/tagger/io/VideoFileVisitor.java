package at.favre.tools.tagger.io;

import at.favre.tools.tagger.analyzer.FileNameAnalyser;
import at.favre.tools.tagger.analyzer.ScannerConfig;
import at.favre.tools.tagger.analyzer.metadata.FileMetaData;
import at.favre.tools.tagger.analyzer.metadata.FolderMetaData;
import at.favre.tools.tagger.system.ConfigManager;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
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
	private FolderMetaData folderMetaData;

	public VideoFileVisitor(ScannerConfig config) {
		analyser = new FileNameAnalyser(config);
	}

	/**
	 * Invoked for a directory before entries in the directory are visited.
	 * <p/>
	 * <p> Unless overridden, this method returns {@link java.nio.file.FileVisitResult#CONTINUE
	 * CONTINUE}.
	 */
	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		FolderMetaData newFolder = new FolderMetaData(folderMetaData,dir.toFile().getName());

		if(folderMetaData != null) {
			folderMetaData.addChildFolder(newFolder);
		}

		folderMetaData = newFolder;
		return super.preVisitDirectory(dir, attrs);    //Autocreated
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
		//log.trace("visiting file "+file+ " with extension "+getFileExtension(file.toString()));
		if(attr.isRegularFile() && hasCorrectExtension(getFileExtension(file.toString()))) {
			sumFileVisited++;
			FileMetaData fileMetaData = analyser.analyzeFile(file.toString());
			fileMetaData.setFolder(folderMetaData);
			folderMetaData.addFile(fileMetaData);

			if(fileMetaData.getSeriesData().isCouldReadSeasonEpisodeData()) {
				sumRecognizedFiles++;
			} else {
				//log.trace("could not recognize "+file+ " with extension "+getFileExtension(file.toString()));
			}
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		folderMetaData = folderMetaData.getParentFolder();
		return super.postVisitDirectory(dir, exc);    //Autocreated
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
