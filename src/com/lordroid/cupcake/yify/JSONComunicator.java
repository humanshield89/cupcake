package com.lordroid.cupcake.yify;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.UnknownHostException;

import javax.net.ssl.HttpsURLConnection;


import com.lordroid.cupcake.res.Settings;
import com.lordroid.cupcake.ui.MovieListPan;

public class JSONComunicator {
	private static final String API_URL = "https://yts.am/api/v2/list_movies.json?";

	private static String readAll(BufferedReader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = rd.readLine()) != null) {
			sb.append(line);
		}
		
		return sb.toString();
	}

	public static JSONObject readJsonFromUrl(String url) throws IOException,
			JSONException, Exception {
		// InputStream is = new URL(url).openStream();
		URL u = new URL(url);
		HttpsURLConnection c = (HttpsURLConnection) u.openConnection();
		c.setRequestMethod("GET");
		c.setRequestProperty("Content-length", "0");
		c.setUseCaches(false);
		c.setAllowUserInteraction(false);
		c.setConnectTimeout(1000);
		c.setReadTimeout(5000);
		try {
			c.connect();
		} catch (UnknownHostException e) {
			return null;
		}
		InputStream is = c.getInputStream();

		try {
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String jsonText = readAll(rd);
			JSONObject json = new JSONObject(jsonText);
			is.close();
			c.disconnect();
			return json;
		} finally {
			is.close();
		}
	}

	public static String getJsonQueryUrl(int page, int quality,
			int minimumRating, String queryTerm, int genre, int sortBy,
			int order) {
		int limit = Settings.MAX_RESULT_PER_SEARCH[Settings
				.getMaxSearchItemsPerPage()];
		String query = API_URL + YifyS.REQUEST_LIMIT_KEY + limit + "&"
				+ YifyS.REQUEST_PAGE_KEY + page + "&"
				+ YifyS.REQUEST_MINIM_RATING_KEY + minimumRating + "&"
				+ YifyS.REQUEST_SORT_BY_KEY
				+ Settings.SORT_BY_KEY_ARRAY[sortBy] + "&"
				+ YifyS.REQUEST_ORDER_BY_KEY + Settings.ORDER_KEYS[order];

		if (quality != 0) {
			// if user chose a quality add it to the url
			query = query + "&" + YifyS.REQUEST_QUALITY_KEY
					+ Settings.QUALITY_COMBO[quality];
		}

		if (genre != 0) {
			// add it the the query url
			query = query + "&" + YifyS.REQUEST_GENRE_KEY
					+ Settings.GENRES_COMBO_ARRAY[genre];
		}
		if (!queryTerm.equals(null) && !queryTerm.equals("")
				&& !queryTerm.equals(MovieListPan.SEARHC_DEFAULT_TEXT)) {
			// add query term
			String queryTermFormated = "";
			for (int i = 0; i < queryTerm.length(); i++) {
				if (queryTerm.charAt(i) == ' ') {
					queryTermFormated = queryTermFormated + "+";
				} else {
					queryTermFormated = queryTermFormated + queryTerm.charAt(i);
				}
			}
			query = query + "&" + YifyS.REQUEST_QUERY_TERM_KEY
					+ queryTermFormated;
		}
		return query;

	}

}
