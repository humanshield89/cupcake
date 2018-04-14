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

import com.lordroid.cupcake.utils.PropReader;
import com.lordroid.cupcake.yify.YifyS;

/**
 * @author HumanShield85
 * 
 */
public class Settings {

	public static final String[] SUB_ENCODING = { "UTF8", "KOI8-R",
			"Windows-1256" };
	public static final String[] SUB_ENCODING_COMBO = { "Universal (UTF8)",
			"Russian (KOI8-R)", "Arabic (Windows-1256)" };

	public static final String[] SORT_BY_KEY_ARRAY = { YifyS.SORT_DATE_ADDED,
			YifyS.SORT_YEAR, YifyS.SORT_TITLE, YifyS.SORT_RATING,
			YifyS.SORT_DOWNLOAD_COUNT, YifyS.SORT_LIKE_COUNT };

	public static final String[] SORT_BY_COMBO_ARRAY = { "Added date", "Year",
			"Title", "Rating", "Most Watched", "Most Liked" };

	public static final String[] MINIMUM_RATING_COMBO_ARRAY = { "All", "1+",
			"2+", "3+", "4+", "5+", "6+", "7+", "8+", "9+" };
	public static final String[] GENRES_COMBO_ARRAY = { "All", "Action",
			"Adventure", "Animation", "Biography", "Comedy", "Crime",
			"Documentary", "Drama", "Family", "Fantasy", "Film Noir",
			"History", "Horror", "Music", "Musical", "Mystery", "Romance",
			"Sci-Fi", "Short", "Sport", "Superhero", "Thriller", "War",
			"Western" };

	public static final String[] QUALITY_COMBO = { "All", "720p", "1080p", "3D" };
	public static final int[] MAX_RESULT_PER_SEARCH = { 10, 15, 20, 25, 30, 35,
			40, 45, 50 };

	public static final String[] ORDER_KEYS = { "desc", "asc" };
	public static final String[] ORDER_COMBO = { "Descending", "Ascending" };
	public static final String[] DEFAULT_PLAY_QUALITY = { "720p", "1080p", "3d" };

	/**
	 * 
	 * @return
	 */
	public static int getSubtitlesLang1() {
		Integer i = new Integer(PropReader.getProp("SubtitleLang1",
				S.CONFIG_FILE));
		return i;
	}

	/**
	 * 
	 * @return
	 */
	public static int getSubtitlesLang2() {
		Integer i = new Integer(PropReader.getProp("SubtitleLang2",
				S.CONFIG_FILE));
		return i;
	}

	/**
	 * 
	 * @return
	 */
	public static int getSubtitlesLang3() {
		Integer i = new Integer(PropReader.getProp("SubtitleLang3",
				S.CONFIG_FILE));
		return i;
	}

	/**
	 * 
	 * @param size
	 */
	public static void getCurrentMaxCacheSize(long size) {
		PropReader.writeProp("CurrentDefaultPlayQuality", size + "",
				S.CONFIG_FILE);

	}

	/**
	 * 
	 * @return
	 */
	public static long getCurrentMaxCacheSize() {
		long l = new Long(PropReader.getProp("CurrentMaxCacheSize",
				S.CONFIG_FILE));
		return l;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentQuality() {
		Integer i = new Integer(PropReader.getProp("CurrentFilterQuality",
				S.CONFIG_FILE));
		return i;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentMinimumRating() {
		Integer i = new Integer(PropReader.getProp("currentMiniRating",
				S.CONFIG_FILE));
		return i;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentSortBy() {
		Integer i = new Integer(PropReader.getProp("currentSortBy",
				S.CONFIG_FILE));
		return i;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentGenre() {
		Integer i = new Integer(PropReader.getProp("CurrentGenre",
				S.CONFIG_FILE));
		return i;

	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentDefaultPlayQuality() {
		Integer i = new Integer(PropReader.getProp("CurrentDefaultPlayQuality",
				S.CONFIG_FILE));
		return DEFAULT_PLAY_QUALITY[i];
	}

	/**
	 * 
	 * @return
	 */
	public static String getDefaultSubtittleEncoding() {

		return PropReader.getProp("DefaultSubtittleEncoding", S.CONFIG_FILE);
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentVolume() {
		// TODO extenrnalize those
		Integer i = new Integer(PropReader.getProp("CurrentVolume",
				S.CONFIG_FILE));
		return i;
	}

	/**
	 * 
	 * @return
	 */
	public static int getMaxSearchItemsPerPage() {
		Integer i = new Integer(PropReader.getProp("MaxSearchItemsPerPage",
				S.CONFIG_FILE));
		return i;
	}

	/**
	 * 
	 * @return indexOfCurrentOrder
	 */
	public static int getCurrentOrder() {
		Integer i = new Integer(PropReader.getProp("CurrentOrder",
				S.CONFIG_FILE));
		return i;
	}

	/**
	 * 
	 * @param str
	 */
	public static void setDefaultSubtittleEncoding(String str) {
		// TODO : do something
		PropReader.writeProp("DefaultSubtittleEncoding", str, S.CONFIG_FILE);
	}

	/**
	 * 
	 * @param i
	 */
	public static void setCurrentVolume(int i) {
		PropReader.writeProp("CurrentVolume", i + "", S.CONFIG_FILE);
	}

	/**
	 * 
	 * @param quality
	 */
	public static void setCurrentQuality(int quality) {
		PropReader.writeProp("CurrentFilterQuality", quality + "",
				S.CONFIG_FILE);
	}

	/**
	 * 
	 * @param sortBy
	 */
	public static void setCurrentSortBy(int sortBy) {
		PropReader.writeProp("currentSortBy", sortBy + "", S.CONFIG_FILE);
	}

	/**
	 * 
	 * @param genre
	 */
	public static void setCurrentGenre(int genre) {
		PropReader.writeProp("CurrentGenre", genre + "", S.CONFIG_FILE);
	}

	/**
	 * 
	 * @param minRating
	 */
	public static void setCurrentminRating(int minRating) {
		PropReader
				.writeProp("currentMiniRating", minRating + "", S.CONFIG_FILE);
	}

	/**
	 * 
	 * @param order
	 */
	public static void setCurrentOrder(int order) {
		PropReader.writeProp("CurrentOrder", order + "", S.CONFIG_FILE);
	}

	/**
	 * 
	 * @param index
	 */
	public static void setDefaultPlayQuality(int index) {
		PropReader.writeProp("CurrentDefaultPlayQuality", index + "",
				S.CONFIG_FILE);
	}

	/**
	 * 
	 * @param langIndex
	 */
	public static void setSubtitlesLang1(int langIndex) {
		PropReader.writeProp("SubtitleLang1", langIndex + "", S.CONFIG_FILE);

	}

	/**
	 * 
	 * @param langIndex
	 */
	public static void setSubtitlesLang2(int langIndex) {
		PropReader.writeProp("SubtitleLang2", langIndex + "", S.CONFIG_FILE);

	}

	/**
	 * 
	 * @param langIndex
	 */
	public static void setSubtitlesLang3(int langIndex) {
		PropReader.writeProp("SubtitleLang3", langIndex + "", S.CONFIG_FILE);

	}

	/**
	 * 
	 * @param index
	 */
	public static void setDefaultSubtittleEncoding(int index) {

		PropReader.writeProp("DefaultSubtittleEncoding", SUB_ENCODING[index],
				S.CONFIG_FILE);

	}
}
