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

/**
 * @author HumanShield85
 * 
 */

public class StringUtils {

	public static boolean endsWith(String fileName, String[] exts) {
		for (String ext : exts) {
			if (fileName.endsWith(ext))
				return true;
		}
		return false;

	}

	public static String getCropString(String str, int endIndex)
			throws IndexOutOfBoundsException {
		String subStr = "";
		if (endIndex < 0) {
			throw new IndexOutOfBoundsException();
		} else if (endIndex >= str.length())
			return str;

		for (int i = 0; i < endIndex; i++) {
			subStr = subStr + str.charAt(i);
		}

		return subStr;

	}

	public static String getLastchars(String str, int charCount) {

		int startIndex = str.length() - (charCount + 1);
		String tmp = StringUtils.getSubString(str, startIndex);

		return tmp;
	}

	public static String getSubString(String str, int startIndex)
			throws IndexOutOfBoundsException {
		String subStr = "";
		if (startIndex >= str.length() || startIndex < 0) {
			throw new IndexOutOfBoundsException();
		}
		for (int i = startIndex + 1; i < str.length(); i++) {
			subStr = subStr + str.charAt(i);
		}

		return subStr;

	}

	public static String removeSpaces(String str) {
		String tmp = "";
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) != ' ') {
				tmp = tmp + str.charAt(i);
			}
		}

		return str;

	}
}