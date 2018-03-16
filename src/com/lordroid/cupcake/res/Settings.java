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
package com.lordroid.cupcake.res;

/**
 * @author HumanShield85
 * 
 */
public class Settings {

	public static void setDefaultSubtittleEncoding() {
		// TODO : do something

	}

	public static String getDefaultSubtittleEncoding() {

		return "Windows-1256";
	}

	public static int getCurrentVolume() {
		// TODO extenrnalize those
		return 75;
	}

	/**
	 * 
	 */
	public static void setCurrentVolume() {
		// TODO do something with this to store it to a file and restore on boot

	}
}
