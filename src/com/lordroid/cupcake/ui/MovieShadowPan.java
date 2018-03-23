package com.lordroid.cupcake.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.lordroid.cupcake.res.R;

public class MovieShadowPan extends JPanel{

	public MovieShadowPan(Component c) {
		c.setBounds(0, 0, 230, 345);
		this.setLayout(null);
		this.add(c);
		this.setPreferredSize(new Dimension(250,365));
	}
	public void paintComponent(Graphics g){
		g.drawImage(R.MOVIE_SHADOW_BG, 0, 0, this);
	}
}
