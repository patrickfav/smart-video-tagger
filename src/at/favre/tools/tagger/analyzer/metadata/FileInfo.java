package at.favre.tools.tagger.analyzer.metadata;

import at.favre.tools.tagger.analyzer.util.TitleCleaner;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public class FileInfo {

	private final String fullPath;
	private final FolderInfo parentFolder;
	private final String cleanedFileName;
	private final String pureFileName;

	public FileInfo(String fullPath, FolderInfo parentFolder) {
		this.fullPath = fullPath;
		this.parentFolder = parentFolder;
		this.pureFileName = TitleCleaner.getPureFileName(fullPath);
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FileInfo fileInfo = (FileInfo) o;

		if (!fullPath.equals(fileInfo.fullPath)) return false;
		if (parentFolder != null ? !parentFolder.equals(fileInfo.parentFolder) : fileInfo.parentFolder != null)
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = fullPath.hashCode();
		result = 31 * result + (parentFolder != null ? parentFolder.hashCode() : 0);
		return result;
	}
}
