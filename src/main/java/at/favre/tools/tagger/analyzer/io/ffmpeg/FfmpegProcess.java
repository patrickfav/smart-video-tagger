package at.favre.tools.tagger.analyzer.io.ffmpeg;

import at.favre.tools.tagger.analyzer.io.filereader.TextFileReader;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @author PatrickF
 * @since 31.03.13
 */
public class FfmpegProcess {
	private final Logger log = LogManager.getLogger(this.getClass().getSimpleName());
	private static boolean MODULE_ENABLED;
	private static final String EXTENSION = ".tmp";
	private File ffmpegPath;
	private Path tempFolderPath;

	public FfmpegProcess(String pathToFfmpeg) {
		MODULE_ENABLED = true;

		try {
			ffmpegPath = new File(pathToFfmpeg);

			File tempFolder =new File("tempFolder");

			if(!tempFolder.exists()) {
				tempFolder.mkdir();
			}
		} catch (Exception e) {
			registerError("Could not create temp folder",e);
		}

		try {
			tempFolderPath = Files.createTempDirectory(FileSystems.getDefault().getPath("tempFolder"),"");
		} catch (IOException e) {
			registerError("Could not get temp folder",e);
		}

	}

	public Properties getAllMetaData(String pathToMovie) {
		if(MODULE_ENABLED) {
			File tempFile = null;
			Process ffmpegProcess =null;
			try {
				tempFile = new File(tempFolderPath.toFile(),UUID.randomUUID().toString()+EXTENSION);

				ProcessBuilder builder = new ProcessBuilder("\""+ffmpegPath.getAbsolutePath() +"\""+
						" -i \""+pathToMovie+"\" -f ffmetadata \""+tempFile.getAbsolutePath()+"\"");
				builder.redirectErrorStream(true);
				ffmpegProcess = builder.start();

				handleStream(ffmpegProcess.getInputStream(), "OUTPUT");
				ffmpegProcess.getInputStream().close();

				ffmpegProcess.waitFor();

				TextFileReader reader = new TextFileReader(tempFile);
				List<String> lines = reader.getAllItemsInTextFile();
				lines.add("parserComment="+pathToMovie);

				return parseMetaDataFile(lines);
			} catch(Exception e) {
				registerError("Generic error happened while trying to start ffmpeg process",e);
			} finally {
				if(tempFile != null && tempFile.exists()) {
					tempFile.delete();
				}

				if(ffmpegProcess != null) {
					ffmpegProcess.destroy();
				}
			}
		}
		return null;
	}

	private Properties parseMetaDataFile(List<String> lines) {
		Properties p = new Properties();
		for(String line : lines) {
			if(line.contains("=")) {
				List<String> property = Arrays.asList(line.split("="));

				if(property.size() == 2) {
					if(!p.contains(property.get(0))) {
						p.put(property.get(0).toLowerCase(Locale.US),property.get(1));
					}
				}
			}
		}

		return p;
	}

	private void registerError(String message, Exception e) {
		log.error(message+". Disable module "+this.getClass().getSimpleName(),e);
		MODULE_ENABLED=false;
		cleanUp();
	}

	public void cleanUp() {
		if(tempFolderPath != null && tempFolderPath.toFile().exists()) {
			tempFolderPath.toFile().delete();
		}
	}

	private void handleStream(InputStream inputStream, String type)  {
		InputStreamReader isr = new InputStreamReader(inputStream);
		BufferedReader br = new BufferedReader(isr);
		String line;
		try {
			while ((line = br.readLine()) != null)
				;//log.trace(type+": "+line);
		} catch (IOException e) {
			registerError("Could not read process output stream",e);
		}

		try {
			br.close();
			isr.close();
		} catch (IOException e) {
			registerError("Could not close process output stream", e);
		}

	}

}
