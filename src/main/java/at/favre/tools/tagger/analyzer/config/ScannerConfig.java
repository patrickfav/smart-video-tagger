package at.favre.tools.tagger.analyzer.config;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class ScannerConfig {
	private final EContainedTypes containedTypes;
	private final boolean seriesStoredHierarchical;
	private final boolean seriesSeasonStoredHierarchical;
	private final String ffmpegPath;

	public ScannerConfig(EContainedTypes containedTypes, boolean seriesStoredHierarchical, boolean seriesSeasonStoredHierarchical, String ffmpegPath) {
		this.containedTypes = containedTypes;
		this.seriesStoredHierarchical = seriesStoredHierarchical;
		this.seriesSeasonStoredHierarchical = seriesSeasonStoredHierarchical;
		this.ffmpegPath = ffmpegPath;
	}

	public EContainedTypes getContainedTypes() {
		return containedTypes;
	}

	public boolean isSeriesStoredHierarchical() {
		return seriesStoredHierarchical;
	}

	public boolean isSeriesSeasonStoredHierarchical() {
		return seriesSeasonStoredHierarchical;
	}

	public String getFfmpegPath() {
		return ffmpegPath;
	}
}
