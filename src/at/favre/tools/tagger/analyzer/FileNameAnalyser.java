package at.favre.tools.tagger.analyzer;

import at.favre.tools.tagger.MetaDataParser;
import at.favre.tools.tagger.analyzer.matcher.*;
import at.favre.tools.tagger.analyzer.metadata.FileMetaData;
import at.favre.tools.tagger.analyzer.metadata.FolderInfo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class FileNameAnalyser {
	private static Logger log = LogManager.getLogger(FileNameAnalyser.class.getSimpleName());

	private List<IAnalyzer> analyzers;
	private FolderInfo rootFolder;

	public FileNameAnalyser(FolderInfo rootFolder) {
		this.rootFolder = rootFolder;
		analyzers = new ArrayList<IAnalyzer>();
		analyzers.add(new DateAnalyzer());
		analyzers.add(new TitlePathAnalyzer());
		analyzers.add(new SeasonEpisodeAnalyser());
	}

	public void analyzeAll() {
		expand(rootFolder);

		while(WorkerManager.getInstance().getThreadPool().isTerminated()) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		new MetaDataParser(rootFolder).parseToLog();
	}

	private void expand(FolderInfo folderToExpand) {

		for(FileMetaData file: folderToExpand.getFiles()) {
			for(IAnalyzer analyzer:analyzers) {
				Runnable r = new AnalyseRunner(analyzer,file.getFileInfo(),file);
				WorkerManager.getInstance().getThreadPool().execute(r);
			}
		}

		for(FolderInfo folder: folderToExpand.getChildren()) {
			expand(folder);
		}
	}


}
