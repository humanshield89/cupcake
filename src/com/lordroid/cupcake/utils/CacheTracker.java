package com.lordroid.cupcake.utils;

import java.io.File;

import com.lordroid.cupcake.res.S;

public class CacheTracker {

	public static void main(String args[]) {
		// System.out.println(getTotalDownFolder() / 1024 / 1024 + "  mb");
	}

	public static long getTotalDownFolderSize() {
		return getTotalSize(new File(S.MOVIE_DOWNLOAD_FOLDER));
	}

	public static long getImageCacheSize() {

		return getTotalSize(new File(S.IMAGE_CACHE_TMP_FOLDER));
	}

	private static long getTotalSize(File folder) {
		long size = 0L;
		for (File f : FileUtils.listAllFiles(folder)) {
			if (f.isFile()) {

				size = size + f.length();
			}
		}
		return size;
	}
}
