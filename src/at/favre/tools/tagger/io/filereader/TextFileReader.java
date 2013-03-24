package at.favre.tools.tagger.io.filereader;

import java.io.*;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class TextFileReader {
	private static final String COMMENT_LINE = "#";
	public String pathToTextFile;

	public TextFileReader(String pathToTextFile) {
		this.pathToTextFile = pathToTextFile;
	}

	public List<String> getAllItemsInTextFile() {
		List<String> lines = new ArrayList<String>();

		BufferedReader reader = null;
		File file = null;
		try {
			file = new File(this.getClass().getResource(pathToTextFile).toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException("Could not get file from classpath.",e);
		}
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not find the file.",e);
		}

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				if(!line.startsWith(COMMENT_LINE))
					lines.add(line.replaceAll("\\#","#"));
			}
		} catch (IOException e) {
			throw new RuntimeException("IO Exception while reading the line",e);
		}

		return lines;
	}
}
