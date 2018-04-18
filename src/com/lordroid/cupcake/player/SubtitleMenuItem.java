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
import java.io.IOException;

import javax.swing.JRadioButtonMenuItem;

import Opensubs.SubtitleInfo;

import com.lordroid.cupcake.res.S;
import com.lordroid.cupcake.utils.SubtitleFetcher;

@SuppressWarnings("serial")
public class SubtitleMenuItem extends JRadioButtonMenuItem implements ActionListener {
	public static final String TMP_SUBS_FOLDER = S.SYSTEM_TMP_FOLDER
			+ File.separator + "subtitles";
	MediaPlayer mediaPlayer;
	SubtitleInfo subInfo;
	boolean cached = false;
	private File sub;

	public SubtitleMenuItem(SubtitleInfo subInfo, MediaPlayer mediaPlayer) {
		new File(TMP_SUBS_FOLDER).mkdirs();
		this.mediaPlayer = mediaPlayer;
		this.subInfo = subInfo;
		this.setText(subInfo.getLanguageName() + " | "
				+ subInfo.getIDSubtitle());
		// this.addA
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (!cached) {
			try {
				sub = SubtitleFetcher.getSubtitle(subInfo, new File(
						TMP_SUBS_FOLDER));
				this.mediaPlayer.getMediaPlayerComponent().getMediaPlayer()
						.setSubTitleFile(sub);
				this.mediaPlayer.setSubtitle(sub);
				cached = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cached = false;
			}
		} else {
			this.mediaPlayer.setSubtitle(sub);
		}
	}

}
