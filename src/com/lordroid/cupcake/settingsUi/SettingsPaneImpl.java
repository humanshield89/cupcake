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
package com.lordroid.cupcake.settingsUi;

import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class SettingsPaneImpl extends JPanel {
	static final int P_WIDTH = 600;
	static final int P_HEIGHT = 400;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SettingsPaneImpl() {
		this.setPreferredSize(new Dimension(P_WIDTH, P_HEIGHT));
	}

	public abstract void ApplySettings();

	public abstract void loadCurrentSettings();

	public abstract void loadDefaultSettings();

}
