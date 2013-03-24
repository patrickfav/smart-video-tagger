package at.favre.tools.tagger.system;

import at.favre.tools.tagger.io.filereader.TextFileReader;

import java.util.List;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class ConfigManager {
	private static ConfigManager ourInstance = new ConfigManager();

	private final List<String> extensions;
	private final List<String> ignoreWords;

	public static ConfigManager getInstance() {
		return ourInstance;
	}

	private ConfigManager() {
		extensions = readExtensions();
		ignoreWords = readIgnoreWords();
	}

	private List<String> readExtensions() {
		return new TextFileReader("/extensions.txt").getAllItemsInTextFile();
	}

	private List<String> readIgnoreWords() {
		return new TextFileReader("/ignore-words.txt").getAllItemsInTextFile();
	}

	public List<String> getExtensions() {
		return extensions;
	}

	public List<String> getIgnoreWords() {
		return ignoreWords;
	}
}
