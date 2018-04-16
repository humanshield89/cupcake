package com.lordroid.cupcake.settingsUi;

import java.awt.Dimension;

import javax.swing.JPanel;

public abstract class SettingsPaneImpl extends JPanel {
	static final int P_WIDTH = 600;
	static final int P_HEIGHT = 400;

	public SettingsPaneImpl() {
		this.setPreferredSize(new Dimension(P_WIDTH, P_HEIGHT));
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public abstract void loadDefaultSettings();

	public abstract void loadCurrentSettings();

	public abstract void ApplySettings();

}
