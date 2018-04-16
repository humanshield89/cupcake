package com.lordroid.cupcake.settingsUi;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.Settings;
import com.lordroid.cupcake.utils.SubtitleFetcher;

@SuppressWarnings("serial")
public class MediaPlayerSettings extends SettingsPaneImpl{
	static final String OS_ENABLED ="Get subtitles from Opensubtitles.org";
	static final String OS_DISABLED ="DOT NOT Get subtitles from Opensubtitles.org";

	JLabel subtitleEncodingLab = new JLabel("Subtitles Encoding");
	JComboBox<String> subtileEncodingCombo = new JComboBox<String>(Settings.SUB_ENCODING_COMBO);
	// subs Open subs .org
	JPanel subtitleSettingsPan = new JPanel();
	JCheckBox enableOs = new JCheckBox(OS_DISABLED);
	
	JLabel lan1Lab = new JLabel("First prefered Launguage ");
	JLabel lan2Lab = new JLabel("Second Prefered Launguage");
	JLabel lan3Lab = new JLabel("Third Prefered Launguage");

	JComboBox<String> lang1Combo = new JComboBox<String>(SubtitleFetcher.SUBTITLE_LANGUAGES_NAMES);
	JComboBox<String> lang2Combo = new JComboBox<String>(SubtitleFetcher.SUBTITLE_LANGUAGES_NAMES);
	JComboBox<String> lang3Combo = new JComboBox<String>(SubtitleFetcher.SUBTITLE_LANGUAGES_NAMES);

	JCheckBox autoLoad = new JCheckBox("Automatically download and show subtitles for movies I watch in Cupcake");
	
	JComponent[] components = {subtitleEncodingLab, subtileEncodingCombo ,subtitleSettingsPan ,lang1Combo,lang2Combo,lang3Combo,
			lan1Lab ,lan3Lab,lan2Lab};
	JComponent[] subComponents = {lang1Combo,lang2Combo,lang3Combo,lan1Lab ,lan3Lab,lan2Lab,autoLoad};
	public MediaPlayerSettings() {
		super();
		for (JComponent c : components)
			c.setFont(R.NORMAL_FONT);
		this.setLayout(null);
		subtitleEncodingLab.setBounds(20, 20, 120, 30);
		subtileEncodingCombo.setBounds(140, 20, 300, 30);
		// subs
		// enable 
		subtitleSettingsPan.setBounds(20, 80, SettingsPaneImpl.P_WIDTH-40, SettingsPaneImpl.P_HEIGHT-100);
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		subtitleSettingsPan.setBorder(BorderFactory.createTitledBorder(border,
				"Open Subtitles API", 0, 0, R.NORMAL_FONT, Color.WHITE));
		enableOs.setBounds(30, 100, 300, 30);

		lan1Lab.setBounds(30, 140, 200, 30);
		lang1Combo.setBounds(240, 140, 200, 30);
		
		lan2Lab.setBounds(30, 180, 200, 30);
		lang2Combo.setBounds(240, 180, 200, 30);
		
		lan3Lab.setBounds(30, 220, 200, 30);
		lang3Combo.setBounds(240, 220, 200, 30);
		
		autoLoad.setBounds(30, 260, 450, 30);
		
		
		
		
		
		enableOs.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				if(enableOs.isSelected()){
					enableOs.setText(OS_ENABLED);

				} else {
					enableOs.setText(OS_DISABLED);
				}
				// disable and enable acordingly
				for (JComponent c : subComponents) {
					c.setEnabled(enableOs.isSelected());
				}
			}
			
		});
		
		
		
		
		
		// adding components to panel
		this.add(subtitleEncodingLab);
		this.add(subtileEncodingCombo);
		this.add(subtitleSettingsPan);
		this.add(enableOs);
		this.add(lan2Lab );
		this.add( lan1Lab);
		this.add( lan3Lab);
		this.add( lang1Combo);
		this.add( lang2Combo);
		this.add( lang3Combo);
		this.add( autoLoad);

		loadCurrentSettings();
	}
	@Override
	public void loadDefaultSettings() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadCurrentSettings() {
		// TODO Auto-generated method stub
		subtileEncodingCombo.setSelectedIndex(Settings.getDefaultSubtittleEncodingIndex());
		enableOs.setSelected(Settings.LoadOsSubtitles());
		for (JComponent c : subComponents) {
			c.setEnabled(enableOs.isSelected());
		}
		lang1Combo.setSelectedIndex(Settings.getSubtitlesLang1());
		lang2Combo.setSelectedIndex(Settings.getSubtitlesLang2());
		lang3Combo.setSelectedIndex(Settings.getSubtitlesLang3());
		autoLoad.setSelected(Settings.autoLoadSubtitles());
	}

	@Override
	public void ApplySettings() {
		// TODO Auto-generated method stub
		Settings.setDefaultSubtittleEncoding(subtileEncodingCombo.getSelectedIndex());
		Settings.setLoadOsSubtitles(enableOs.isSelected());
		Settings.setSubtitlesLang1(lang1Combo.getSelectedIndex());
		Settings.setSubtitlesLang2(lang2Combo.getSelectedIndex());
		Settings.setSubtitlesLang3(lang3Combo.getSelectedIndex());
		
	}

}
