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
import java.awt.Graphics;

import javax.swing.JPanel;

import com.lordroid.cupcake.res.R;

@SuppressWarnings("serial")
public class MovieShadowPan extends JPanel {
	public MovieShadowPan(MovieItem c) {
		c.setBounds(0, 0, 230, 345);
		this.setLayout(null);
		this.add(c);
		this.setPreferredSize(new Dimension(250, 365));
	}

	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(R.MOVIE_SHADOW_BG, 0, 0, this);
	}

}
