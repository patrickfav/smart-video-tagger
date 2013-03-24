package at.favre.tools.tagger.analyzer;

import at.favre.tools.tagger.analyzer.matcher.DateAnalyzer;
import at.favre.tools.tagger.analyzer.matcher.SeasonEpisodeAnalyser;
import at.favre.tools.tagger.analyzer.matcher.SeasonEpisodePatternType;
import at.favre.tools.tagger.analyzer.matcher.TitleAnalyser;
import at.favre.tools.tagger.analyzer.metadata.FileMetaData;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class FileNameAnalyser {
	private static final int MAX_SEASON = 29;
	private static final int MAX_EPISODE = 49;

	private Logger log = LogManager.getLogger(this.getClass().getSimpleName());
	private List<SeasonEpisodeAnalyser> seasonEpisodeAnalyzers;
	private ScannerConfig config;

	public FileNameAnalyser(ScannerConfig config) {
		this.config = config;
		constructSeasonEpisodeAnalyzers();
	}

	private void constructSeasonEpisodeAnalyzers() {
		seasonEpisodeAnalyzers = new ArrayList<SeasonEpisodeAnalyser>(SeasonEpisodePatternType.values().length);

		for(SeasonEpisodePatternType type: SeasonEpisodePatternType.values()) {
			seasonEpisodeAnalyzers.add(type.ordinal(),new SeasonEpisodeAnalyser(type));
		}
	}

	public FileMetaData analyzeFile(String fileName) {
		FileMetaData fileMetaData = new FileMetaData(fileName);
		if(config.getContainedTypes().equals(EContainedTypes.MIXED) || config.getContainedTypes().equals(EContainedTypes.SERIES)) {
			fileMetaData = analyseSeasonEpisodeData(fileMetaData);
		} else {
			fileMetaData.setType(EVideoType.MOVIE);
		}

		fileMetaData = analyseTitle(fileMetaData);
		fileMetaData = analyseYearDate(fileMetaData);

		log.debug(fileMetaData);

		return fileMetaData;
	}

	private FileMetaData analyseTitle(FileMetaData fileMetaData) {
		List<String> titleChunks = TitleAnalyser.analyseTitle(fileMetaData.getPureFileName());

		StringBuilder sb = new StringBuilder();
		for(String s: titleChunks) {
			sb.append(s+" ");
		}

		fileMetaData.setTitleChunks(sb.toString());

		return fileMetaData;
	}

	private FileMetaData analyseSeasonEpisodeData(FileMetaData fileMetaData) {
		String fileName = fileMetaData.getPureFileName();

		for(SeasonEpisodeAnalyser analyser :seasonEpisodeAnalyzers) {
			analyser.setSourceString(fileName);
			if(analyser.containsPattern()) {
				if(analyser.getSeasonNumber() < 0 || analyser.getSeasonNumber() > MAX_SEASON) {
					log.debug(fileName +" has too high of a season number "+analyser.getSeasonNumber());
				} else if(analyser.getEpisodeNumber() < 0 || analyser.getEpisodeNumber() > MAX_EPISODE) {
					log.debug(fileName +" has too high of a episode number "+analyser.getEpisodeNumber());
				} else {
					//log.debug(fileName+" has been analyzed and "+analyser.getType()+" has found a match" +
					//		" with season "+analyser.getSeasonNumber()+" and episode "+analyser.getEpisodeNumber());
					fileMetaData.getSeriesData().setSeason(analyser.getSeasonNumber());
					fileMetaData.getSeriesData().setEpisode(analyser.getEpisodeNumber());
					fileMetaData.getSeriesData().setCouldReadSeasonEpisodeData(true);
					fileMetaData.setType(EVideoType.SERIES);
				}
			}
		}
		return fileMetaData;
	}

	private FileMetaData analyseYearDate(FileMetaData fileMetaData) {
		String fileName = fileMetaData.getPureFileName();
		int year;
		if((year = DateAnalyzer.analyse(fileName)) > 0) {
			fileMetaData.setYear(year);
		}

		return fileMetaData;
	}
}
