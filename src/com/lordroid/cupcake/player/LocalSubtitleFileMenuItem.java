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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class LocalSubtitleFileMenuItem extends JMenuItem implements
		ActionListener {
	final File subtitle;
	final MediaPlayer mediaPlayer;

	public LocalSubtitleFileMenuItem(File sub, MediaPlayer arg1) {
		this.setText(sub.getName());
		subtitle = sub;
		mediaPlayer = arg1;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		mediaPlayer.getMediaPlayerComponent().getMediaPlayer()
				.setSubTitleFile(subtitle);
	}
}
