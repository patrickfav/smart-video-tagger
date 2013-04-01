package at.favre.tools.tagger.analyzer.io.filereader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class TextFileReader {
	public File textFile;

	public TextFileReader(File textFile) {
		this.textFile = textFile;
	}

	public List<String> getAllItemsInTextFile() {
		List<String> lines = new ArrayList<String>();

		BufferedReader reader = null;
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(textFile);
			reader = new BufferedReader(new FileReader(textFile));
		} catch (FileNotFoundException e) {
			throw new RuntimeException("Could not find the file.",e);
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				lines.add(line.replaceAll("\\#","#"));
			}
		} catch (IOException e) {
			throw new RuntimeException("IO Exception while reading the line",e);
		}

		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return lines;
	}
}
