package com.lordroid.cupcake.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.alee.laf.button.WebButton;
import com.alee.laf.combobox.WebComboBox;
import com.alee.laf.panel.WebPanel;
import com.lordroid.cupcake.App;
import com.lordroid.cupcake.controlers.ListPanWatcher;
import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.Settings;
import com.lordroid.cupcake.yify.JSONArray;
import com.lordroid.cupcake.yify.JSONComunicator;
import com.lordroid.cupcake.yify.JSONException;
import com.lordroid.cupcake.yify.JSONObject;
import com.lordroid.cupcake.yify.YifyMovie;
import com.lordroid.cupcake.yify.YifyS;

@SuppressWarnings("serial")
public class MovieListPan extends JPanel implements ActionListener,
		KeyListener, ListPanWatcher {
	public static final String SEARHC_DEFAULT_TEXT = "Search Title/imdbCode ...";
	private final ListPanWatcher listWatcher;
	private ArrayList<MovieItem> moviesList = new ArrayList<MovieItem>();
	private int currentlySelected = -999;
	// JPanel mainContainer = new JPa
	private WebPanel seachPanContainer = new WebPanel() {
		// public void paintComponent(Graphics g) {
		// int i = 0;
		// int n = 0;
		// while (n < this.getHeight()) {
		// i = 0;
		// while (i < this.getWidth()) {
		// g.drawImage(R.SEARCH_BACKGROUND_IMG, i, n, this);
		// i = i + R.SEARCH_BACKGROUND_IMG.getWidth(this);
		// }
		// n = n + R.SEARCH_BACKGROUND_IMG.getHeight(this);
		// }
		// }
	};
	// sort by components
	private JLabel sortByLabel = new JLabel("Sort by : ");
	private WebComboBox sortByCombo = new WebComboBox(
			Settings.SORT_BY_COMBO_ARRAY);
	// order components
	private JLabel orderLab = new JLabel("Order : ");
	private JComboBox<String> orderCombo = new JComboBox<String>(
			Settings.ORDER_COMBO);

	// minimum ratting components
	private JLabel minimumRatingLab = new JLabel("Rating : ");
	private JComboBox<String> minRatingCombo = new JComboBox<String>(
			Settings.MINIMUM_RATING_COMBO_ARRAY);
	// genre component
	private JLabel genreLab = new JLabel("Genre : ");
	private JComboBox<String> genreCombo = new JComboBox<String>(
			Settings.GENRES_COMBO_ARRAY);
	// quality filter components
	private JLabel qualityLab = new JLabel("Quality : ");
	private JComboBox<String> qualityCombo = new JComboBox<String>(
			Settings.QUALITY_COMBO);

	private JTextField searchField = new JTextField();
	private WebButton searchBtn = new WebButton("Search");

	private JPanel movieListContainer = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int i = 0;
			int n = 0;
			while (n < this.getHeight()) {
				i = 0;
				while (i < this.getWidth()) {
					g.drawImage(R.LIST_BACKGROUND_IMG, i, n, this);
					i = i + R.LIST_BACKGROUND_IMG.getWidth(this);
				}
				n = n + R.LIST_BACKGROUND_IMG.getHeight(this);
			}
		}
	};
	private JScrollPane scrollPan;

	private LoadMoreLab loadMoreBtn = new LoadMoreLab();

	private JLabel noResult = new JLabel(
			"No results found try with diferent search options");
	private JLabel noResponse = new JLabel(
			"Couldn't fetch data from remote server please check your connection");
	private JLabel noMoreentriesLab = new JLabel(
			"No more entries to show /End of list ");

	private int page;

	private int quality;

	private int sortBy;

	private int genre;

	private String term;

	private int minRating;

	private int maxPage;

	private int order;

	public MovieListPan(ListPanWatcher watcher) {

		this.listWatcher = watcher;
		seachPanContainer.setLayout(new FlowLayout());

		genreLab.setForeground(Color.WHITE);
		minimumRatingLab.setForeground(Color.WHITE);
		qualityLab.setForeground(Color.WHITE);
		sortByLabel.setForeground(Color.WHITE);
		orderLab.setForeground(Color.WHITE);
		// initialize default or previously selected values
		sortByCombo.setSelectedIndex(Settings.getCurrentSortBy());
		minRatingCombo.setSelectedIndex(Settings.getCurrentMinimumRating());
		genreCombo.setSelectedIndex(Settings.getCurrentGenre());
		qualityCombo.setSelectedIndex(Settings.getCurrentQuality());
		orderCombo.setSelectedIndex(Settings.getCurrentOrder());

		sortByCombo.setFocusable(false);
		minRatingCombo.setFocusable(false);
		genreCombo.setFocusable(false);
		qualityCombo.setFocusable(false);
		orderCombo.setFocusable(false);
		sortByCombo.setFocusable(false);
		searchField.setFocusable(false);
		searchBtn.setFocusable(false);

		searchField.setText(SEARHC_DEFAULT_TEXT);
		searchField.setPreferredSize(new Dimension(148, 23));

		this.setLayout(new BorderLayout());

		// search container init
		seachPanContainer.add(genreLab);
		seachPanContainer.add(genreCombo);
		seachPanContainer.add(minimumRatingLab);
		seachPanContainer.add(minRatingCombo);
		seachPanContainer.add(qualityLab);
		seachPanContainer.add(qualityCombo);
		seachPanContainer.add(sortByLabel);
		seachPanContainer.add(sortByCombo);
		seachPanContainer.add(orderLab);
		seachPanContainer.add(orderCombo);
		seachPanContainer.add(searchField);
		seachPanContainer.add(searchBtn);

		// movie list container init
		movieListContainer.setOpaque(false);
		movieListContainer.setLayout(new ModifiedFlowLayout(
				FlowLayout.CENTER, 10, 20));
		scrollPan = new JScrollPane(movieListContainer);
		this.scrollPan.setOpaque(false);
		scrollPan.getVerticalScrollBar().setUnitIncrement(20);
		this.add(this.seachPanContainer, BorderLayout.NORTH);
		this.add(scrollPan, BorderLayout.CENTER);

		// scrollPan.setF
		// actionListeners
		this.searchBtn.addActionListener(this);
		this.loadMoreBtn.addActionListener(this);
		searchField.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (searchField.getText().equals(SEARHC_DEFAULT_TEXT)) {
					searchField.setText("");
				} else if (e.getClickCount() == 2) {
					searchField.selectAll();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		// TODO : change this load a blank pannel with the frame size
		// add a status panel on the south and display loading ... or something
		// like contacting
		movieListContainer.add(new JPanel());
		loadMoreBtn.addKeyListener(this);
		this.actionPerformed(new ActionEvent(searchBtn,
				ActionEvent.ACTION_PERFORMED, "search"));
		this.addKeyListener(this);
		this.requestFocus();
		this.requestFocusInWindow();
	}

	@Override
	public boolean isFocusTraversable() {
		return true;
	}

	private void getNextPage() {
		this.searchBtn.setEnabled(false);
		// TODO Auto-generated method stub
		movieListContainer.remove(loadMoreBtn);
		currentlySelected--;
		page++;
		String url = JSONComunicator.getJsonQueryUrl(page, quality, minRating,
				term, genre, sortBy, order);
		App.LOGGER.info(url);
		// let's JSON
		JSONObject obj = null;
		JSONObject data = null;
		JSONArray movies = null;

		try {
			obj = JSONComunicator.readJsonFromUrl(url);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			movieListContainer.add(this.noResponse);
			this.searchBtn.setEnabled(true);
			return;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			noResponse();
			return;
		}
		if (obj != null) {
			data = obj.getJSONObject(YifyS.RESPONSE_DATA_KEY);
		} else {
			// TODO : something went wrong handle it
			noResponse();
			return;
		}

		if (data != null) {
			try {
				movies = data.getJSONArray("movies");
			} catch (JSONException e) {
				e.printStackTrace();

				noResults();
				return;
			}
		} else {
			// TODO : something went wrong handle it
			noResults();
			return;
		}
		if (movies != null && movies.length() > 0) {
			// TODO : keep track of elements ? a List maybe
			for (int i = 0; i < movies.length(); i++) {
				this.addMovieItem((JSONObject) movies.get(i));
				movieListContainer.revalidate();
				// JScrollBar vertical = scrollPan.getVerticalScrollBar();
				// vertical.setValue(vertical.getMaximum());

			}

			if (page < maxPage) {
				// loadMoreBtn.setFont(R.LOAD_MORE_FONT);
				// loadMoreBtn.setPreferredSize(new Dimension(
				// this.getWidth() - 30, 40));
				movieListContainer.add(loadMoreBtn);
			}
		} else {
			// TODO : something went wrong handle it

		}
		this.searchBtn.setEnabled(true);
		this.requestFocus();

	}

	private void addMovieItem(JSONObject obj) {
		MovieItem mv = new MovieItem(new YifyMovie(obj));
		mv.addListPanWatcher(listWatcher);
		mv.addListPanWatcher(this);
		movieListContainer.add(new MovieShadowPan(mv));
		moviesList.add(mv);
	}

	private void noResults() {
		// TODO Auto-generated method stub
		movieListContainer.add(this.noResult);
		this.searchBtn.setEnabled(true);
	}

	private void noResponse() {
		// TODO Auto-generated method stub
		movieListContainer.add(this.noResponse);
		this.searchBtn.setEnabled(true);
	}

	private void noMoreEntries() {
		// TODO Auto-generated method stub
		noMoreentriesLab.setPreferredSize(new Dimension(movieListContainer
				.getWidth() / 2, 40));
		noMoreentriesLab.setFont(new Font("Aril", Font.BOLD, 25));
		movieListContainer.add(noMoreentriesLab);
	}

	public void search() {
		// TODO Auto-generated method stub
		this.searchBtn.setEnabled(false);
		movieListContainer.removeAll();
		this.moviesList = new ArrayList<MovieItem>();
		page = 1;
		quality = this.qualityCombo.getSelectedIndex();
		sortBy = this.sortByCombo.getSelectedIndex();
		genre = this.genreCombo.getSelectedIndex();
		term = this.searchField.getText();
		minRating = this.minRatingCombo.getSelectedIndex();
		order = this.orderCombo.getSelectedIndex();
		
//		// Only if settings are set 
		if(Settings.getRememberFiltersOnStartup() == 0){
		Settings.setCurrentQuality(quality);
		Settings.setCurrentSortBy(sortBy);
		Settings.setCurrentGenre(genre);
		Settings.setCurrentminRating(minRating);
		Settings.setCurrentOrder(order);
		}
		
		String url = JSONComunicator.getJsonQueryUrl(page, quality, minRating,
				term, genre, sortBy, order);
		App.LOGGER.info(url);
		// let's JSON
		JSONObject obj = null;
		JSONObject data = null;
		JSONArray movies = null;

		try {
			obj = JSONComunicator.readJsonFromUrl(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			movieListContainer.add(this.noResponse);
			this.searchBtn.setEnabled(true);
			return;
		}
		if (obj != null) {
			data = obj.getJSONObject(YifyS.RESPONSE_DATA_KEY);
			// maxPage = obj.getJSONObject("");
		} else {
			// TODO : something went wrong handle it
			movieListContainer.add(this.noResponse);
			this.searchBtn.setEnabled(true);
			return;
		}

		if (data != null) {
			try {
				maxPage = data.getInt("movie_count")
						/ Settings.MAX_RESULT_PER_SEARCH[Settings.getMaxSearchItemsPerPage()];
				movies = data.getJSONArray("movies");
			} catch (JSONException e) {
				movieListContainer.add(this.noResult);
				this.searchBtn.setEnabled(true);
				return;
			}
		} else {
			// TODO : something went wrong handle it

		}
		if (movies != null && movies.length() > 0) {
			for (int i = 0; i < movies.length(); i++) {

				this.addMovieItem((JSONObject) movies.get(i));
				movieListContainer.revalidate();

			}
			if (page < maxPage && maxPage != 0) {
				movieListContainer.add(loadMoreBtn);
			} else {
				noMoreEntries();
			}
		} else {
			// TODO : something went wrong handle it

		}
		this.searchBtn.setEnabled(true);
		this.currentlySelected = -999;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource().equals(searchBtn)) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					search();
				}

			}).start();
		} else if (event.getSource().equals(loadMoreBtn)) {
			
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					loadMoreBtn.setVisible(false);
					getNextPage();
					loadMoreBtn.setVisible(true);
				}

			}).start();
		}
	}

	private int getNumberOfItemInRow() {
		int width = movieListContainer.getWidth();
		// int height = movieListContainer.getHeight();

		return width / 260;

	}

	private void NavigateTo(int increment) {
		// TODO : maybe there is better ways to do this my man
		int inc = increment;
		if (this.currentlySelected == -999) {
			currentlySelected = 0;
			inc = 0;
		}
		if (currentlySelected + inc >= moviesList.size()
				|| currentlySelected + inc < 0) {
			if (currentlySelected + inc >= moviesList.size()) {
				if (currentlySelected < moviesList.size())
					moviesList.get(currentlySelected).setSelected(false);
				loadMoreBtn.setSelected(true);
				loadMoreBtn.requestFocus();
				currentlySelected = moviesList.size();
			}
		} else {
			if (currentlySelected < moviesList.size())
				moviesList.get(currentlySelected).setSelected(false);
			else
				loadMoreBtn.setSelected(false);

			currentlySelected = currentlySelected + inc;
			moviesList.get(currentlySelected).setSelected(true);
			this.requestFocus();
		}
		double rowsPerView = (scrollPan.getViewport().getHeight() / 385.0) - 1;
		JScrollBar vertical = scrollPan.getVerticalScrollBar();
		int newValue = (int) ((((double) (currentlySelected / getNumberOfItemInRow())) * 385) - (385 * rowsPerView) / 2);
		vertical.setValue(newValue);
		// TODO
		App.LOGGER.info("currently sellected is "+currentlySelected +"  Max index is "+moviesList.size());

	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub
		App.LOGGER.info("Key Event   : " + arg0.getKeyCode());
		if (arg0.getKeyCode() == KeyEvent.VK_UP) {
			App.LOGGER.info("Arrow key up presssed ");
			NavigateTo(-getNumberOfItemInRow());

		} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
			App.LOGGER.info("Arrow key down presssed ");
			NavigateTo(getNumberOfItemInRow());

		} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
			App.LOGGER.info("Arrow key right presssed ");
			NavigateTo(1);

		} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
			App.LOGGER.info("Arrow key left presssed ");
			NavigateTo(-1);
		} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
			if(currentlySelected < moviesList.size())
			listWatcher.ListActionPerformed(moviesList.get(currentlySelected).getMovie() , Settings.getDefaultEnterOperation());
			if(currentlySelected ==  moviesList.size()) {
				this.actionPerformed(new ActionEvent(loadMoreBtn,
						ActionEvent.ACTION_PERFORMED, "update"));
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void ListActionPerformed(YifyMovie movie, int action) {
		// TODO Auto-generated method stub
		// DO nothing here
	}

	@Override
	public void ItemSelected(MovieItem movieItem) {
		// TODO Auto-generated method stub
		try {
			moviesList.get(currentlySelected).setSelected(false);
		} catch (Exception e) {

		}
		currentlySelected = moviesList.indexOf(movieItem);
		movieItem.setSelected(true);
		// TODO : switch to debug on release
		App.LOGGER.info("currently sellected is "+currentlySelected +"  Max index is "+moviesList.size());
	}

}
