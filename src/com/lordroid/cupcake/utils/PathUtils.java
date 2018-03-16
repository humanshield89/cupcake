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

import java.net.URISyntaxException;

public class PathUtils {

	/**
	 * call this method to get where are we located on the file system don't
	 * abuse on it's use call it once on every execution and save the value
	 * because this will not change
	 * 
	 * @return our current location on the fileSystem
	 */
	public static String getExcutionPath() {
		String path = "";
		try {
			path = PathUtils.class.getProtectionDomain().getCodeSource()
					.getLocation().toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
			System.out
					.println("Somthing went wrong couldn't detemine our current location !");
		}
		return path.substring(0, path.lastIndexOf("/"));
	}

}
