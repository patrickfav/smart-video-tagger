package at.favre.tools.tagger.analyzer;

import at.favre.tools.tagger.analyzer.matcher.SeasonEpisodeAnalyser;
import at.favre.tools.tagger.analyzer.matcher.SeasonEpisodePatternType;
import at.favre.tools.tagger.analyzer.matcher.TitleAnalyser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
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

	public FileNameAnalyser() {
		constructSeasonEpisodeAnalyzers();
	}

	private void constructSeasonEpisodeAnalyzers() {
		seasonEpisodeAnalyzers = new ArrayList<SeasonEpisodeAnalyser>(SeasonEpisodePatternType.values().length);

		for(SeasonEpisodePatternType type: SeasonEpisodePatternType.values()) {
			seasonEpisodeAnalyzers.add(type.ordinal(),new SeasonEpisodeAnalyser(type));
		}
	}

	public MetaData analyzeFile(String fileName) {
		MetaData metaData = new MetaData();
		File file = new File(fileName);
		String pureFileName = stripExtension(file.getName());

		metaData = analyseSeasonEpisodeData(pureFileName,metaData);
		metaData = analyseTitle(pureFileName,metaData);

		return metaData;
	}

	private MetaData analyseTitle(String fileName, MetaData metaData) {
		List<String> titleChunks = TitleAnalyser.analyseTitle(fileName);

		StringBuilder sb = new StringBuilder();
		for(String s: titleChunks) {
			sb.append(s+" ");
		}

		log.debug("Title Chunks: "+sb.toString()+" from "+fileName);

		metaData.setTitle(sb.toString());

		return metaData;
	}

	private MetaData analyseSeasonEpisodeData(String fileName,MetaData metaData) {
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
					metaData.setSeason(analyser.getSeasonNumber());
					metaData.setEpisode(analyser.getEpisodeNumber());
					metaData.setCouldReadSeasonEpisodeData(true);
				}
			}
		}
		return metaData;
	}

	private static String stripExtension(String fileName) {
		int lastIndexOfPoint = fileName.lastIndexOf('.');

		if(lastIndexOfPoint != -1) {
			return fileName.substring(0,lastIndexOfPoint);
		}
		return fileName;
	}


}
