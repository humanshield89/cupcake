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
package com.lordroid.cupcake.player;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;

import com.lordroid.cupcake.App;
import com.lordroid.cupcake.controlers.Watchable;
import com.lordroid.cupcake.controlers.Watcher;
import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.S;
import com.lordroid.cupcake.res.Settings;
import com.lordroid.cupcake.utils.TimeUtils;

/**
 * @author HumanShield85
 * 
 */
public class ControlPanel extends JPanel implements Watcher, Watchable {

	public class BtnActionListner implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent
		 * )
		 */
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource().equals(playBtn)) {
				App.LOGGER.debug("Play pressed ? ");
				updateWatchers(S.PLAY_BTN_PRESSED);
			} else if (arg0.getSource().equals(skipBtn)) {
				// perform action
				App.LOGGER.debug("skip button pressed ");
				updateWatchers(S.SKIP_BTN_PRESSED);

			} else if (arg0.getSource().equals(rewindBtn)) {
				updateWatchers(S.REWIND_BTN_PRESSED);
			} else if (arg0.getSource().equals(volumeBtn)) {
				updateWatchers(S.VOLUME_MUTE_PRESSED);
			} else if (arg0.getSource().equals(fullScreen)) {
				updateWatchers(S.FULL_SCREEN_BTN_PRESSED);
			} else if (arg0.getSource().equals(fullScreen)) {
				updateWatchers(S.FULL_SCREEN_BTN_PRESSED);
			} else if (arg0.getSource().equals(stopBtn)) {
				updateWatchers(S.STOP_BTN_PRESSED);
			}
		}

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ArrayList<Watcher> watchers = new ArrayList<Watcher>();

	BasicSliderUI uix;
	private JSlider progress;

	private JSlider volumeControl = new JSlider(0, 100,
			Settings.getCurrentVolume());

	private JButton playBtn = new JButton(new ImageIcon(R.PLAY_BTN_ICON));
	private JButton stopBtn = new JButton(new ImageIcon(R.STOP_BTN_ICON));

	private JButton rewindBtn = new JButton(new ImageIcon(R.REWIND_BTN_ICON));

	private JButton skipBtn = new JButton(new ImageIcon(R.SKIP_BTN_ICON));

	private JLabel currentTimeLab = new JLabel("  --:--:--  ");

	private JButton volumeBtn = new JButton(new ImageIcon(R.VOLUME_MEDIUM));

	private JButton fullScreen = new JButton(new ImageIcon(R.FULL_SCREEN));

	private JLabel currentVolume = new JLabel() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void setText(String text) {
			if (text.length() == 1) {
				text = "  " + text;
			} else if (text.length() == 2) {
				text = " " + text;
			}
			super.setText(text);
		}
	};

	private JPanel volumePanel = new JPanel();

	private JLabel totalTimeLab = new JLabel("  --:--:--  ");

	private JPanel progressContainer = new JPanel();

	private JPanel btnContainer = new JPanel();

	private JPanel controlsContainer = new JPanel();

	@SuppressWarnings("serial")
	public ControlPanel() {
		LookAndFeel previousLF = UIManager.getLookAndFeel();
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		progress = new JSlider() {
			{
				MouseListener[] listeners = getMouseListeners();
				for (MouseListener l : listeners)
					removeMouseListener(l); // remove UI-installed TrackListener
				/** BasicSliderUI **/
				uix = (BasicSliderUI) getUI();
				// uix = ui;
				BasicSliderUI.TrackListener tl = uix.new TrackListener() {

					// this is where we jump to absolute value of click
					@Override
					public void mouseClicked(MouseEvent e) {
						Point p = e.getPoint();
						int value = uix.valueForXPosition(p.x);

						setValue(value);
						updateWatchers(S.TIMER_CLICKED);
					}

					@Override
					public void mouseDragged(MouseEvent e) {
						// TODO Auto-generated method stub
						Point p = e.getPoint();
						int value = uix.valueForXPosition(p.x);

						setValue(value);
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						Point p = e.getPoint();
						progress.setToolTipText(TimeUtils
								.getLabelFormatedTime(uix
										.valueForXPosition(p.x) * 1000));

					}

					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						progress.setToolTipText("");
					}

					@Override
					public void mouseMoved(MouseEvent e) {

						Point p = e.getPoint();
						progress.setToolTipText(TimeUtils
								.getLabelFormatedTime(uix
										.valueForXPosition(p.x) * 1000));
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						updateWatchers(S.TIMER_CLICKED);

					}

					// disable check that will invoke scrollDueToClickInTrack
					@Override
					public boolean shouldScroll(int dir) {
						return false;
					}

				};
				addMouseListener(tl);
				this.addMouseMotionListener(new MouseMotionListener() {

					@Override
					public void mouseDragged(MouseEvent arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void mouseMoved(MouseEvent arg0) {
						// TODO Auto-generated method stub
						Point p = arg0.getPoint();
						progress.setToolTipText(TimeUtils
								.getLabelFormatedTime(uix
										.valueForXPosition(p.x) * 1000));
					}

				});
			}

		};
		progress.setBackground(this.getBackground());
		// progress.setSize(950,50);
		try {
			UIManager.setLookAndFeel(previousLF);
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		this.currentVolume.setFont(R.NORMAL_FONT);

		progress.setFocusable(false);
		progress.setValue(0);

		this.fullScreen.addActionListener(new BtnActionListner());
		volumeControl.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				updateWatchers(S.VOLUME_UP_DOWN_PRESSED);
			}

		});
		this.volumeBtn.addActionListener(new BtnActionListner());

		volumeControl.setMaximumSize(new Dimension(50, 20));

		controlsContainer.setLayout(new BorderLayout());

		progressContainer.setLayout(new BorderLayout());
		progressContainer.add(currentTimeLab, BorderLayout.WEST);
		progressContainer.add(progress, BorderLayout.CENTER);
		progressContainer.add(totalTimeLab, BorderLayout.EAST);

		this.setLayout(new BorderLayout());
		this.add(progressContainer, BorderLayout.CENTER);
		btnContainer.add(rewindBtn);
		btnContainer.add(playBtn);
		btnContainer.add(stopBtn);
		btnContainer.add(skipBtn);

		volumePanel.add(currentVolume);
		volumePanel.add(volumeControl);
		volumePanel.add(volumeBtn);

		currentVolume.setText("" + Settings.getCurrentVolume());

		volumePanel.add(fullScreen);

		controlsContainer.add(btnContainer, BorderLayout.WEST);
		controlsContainer.add(volumePanel, BorderLayout.EAST);
		this.add(controlsContainer, BorderLayout.SOUTH);
		playBtn.addActionListener(new BtnActionListner());
		stopBtn.addActionListener(new BtnActionListner());
		skipBtn.addActionListener(new BtnActionListner());
		rewindBtn.addActionListener(new BtnActionListner());

		// progress.setMaximum(100);
		this.setOpaque(true);
		this.setSize(new Dimension(1000, 200));
		// remove focus
		// progressPan.setFocusable(false);
		progress.setFocusable(false);
		volumeControl.setFocusable(false);
		// playBtn.setFocusable(false);
		rewindBtn.setFocusable(false);
		skipBtn.setFocusable(false);
		stopBtn.setFocusable(false);
		// currentTimeLab.setFocusable(false);
		// volumeBtn.setFocusable(false);
		// fullScreen.setFocusable(false);
		// currentVolume.setFocusable(false);
		// volumePanel.setFocusable(false);
		// totalTimeLab.setFocusable(false);
		// progressContainer.setFocusable(false);
		// btnContainer.setFocusable(false);
		// controlsContainer.setFocusable(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.controlers.Watchable#addWatcher(com.lordroid.cupcake
	 * .controlers.Watcher)
	 */
	@Override
	public void addWatcher(Watcher wcher) {
		// TODO Auto-generated method stub
		watchers.add(wcher);
	}

	/**
	 * @return the btnContainer
	 */
	public JPanel getBtnContainer() {
		return btnContainer;
	}

	/**
	 * @return the currentTimeLab
	 */
	public JLabel getCurrentTimeLab() {
		return currentTimeLab;
	}

	/**
	 * @return the currentVolume
	 */
	public JLabel getCurrentVolume() {
		return currentVolume;
	}

	/**
	 * @return the fullScreen
	 */
	public JButton getFullScreen() {
		return fullScreen;
	}

	/**
	 * @return the playBtn
	 */
	public JButton getPlayBtn() {
		return playBtn;
	}

	/**
	 * @return the progress
	 */
	public JSlider getProgress() {

		return progress;
	}

	/**
	 * @return the rewindBtn
	 */
	public JButton getRewindBtn() {
		return rewindBtn;
	}

	/**
	 * @return the skipBtn
	 */
	public JButton getSkipBtn() {
		return skipBtn;
	}

	/**
	 * @return the totalTimeLab
	 */
	public JLabel getTotalTimeLab() {
		return totalTimeLab;
	}

	/**
	 * @return the volumeBtn
	 */
	public JButton getVolumeBtn() {
		return volumeBtn;
	}

	/**
	 * @return the volumeControl
	 */
	public JSlider getVolumeControl() {
		return volumeControl;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.controlers.Watchable#remove(com.lordroid.cupcake
	 * .controlers.Watcher)
	 */
	@Override
	public void removeWatcher(Watcher w) {
		// TODO Auto-generated method stub
		watchers.remove(w);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.controlers.Watcher#updateHundler(com.lordroid.cupcake
	 * .controlers.Watchable)
	 */
	@Override
	public void updateHundler(Watchable wble, int message) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lordroid.cupcake.controlers.Watchable#updateWatchers()
	 */
	@Override
	public void updateWatchers(int message) {
		// TODO Auto-generated method stub
		for (Watcher w : watchers) {

			w.updateHundler(this, message);
		}
	}

}
