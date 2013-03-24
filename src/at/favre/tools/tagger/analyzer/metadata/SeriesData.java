package at.favre.tools.tagger.analyzer.metadata;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class SeriesData {
	public static final int INVALID = -1;

	private int season = INVALID;
	private int episode = INVALID;
	private String episodeTitle="";
	private boolean couldReadSeasonEpisodeData = false;


	public int getSeason() {
		return season;
	}

	public void setSeason(int season) {
		this.season = season;
	}

	public int getEpisode() {
		return episode;
	}

	public void setEpisode(int episode) {
		this.episode = episode;
	}

	public String getEpisodeTitle() {
		return episodeTitle;
	}

	public void setEpisodeTitle(String episodeTitle) {
		this.episodeTitle = episodeTitle;
	}

	public boolean isCouldReadSeasonEpisodeData() {
		return couldReadSeasonEpisodeData;
	}

	public void setCouldReadSeasonEpisodeData(boolean couldReadSeasonEpisodeData) {
		this.couldReadSeasonEpisodeData = couldReadSeasonEpisodeData;
	}
}
