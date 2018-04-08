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

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author HumanShield85
 * 
 */
public class R {
	public static final Font NORMAL_FONT = new Font("Arial", Font.BOLD, 12);
	public static final Font MOVIE_TITLE_FONT = new Font("Arial", Font.BOLD, 12);
	public static final Font VIEWS_FONT = new Font("Arial", Font.PLAIN, 10);
	public static final Font DESCRIPTION_FONT = new Font("Arial", Font.BOLD, 12);
	public static final Font GENRE_FONT = new Font("Arial", Font.BOLD, 10);
	public static final Font LOAD_MORE_FONT = new Font("Arial", Font.BOLD, 18);

	public static final BufferedImage ICON = getIcon("icon.png");

	public static final BufferedImage PLAY_BTN_ICON = getIcon("play.png");
	public static final BufferedImage PAUSE_BTN_ICON = getIcon("pause.png");
	public static final BufferedImage SKIP_BTN_ICON = getIcon("skip.png");
	public static final BufferedImage REWIND_BTN_ICON = getIcon("rewind.png");
	public static final BufferedImage VOLUME_LOW = getIcon("low_volume.png");
	public static final BufferedImage VOLUME_MEDIUM = getIcon("medium_volume.png");
	public static final BufferedImage VOLUME_FULL = getIcon("full_volume.png");
	public static final BufferedImage VOLUME_MUTED = getIcon("muted.png");
	public static final BufferedImage FULL_SCREEN = getIcon("tgl_fullscreen.png");
	public static final BufferedImage EXIT_FULL_SCREEN = getIcon("exut_full_screen.png");
	public static final BufferedImage VOLUME_DOWN = getIcon("volumeDown.png");
	public static final BufferedImage VOLUME_UP = getIcon("volumeUp.png");

	public static final BufferedImage QUALITY_3D = getIcon("3D.png");
	public static final BufferedImage QUALITY_720P = getIcon("720p.png");
	public static final BufferedImage QUALITY_1080P = getIcon("1080p.png");

	public static final BufferedImage IMDB_ICON = getIcon("imdb2.png");
	public static final BufferedImage VIEWS_ICON = getIcon("views2.png");
	public static final BufferedImage GENRE_ICON = getIcon("genre.png");

	public static final BufferedImage RATING_G = getIcon("ratting_g.png");
	public static final BufferedImage RATING_PG = getIcon("ratting_PG.png");
	public static final BufferedImage RATING_PG13 = getIcon("ratting_pg-13.png");
	public static final BufferedImage RATING_R16 = getIcon("ratting_R-16.png");
	public static final BufferedImage RATING_R18 = getIcon("ratting_R-18.png");

	public static final BufferedImage STATUS_GOOD = getIcon("status_good.png");
	public static final BufferedImage STATUS_MEDIUM = getIcon("status_medium.png");
	public static final BufferedImage STATUS_POOR = getIcon("status_poor.png");
	public static final BufferedImage BORDER_IMAGE = getIcon("border.png");

	public static final BufferedImage LIST_BACKGROUND_IMG = getIcon("wood.jpg");
	public static final BufferedImage MOVIE_SHADOW_BG = getIcon("movie_item_shadow.png");
	public static final BufferedImage SEARCH_BACKGROUND_IMG = getIcon("wood3.jpg");

	public static final BufferedImage LOAD_MORE_BACK = getIcon("Load_More.png");
	public static final BufferedImage LOAD_MORE_BACK_HOVERED = getIcon("Load_More_border.png");
	public static final BufferedImage LOAD_MORE_BACK_PRESSED = getIcon("Load_More_border_pressed.png");

	public static final BufferedImage PLAY_NOW_BTN = getIcon("watch_now.png");
	public static final BufferedImage PLAY_NOW_BTN_HOVERED = getIcon("watch_now_hovered.png");
	public static final BufferedImage PLAY_NOW_BTN_PRESSED = getIcon("watch_now_pressed.png");
	
	public static final BufferedImage PLAY_LATER_BTN = getIcon("watch_later.png");
	public static final BufferedImage PLAY_LATER_BTN_HOVERED = getIcon("watch_later_hovered.png");
	public static final BufferedImage PLAY_LATER_BTN_PRESSED = getIcon("watch_later_pressed.png");

	public static final BufferedImage DETAILS_BTN = getIcon("watch_Details.png");
	public static final BufferedImage DETAILS_BTN_HOVERED = getIcon("watch_Details_hovered.png");
	public static final BufferedImage DETAILS_BTN_PRESSED = getIcon("watch_Details_pressed.png");

	public static final BufferedImage[][] MOVIE_ITEM_BUTTON_IMAGES = {{PLAY_NOW_BTN,PLAY_NOW_BTN_HOVERED,PLAY_NOW_BTN_PRESSED}
											,{PLAY_LATER_BTN,PLAY_LATER_BTN_HOVERED,PLAY_LATER_BTN_PRESSED},
											{DETAILS_BTN,DETAILS_BTN_HOVERED,DETAILS_BTN_PRESSED}};
	
	private static BufferedImage getIcon(String file) {
		BufferedImage image = null;
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
