package at.favre.tools.tagger;

import at.favre.tools.tagger.analyzer.metadata.FileMetaData;
import at.favre.tools.tagger.analyzer.metadata.FolderInfo;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public class MetaDataParser {
	private static Logger log = LogManager.getLogger(MetaDataParser.class.getSimpleName());

	private FolderInfo root;

	public MetaDataParser(FolderInfo root) {
		this.root = root;
	}

	public void parseToLog() {
		expand(root,0);
	}

	private static void expand(FolderInfo folderToExpand, int depth) {
		log.info(getIntend(depth)+folderToExpand.getFolderName());

		depth++;

		for(FileMetaData fInfo: folderToExpand.getFiles()) {
			log.info(getIntend(depth)+fInfo);
		}


		for(FolderInfo fInfo: folderToExpand.getChildren()) {
			expand(fInfo,depth);
		}
	}

	private static String getIntend(int width) {
		StringBuilder sb = new StringBuilder();

		for(int i=0;i<width;i++) {
			sb.append("\t");
		}
		return sb.toString();
	}
}
