package at.favre.tools.tagger.analyzer.metadata;

import at.favre.tools.tagger.analyzer.EVideoType;

import java.io.File;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class FileMetaData {
	private static final int INVALID = -1;
	private static final boolean VERBOSE_LOG = false;

	private String originalFullPath;
	private EVideoType type = EVideoType.SERIES;
	private FolderMetaData folder;

	private String titleChunks = "";
	private String guessedTitle = "";
	private int year = INVALID;

	private SeriesData seriesData;
	private ServiceIds serviceIds;

	public FileMetaData(String originalFullPath) {
		this.originalFullPath = originalFullPath;
		serviceIds = new ServiceIds();
		seriesData=new SeriesData();
	}

	public EVideoType getType() {
		return type;
	}

	public void setType(EVideoType type) {
		this.type = type;
	}

	public String getTitleChunks() {
		return titleChunks;
	}

	public void setTitleChunks(String titleChunks) {
		this.titleChunks = titleChunks;
	}

	public FolderMetaData getFolder() {
		return folder;
	}

	public void setFolder(FolderMetaData folder) {
		this.folder = folder;
	}

	public SeriesData getSeriesData() {
		return seriesData;
	}

	public void setSeriesData(SeriesData seriesData) {
		this.seriesData = seriesData;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getGuessedTitle() {
		return guessedTitle;
	}

	public void setGuessedTitle(String guessedTitle) {
		this.guessedTitle = guessedTitle;
	}


	public String getPureFileName() {
		File file = new File(originalFullPath);
		return stripExtension(file.getName());
	}

	private static String stripExtension(String fileName) {
		int lastIndexOfPoint = fileName.lastIndexOf('.');

		if(lastIndexOfPoint != -1) {
			return fileName.substring(0,lastIndexOfPoint);
		}
		return fileName;
	}

	@Override
	public String toString() {
		return getStringRepresentation();
	}

	private String getStringRepresentation() {
		StringBuilder sb = new StringBuilder();
		sb.append(titleChunks);

		if(year != INVALID) {
			sb.append(" ("+year+")");
		}

		if(guessedTitle.length() > 0) {
			sb.append(" [guessed: "+guessedTitle+"]");
		}

		if(VERBOSE_LOG) {
			sb.append(" FULLPATH: "+originalFullPath);
		}


		if (type.equals(EVideoType.SERIES)) {
			return "S" + String.format("%02d", seriesData.getSeason()) + "E" + String.format("%02d", seriesData.getEpisode()) + " " + sb.toString();
		}

		return sb.toString();
	}
}
