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
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.lordroid.cupcake.bt.YifyMovieTorrent;
import com.lordroid.cupcake.controlers.ListPanWatcher;
import com.lordroid.cupcake.player.MediaPlayer;
import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.Strings;
import com.lordroid.cupcake.yify.YifyMovie;

public class MainFram extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPan = new JPanel();
	private MovieListPan movieListPan ;
	private final MediaPlayer player = new MediaPlayer(this);
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
				MediaPlayer.getMediaplayerfactory().release();
				System.exit(0);
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
		
	}
	
	public void initMovieListPan() {
		this.getContentPane().removeAll();
		this.setContentPane(contentPan);
		contentPan.add(movieListPan,BorderLayout.CENTER);
		contentPan.revalidate();
	}
	
	public void initPlayerView(){
		this.getContentPane().removeAll();
		this.setContentPane(contentPan);
		contentPan.add(player, BorderLayout.CENTER);
		contentPan.revalidate();
	}
	
	


}
