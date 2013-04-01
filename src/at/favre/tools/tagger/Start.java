package at.favre.tools.tagger;

import at.favre.tools.tagger.analyzer.FileAnalyzer;
import at.favre.tools.tagger.analyzer.config.EContainedTypes;
import at.favre.tools.tagger.analyzer.config.ScannerConfig;
import at.favre.tools.tagger.analyzer.worker.WorkerManager;
import at.favre.tools.tagger.analyzer.io.VideoFileVisitor;
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

		ScannerConfig config = new ScannerConfig(EContainedTypes.MIXED,true,true, "C:\\Program Files (x86)\\ffmpeg\\ffmpeg-win32-static\\bin\\ffmpeg.exe");

		VideoFileVisitor visitor = new VideoFileVisitor();

		try {
			log.info("Start reading filesystem.");
			Files.walkFileTree(path, visitor);
			log.info("Found files "+visitor.getSumFileVisited());
		} catch (IOException e) {
			e.printStackTrace();
		}

		FileAnalyzer analyzer = new FileAnalyzer(visitor.getRoot(),config);
		analyzer.analyzeAll();
		analyzer.parseToLog();

		WorkerManager.getInstance().getThreadPool().shutdown();
	}
}
