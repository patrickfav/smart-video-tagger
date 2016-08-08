package at.favre.tools.tagger.analyzer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;

/**
 * @author PatrickF
 * @since 07.04.13
 */
public class FileUtil {
	public static String getPureFileName(String fullPath) {
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

	public static String getFileExtension(String fileName) {
		int lastIndexOfPoint = fileName.lastIndexOf('.');

		if(lastIndexOfPoint != -1) {
			return fileName.substring(lastIndexOfPoint+1);
		}

		return "";
	}

	public static String getFileExtension(File f) {
		return getFileExtension(f.getName());
	}

	public static String hashFile(File f, MessageDigest hashAlgorithm) throws IOException{
		InputStream is = null;
		try {
			is = new FileInputStream(f);
			is = new DigestInputStream(is, hashAlgorithm);
		} catch(Exception e) {
			throw new IOException("Could not hash file "+f,e);
		} finally {
			is.close();
		}
		return  new BigInteger(1,hashAlgorithm.digest()).toString(16);
	}
}
