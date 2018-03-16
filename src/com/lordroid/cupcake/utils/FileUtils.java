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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * @author HumanShield85
 * 
 */
public class FileUtils {

	/**
	 * 
	 * @param input
	 *            the input file to be copied
	 * @param dest
	 *            the destination file
	 * @return true only if the file was copied
	 */
	public static boolean copyFile(File input, File dest) {
		// making sure the path is there and writable !
		dest.getParentFile().mkdirs();
		// dest.delete();
		if (dest.getParentFile().exists()) { // if the parent doesn't exist then
												// don't bother copy

			try {

				dest.delete();

				InputStream is = new FileInputStream(input);
				OutputStream out = new FileOutputStream(dest);
				byte[] buffer = new byte[32768];
				int len;
				while ((len = is.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				is.close();
				out.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return false;
		}
		return dest.exists();
	}

	/**
	 * 
	 * @param in
	 *            InputStream
	 * @param dest
	 *            destination file
	 * @return true if the inputStream was saved to the file
	 */
	public static boolean copyFile(InputStream in, File dest) {
		dest.mkdirs();
		dest.delete();
		if (dest.getParentFile().exists()) {
			try {
				OutputStream out = new FileOutputStream(dest);
				byte[] buffer = new byte[32768];
				int len;
				while ((len = in.read(buffer)) != -1) {
					out.write(buffer, 0, len);
				}
				in.close();
				out.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			return false;
		}
		return dest.exists();
	}

	public static boolean copyFileRecurcively(File in, File out) {
		boolean status = true;
		if (in.isDirectory()) {
			out.mkdir();
			File[] list = in.listFiles();
			for (File f : list) {
				status = status
						&& copyFileRecurcively(f,
								new File(out.getAbsolutePath() + File.separator
										+ f.getName()));
			}
		} else {
			status = status && copyFile(in, out);
		}

		return out.exists() && status;
	}

	public static void deleteFiles(ArrayList<File> files) {
		if (files != null && files.size() > 0)
			for (File f : files) {
				if (f.isFile()) {
					f.delete();
				}
			}

	}

	/**
	 * deletes recursively a folder (USE WITH CARE)
	 * 
	 * @param f
	 *            folder to be deleted
	 * @return true only if the folder and all it's content was deleted
	 */
	public static boolean deleteRecursively(File f) {
		boolean done = false;
		if (f.isFile()) {
			f.delete();
			return true;
		}
		if (f.isDirectory()) {
			File[] list = f.listFiles();
			if (list.length < 0) {
				return f.delete();
			} else {

				for (File file : list) {
					deleteRecursively(file);
				}

				return f.delete();
			}

		}
		return done;
	}

	/**
	 * cleans the given folder from all it's unpty folders
	 * 
	 * @param folder
	 *            the folder to be cleaned
	 */
	public static void deleteUmptyFoldersInFolder(File folder) {
		if (folder.isFile())
			return;
		if (folder.listFiles() == null || folder.listFiles().length <= 0) {
			folder.delete();
		} else {
			for (File f : folder.listFiles()) {
				if (f.isDirectory())
					deleteUmptyFoldersInFolder(f);
			}
		}
		if (folder.listFiles() == null || folder.listFiles().length <= 0) {
			folder.delete();
		}
	}

	/**
	 * lists all files in a given folder
	 * 
	 * @param folder
	 *            the folder to list files from
	 * @return a list of all the files
	 */
	public static ArrayList<File> listAllFiles(File folder) {
		ArrayList<File> list = new ArrayList<File>();
		if (!folder.exists() || folder.listFiles() == null
				|| folder.listFiles().length <= 0 || !folder.canRead()) {
			return list;
		}
		File[] listf = folder.listFiles();
		for (File f : listf) {
			if (f.isFile()) {
				list.add(f);
			} else {
				if (listAllFiles(f) != null)
					for (File f1 : listAllFiles(f)) {
						list.add(f1);
					}
			}
		}
		return list;
	}

	/**
	 * logs all the files from a given folder to stdIO and to the full main log
	 * file
	 * 
	 * @param folder
	 *            the folder to be logged
	 */
	public static void LogFilesListToFile(File folder) {
		String str = "System folder Files list :\n";
		File app = new File(folder.getAbsolutePath() + File.separator + "app");
		File privApp = new File(folder.getAbsolutePath() + File.separator
				+ "priv-app");
		File framework = new File(folder.getAbsolutePath() + File.separator
				+ "framework");

		if (app.exists())
			for (File f : listAllFiles(app)) {
				str = str
						+ (f.getAbsolutePath().substring(app.getAbsolutePath()
								.length() + 1)) + "\n";
			}
		if (privApp.exists())
			for (File f : listAllFiles(privApp)) {
				str = str
						+ (f.getAbsolutePath().substring(privApp
								.getAbsolutePath().length() + 1)) + "\n";
			}
		if (framework.exists())
			for (File f : listAllFiles(framework)) {
				str = str
						+ (f.getAbsolutePath().substring(framework
								.getAbsolutePath().length() + 1)) + "\n";
			}

	}

	// TODO there is better way use (rename() ? holly shit dude
	public static boolean moveFile(File in, File dest) {
		boolean iscopied = copyFile(in, dest);
		if (!iscopied)
			return false;
		if (dest.exists())
			in.delete();

		return !in.exists();
	}

	/**
	 * search a folder for files with a given Name (full name match)
	 * 
	 * @param folder
	 *            the folder to be searched
	 * @param ext
	 *            the file name to be searched
	 * @return a list of all the matching files
	 */
	public static ArrayList<File> searchExactFileNames(File folder, String ext) {
		ArrayList<File> list = new ArrayList<File>();
		File[] files = folder.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				for (File f1 : searchExactFileNames(f, ext)) {
					list.add(f1);
				}
			} else if (f.isFile() && f.getName().equals(ext)) {
				list.add(f);
			}
		}
		return list;
	}

	/**
	 * search a folder for files with a given file extension
	 * 
	 * @param folder
	 *            the folder to be searched
	 * @param ext
	 *            the extension query
	 * @return ArrayfilesList list of all the matching files
	 */
	public static ArrayList<File> searchrecursively(File folder, String ext) {
		ArrayList<File> list = new ArrayList<File>();
		File[] files = folder.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				for (File f1 : searchrecursively(f, ext)) {
					list.add(f1);
				}
			} else if (f.isFile() && f.getName().endsWith(ext)) {
				list.add(f);
			}
		}

		return list;
	}
}
