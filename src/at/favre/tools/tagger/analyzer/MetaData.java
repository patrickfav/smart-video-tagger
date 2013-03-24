package at.favre.tools.tagger.analyzer;

import java.util.Date;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class MetaData {
	private VideoType type;
	private String title;
	private Date release;
	private int season;
	private int episode;
	private String episodeTitle;
	private boolean couldReadSeasonEpisodeData = false;

	public VideoType getType() {
		return type;
	}

	public void setType(VideoType type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getRelease() {
		return release;
	}

	public void setRelease(Date release) {
		this.release = release;
	}

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
