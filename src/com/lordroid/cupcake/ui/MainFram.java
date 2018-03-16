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

import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.lordroid.cupcake.res.Strings;

public class MainFram extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MainFram() {
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

		// this.setLocationRelativeTo(null);
		this.setSize(900, 600);
		this.setLocation(new Point((screenWidth - this.getWidth()) / 2,
				(screenHeight - this.getHeight()) / 2));

		this.setVisible(true);
		this.setTitle(Strings.getTitle() + Strings.getVersion());
	}

}
