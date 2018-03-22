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

import com.lordroid.cupcake.yify.YifyS;

/**
 * @author HumanShield85
 * 
 */
public class Settings {
	
	public static final String[] SORT_BY_KEY_ARRAY = { YifyS.SORT_DATE_ADDED,
			YifyS.SORT_YEAR, YifyS.SORT_TITLE, YifyS.SORT_RATING,
			YifyS.SORT_DOWNLOAD_COUNT, YifyS.SORT_LIKE_COUNT };

	public static final String[] SORT_BY_COMBO_ARRAY = { "Added date", "Year",
			"Title", "Rating", "Most Watched", "Most Liked" };

	public static final String[] MINIMUM_RATING_COMBO_ARRAY = { "All ratings",
			" 1 ", " 2 ", " 3 ", " 4 ", " 5 ", " 6 ", " 7 ", " 8 ", " 9 " };
	public static final String[] GENRES_COMBO_ARRAY = { "All","Action", "Adventure",
			"Animation", "Biography", "Comedy", "Crime", "Documentary",
			"Drama", "Family", "Fantasy", "Film Noir", "History", "Horror",
			"Music", "Musical", "Mystery", "Romance", "Sci-Fi", "Short",
			"Sport", "Superhero", "Thriller", "War", "Western" };

	public static final String[] QUALITY_COMBO = {"All","720p","1080p","3D"};
	public static final int[] MAX_RESULT_PER_SEARCH = {10,15,20,25,30,35,40,45,50};
	
	
	public static int getCurrentQuality() {
		return 0;
	}
	
	public static int getCurrentMinimumRating() {
		return 0;
	}

	public static int getCurrentSortBy() {

		return 0;
	}

	public static int getCurrentGenre() {
		return 0;
		
	}
	
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
	
	public static int getMaxSearchItemsPerPage() {
		
		return 1;
	}

	public static void setCurrentQuality(int quality) {
		// TODO Auto-generated method stub
		
	}

	public static void setCurrentSortBy(int sortBy) {
		// TODO Auto-generated method stub
		
	}

	public static void setCurrentGenre(int genre) {
		// TODO Auto-generated method stub
		
	}

	public static void setCurrentminRating(int minRating) {
		// TODO Auto-generated method stub
		
	}
}
