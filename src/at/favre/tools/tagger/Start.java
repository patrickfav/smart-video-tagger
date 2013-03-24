package at.favre.tools.tagger;

import at.favre.tools.tagger.analyzer.EContainedTypes;
import at.favre.tools.tagger.analyzer.ScannerConfig;
import at.favre.tools.tagger.io.VideoFileVisitor;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class Start {
	private static Logger log = LogManager.getLogger(Start.class.getSimpleName());


	public static void main(String[] args) {
		Path path = FileSystems.getDefault().getPath("H:\\== MOVIES ==");

		ScannerConfig config = new ScannerConfig(EContainedTypes.MOVIES,true,true);

		try {
			VideoFileVisitor visitor = new VideoFileVisitor(config);
			Files.walkFileTree(path, visitor);
			log.info("Found files "+visitor.getSumFileVisited()+", recognized "+visitor.getSumRecognizedFiles());
		} catch (IOException e) {
			e.printStackTrace();
		}



	}
}
