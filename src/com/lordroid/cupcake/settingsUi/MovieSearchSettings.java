package com.lordroid.cupcake.settingsUi;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.border.Border;

import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.Settings;
import com.lordroid.cupcake.ui.MovieItem;

@SuppressWarnings("serial")
public class MovieSearchSettings extends SettingsPaneImpl {
	public static final String[] YES_NO_COMBO = {
			"Restore last filters on startup", "Reset to default on startup" };
	public static final String[] DEFAULT_ACTIONS_COMBO = {
			"Open movie in player", "Show Movie details" };
	public static final int[] DEFAULT_ACTIONS = { MovieItem.PLAY_ACTION,
			MovieItem.DETAILS_ACTION };
	public static final String[] PARENTAL_COMBO = { "Never",
			"PG when parental guidence is needed For young children",
			"PG-13 when containing innapropriate material for pre-Teens",
			"Only for rated R movies ( containing Adult content ) " };

	JLabel maxItemsPerPageLab = new JLabel("Items per page : ");
	JComboBox<String> maxItemsPerPageCombo = new JComboBox<String>(
			Settings.MAX_RESULT_PER_SEARCH_COMBO);
	JLabel remeberFiltersLab = new JLabel("Search filters : ");
	JComboBox<String> remeberFiltersCombo = new JComboBox<String>(YES_NO_COMBO);
	JLabel defaultOperationLab = new JLabel("Movie default action");
	JComboBox<String> defaultOperationCombo = new JComboBox<String>(
			DEFAULT_ACTIONS_COMBO);
	JLabel warnParentalLab = new JLabel("Parental guide");
	JComboBox<String> warnParentalCombo = new JComboBox<String>(PARENTAL_COMBO);

	JComponent[] components = { maxItemsPerPageLab, maxItemsPerPageCombo,
			remeberFiltersLab, remeberFiltersCombo, defaultOperationLab,
			defaultOperationCombo, warnParentalLab, warnParentalCombo };

	public MovieSearchSettings() {
		super();
		for (JComponent c : components) {
			c.setFont(R.NORMAL_FONT);
		}
		this.setBounds(10, 10, P_WIDTH, P_HEIGHT);
		this.setLayout(null);
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		this.setBorder(BorderFactory.createTitledBorder(border,
				"Movie List Settings", 0, 0, R.NORMAL_FONT, Color.WHITE));
		maxItemsPerPageLab.setBounds(20, 20, 120, 30);
		maxItemsPerPageCombo.setBounds(145, 20, 120, 30);

		remeberFiltersLab.setBounds(20, 60, 120, 30);
		remeberFiltersCombo.setBounds(145, 60, 230, 30);

		defaultOperationLab.setBounds(20, 100, 120, 30);
		defaultOperationCombo.setBounds(145, 100, 230, 30);

		warnParentalLab.setBounds(20, 140, 120, 30);
		warnParentalCombo.setBounds(145, 140, 400, 30);

		this.add(maxItemsPerPageLab);
		this.add(maxItemsPerPageCombo);
		this.add(remeberFiltersLab);
		this.add(remeberFiltersCombo);
		this.add(defaultOperationLab);
		this.add(defaultOperationCombo);
		this.add(warnParentalCombo);
		this.add(warnParentalLab);
		loadCurrentSettings();
	}

	@Override
	public void loadDefaultSettings() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCurrentSettings() {
		// TODO Auto-generated method stub
		maxItemsPerPageCombo.setSelectedIndex(Settings
				.getMaxSearchItemsPerPage());
		defaultOperationCombo.setSelectedIndex(Settings
				.getDefaultEnterOperation() == DEFAULT_ACTIONS[0] ? 0 : 1);
		remeberFiltersCombo.setSelectedIndex(Settings
				.getRememberFiltersOnStartup());
		warnParentalCombo.setSelectedIndex(Settings.getParentalWarningLvl());
	}

	@Override
	public void ApplySettings() {
		// TODO Auto-generated method stub
		Settings.setMaxSearchItemsPerPage(maxItemsPerPageCombo
				.getSelectedIndex());
		Settings.setDefaultEnterOperation(DEFAULT_ACTIONS[defaultOperationCombo
				.getSelectedIndex()]);
		Settings.setRememberFiltersOnStartup(remeberFiltersCombo
				.getSelectedIndex());
		Settings.setParentalWarningLvl(warnParentalCombo.getSelectedIndex());
	}

}
