package com.lordroid.cupcake.player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenuItem;

import Opensubs.SubtitleInfo;

import com.lordroid.cupcake.res.S;
import com.lordroid.cupcake.utils.SubtitleFetcher;

@SuppressWarnings("serial")
public class SubtitleMenuItem extends JMenuItem implements ActionListener {
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
				cached = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				cached = false;
			}
		} else {
			this.mediaPlayer.getMediaPlayerComponent().getMediaPlayer()
					.setSubTitleFile(sub);
		}
	}

}
