package at.favre.tools.tagger.analyzer.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class FolderInfo {
	private final FolderInfo parentFolder;
	private final String folderName;
	private List<FolderInfo> children;
	private List<FileMetaData> files;

	public FolderInfo(FolderInfo parentFolder, String folderName) {
		this.parentFolder = parentFolder;
		this.folderName = folderName;
		children=new ArrayList<FolderInfo>();
		files=new ArrayList<FileMetaData>();
	}

	public FolderInfo getParentFolder() {
		return parentFolder;
	}

	public String getFolderName() {
		return folderName;
	}

	public void addChildFolder(FolderInfo folder) {
		children.add(folder);
	}

	public void addFile(FileMetaData file) {
		files.add(file);
	}

	public List<FolderInfo> getChildren() {
		return children;
	}

	public List<FileMetaData> getFiles() {
		return files;
	}

	public FolderInfo getRoot() {
		FolderInfo folder = this;

		while(folder.getParentFolder() != null) {
			folder = folder.getParentFolder();
		}

		return folder;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		FolderInfo that = (FolderInfo) o;

		if (children != null ? !children.equals(that.children) : that.children != null) return false;
		if (files != null ? !files.equals(that.files) : that.files != null) return false;
		if (!folderName.equals(that.folderName)) return false;
		if (parentFolder != null ? !parentFolder.equals(that.parentFolder) : that.parentFolder != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = parentFolder != null ? parentFolder.hashCode() : 0;
		result = 31 * result + folderName.hashCode();
		result = 31 * result + (children != null ? children.hashCode() : 0);
		result = 31 * result + (files != null ? files.hashCode() : 0);
		return result;
	}
}

