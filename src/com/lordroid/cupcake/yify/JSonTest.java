package com.lordroid.cupcake.yify;

import java.io.IOException;
import java.util.ArrayList;

import com.lordroid.cupcake.controlers.Watchable;
import com.lordroid.cupcake.controlers.Watcher;
import com.lordroid.cupcake.utils.TimeUtils;

public class JSonTest {
	static long start;
	static long end;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		System.out.println("Start time " + System.currentTimeMillis());
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
				.readJsonFromUrl("https://yts.am/api/v2/list_movies.json?limit=50&sort_by=year");
		JSONObject data = (JSONObject) json.getJSONObject("data");
		JSONArray arr = data.getJSONArray("movies");
		ArrayList<YifyMovie> movies = new ArrayList<YifyMovie>();
		start = System.currentTimeMillis();
		for (int i = 0; i < arr.length(); i++) {
			movies.add(new YifyMovie(arr.getJSONObject(i)));

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
		}
		for (YifyMovie movie : movies) {
			System.out
					.println("=========================================================");
			System.out.println("==================    " + movie.getTitle()
					+ "    ======================");
			System.out.println("==================    " + movie.getTitleLong()
					+ "    ======================");
			System.out.println("==================    " + movie.getId()
					+ "    ======================");
			System.out.println("==================    "
					+ movie.getBgImageOriginalURL()
					+ "    ======================");
			System.out.println("==================    " + movie.getBgImageURL()
					+ "    ======================");
			System.out.println("==================    "
					+ movie.getCoverLargeURL() + "    ======================");
			System.out.println("==================    "
					+ movie.getCoverMediumURL() + "    ======================");
			System.out.println("==================    "
					+ movie.getCoverSmallURL() + "    ======================");
			System.out
					.println("==================    "
							+ movie.getDescriptionFull()
							+ "    ======================");
			System.out.println("==================    "
					+ movie.getDescriptionShort()
					+ "    ======================");
			System.out.println("==================    " + movie.getImdbCode()
					+ "    ======================");
			System.out.println("==================    " + movie.getLanguage()
					+ "    ======================");
			System.out.println("==================    " + movie.getMPARating()
					+ "    ======================");

			System.out.println("==================    " + movie.getRating()
					+ "    ======================");
			// System.out.println();
			System.out.println("==================    " + movie.getRuntime()
					+ "    ======================");
			System.out.println("==================    " + movie.getSlug()
					+ "    ======================");
			System.out.println("==================    " + movie.getState()
					+ "    ======================");
			System.out.println("==================    "
					+ movie.getTitleEnglish() + "    ======================");
			System.out.println("==================    " + movie.getTitleLong()
					+ "    ======================");
			System.out.println("==================    " + movie.getUrl()
					+ "    ======================");
			System.out.println("==================    " + movie.getYear()
					+ "    ======================");
			System.out.println("==================    "
					+ movie.getYoutubeTrailerURL()
					+ "    ======================");
			// System.out.println("=================="+movie.getGenre()+"    ======================");

			System.out
					.println("=========================================================");
		}
		// Thread.sleep(5000);
		// System.out.println(HttpsDownloadUtility.downloadFile("https://yts.am/torrent/download/5D14C2E9E8577402F91B6A4C638E22DAA4930E23",
		// "").getAbsolutePath());
	}

}
