package at.favre.tools.tagger.analyzer.io;

import at.favre.tools.tagger.analyzer.config.ConfigManager;
import at.favre.tools.tagger.analyzer.metadata.FileMetaData;
import at.favre.tools.tagger.analyzer.metadata.FolderInfo;
import at.favre.tools.tagger.analyzer.util.FileUtil;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author PatrickF
 * @since 23.03.13
 */
public class VideoFileVisitor extends SimpleFileVisitor<Path> {
	private Logger log = LogManager.getLogger(this.getClass().getSimpleName());

	private int sumFileVisited;
	private int maxFiles;
	private FolderInfo folderInfo;
	private FolderInfo refFolder;
	private FileScanProgressListener listener;
	private MessageDigest hashAlgorithm;

	public VideoFileVisitor() {
		try {
			hashAlgorithm = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	public void setListener(FileScanProgressListener listener) {
		this.listener = listener;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
		FolderInfo newFolder = new FolderInfo(folderInfo,dir.toFile().getName());
		if(folderInfo != null) {
			folderInfo.addChildFolder(newFolder);
		}

		folderInfo = newFolder;

		if(refFolder == null)
			refFolder =newFolder;

		return super.preVisitDirectory(dir, attrs);    //Autocreated
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attr) {
		if(attr.isRegularFile() && hasCorrectExtension(FileUtil.getFileExtension(file.toString()))) {
			sumFileVisited++;
			FileMetaData fileMetaData = null;
			try {
				fileMetaData = new FileMetaData(
						file.toString(),
						folderInfo,
						FileUtil.hashFile(file.toFile(), hashAlgorithm),
						file.toFile().lastModified());
			} catch (IOException e) {
				e.printStackTrace();
			}

			folderInfo.addFile(fileMetaData);

			if(listener != null) {
				listener.onProgressUpdate(sumFileVisited);
			}
		}
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
		folderInfo = folderInfo.getParentFolder();
		return super.postVisitDirectory(dir, exc);    //Autocreated
	}

	private static boolean hasCorrectExtension(String currentExtension) {
		for(String extension : ConfigManager.getInstance().getExtensions()) {
			if(extension.equalsIgnoreCase(currentExtension)) {
				return true;
			}
		}
		return false;
	}

	public int getSumFileVisited() {
		return sumFileVisited;
	}

	public FolderInfo getRoot() {
		return refFolder.getRoot();
	}

	public interface FileScanProgressListener {
		public void onProgressUpdate(int filesScanned);
	}
}
