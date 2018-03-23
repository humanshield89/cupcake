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
package com.lordroid.cupcake;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import com.lordroid.cupcake.player.ControlPanel;
import com.lordroid.cupcake.utils.HttpsDownloadUtility;
import com.lordroid.cupcake.utils.PathUtils;
import com.lordroid.cupcake.yify.JSONArray;
import com.lordroid.cupcake.yify.JSONComunicator;
import com.lordroid.cupcake.yify.JSONException;
import com.lordroid.cupcake.yify.JSONObject;

/**
 * @author HumanShield85
 * 
 */
public class Test   {
	private static final String MOVIE_DETAILS_API = "https://yts.am/api/v2/movie_details.json?movie_id=";
	public static void main(String[] args) throws JSONException, IOException, Exception {
		System.out.println("current location = " +PathUtils.getExcutionPath());
		System.setProperty(
				"http.agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
		JSONObject json = JSONComunicator
				.readJsonFromUrl("https://yts.am/api/v2/list_movies.json?limit=50");
		int limit = 50;
		JSONObject data = (JSONObject) json.getJSONObject("data");
		int totalMovieCount = data.getInt("movie_count");
		JSONArray arr = data.getJSONArray("movies");
		int maxPage;
		maxPage = (totalMovieCount / limit ) +( (totalMovieCount % limit == 0) ? 0 : 1);
		long start = System.currentTimeMillis();
		for (int i = 1 ; i <= maxPage ; i++){
			json = JSONComunicator
					.readJsonFromUrl("https://yts.am/api/v2/list_movies.json?limit=50&page="+i);
			data = (JSONObject) json.getJSONObject("data");
			arr = data.getJSONArray("movies");
			for (int n = 0 ; n < arr.length() ; n++) {
				final int id = arr.getJSONObject(n).getInt("id");
				new Thread(new Runnable(){

					public void run() {
						// TODO Auto-generated method stub
						try {
							HttpsDownloadUtility.downloadFileToDest(MOVIE_DETAILS_API+id, PathUtils.getExcutionPath()+File.separator+"movies"+File.separator+id+"/movie.json");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}).start();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("total time = "+(end-start));
	}
}
