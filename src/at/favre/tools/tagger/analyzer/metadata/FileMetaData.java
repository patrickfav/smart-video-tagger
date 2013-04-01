package at.favre.tools.tagger.analyzer.metadata;

import at.favre.tools.tagger.analyzer.worker.IAnalyzer;
import at.favre.tools.tagger.analyzer.worker.WorkerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class FileMetaData implements IAnalyzer.GuessCallback {
	private static final int INVALID = -1;
	private static final boolean VERBOSE_LOG = false;

	private EVideoType type = EVideoType.SERIES;

	private final FileInfo fileInfo;

	private SeriesData seriesData;
	private ServiceIds serviceIds;

	private List<Guess> guesses;

	public FileMetaData(String originalFullPath, FolderInfo parentFolder) {
		fileInfo = new FileInfo(originalFullPath,parentFolder);
		serviceIds = new ServiceIds();
		seriesData=new SeriesData();
		guesses = new CopyOnWriteArrayList<Guess>();
	}

	public FileInfo getFileInfo() {
		return fileInfo;
	}

	public EVideoType getType() {
		return type;
	}

	public void setType(EVideoType type) {
		this.type = type;
	}

	public SeriesData getSeriesData() {
		return seriesData;
	}

	public void setSeriesData(SeriesData seriesData) {
		this.seriesData = seriesData;
	}

	public List<Guess> getGuesses() {
		return guesses;
	}

	public  List<Guess> getGuessesByType(Guess.Type type) {
		List<Guess> typeList = new ArrayList<Guess>();

		for(Guess g: guesses) {
			if(g.getType().equals(type)) {
				typeList.add(g);
			}
		}

		return Collections.unmodifiableList(typeList);
	}

	@Override
	public String toString() {
		return getStringRepresentation();
	}

	private String getStringRepresentation() {
		StringBuilder sb = new StringBuilder();
		sb.append(fileInfo.getPureFileName());


		for(Guess.Type type:Guess.Type.values()) {
			for(Guess g: getGuessesByType(type)) {
				sb.append(" | "+g);
			}
		}

		return sb.toString();
	}

	@Override
	public void onAnalyseComplete(List<Guess> guessList) {
		WorkerManager.getFinishedThreads().incrementAndGet();
		guesses.addAll(guessList);
	}
}
