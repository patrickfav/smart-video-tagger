package at.favre.tools.tagger.analyzer.metadata;

import java.util.ArrayList;
import java.util.List;

/**
 * @author PatrickF
 * @since 24.03.13
 */
public class FolderMetaData {
	private final FolderMetaData parentFolder;
	private final String folderName;
	private List<FolderMetaData> children;
	private List<FileMetaData> files;

	public FolderMetaData(FolderMetaData parentFolder, String folderName) {
		this.parentFolder = parentFolder;
		this.folderName = folderName;
		children=new ArrayList<FolderMetaData>();
		files=new ArrayList<FileMetaData>();
	}

	public FolderMetaData getParentFolder() {
		return parentFolder;
	}

	public String getFolderName() {
		return folderName;
	}

	public void addChildFolder(FolderMetaData folder) {
		children.add(folder);
	}

	public void addFile(FileMetaData file) {
		files.add(file);
	}

	public List<FolderMetaData> getChildren() {
		return children;
	}

	public List<FileMetaData> getFiles() {
		return files;
	}
}

