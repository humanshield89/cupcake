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

import java.io.File;

import com.lordroid.cupcake.utils.PathUtils;

/**
 * @author HumanShield85
 * 
 */
public class S {
	
	public static final String SYSTEM_TMP_FOLDER = System
			.getProperty("java.io.tmpdir") + "cupcake" + File.separator;
	public static final File DEFAULT_BG = new File(PathUtils.getExcutionPath()
			+ File.separator + "resources" + File.separator + "defaultBG.jpg");
	public static final String NATIVE_LIB_FOLDER = "native";
	public static final int PLAY_BTN_PRESSED = 0;
	public static final int SKIP_BTN_PRESSED = 1;
	public static final int REWIND_BTN_PRESSED = 2;
	public static final int TIMER_SLIDED = 3;
	public static final int TIMER_CLICKED = 4;
	public static final int TIMER_RELEASED = 5;
	public static final int TIMER_PRESSED = 6;
	public static final int VOLUME_MUTE_PRESSED = 7;
	public static final int VOLUME_CONTROL_SLIDED = 8;
	public static final int FULL_SCREEN_BTN_PRESSED = 9;
	public static final int VOLUME_UP_DOWN_PRESSED = 10;

	public static final String MEDIA_STOPED = " | Stopped";
	public static final String MEDIA_PAUSED = " | Paused";
	public static final String MEDIA_PLAYED = " | Playing ...";
	public static final String[] VID_EXT = { ".webm", ".mkv", ".flv", ".vob",
			".avi", ".wmv", ".mp4", ".m4p", ".mpg", ".mpeg", ".m2v", ".m4v",
			".3gp" };
	public static final String[] SUB_EXT = { "srt", "vtt" };
	public static final File DEFAULT_COVER = new File(PathUtils.getExcutionPath()
			+ File.separator + "resources" + File.separator + "default_cover_medium.jpg");
	

}
