package at.favre.tools.tagger.analyzer;

import at.favre.tools.tagger.analyzer.config.ScannerConfig;
import at.favre.tools.tagger.analyzer.metadata.FileMetaData;
import at.favre.tools.tagger.analyzer.metadata.FolderInfo;
import at.favre.tools.tagger.analyzer.metadata.MetaDataParser;
import at.favre.tools.tagger.analyzer.worker.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class FileAnalyzer {
	private static Logger log = LogManager.getLogger(FileAnalyzer.class.getSimpleName());

	private List<IAnalyzer> analyzers;
	private FolderInfo rootFolder;
	private Date analyzeStart;
	private Date analyzeEnd;

	private int startedThreads = 0;

	public FileAnalyzer(FolderInfo rootFolder, ScannerConfig config) {
		this.rootFolder = rootFolder;
		analyzers = new ArrayList<>();
		analyzers.add(new DateAnalyzer());
		analyzers.add(new TitlePathAnalyzer());
		analyzers.add(new SeasonEpisodeAnalyser());
		analyzers.add(new FilesInPathTitleAnalyzer());
		analyzers.add(new ReadMetaDataAnalyzer(config.getFfmpegPath()));
	}

	public void analyzeAll() {
		analyzeStart = new Date();
		expand(rootFolder);

		log.info("Startet threads: "+startedThreads);
		while(!WorkerManager.getInstance().getThreadPool().isShutdown()) {
			try {
				log.info("Finished threads: "+WorkerManager.getFinishedThreads().toString());

				if(WorkerManager.getFinishedThreads().intValue() == startedThreads) {
					WorkerManager.getInstance().getThreadPool().shutdownNow();
				}

				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		analyzeEnd = new Date();

		log.info("Analyse complete. Duration: "+getLastAnalysationDuration()+" ms");

		for(IAnalyzer analyzer:analyzers) {
			analyzer.close();
		}
	}

	private void expand(FolderInfo folderToExpand) {

		for(FileMetaData file: folderToExpand.getFiles()) {
			for(IAnalyzer analyzer:analyzers) {
				Runnable r = new AnalyseRunner(analyzer,file.getFileInfo(),file);
				WorkerManager.getInstance().getThreadPool().execute(r);
				startedThreads++;
			}
		}

		for(FolderInfo folder: folderToExpand.getChildren()) {
			expand(folder);
		}
	}

	public void parseToLog() {
		new MetaDataParser(rootFolder).parseToLog();
	}

	public long getLastAnalysationDuration() {
		if(analyzeStart != null && analyzeEnd != null) {
			return analyzeEnd.getTime() - analyzeStart.getTime();
		}

		return -1;
	}


}
