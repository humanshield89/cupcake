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

import java.io.File;

import com.lordroid.cupcake.bt.YifyMovieTorrent;

/**
 * @author HumanShield85
 * 
 */
public interface MediaPlayerImp {
	/**
	 * this is called when trying to load a new media calling this will remove
	 * any previous playing media on the player
	 * 
	 * @param yifyTorrent
	 *            the Yify torrent that will be played on the player
	 */
	public void setMediaFromYifyTorrent(YifyMovieTorrent yifyTorrent);

	/**
	 * 
	 * @param video
	 */
	public void setMediaFromLocalVideo(File video);

	// TODO set this to a local torrent object once we make one
	/**
	 * TODO : this is not implemented yet ,finish this when we create a local
	 * torrent Object
	 * 
	 * @param torrent
	 */
	public void setMediaFromLocalTorrent(Object torrent);

}
