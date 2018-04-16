package com.lordroid.cupcake.settingsUi;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.S;
import com.lordroid.cupcake.res.Settings;
import com.lordroid.cupcake.utils.CacheTracker;
import com.lordroid.cupcake.utils.FileUtils;

import de.sciss.submin.Submin;

@SuppressWarnings("serial")
public class GeneralSettingsPan extends SettingsPaneImpl implements
		ActionListener {


	private JLabel uiLangLab = new JLabel("GUI Language   :  ");
	private JComboBox<String> uiLangCombo = new JComboBox<String>(
			Settings.AVAILABLE_GUI_LANGUAGE_COMBO);
	private JPanel langContainer = new JPanel();
	private JPanel cacheManagementPan = new JPanel();
	private JLabel cacheLab = new JLabel("Max cache size");
	private JSlider cacheSlider = new JSlider();
	private JLabel cacheValueLab = new JLabel();
	private JLabel currentCacheSizeLab = new JLabel(
			"Current Cache size on disk");
	private JLabel currentCacheSizeValueLab = new JLabel();
	private JButton clearCacheBtn = new JButton("Clear Torrent cache");
	private JLabel imageCacheLab = new JLabel("Image cache size");
	private JLabel imageCacheValueLab = new JLabel();
	private JButton clearImageCacheBtn = new JButton("Clear Image cache");
	private JComponent[] components = { uiLangLab, uiLangCombo, langContainer,
			cacheManagementPan, cacheLab, cacheSlider, currentCacheSizeLab,
			currentCacheSizeValueLab, cacheValueLab, clearCacheBtn,
			imageCacheLab, imageCacheValueLab, clearImageCacheBtn };

//	public static void main(String args[]) {
//		Submin.install(true);
////		JFrame frame = new JFrame();
////		frame.setSize(600, 400);
////		frame.setContentPane(new GeneralSettingsPan());
////		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
////		frame.setVisible(true);
//		GlobalSettings settings = new GlobalSettings();
//		int action = JOptionPane
//				.showConfirmDialog(
//						null,
//						settings,
//						"Settings ",
//						JOptionPane.OK_CANCEL_OPTION);
//		if(action == JOptionPane.OK_OPTION)
//			settings.ApplySettings();
//		System.out.println(action);
//	}

	public GeneralSettingsPan() {
		super();
		for (JComponent c : components) {
			c.setFont(R.NORMAL_FONT);
		}
		clearCacheBtn.addActionListener(this);
		clearImageCacheBtn.addActionListener(this);
		this.setLayout(null);
		this.setBounds(10, 10, P_WIDTH, P_HEIGHT);
		// this.setBorder(BorderFactory.createTitledBorder("General Settings"));
		Border border = BorderFactory.createLineBorder(Color.WHITE);
		this.setBorder(BorderFactory.createTitledBorder(border,
				"General Settings", 0, 0, R.NORMAL_FONT, Color.WHITE));
		// let's place components
		// lang
		langContainer.setBounds(20, 25, 560, 40);
		langContainer.setLayout(null);
		uiLangLab.setBounds(2, 2, 120, 30);
		uiLangCombo.setBounds(144, 2, 120, 30);
		langContainer.add(uiLangLab);
		langContainer.add(uiLangCombo);
		// cache
		cacheManagementPan.setLayout(null);
		cacheManagementPan.setBounds(20, 70, 550, 200);
		cacheManagementPan.setBorder(BorderFactory.createTitledBorder(border,
				"Cache Management Settings", 0, 0, R.NORMAL_FONT, Color.WHITE));
		// max cache
		cacheLab.setBounds(20, 30, 120, 30);
		cacheSlider.setBounds(144, 30, 300, 30);
		cacheValueLab.setBounds(450, 30, 80, 30);
		cacheSlider.setMinimum(2048);
		cacheSlider.setMaximum(10240);
		cacheSlider.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				cacheValueLab.setText(cacheSlider.getValue() + " MB");
			}

		});
		cacheSlider.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				// TODO Auto-generated method stub
				// arg0.getScrollType(MouseWheelEvent.)
				int i = arg0.getWheelRotation();
				cacheSlider.setValue(cacheSlider.getValue() + (i * 50));
			}

		});
		// current cache
		currentCacheSizeLab.setBounds(20, 70, 200, 30);
		currentCacheSizeValueLab.setBounds(224, 70, 120, 30);
		clearCacheBtn.setBounds(346, 70, 175, 30);

		// Image cache
		imageCacheLab.setBounds(20, 110, 200, 30);
		imageCacheValueLab.setBounds(224, 110, 200, 30);
		clearImageCacheBtn.setBounds(346, 110, 175, 30);

		// cache management panel
		cacheManagementPan.add(cacheLab);
		cacheManagementPan.add(cacheSlider);
		cacheManagementPan.add(cacheValueLab);
		cacheManagementPan.add(currentCacheSizeLab);
		cacheManagementPan.add(currentCacheSizeValueLab);
		cacheManagementPan.add(clearCacheBtn);
		cacheManagementPan.add(imageCacheLab);
		cacheManagementPan.add(imageCacheValueLab);
		cacheManagementPan.add(clearImageCacheBtn);

		this.add(langContainer);
		this.add(cacheManagementPan);
		loadCurrentSettings();
	}

	@Override
	public void loadDefaultSettings() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadCurrentSettings() {
		// TODO Auto-generated method stub
		uiLangCombo.setSelectedIndex(Settings.getGuiLanguage());
		cacheSlider
				.setValue((int) (Settings.getCurrentMaxCacheSize() / 1024L / 1024L));
		cacheValueLab.setText(cacheSlider.getValue() + " MB");
		currentCacheSizeValueLab.setText(CacheTracker.getTotalDownFolderSize()
				/ 1024 / 1024 + "MB");
		imageCacheValueLab.setText(CacheTracker.getImageCacheSize() / 1024
				/ 1024 + "MB");

	}

	@Override
	public void ApplySettings() {
		// TODO Auto-generated method stub
		Settings.setGuiLanugage(uiLangCombo.getSelectedIndex());
		Settings.setCurrentMaxCacheSize((long)cacheSlider.getValue()*1024*1024);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		if (source.equals(clearImageCacheBtn)) {
			int action = JOptionPane
					.showConfirmDialog(
							this,
							"This will delete Image cache from disk \n usually it's not needed to do this \n are you sure ?",
							"Please confirm your action ",
							JOptionPane.YES_NO_OPTION);
			if (action == JOptionPane.YES_OPTION) {
			FileUtils.deleteRecursively(new File(S.IMAGE_CACHE_TMP_FOLDER));
			new File(S.IMAGE_CACHE_TMP_FOLDER).mkdirs();
			imageCacheValueLab.setText(CacheTracker.getImageCacheSize() / 1024
					/ 1024 + "MB");
			}
		} else if (source.equals(clearCacheBtn)) {
			// JDialogBox.
			int action = JOptionPane
					.showConfirmDialog(
							this,
							"This action will delete all the downloaded movies including \n any movie currently playing from a torrent source are you \n sure you wanna continue ?",
							"Please confirm your action ",
							JOptionPane.YES_NO_OPTION);
			if (action == JOptionPane.YES_OPTION) {
				FileUtils.deleteRecursively(new File(S.MOVIE_DOWNLOAD_FOLDER));
				new File(S.MOVIE_DOWNLOAD_FOLDER).mkdirs();
				currentCacheSizeValueLab.setText(CacheTracker
						.getTotalDownFolderSize() / 1024 / 1024 + "MB");
			}

		}
	}

}
