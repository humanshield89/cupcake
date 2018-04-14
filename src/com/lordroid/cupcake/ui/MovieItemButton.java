package com.lordroid.cupcake.ui;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import com.alee.laf.button.WebButton;
import com.lordroid.cupcake.res.R;

@SuppressWarnings("serial")
public class MovieItemButton extends WebButton implements MouseListener {
	public static final int PLAY_NOW = 0;
	public static final int PLAY_LATER = 1;
	public static final int DETAILS = 2;
	private static final String[] TOOL_TIP = { "Play this movie now",
			"Watch Trailer On Youtube (opens in external Browser)",
			"Show more details about this movie" };
	final BufferedImage[] images;

	public MovieItemButton(int buttonType) {
		super();
		// this.setBottomBgColor(new Color(254,254,254,50));
		// this.setTopBgColor(new Color(150,150,150,50));

		images = R.MOVIE_ITEM_BUTTON_IMAGES[buttonType];
		this.setIcon(new ImageIcon(images[0]));
		this.addMouseListener(this);
		this.setToolTipText(TOOL_TIP[buttonType]);
		this.setFocusable(false);
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setIcon(new ImageIcon(images[1]));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setIcon(new ImageIcon(images[0]));
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setIcon(new ImageIcon(images[2]));
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setIcon(new ImageIcon(images[1]));
	}
}
