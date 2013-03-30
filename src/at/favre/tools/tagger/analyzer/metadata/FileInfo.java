package at.favre.tools.tagger.analyzer.metadata;

import at.favre.tools.tagger.analyzer.TitleCleaner;

import java.io.File;

/**
 * @author PatrickF
 * @since 30.03.13
 */
public class FileInfo {

	private final String fullPath;
	private final FolderInfo parentFolder;
	private final String cleanedFileName;

	public FileInfo(String fullPath, FolderInfo parentFolder) {
		this.fullPath = fullPath;
		this.parentFolder = parentFolder;
		cleanedFileName = TitleCleaner.cleanTitle(getPureFileName());
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
		File file = new File(fullPath);
		return stripExtension(file.getName());
	}

	private static String stripExtension(String fileName) {
		int lastIndexOfPoint = fileName.lastIndexOf('.');

		if(lastIndexOfPoint != -1) {
			return fileName.substring(0,lastIndexOfPoint);
		}
		return fileName;
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
