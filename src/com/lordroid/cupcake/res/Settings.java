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
	// TODO
	public static final String[] SUB_ENCODING = { "UTF8","UTF-16", 
			"Windows-1256" , "ISO-8859-6", "KOI8-R" ,"GB18030","Windows-1252","IBM-00850",
			"Latin-2","Windows-1250","Latin-3","Latin-6",
			"Windows-1251","KOI8-U","ISO-8859-7","Windows-1253",
			"ISO-8859-9","Windows-1254","ISO-8859-11","Windows-874","Latin-7","Windows-1257",
			"Latin-8","Latin-10","ISO-2022-CN-EXT","EUC-CN","ISO-2022-JP-2","EUC-JP","Shift-JIS",
			"EUC-KR","ISO-2022-KR","Big5","EUC-TW","HKSCS","VISCII","Windows-1258"};
	public static final String[] SUB_ENCODING_COMBO = { "Universal (UTF8)","Universal (UTF-16)",
			"Arabic (Windows-1256)","Arabic (ISO8859-6)","Russian (KOI8-R)",
			"Universal,Chinese (GB18030)","Western European (Windows-1252)",
			"Western European (IBM-00850)","Eastern European (Latin-2)",
			"Eastern European (Windows-1250)","Esperanto (Latin-3)",
			"Nordic (Latin-6)","Cyrillic (Windows-1251)","Ukrainian (KOI8-U)",
			"Greek (ISO-8859-7)","Greek (Windows-1253)","Turkish (ISO-8859-9)","Turkish (Windows-1254)",
			"Thai (ISO-8859-11)","Thai (Windows-874)","Baltic (Latin-7)","Baltic (Windows-1257)",
			"Celtic (Latin-8)","South-Eastern European (Latin-10)","Simplified Chinese (ISO-2022-CN-EXT)",
			"Simplified Chinese (EUC-CN)","Japanese (ISO-2022-JP-2)","Japanese (EUC-JP)","Japanese (Shift-JIS)",
			"Korean (EUC-KR)","Korean (ISO-2022-KR)","Traditional Chinese (Big5)","Traditional Chinese UNIX (EUC-TW)",
			"Hong-Kong Supplementary (HKSCS)","Vietnamese (VISCII)","Vietnamese (Windows-1258)" };

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
	public static final String[] MAX_RESULT_PER_SEARCH_COMBO = { "10", "15", "20", "25", "30", "35",
		"40", "45", "50" };
	public static final String[] ORDER_KEYS = { "desc", "asc" };
	public static final String[] ORDER_COMBO = { "Descending", "Ascending" };
	public static final String[] DEFAULT_PLAY_QUALITY = { "720p", "1080p", "3d" };

	public static final String[] AVAILABLE_GUI_LANGUAGE_COMBO = {"english","french","arabic"};
	public static final String[] AVAILABLE_GUI_LANGUAGE_files = {"","",""};
	
	// settings vars 
		private static int		currentMiniRating = new Integer(PropReader.getProp("currentMiniRating",
				S.CONFIG_FILE));
		private static int		currentSortBy = new Integer(PropReader.getProp("currentSortBy",
				S.CONFIG_FILE));
		private static int		CurrentFilterQuality = new Integer(PropReader.getProp("CurrentFilterQuality",
				S.CONFIG_FILE));
		private static int		CurrentGenre = new Integer(PropReader.getProp("CurrentGenre",
				S.CONFIG_FILE));
		private static int		CurrentDefaultPlayQuality = new Integer(PropReader.getProp("CurrentDefaultPlayQuality",
				S.CONFIG_FILE));
		private static int		CurrentVolume = new Integer(PropReader.getProp("CurrentVolume",
				S.CONFIG_FILE));
		private static int		MaxSearchItemsPerPage = new Integer(PropReader.getProp("MaxSearchItemsPerPage",
				S.CONFIG_FILE));
		private static int		CurrentOrder = new Integer(PropReader.getProp("CurrentOrder",
				S.CONFIG_FILE));
		private static long 	currentMaxCacheSize = new Long(PropReader.getProp("CurrentMaxCacheSize",
				S.CONFIG_FILE));
		private static int		SubtitleLang1 = new Integer(PropReader.getProp("SubtitleLang1",
				S.CONFIG_FILE));
		private static int		SubtitleLang2 = new Integer(PropReader.getProp("SubtitleLang2",
				S.CONFIG_FILE));
		private static int		SubtitleLang3 = new Integer(PropReader.getProp("SubtitleLang3",
				S.CONFIG_FILE));;
		private static int		GuiLanguage = new Integer(PropReader.getProp("GuiLanguage",
				S.CONFIG_FILE));
		private static int		DefaultEnterOperation = new Integer(PropReader.getProp("DefaultEnterOperation",
				S.CONFIG_FILE));
		private static int		RememberFiltersOnStartup = new Integer(PropReader.getProp("RememberFiltersOnStartup",
				S.CONFIG_FILE));
		private static int		ParentalWarningLvl = new Integer(PropReader.getProp("ParentalWarningLvl",
				S.CONFIG_FILE));;
		private static int		DefaultSubtittleEncoding = new Integer(PropReader.getProp("DefaultSubtittleEncoding", S.CONFIG_FILE));
		private static int		LoadOsSubtitles = new Integer(PropReader.getProp("LoadOsSubtitles",
				S.CONFIG_FILE));
		private static int		AutoLoadSubtitles = new Integer(PropReader.getProp("AutoLoadSubtitles",
				S.CONFIG_FILE));
	
	
	/**
	 * 
	 * @return
	 */
	public static boolean autoLoadSubtitles() {
//		Integer i = new Integer(PropReader.getProp("AutoLoadSubtitles",
//				S.CONFIG_FILE));
		return AutoLoadSubtitles == 1;
	}

	/**
	 * 
	 * @param bool
	 */
	public static void setautoLoadSubtitles(boolean bool) {
		int i = 0;
		if(bool)
			i = 1;
		PropReader.writeProp("AutoLoadSubtitles", i+"", S.CONFIG_FILE);
		AutoLoadSubtitles = i;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static boolean LoadOsSubtitles() {

		return LoadOsSubtitles == 1;
	}

	/**
	* 
	 * @param bool
	 */
	public static void setLoadOsSubtitles(boolean bool) {
		int i = 0;
		if(bool)
			i = 1;
		PropReader.writeProp("LoadOsSubtitles", i+"", S.CONFIG_FILE);
		AutoLoadSubtitles = i;
	}
	
	
	
	/**
	 * 
	 * @return
	 */
	public static int getGuiLanguage() {
//		Integer i = new Integer(PropReader.getProp("GuiLanguage",
//				S.CONFIG_FILE));
		return GuiLanguage;
	}

	/**
	 * 
	 * @return
	 */
	public static int getSubtitlesLang1() {
		return SubtitleLang1;
	}

	/**
	 * 
	 * @return
	 */
	public static int getSubtitlesLang2() {

		return SubtitleLang2;
	}

	/**
	 * 
	 * @return
	 */
	public static int getSubtitlesLang3() {
		return SubtitleLang3;
	}

	/**
	 * 
	 * @param size
	 */
	public static void setCurrentMaxCacheSize(long size) {
		PropReader.writeProp("CurrentMaxCacheSize", size + "",
				S.CONFIG_FILE);
		currentMaxCacheSize = size;
	}

	public static void setDefaultEnterOperation(int i) {
		PropReader.writeProp("DefaultEnterOperation", i + "",
				S.CONFIG_FILE);
		DefaultEnterOperation = i;
	}
	
	/**
	 * 
	 * @return
	 */
	public static long getCurrentMaxCacheSize() {
		return currentMaxCacheSize;
	}

	/**
	 * 
	 * @return
 	 */
	public static int getCurrentQuality() {
		return CurrentFilterQuality;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentMinimumRating() {
		return currentMiniRating;
	}

	/**
	 * 
	 * @return currentSortBy
	 */
	public static int getCurrentSortBy() {
		return currentSortBy;
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentGenre() {
		return CurrentGenre;
	}

	/**
	 * 
	 * @return
	 */
	public static String getCurrentDefaultPlayQuality() {
		
		return DEFAULT_PLAY_QUALITY[CurrentDefaultPlayQuality];
	}

	
	/**
	 * 
	 * @return
	 */
	public static int getDefaultSubtittleEncodingIndex() {
// TODO
		return DefaultSubtittleEncoding;
	}
	/**
	 * 
	 * @return
	 */
	public static String getDefaultSubtittleEncoding() {
// TODO
		return SUB_ENCODING[DefaultSubtittleEncoding];
	}

	/**
	 * 
	 * @return
	 */
	public static int getCurrentVolume() {
		return CurrentVolume;
	}

	/**
	 * 
	 * @return maxSearchIndex
	 */
	public static int getMaxSearchItemsPerPage() {
		return MaxSearchItemsPerPage;
	}


	/**
	 * 
	 * @return indexOfCurrentOrder
	 */
	public static int getCurrentOrder() {
		return CurrentOrder;
	}

	/**
	 * 
	 * @return
	 */
	public static int getDefaultEnterOperation() {
		 
		return DefaultEnterOperation;
	}

	/**
	 * 
	 * @return
	 */
	public static int getRememberFiltersOnStartup() {
		return RememberFiltersOnStartup;	
	}
	
	/**
	 * 
	 * @return
	 */
	public static int getParentalWarningLvl() {
		return ParentalWarningLvl;	
	}
	
	public static void setParentalWarningLvl(int lvl) {

		PropReader.writeProp("ParentalWarningLvl", lvl+"", S.CONFIG_FILE);
		ParentalWarningLvl=lvl;
	}
	
	/**
	 * 
	 * @param i
	 */
	public static void setCurrentVolume(int i) {
		PropReader.writeProp("CurrentVolume", i + "", S.CONFIG_FILE);
		CurrentVolume=i;
	}

	/**
	 * 
	 * @param quality
	 */
	public static void setCurrentQuality(int quality) {
		PropReader.writeProp("CurrentFilterQuality", quality + "",
				S.CONFIG_FILE);
		CurrentFilterQuality = quality;
	}

	/**
	 * 
	 * @param sortBy
	 */
	public static void setCurrentSortBy(int sortBy) {
		PropReader.writeProp("currentSortBy", sortBy + "", S.CONFIG_FILE);
		currentSortBy = sortBy;
	}

	/**
	 * 
	 * @param genre
	 */
	public static void setCurrentGenre(int genre) {
		PropReader.writeProp("CurrentGenre", genre + "", S.CONFIG_FILE);
		CurrentGenre = genre;
	}

	/**
	 * 
	 * @param minRating
	 */
	public static void setCurrentminRating(int minRating) {
		PropReader
				.writeProp("currentMiniRating", minRating + "", S.CONFIG_FILE);
		currentMiniRating=minRating;
	}

	/**
	 * 
	 * @param order
	 */
	public static void setCurrentOrder(int order) {
		PropReader.writeProp("CurrentOrder", order + "", S.CONFIG_FILE);
		CurrentOrder = order;
	}

	/**
	 * 
	 * @param index
	 */
	public static void setDefaultPlayQuality(int index) {
		PropReader.writeProp("CurrentDefaultPlayQuality", index + "",
				S.CONFIG_FILE);
		CurrentDefaultPlayQuality=index;
	}

	/**
	 * 
	 * @param langIndex
	 */
	public static void setSubtitlesLang1(int langIndex) {
		PropReader.writeProp("SubtitleLang1", langIndex + "", S.CONFIG_FILE);
		SubtitleLang1 = langIndex;

	}

	/**
	 * 
	 * @param langIndex
	 */
	public static void setSubtitlesLang2(int langIndex) {
		PropReader.writeProp("SubtitleLang2", langIndex + "", S.CONFIG_FILE);
		SubtitleLang2 = langIndex;
	}

	/**
	 * 
	 * @param langIndex
	 */
	public static void setSubtitlesLang3(int langIndex) {
		PropReader.writeProp("SubtitleLang3", langIndex + "", S.CONFIG_FILE);
		SubtitleLang3 = langIndex;
	}

	/**
	 * 
	 * @param index
	 */
	public static void setDefaultSubtittleEncoding(int index) {
		// TODO
		PropReader.writeProp("DefaultSubtittleEncoding", index+"",
				S.CONFIG_FILE);
		DefaultSubtittleEncoding=index;
	}
	
	/**
	 * 
	 * @param langIndex
	 */
	public static void setGuiLanugage(int langIndex){
		PropReader.writeProp("GuiLanguage", langIndex+"",
				S.CONFIG_FILE);
		GuiLanguage=langIndex;
	}
	/**
	 * 
	 * @param index
	 */
	public static void setMaxSearchItemsPerPage(int index ) {

		PropReader.writeProp("MaxSearchItemsPerPage", index+"", S.CONFIG_FILE);
		MaxSearchItemsPerPage = index;
	}
	
	/**
	 * 
	 * @param index
	 */
	public static void setRememberFiltersOnStartup(int index) {
		PropReader.writeProp("RememberFiltersOnStartup", index+"", S.CONFIG_FILE);
		RememberFiltersOnStartup = index;
	}


	
	
	

}
