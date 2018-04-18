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
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import com.lordroid.cupcake.App;
import com.lordroid.cupcake.bt.YifyMovieTorrent;
import com.lordroid.cupcake.controlers.ListPanWatcher;
import com.lordroid.cupcake.player.MediaPlayer;
import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.Strings;
import com.lordroid.cupcake.settingsUi.GlobalSettings;
import com.lordroid.cupcake.utils.DesktopUtils;
import com.lordroid.cupcake.yify.YifyMovie;

public class MainFram extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String PLAYER_CARD_NAME = "Cupcake Player";
	private static final String LIST_CARD_NAME = "Cupcake Movies";

	private JPanel CardContentPan = new JPanel();
	private JPanel PlayercontentPan = new JPanel();
	private JPanel MoviecontentPan = new JPanel();
	public MovieListPan movieListPan;
	// private MediaPlayer player = new MediaPlayer(this);
	public JMenuBar mainMenu = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu openMenu = new JMenu("Open");
	JMenu editMenu = new JMenu("Edit");
	JMenu aboutMenu = new JMenu("About");
	JMenu viewMenuItem = new JMenu("View");

	JMenuItem settingsMenuItem = new JMenuItem("Settings");
	JMenuItem openVideoMenuItem = new JMenuItem("Video File");
	JMenuItem openTorrentMenuItem = new JMenuItem("Torrent File");
	JMenuItem exitMenuItem = new JMenuItem("Exit");

	JMenuItem aboutCupcakeMenuItem = new JMenuItem("About Cupcake");
	JMenuItem licenseMenuItem = new JMenuItem("License");
	JMenuItem librariesMenuItem = new JMenuItem("Used Libraries");

	JMenuItem playerViewMenuItem = new JMenuItem("Player View");
	JMenuItem MovieListViewMenuItem = new JMenuItem("Movie List View");
	public static MediaPlayerFrame mediaPlayerFrame = new MediaPlayerFrame();

	public MainFram() {
		this.setBackground(Color.BLACK);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener() {

			@Override
			public void windowActivated(WindowEvent arg0) {

			}

			@Override
			public void windowClosed(WindowEvent arg0) {

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				systemExit();
			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {

			}

			@Override
			public void windowIconified(WindowEvent arg0) {

			}

			@Override
			public void windowOpened(WindowEvent arg0) {

			}

		});

		this.setIconImage(R.ICON);
		// TODO : change this

		// initializing components
		movieListPan = new MovieListPan(new ListPanWatcher() {

			@Override
			public void ListActionPerformed(YifyMovie movie, int action) {
				// TODO Auto-generated method stub
				// App.LOGGER.info("Action performed on " + movie.getTitle()
				// + " language =  " + movie.getLanguage() + "  id ="
				// + movie.getId() + " Action value is : " + action);
				if (action == MovieItem.PLAY_ACTION) {
					// initPlayerView();
					mediaPlayerFrame.myMediaPlayer
							.setMediaFromYifyTorrent(new YifyMovieTorrent(
									movie, -1));
					OpenMediaPlayer();
				} else if (action == MovieItem.LATER_ACTION) {
					// initPlayerView();
					// player.setYoutube(movie.getYoutubeTrailerURL());
					try {
						DesktopUtils.openWebpage(new URL(movie
								.getYoutubeTrailerURL()));
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();

					}
				}
			}

			@Override
			public void ItemSelected(MovieItem movieItem) {
				// TODO Auto-generated method stub
				// do nothing
			}

		});

		// window properties
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(1000, 600);
		this.setMinimumSize(new Dimension(1000, 600));
		this.setLocation(new Point((screenWidth - this.getWidth()) / 2,
				(screenHeight - this.getHeight()) / 2));
		// this.setVisible(true);
		this.setTitle(Strings.getTitle() + Strings.getVersion());

		// layouts
		CardContentPan.setLayout(new CardLayout());

		this.setContentPane(CardContentPan);

		// menu bar
		mainMenu.add(fileMenu);
		// fileMenu.add(openMenu);
		fileMenu.add(openVideoMenuItem);
		fileMenu.add(openTorrentMenuItem);
		fileMenu.add(exitMenuItem);
		editMenu.add(settingsMenuItem);
		mainMenu.add(editMenu);
		mainMenu.add(viewMenuItem);
		viewMenuItem.add(playerViewMenuItem);
		viewMenuItem.add(MovieListViewMenuItem);
		mainMenu.add(aboutMenu);
		aboutMenu.add(aboutCupcakeMenuItem);
		aboutMenu.add(librariesMenuItem);
		aboutMenu.add(licenseMenuItem);
		this.setJMenuBar(mainMenu);
		// actions
		playerViewMenuItem.addActionListener(this);
		MovieListViewMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		settingsMenuItem.addActionListener(this);
		// creating the components
		MoviecontentPan.setLayout(new BorderLayout());
		PlayercontentPan.setLayout(new BorderLayout());
		CardContentPan.add(this.MoviecontentPan, LIST_CARD_NAME);
		MoviecontentPan.add(movieListPan, BorderLayout.CENTER);
		CardContentPan.add(this.PlayercontentPan, PLAYER_CARD_NAME);
		PlayercontentPan.add(mediaPlayerFrame.getContentPane(),
				BorderLayout.CENTER);

		initMovieListPan();
		this.setVisible(true);

	}

	protected void systemExit() {
		// TODO Auto-generated method stub
		MediaPlayer.getMediaplayerfactory().release();
		System.exit(0);

	}

	public void initMovieListPan() {

		movieListPan.requestFocus();
		this.setTitle("Cupcake " + "Movie list ");
		// contentPan.revalidate();
		playerViewMenuItem.setEnabled(true);
		MovieListViewMenuItem.setEnabled(false);
		CardLayout cl = (CardLayout) (CardContentPan.getLayout());
		cl.show(CardContentPan, LIST_CARD_NAME);
		mediaPlayerFrame.myMediaPlayer.getMediaPlayerComponent()
				.getMediaPlayer().pause();
	}

	public void OpenMediaPlayer() {
		playerViewMenuItem.setEnabled(false);
		MovieListViewMenuItem.setEnabled(true);
		CardLayout cl = (CardLayout) (CardContentPan.getLayout());
		cl.show(CardContentPan, PLAYER_CARD_NAME);
		mediaPlayerFrame.myMediaPlayer.setFullScreenStrategy(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		Object source = arg0.getSource();
		if (source.equals(playerViewMenuItem)) {
			OpenMediaPlayer();
		} else if (source.equals(MovieListViewMenuItem)) {
			initMovieListPan();
		} else if (source.equals(exitMenuItem)) {
			systemExit();
		} else if (source.equals(settingsMenuItem)) {
			GlobalSettings.showSettings();
		}
	}

}
