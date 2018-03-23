package com.lordroid.cupcake.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.lordroid.cupcake.App;
import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.Settings;
import com.lordroid.cupcake.yify.JSONArray;
import com.lordroid.cupcake.yify.JSONComunicator;
import com.lordroid.cupcake.yify.JSONException;
import com.lordroid.cupcake.yify.JSONObject;
import com.lordroid.cupcake.yify.YifyMovie;
import com.lordroid.cupcake.yify.YifyS;

public class MovieListPan extends JPanel implements ActionListener {
	public static final String SEARHC_DEFAULT_TEXT = "Search Title/imdbCode ...";

	private ArrayList<YifyMovie> moviesList = new ArrayList<YifyMovie>();

	// JPanel mainContainer = new JPa
	private JPanel seachPanContainer = new JPanel();
	// sort by components
	private JLabel sortByLabel = new JLabel("Sort by : ");
	private JComboBox<String> sortByCombo = new JComboBox<String>(
			Settings.SORT_BY_COMBO_ARRAY);
	//order components
	private JLabel orderLab = new JLabel("Order : ");
	private JComboBox<String> orderCombo = new JComboBox<String>(Settings.ORDER_COMBO);
	
	// minimum ratting components
	private JLabel minimumRatingLab = new JLabel("rating : ");
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
	private JButton searchBtn = new JButton("Search");

	private JPanel movieListContainer = new JPanel();
	private JScrollPane scrollPan;

	private JButton loadMoreBtn = new JButton("Load more");

	private JLabel noResult = new JLabel("No results found try with diferent search options");
	private JLabel noResponse = new JLabel("Couldn't fetch data from remote server please check your connection");
	private JLabel noMoreentriesLab = new JLabel("No more entrie to show /End of list ");
	
	private int page;

	private int quality;

	private int sortBy;

	private int genre;

	private String term;

	private int minRating;

	private int maxPage;

	private int order;
	public MovieListPan() {
		// initialize default or previously selected values
		sortByCombo.setSelectedIndex(Settings.getCurrentMinimumRating());
		minRatingCombo.setSelectedIndex(Settings.getCurrentMinimumRating());
		genreCombo.setSelectedIndex(Settings.getCurrentGenre());
		qualityCombo.setSelectedIndex(Settings.getCurrentQuality());
		orderCombo.setSelectedIndex(Settings.getCurrentOrder());
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
		movieListContainer.setLayout(new ModifiedFlowLayout());
		scrollPan = new JScrollPane(movieListContainer);
		this.scrollPan.setOpaque(false);
		movieListContainer.setBackground(Color.BLACK);
		scrollPan.setBackground(Color.GRAY);
		scrollPan.getVerticalScrollBar().setUnitIncrement(20);
		seachPanContainer.setBorder(BorderFactory
				.createTitledBorder("Search Options"));

		this.add(this.seachPanContainer, BorderLayout.NORTH);
		this.add(scrollPan, BorderLayout.CENTER);

		// actionListeners
		this.searchBtn.addActionListener(this);
		this.loadMoreBtn.addActionListener(this);
		searchField.addMouseListener(new MouseListener(){

			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if (searchField.getText().equals(SEARHC_DEFAULT_TEXT)){
					searchField.setText("");
				} else if (e.getClickCount() == 2){
					searchField.selectAll();
				}
			}

			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		// search ();
	}

	private void getNextPage() {
		this.searchBtn.setEnabled(false);
		// TODO Auto-generated method stub
		movieListContainer.remove(loadMoreBtn);
		page++;
		String url = JSONComunicator.getJsonQueryUrl(page, quality, minRating,
				term, genre, sortBy,order);
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
				movieListContainer.add(new MovieItem(new YifyMovie(
						(JSONObject) movies.get(i))));
				movieListContainer.revalidate();
				JScrollBar vertical = scrollPan.getVerticalScrollBar();
				vertical.setValue( vertical.getMaximum() );

			}

			if (page < maxPage) {
				loadMoreBtn.setFont(R.LOAD_MORE_FONT);
				loadMoreBtn.setPreferredSize(new Dimension(
						this.getWidth() - 30, 40));
				movieListContainer.add(loadMoreBtn);
			}
		} else {
			// TODO : something went wrong handle it
			
		}
		this.searchBtn.setEnabled(true);

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
		noMoreentriesLab.setPreferredSize(new Dimension(movieListContainer.getWidth()/2,40));
		noMoreentriesLab.setFont(new Font("Aril",Font.BOLD,25));
		movieListContainer.add(noMoreentriesLab);
	}
	
	public void search() {
		// TODO Auto-generated method stub
		this.searchBtn.setEnabled(false);
		movieListContainer.removeAll();
		page = 1;
		quality = this.qualityCombo.getSelectedIndex();
		sortBy = this.sortByCombo.getSelectedIndex();
		genre = this.genreCombo.getSelectedIndex();
		term = this.searchField.getText();
		minRating = this.minRatingCombo.getSelectedIndex();
		order = this.orderCombo.getSelectedIndex();
		Settings.setCurrentQuality(quality);
		Settings.setCurrentSortBy(sortBy);
		Settings.setCurrentGenre(genre);
		Settings.setCurrentminRating(minRating);
		Settings.setCurrentOrder(order);
		String url = JSONComunicator.getJsonQueryUrl(page, quality, minRating,
				term, genre, sortBy,order);
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			movieListContainer.add(this.noResponse);
			this.searchBtn.setEnabled(true);
			return;
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
						/ Settings.getMaxSearchItemsPerPage();
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

				movieListContainer.add(new MovieItem(new YifyMovie(
						(JSONObject) movies.get(i))));
				movieListContainer.revalidate();

			}
			if (page < maxPage && maxPage != 0) {
				loadMoreBtn.setFont(R.LOAD_MORE_FONT);
				loadMoreBtn.setPreferredSize(new Dimension(
						this.getWidth() - 30, 40));
				movieListContainer.add(loadMoreBtn);
			} else {
				noMoreEntries();
			}
		} else {
			// TODO : something went wrong handle it

		}
		this.searchBtn.setEnabled(true);
	}



	public void actionPerformed(ActionEvent event) {
		// TODO Auto-generated method stub
		if (event.getSource().equals(searchBtn)) {
			new Thread(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					search();
				}

			}).start();
		} else if (event.getSource().equals(loadMoreBtn)) {
			new Thread(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					getNextPage();
				}

			}).start();
		}
	}

}
