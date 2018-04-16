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

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.alee.laf.tabbedpane.WebTabbedPane;
import com.lordroid.cupcake.res.R;

@SuppressWarnings("serial")
public class GlobalSettings extends WebTabbedPane {
	private GeneralSettingsPan generalSettings = new GeneralSettingsPan();
	private MovieSearchSettings movieSearchSettings = new MovieSearchSettings();
	private MediaPlayerSettings mediaPlayerSettings = new MediaPlayerSettings();

	public GlobalSettings() {
		super();
		this.setBackground(generalSettings.getBackground());

		this.addTab("General  ", new ImageIcon(R.GENERALE_SETINGS_ICON),
				generalSettings);
		this.addTab("Movie List  ", new ImageIcon(R.LIST_SETINGS_ICON),
				movieSearchSettings);
		this.addTab("Media Player ", new ImageIcon(R.PLAYER_SETINGS_ICON),
				mediaPlayerSettings);

	}

	/**
	 * @return the generalSettings
	 */
	public GeneralSettingsPan getGeneralSettings() {
		return generalSettings;
	}

	/**
	 * @return the movieSearchSettings
	 */
	public MovieSearchSettings getMovieSearchSettings() {
		return movieSearchSettings;
	}

	public void ApplySettings() {
		generalSettings.ApplySettings();
		movieSearchSettings.ApplySettings();
		mediaPlayerSettings.ApplySettings();
	}

	public static void showSettings() {

		GlobalSettings settings = new GlobalSettings();
		int action = JOptionPane.showConfirmDialog(null, settings, "Settings ",
				JOptionPane.OK_CANCEL_OPTION);
		if (action == JOptionPane.OK_OPTION)
			settings.ApplySettings();

	}

}
