package at.favre.tools.tagger.analyzer.metadata;

import at.favre.tools.tagger.analyzer.util.FileUtil;
import at.favre.tools.tagger.analyzer.util.TitleCleaner;

import java.io.File;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public class FileInfo {

	private final String fullPath;
	private final FolderInfo parentFolder;
	private final String cleanedFileName;
	private final String pureFileName;
	private final String hash;
	private final long lastModified;

	public FileInfo(File f, FolderInfo parentFolder, String hash) {
		this(f.getAbsolutePath(),parentFolder,hash,f.lastModified());
	}

	public FileInfo(String fullPath, FolderInfo parentFolder, String hash, long lastModified) {
		this.fullPath = fullPath;
		this.parentFolder = parentFolder;
		this.hash = hash;
		this.lastModified = lastModified;
		this.pureFileName = FileUtil.getPureFileName(fullPath);
		this.cleanedFileName = TitleCleaner.cleanTitle(pureFileName);
	}


	public String getFullPath() {
		return fullPath;
	}

	public FolderInfo getParentFolder() {
		return parentFolder;
	}

	public String getCleanedFileName() {
		return cleanedFileName;
	}

	public String getPureFileName() {
		return pureFileName;
	}

	public String getHash() {
		return hash;
	}

	public long getLastModified() {
		return lastModified;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FileInfo fileInfo = (FileInfo) o;

		if (lastModified != fileInfo.lastModified) return false;
		if (!fullPath.equals(fileInfo.fullPath)) return false;
		if (!hash.equals(fileInfo.hash)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = fullPath.hashCode();
		result = 31 * result + hash.hashCode();
		result = 31 * result + (int) (lastModified ^ (lastModified >>> 32));
		return result;
	}
}
