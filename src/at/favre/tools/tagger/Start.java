package at.favre.tools.tagger;

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
		Path path = FileSystems.getDefault().getPath("H:\\== SERIES ==");

		try {
			VideoFileVisitor visitor = new VideoFileVisitor();
			Files.walkFileTree(path, visitor);
			log.info("Found files "+visitor.getSumFileVisited()+", recognized "+visitor.getSumRecognizedFiles());
		} catch (IOException e) {
			e.printStackTrace();
		}



	}
}
