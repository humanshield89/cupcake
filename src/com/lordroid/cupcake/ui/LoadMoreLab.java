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

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import com.lordroid.cupcake.res.R;

@SuppressWarnings("serial")
public class LoadMoreLab extends JButton implements MouseListener {

	public LoadMoreLab() {
		super(new ImageIcon(R.LOAD_MORE_BACK));
		// this = new JLabel();
		this.setPreferredSize(new Dimension(250, 365));
		this.addMouseListener(this);
		this.setBackground(new Color(0, 0, 0, 0));
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setIcon(new ImageIcon(R.LOAD_MORE_BACK_HOVERED));
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setIcon(new ImageIcon(R.LOAD_MORE_BACK));
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setIcon(new ImageIcon(R.LOAD_MORE_BACK_PRESSED));
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		this.setIcon(new ImageIcon(R.LOAD_MORE_BACK_HOVERED));
	}

	@Override
	public void setSelected(boolean bool) {
		super.setSelected(bool);
		if (bool)
			this.setIcon(new ImageIcon(R.LOAD_MORE_BACK_HOVERED));
		else
			this.setIcon(new ImageIcon(R.LOAD_MORE_BACK));

	}

}
