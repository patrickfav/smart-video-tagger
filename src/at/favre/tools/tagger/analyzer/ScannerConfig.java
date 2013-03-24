package at.favre.tools.tagger.analyzer;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class ScannerConfig {
	private final EContainedTypes containedTypes;
	private final boolean seriesStoredHierarchical;
	private final boolean seriesSeasonStoredHierarchical;

	public ScannerConfig(EContainedTypes containedTypes, boolean seriesStoredHierarchical, boolean seriesSeasonStoredHierarchical) {
		this.containedTypes = containedTypes;
		this.seriesStoredHierarchical = seriesStoredHierarchical;
		this.seriesSeasonStoredHierarchical = seriesSeasonStoredHierarchical;
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
}
