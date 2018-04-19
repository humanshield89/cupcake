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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.lordroid.cupcake.controlers.ListPanWatcher;
import com.lordroid.cupcake.controlers.WatchableListPan;
import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.utils.GaussianFilter;
import com.lordroid.cupcake.utils.TimeUtils;
import com.lordroid.cupcake.yify.YifyMovie;

@SuppressWarnings("serial")
public class MovieItem extends JPanel implements MouseListener,
		WatchableListPan, ActionListener {
	public static final int PLAY_ACTION = 0;
	public static final int DETAILS_ACTION = 2;
	public static final int LATER_ACTION = 1;
	ArrayList<ListPanWatcher> watchersList = new ArrayList<ListPanWatcher>();
	MovieItem thisPan;
	private final YifyMovie movie;

	/**
	 * @return the movie
	 */
	public YifyMovie getMovie() {
		return movie;
	}

	/**
	 * @return the itemButtons
	 */
	public MovieItemButton[] getItemButtons() {
		return itemButtons;
	}

	private BufferedImage coverImage;
	private boolean isImageCached = false;
	private BufferedImage blurryImage;
	private boolean isBluredCached = false;
	private boolean selected = false;
	private JPanel infoPan = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			BufferedImage img = null;
			BufferedImage img2 = null;
			try {
				if (selected) {
					img = blurryImage.getSubimage(0, 145, 230, 200);
					img2 = R.BORDER_IMAGE.getSubimage(0, 145, 230, 200);
				} else
					img = coverImage.getSubimage(0, infoPanY, 230, 200);
			} catch (Exception e) {

			}
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g2d.setColor(new Color(0, 0, 0, 150));

			if (selected) {
				g2d.drawImage(img, 0, 0, this);

			}
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
			if (selected)
				g2d.drawImage(img2, 0, 0, this);

		}
	};

	private final int infoPanY;
	private JLabel titleLab = new JLabel();
	private JLabel imdbIcon = new JLabel(new ImageIcon(R.IMDB_ICON));
	private JLabel viewsIcon = new JLabel(new ImageIcon(R.VIEWS_ICON));
	private JLabel imdbRatingLab = new JLabel();
	private JLabel viewCountLab = new JLabel();
	private JLabel genreLAb = new JLabel(new ImageIcon(R.GENRE_ICON));
	private JLabel genreText = new JLabel();

	private JLabel descriText = new JLabel();
	private JLabel lab720p = new JLabel(new ImageIcon(R.QUALITY_720P));
	private JLabel lab1080p = new JLabel(new ImageIcon(R.QUALITY_1080P));
	private JLabel lab3d = new JLabel(new ImageIcon(R.QUALITY_3D));

	private MovieItemButton[] itemButtons = { new MovieItemButton(0),
			new MovieItemButton(1), new MovieItemButton(2) };

	private final int infoPanHoveredY;

	public MovieItem(YifyMovie moviearg) {
		thisPan = this;
		this.movie = moviearg;
		try {
			coverImage = ImageIO.read(movie.getCoverImageFileMedium());
			this.isImageCached = true;
		} catch (Exception e) {
			this.isImageCached = false;
			try {
				coverImage = ImageIO.read(Thread.currentThread()
						.getContextClassLoader()
						.getResourceAsStream("default_cover_medium.jpg"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		if (isImageCached) {
			blurryImage = new GaussianFilter(10)
					.filter(coverImage, blurryImage);
			isBluredCached = true;
		}
		this.setSize(230, 345);
		this.setPreferredSize(new Dimension(230, 345));
		this.setMinimumSize(new Dimension(230, 345));
		this.infoPan.setSize(230, 200);
		this.infoPan.setBounds(0, this.getHeight() - 71, 230, 200);
		infoPan.setLayout(null);

		titleLab.setText("<html><h3 style='text-align: center;'><u>"
				+ movie.getTitleLong() + "</u></h3></html>");
		titleLab.setForeground(Color.WHITE);
		titleLab.setFont(R.MOVIE_TITLE_FONT);
		titleLab.setBounds(2, 2, 228, 45);
		titleLab.setHorizontalAlignment(SwingConstants.CENTER);
		titleLab.setVerticalAlignment(SwingConstants.CENTER);

		imdbIcon.setBounds(12, 49, 60, 20);
		imdbRatingLab.setText("<html><b>" + movie.getRating() + "</b></html>");
		imdbRatingLab.setForeground(Color.WHITE);
		imdbRatingLab.setBounds(50, 49, 20, 20);

		this.viewsIcon.setBounds(88, 49, 60, 20);
		this.viewCountLab.setText(TimeUtils.getFormatedViewCount(movie
				.getDownloadCount()));
		viewCountLab.setFont(R.VIEWS_FONT);
		this.viewCountLab.setForeground(Color.WHITE);
		this.viewCountLab.setBounds(113, 49, 32, 20);

		this.genreLAb.setBounds(160, 49, 60, 20);
		this.genreText.setBounds(165, 49, 50, 20);
		genreText.setText(movie.getGenre().get(0));
		genreText.setForeground(Color.WHITE);
		genreText.setFont(R.GENRE_FONT);
		descriText.setBounds(5, 71, 220, 93);
		descriText.setForeground(Color.WHITE);
		String sum = movie.getDescriptionShort();
		if (sum.length() > 200) {
			sum = sum.substring(0, 199);
			sum.substring(0, sum.lastIndexOf(" "));
		}
		descriText.setText("<html><p>" + sum + "...</p></html>");
		descriText.setFont(R.DESCRIPTION_FONT);

		// TODO :
		this.lab720p.setBounds(12, 166, 60, 20);

		this.lab1080p.setBounds(84, 166, 60, 20);

		this.lab3d.setBounds(156, 166, 60, 20);

		infoPan.add(titleLab);
		infoPan.add(imdbIcon);
		infoPan.add(imdbRatingLab);
		infoPan.add(viewsIcon);
		infoPan.add(this.viewCountLab);
		infoPan.add(this.genreLAb);
		infoPan.add(this.genreText);
		infoPan.add(descriText);
		if (movie.isHas720p())
			infoPan.add(this.lab720p);
		if (movie.isHas1080p())
			infoPan.add(this.lab1080p);
		if (movie.isHas3d())
			infoPan.add(this.lab3d);

		infoPanY = this.getHeight() - 71;
		infoPanHoveredY = 145;

		// buttons
		itemButtons[0].setBounds(15, 10, 200, 35);
		itemButtons[1].setBounds(15, 55, 200, 35);
		itemButtons[2].setBounds(15, 100, 200, 35);

		this.setLayout(null);
		this.add(infoPan);
		for (MovieItemButton btn : itemButtons) {
			this.add(btn);
			btn.setVisible(false);
			btn.addMouseListener(this);
		}

		this.addMouseListener(this);
		initToolTip();
		initActions();
		// TODO : conditional if enabled in settings
		loadExtra();
	}

	private void initActions() {
		for (MovieItemButton b : itemButtons) {
			b.addActionListener(this);
		}

	}

	private void loadExtra() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				movie.loadExtras();
				viewCountLab.setText(TimeUtils.getFormatedViewCount(movie
						.getDownloadCount()));
			}

		}).start();
	}

	private void initToolTip() {

		// TODO find some why to work around the flikering cause by mouse
		// hovered over this components
		// being reported to the panel as exited
		// this.descriText.setToolTipText("Movie short description");
		// descriText.addMouseListener(this);
		// this.genreText.setToolTipText("Movie genre ");
		// genreText.addMouseListener(this);
		// this.imdbIcon.setToolTipText("IMDB rating ");
		// imdbIcon.addMouseListener(this);
		// this.viewsIcon.setToolTipText("Number of views based on torrent download count");
		// viewsIcon.addMouseListener(this);
		// this.lab1080p.setToolTipText("This movie is available in 1080p quality");
		// lab1080p.addMouseListener(this);
		// this.lab720p.setToolTipText("This movie is available in 720p quality");
		// lab720p.addMouseListener(this);
		// this.lab3d.setToolTipText("This movie is available in 3D");
		// lab3d.addMouseListener(this);

	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (!this.isImageCached) {
			try {
				coverImage = ImageIO.read(movie.getCoverImageFileMedium());
				isImageCached = true;
			} catch (Exception e) {
				isImageCached = false;

				e.printStackTrace();
			}
		}
		if (isImageCached) {
			if (!isBluredCached) {
				blurryImage = new GaussianFilter(10).filter(coverImage,
						blurryImage);
				isBluredCached = true;
			}
		}

		if (!selected) {
			g2d.drawImage(coverImage, 0, 0, this);
		} else {
			g2d.drawImage(blurryImage, 0, 0, this);
			g2d.drawImage(R.BORDER_IMAGE, 0, 0, this);
		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		this.updateSelectionStatus(this);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		if (arg0.getSource().equals(itemButtons[0])
				|| arg0.getSource().equals(itemButtons[1])
				|| arg0.getSource().equals(itemButtons[1])
		/*
		 * || arg0.getSource().equals(descriText) ||
		 * arg0.getSource().equals(this.lab1080p) ||
		 * arg0.getSource().equals(this.lab720p) ||
		 * arg0.getSource().equals(this.lab3d) ||
		 * arg0.getSource().equals(genreText) ||
		 * arg0.getSource().equals(this.imdbIcon) ||
		 * arg0.getSource().equals(viewsIcon)
		 */) {

			return;
		}

		setSelected(false);

		infoPan.repaint();
		repaint();
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		if (selected) {
			infoPan.setBounds(infoPan.getBounds().x, infoPanHoveredY, 230, 200);
			for (MovieItemButton btn : itemButtons)
				btn.setVisible(true);
			infoPan.repaint();
			repaint();
		} else {
			infoPan.setBounds(0, infoPanY, 230, 200);
			for (MovieItemButton btn : itemButtons)
				btn.setVisible(false);
			infoPan.repaint();
			repaint();
		}
	}

	public boolean isSelected() {
		return selected;
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public MovieItem addListPanWatcher(ListPanWatcher lw) {
		this.watchersList.add(lw);
		return this;
	}

	@Override
	public void removeListPanWatcher(ListPanWatcher lw) {
		this.watchersList.remove(lw);
	}

	@Override
	public void updateListWatchers(int action) {
		for (ListPanWatcher w : watchersList) {
			w.ListActionPerformed(this.movie, action);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		MovieItemButton source = (MovieItemButton) arg0.getSource();
		if (source.equals(itemButtons[0])) {
			this.updateListWatchers(PLAY_ACTION);
		} else if (source.equals(itemButtons[1])) {
			this.updateListWatchers(LATER_ACTION);
		} else if (source.equals(itemButtons[2])) {
			this.updateListWatchers(DETAILS_ACTION);
		}
	}

	@Override
	public void updateSelectionStatus(MovieItem movie) {
		// TODO Auto-generated method stub
		for (ListPanWatcher w : watchersList) {
			w.ItemSelected(movie);
		}
	}
}
