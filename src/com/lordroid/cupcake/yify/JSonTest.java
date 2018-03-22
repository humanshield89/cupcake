package com.lordroid.cupcake.yify;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.lordroid.cupcake.ui.ModifiedFlowLayout;
import com.lordroid.cupcake.ui.MovieItem;

public class JSonTest {
	static long start;
	static long end;

	public static void main(String[] args) throws JSONException, Exception {
		System.out.println("Start time " + System.currentTimeMillis());
		start = System.currentTimeMillis();
		JFrame frame = new JFrame ();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200,700);
		JPanel pan = new JPanel ();
		pan.setOpaque(false);
		//pan.setPreferredSize(new Dimension(frame.getWidth(),frame.getHeight()));
		JScrollPane scrollPane = new JScrollPane(pan);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		//scrollPane.add(pan);
		pan.setLayout(new ModifiedFlowLayout(ModifiedFlowLayout.CENTER, 10, 20));
		pan.setBackground(Color.GRAY);
		//scrollPane.setPreferredSize(new Dimension(frame.getWidth(),frame.getHeight()));
		//pan.setLayout(new FlowLayout());
		frame.add(scrollPane,BorderLayout.CENTER);
		frame.setVisible(true);
		// TODO Auto-generated method stub
		// File json = new File("a.json");
		// URL url = new URL("https://yts.am/api/v2/list_movies.json");
		// url.openConnection();
		// InputStream in = url.openStream();
		// FileUtils.copyFile(in, json);
		// System.out.print(json.exists());
		System.setProperty(
				"http.agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
		JSONObject json = JSONComunicator
				.readJsonFromUrl("https://yts.am/api/v2/list_movies.json?limit=50");
		JSONObject data = (JSONObject) json.getJSONObject("data");
		JSONArray arr = data.getJSONArray("movies");
		JSONArray arrays[] = new JSONArray[10];
		arrays[0] = arr;
		int n = 1;
		for (int i = 2 ; i < 3 ; i++){
			json = JSONComunicator
					.readJsonFromUrl("https://yts.am/api/v2/list_movies.json?limit=50&sort_by=year"+"&page="+i);
			data = (JSONObject) json.getJSONObject("data");
			arr = data.getJSONArray("movies");
			arrays[n] = arr;
			for (int i1 = 0; i1 < arr.length(); i1++) {
				YifyMovie m = new YifyMovie(arr.getJSONObject(i1));
				//movies.add(m);
				pan.add(new MovieItem(m));

				// JSONObject movie = arr.getJSONObject(i);
				// System.out.println("========================================================");
				// System.out.println("=======    "+movie.getString("title")+"");
				// System.out.println("========================================================");
				// System.out.println("======= release year : "+movie.getInt("year"));
				// System.out.println("======= Description : "+movie.getString("summary"));
				// System.out.println("======= torrent : "+movie.getJSONArray("torrents").getJSONObject(0).getString("url"));
				// System.out.println("======= seeders : "+movie.getJSONArray("torrents").getJSONObject(0).getInt("seeds"));
				// System.out.println("======= leachers : "+movie.getJSONArray("torrents").getJSONObject(0).getInt("peers"));
				// String image = movie.getString("background_image");
				// System.out.println(image);
				// URL u = new URL(image);
				// HttpsURLConnection c = (HttpsURLConnection) u.openConnection();
				// c.setRequestMethod("GET");
				// c.setRequestProperty("Content-length", "0");
				// c.setUseCaches(false);
				// c.setAllowUserInteraction(false);
				// c.setConnectTimeout(1000);
				// c.setReadTimeout(5000);
				// c.connect();
				// InputStream is = c.getInputStream();
				// //FileUtils.copyFile(is, new File("image"+i+".jpg"));
				// OutputStream out = new FileOutputStream(new
				// File("image"+i+".jpg"));
				// byte[] buffer = new byte[32768];
				// int len;
				// while ((len = is.read(buffer)) != -1) {
				// out.write(buffer, 0, len);
				// }
				// //in.close();
				// out.close();
				// is.close();
				// c.disconnect();
				
				//frame.pack();
				pan.revalidate();
			}
			n++;
		}
		//ArrayList<YifyMovie> movies = new ArrayList<YifyMovie>();


//		for ( n = 0 ; n < arrays.length ; n++) {
//			arr = arrays[n];
//		
//
//			// just remove this incase of tests 
//		}
//			

//		for (YifyMovie movie : movies) {
//			String genres = "";
//			for (String str : movie.getGenre()) {
//				genres = str +" ";
//			}
//			System.out
//					.println("=========================================================");
//			System.out.println("Title       :   " + movie.getTitle());
//			System.out.println("Title long  :   " + movie.getTitleLong());
//			System.out.println("Title eng   :   "+ movie.getTitleEnglish());
//			System.out.println("Id          :   " + movie.getId());
//			System.out.println("likes       :   " + movie.getLikeCount());
//			System.out.println("downloads   :   " + movie.getDownloadCount());
//			System.out.println("BG image    :   "+ movie.getBgImageOriginalURL());
//			System.out.println("bg image    :   " + movie.getBgImageURL());
//			System.out.println("cover L     :   "+movie.getCoverLargeURL());
//			System.out.println("cover M     :   "+ movie.getCoverMediumURL());
//			System.out.println("cover S     :   "+ movie.getCoverSmallURL());
//			System.out.println("desc full   :   "+ movie.getDescriptionFull());
//			System.out.println("desc short  :   "+ movie.getDescriptionShort());
//			System.out.println("imdbcode    :   " + movie.getImdbCode());
//			System.out.println("language    :   " + movie.getLanguage());
//			System.out.println("MPA rating  :   " + movie.getMPARating());
//			System.out.println("imdb rating :   " + movie.getRating());
//			System.out.println("Total time  :   " + movie.getRuntime());
//			System.out.println("Slug        :   " + movie.getSlug());
//			System.out.println("state       :   " + movie.getState());
//			System.out.println("URL         :   " + movie.getUrl());
//			System.out.println("Year        :   " + movie.getYear());
//			System.out.println("Trailer     :   "+ movie.getYoutubeTrailerURL());
//			System.out.println("genres     :   "+ genres);
//			System.out
//					  .println("=========================================================");
//			
//		}
		// Thread.sleep(5000);
		// System.out.println(HttpsDownloadUtility.downloadFile("https://yts.am/torrent/download/5D14C2E9E8577402F91B6A4C638E22DAA4930E23",
		// "").getAbsolutePath());
	}

}
