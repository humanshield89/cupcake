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

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.alee.laf.progressbar.WebProgressBar;

@SuppressWarnings("serial")
public class BufferingPanel extends JPanel {

	private JLabel peersLab = new JLabel("0") {

		@Override
		public void setText(String text) {
			super.setText("Number of peers : " + text);
		}
	};

	private JLabel currentDownSpeed = new JLabel("0") {
		@Override
		public void setText(String text) {
			String str = text;
			try {
				str = text.substring(0, text.lastIndexOf("."));
			} catch (Exception e) {

			}
			super.setText("Average Speed : " + str + " KB/S");
		}
	};

	private JLabel buffred = new JLabel("0") {
		@Override
		public void setText(String text) {
			super.setText("Buffred : " + text + " MB");
		}
	};

	private WebProgressBar progress = new WebProgressBar();

	private JPanel labelContainer = new JPanel();

	public BufferingPanel() {
		super();
		progress.setIndeterminate(true);
		progress.setStringPainted(true);
		progress.setString("Loading your movie this may take up to a minute ");
		labelContainer.setLayout(new FlowLayout());
		labelContainer.add(peersLab);
		labelContainer.add(currentDownSpeed);
		labelContainer.add(buffred);

		this.setLayout(new BorderLayout());
		this.add(progress, BorderLayout.CENTER);
		this.add(labelContainer, BorderLayout.SOUTH);
	}
	/**
	 * @return the buffred
	 */
	public JLabel getBuffred() {
		return buffred;
	}
	/**
	 * @return the currentDownSpeed
	 */
	public JLabel getCurrentDownSpeed() {
		return currentDownSpeed;
	}
	/**
	 * @return the peersLab
	 */
	public JLabel getPeersLab() {
		return peersLab;
	}

	/**
	 * @return the progress
	 */
	public WebProgressBar getProgress() {
		return progress;
	}

	public void resetValues() {
		peersLab.setText("0");
		currentDownSpeed.setText("0");
		buffred.setText("0");

	}

	public void updateValues(String[] state) {
		peersLab.setText(state[0]);
		currentDownSpeed.setText(state[1]);
		buffred.setText(state[2]);
	}
}
