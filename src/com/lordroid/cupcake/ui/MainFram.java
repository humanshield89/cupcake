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
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.lordroid.cupcake.bt.YifyMovieTorrent;
import com.lordroid.cupcake.controlers.ListPanWatcher;
import com.lordroid.cupcake.player.MediaPlayer;
import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.Strings;
import com.lordroid.cupcake.utils.DesktopUtils;
import com.lordroid.cupcake.yify.YifyMovie;

public class MainFram extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPan = new JPanel();
	private MovieListPan movieListPan ;
	private MediaPlayer player = new MediaPlayer(this);
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

	public MainFram() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowActivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosed(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				systemExit();
			}


			@Override
			public void windowDeactivated(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowIconified(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowOpened(WindowEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		this.setIconImage(R.ICON);
		// TODO : change this 
		
		// initializing components 
		movieListPan = new MovieListPan(new ListPanWatcher(){

			public void ListActionPerformed(YifyMovie movie, int action) {
				// TODO Auto-generated method stub

					// TODO Auto-generated method stub
					System.out.println("Action performed on "+movie.getTitle()+" Action value is : "+action);
					if (action == MovieItem.PLAY_ACTION){
						initPlayerView();
						player.setTorrent(new YifyMovieTorrent(movie ,YifyMovieTorrent.USE_DEFAULT_SETTINGS));
					} else if (action == MovieItem.LATER_ACTION) {
						//initPlayerView();
						//player.setYoutube(movie.getYoutubeTrailerURL());
						try {
							DesktopUtils.openWebpage(new URL(movie.getYoutubeTrailerURL()));
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						
						}
					}
				}
			
			
		});
		
		// window properties 
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(1000, 600);
		this.setMinimumSize(new Dimension(1000, 600));
		this.setLocation(new Point((screenWidth - this.getWidth()) / 2,
				(screenHeight - this.getHeight()) / 2));
		this.setVisible(true);
		this.setTitle(Strings.getTitle() + Strings.getVersion());
		
		
		// layouts 
		contentPan.setLayout(new BorderLayout());
		
		
		this.setContentPane(contentPan);
		initMovieListPan();

		// menu bar 
		mainMenu.add(fileMenu);
		//fileMenu.add(openMenu);
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
		playerViewMenuItem.addActionListener(new MenuActionListener());
		MovieListViewMenuItem.addActionListener(new MenuActionListener());
		exitMenuItem.addActionListener(new MenuActionListener());
	}
	
	protected void systemExit() {
		// TODO Auto-generated method stub
		MediaPlayer.getMediaplayerfactory().release();
		System.exit(0);
		
	}

	public void initMovieListPan() {
	
		try {
			player.getMediaPlayerComponent().getMediaPlayer().pause();
			player.getMediaPlayerComponent().getMediaPlayer().stop();
			player.getMediaPlayerComponent().getMediaPlayer().setFullScreen(false);
			
			player.torrent.stopTorrent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.getContentPane().removeAll();
		this.setContentPane(contentPan);
		contentPan.add(movieListPan,BorderLayout.CENTER);
		this.setTitle("Cupcake "+ "Movie list ");
		contentPan.revalidate();
		playerViewMenuItem.setEnabled(true);
		MovieListViewMenuItem.setEnabled(false);
	}
	
	public void initPlayerView(){
		this.getContentPane().removeAll();
		this.setContentPane(contentPan);
		//player = new MediaPlayer(this);
		contentPan.add(player, BorderLayout.CENTER);
		this.setTitle("Cupcake ");
		player.revalidate();
		contentPan.revalidate();
		playerViewMenuItem.setEnabled(false);
		MovieListViewMenuItem.setEnabled(true);
	}
	
	

	class MenuActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			Object source = arg0.getSource();
			if(source.equals(playerViewMenuItem)){
				initPlayerView();
			} else if (source.equals(MovieListViewMenuItem)){
				initMovieListPan();
			} else if (source.equals(exitMenuItem)){
				systemExit();
			}
		}
		
	}
}
