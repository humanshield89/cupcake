package com.lordroid.cupcake.ui;

import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.lordroid.cupcake.res.R;

@SuppressWarnings("serial")
public class PlayNowBtn extends JButton {

	/**
	 * @return the hovered
	 */
	public boolean isHovered() {
		return hovered;
	}

	boolean hovered = false;

	public PlayNowBtn() {
		super();

	}

	/**
	 * @param hovered
	 *            the hovered to set
	 */
	public void setHovered(boolean hovered) {
		this.hovered = hovered;
		this.setBorder(BorderFactory.createBevelBorder(6));
	}

	public void paintComponent(Graphics g) {
		if (!hovered)
			g.drawImage(R.PLAY_NOW_BTN_ICON, 0, 0, this);
		else
			g.drawImage(R.PLAY_NOW_BTN_HOVERED_ICON, 0, 0, this);
	}
}
