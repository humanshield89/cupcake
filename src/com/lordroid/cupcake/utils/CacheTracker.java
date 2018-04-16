/*
 *  Cupcake Player
 * 
 *  Copyright 2018 Rachid Boudjelida <rachidboudjelida@gmail.com>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
