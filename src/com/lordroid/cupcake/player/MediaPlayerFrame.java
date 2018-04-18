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

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JFrame;

import com.lordroid.cupcake.bt.YifyMovieTorrent;

/**
 * @author HumanShield85
 * 
 */
@SuppressWarnings("serial")
public class MediaPlayerFrame extends JFrame implements MediaPlayerImp {
	public final MediaPlayer myMediaPlayer;

	public MediaPlayerFrame() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
		myMediaPlayer = new MediaPlayer(this);
		this.setContentPane(myMediaPlayer);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.player.MediaPlayerImp#setMediaFromYifyTorrent(com
	 * .lordroid.cupcake.yify.YifyTorrent)
	 */
	@Override
	public void setMediaFromYifyTorrent(YifyMovieTorrent yifyTorrent) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.player.MediaPlayerImp#setMediaFromLocalVideo(java
	 * .io.File)
	 */
	@Override
	public void setMediaFromLocalVideo(File video) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.player.MediaPlayerImp#setMediaFromLocalTorrent(java
	 * .lang.Object)
	 */
	@Override
	public void setMediaFromLocalTorrent(Object torrent) {
		// TODO Auto-generated method stub

	}

}
