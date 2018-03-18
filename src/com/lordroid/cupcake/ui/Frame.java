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
package com.lordroid.cupcake.ui;

import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import com.lordroid.cupcake.player.MediaPlayer;
import com.lordroid.cupcake.res.R;

/**
 * @author HumanShield85
 * 
 */
public class Frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final MediaPlayer player = new MediaPlayer(this);

	public Frame() {
		this.setMinimumSize(new Dimension(400, 300));
		this.setTitle("Cupcake");
		this.setIconImage(R.ICON);
		this.setSize(600, 400);
		this.add(player);
		this.setVisible(true);
		// player.setVideo(str);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {

			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void windowClosing(WindowEvent arg0) {
				// let's release properly
				// TODO : prompt user to prevent accidental close
				player.getMediaPlayerComponent().release();
				MediaPlayer.getMediaplayerfactory().release();
				System.exit(0);
			}

			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub

			}

		});
	}
}
