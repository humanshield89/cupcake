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
