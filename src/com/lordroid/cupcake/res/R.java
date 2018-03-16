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

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author HumanShield85
 * 
 */
public class R {

	public static final Image ICON = getIcon("icon.png");

	public static final Image PLAY_BTN_ICON = getIcon("play.png");
	public static final Image PAUSE_BTN_ICON = getIcon("pause.png");
	public static final Image SKIP_BTN_ICON = getIcon("skip.png");
	public static final Image REWIND_BTN_ICON = getIcon("rewind.png");
	public static final Image VOLUME_LOW = getIcon("low_volume.png");
	public static final Image VOLUME_MEDIUM = getIcon("medium_volume.png");
	public static final Image VOLUME_FULL = getIcon("full_volume.png");
	public static final Image VOLUME_MUTED = getIcon("muted.png");
	public static final Image FULL_SCREEN = getIcon("tgl_fullscreen.png");
	public static final Image EXIT_FULL_SCREEN = getIcon("exut_full_screen.png");

	public static final Image VOLUME_DOWN = getIcon("volumeDown.png");

	public static final Image VOLUME_UP = getIcon("volumeUp.png");

	private static Image getIcon(String file) {
		Image image = null;
		try {

			image = ImageIO.read(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return image;

	}
}
