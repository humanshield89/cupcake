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
package com.lordroid.cupcake.player;

import java.awt.Canvas;
import java.awt.Color;

import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

/**
 * @author HumanShield85
 * 
 */
public class Canvasx extends Canvas {
	final EmbeddedMediaPlayer mediaPlayerx;
	private static final long serialVersionUID = 1L;
	private CanvasVideoSurface videoSurface;

	public Canvasx(EmbeddedMediaPlayer mediaPlayerxxx) {
		super();
		this.setBackground(Color.BLACK);
		mediaPlayerx = mediaPlayerxxx;

	}

	/**
	 * 
	 */
	public EmbeddedMediaPlayer getMediaPlayer() {

		return mediaPlayerx;
	}

	/**
	 * @return the videoSurface
	 */
	public CanvasVideoSurface getVideoSurface() {

		return videoSurface;
	}

	/**
	 * @param videoSurface
	 *            the videoSurface to set
	 */
	public void setVideoSurface(CanvasVideoSurface videoSurface) {
		this.videoSurface = videoSurface;

	}

	/**
	 * 
	 */
	public void release() {
		// TODO Auto-generated method stub
		this.mediaPlayerx.release();
	}
}
