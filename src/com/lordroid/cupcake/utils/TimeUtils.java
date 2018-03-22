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

import com.lordroid.cupcake.App;

/**
 * @author HumanShield85
 * 
 */
public class TimeUtils {

	public static String getLabelFormatedTime(long time) {
		// check if it's more than an hour
		App.LOGGER.debug("About to convert  time = " + time);
		// String timeFormated = null;
		// if (time - (3600 * 1000) >= 0) {
		// timeFormated = new SimpleDateFormat("  KK:mm:ss  ").format(time);
		// } else {
		// timeFormated = new SimpleDateFormat("  mm:ss  ").format(time);
		// }
		// App.LOGGER.info("About to return  time = "+timeFormated);

		long ss = (time / 1000) % 60;
		long mm = (time / 1000 / 60) % 60;
		long hh = (time / 1000 / 60 / 60) % 24;
		App.LOGGER.debug("true ?" + "   " + hh + ":" + mm + ":" + ss + "  ");

		return "   " + hh + ":" + mm + ":" + ss + "  ";
	}
	
	public static String getFormatedViewCount (long views) {
		double viewD;
		if (views/1000000 >= 1) {
			viewD = ((double) views) / 1000000;
			return String.format("%.1f", viewD)+"M";
		} else if (views / 1000 >= 1) {
			viewD = ((double) views) / 1000;
			return String.format("%.0f", viewD)+"K";
		}
		
		
		
		return ">1k";
		
	}
}
