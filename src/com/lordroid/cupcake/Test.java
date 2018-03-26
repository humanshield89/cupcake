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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import Opensubs.OpenSubtitle;

import com.lordroid.cupcake.utils.HttpsDownloadUtility;
import com.lordroid.cupcake.yify.JSONException;
import com.lordroid.cupcake.yify.YifyMovie;

/**
 * @author HumanShield85
 * 
 */
public class Test {
	private static final String MOVIE_DETAILS_API = "https://yts.am/api/v2/movie_details.json?movie_id=";
	
	public static File unGzip(File infile, boolean deleteGzipfileOnSuccess) throws IOException {
	    GZIPInputStream gin = new GZIPInputStream(new FileInputStream(infile));
	    FileOutputStream fos = null;
	    try {
	        File outFile = new File(infile.getParent(), infile.getName().replaceAll("\\.gz$", ""));
	        fos = new FileOutputStream(outFile);
	        byte[] buf = new byte[100000];
	        int len;
	        while ((len = gin.read(buf)) > 0) {
	            fos.write(buf, 0, len);
	        }

	        fos.close();
	        if (deleteGzipfileOnSuccess) {
	            infile.delete();
	        }
	        return outFile; 
	    } finally {
	        if (gin != null) {
	            gin.close();    
	        }
	        if (fos != null) {
	            fos.close();    
	        }
	    }       
	}
	
	public static File main(YifyMovie movie) throws JSONException, IOException,
			Exception {
		OpenSubtitle openSubtitle=new OpenSubtitle();
	    openSubtitle.login();
	    
	//  openSubtitle.ServerInfo();
	//  openSubtitle.getSubLanguages();

	    String str =  openSubtitle.getMovieSubsByName(movie.getTitle(),"20","ara").get(0).getSubDownloadLink();
	   // openSubtitle.sea
	//  openSubtitle.getTvSeriesSubs("game of thrones","1","1","10","eng");
	    //String str = openSubtitle.Search("G:/Movies/American History X (1998)/American.History.X.1998.720p.BluRay.x264.YIFY.mp4").get(0).getSubDownloadLink();
	    File gz = HttpsDownloadUtility.HTTPdownloadFile(str, movie.getTmpFolder().getAbsolutePath());
	    
	   
	    System.out.println(str);
	    openSubtitle.logOut();
	    return unGzip(gz,false);
//		System.out.println("current location = " + PathUtils.getExcutionPath());
//		System.setProperty(
//				"http.agent",
//				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
//		JSONObject json = JSONComunicator
//				.readJsonFromUrl("https://yts.am/api/v2/list_movies.json?limit=50");
//		int limit = 50;
//		JSONObject data = (JSONObject) json.getJSONObject("data");
//		int totalMovieCount = data.getInt("movie_count");
//		JSONArray arr = data.getJSONArray("movies");
//		int maxPage;
//		maxPage = (totalMovieCount / limit)
//				+ ((totalMovieCount % limit == 0) ? 0 : 1);
//		long start = System.currentTimeMillis();
//		for (int i = 1; i <= maxPage; i++) {
//			json = JSONComunicator
//					.readJsonFromUrl("https://yts.am/api/v2/list_movies.json?limit=50&page="
//							+ i);
//			data = (JSONObject) json.getJSONObject("data");
//			arr = data.getJSONArray("movies");
//			for (int n = 0; n < arr.length(); n++) {
//				final int id = arr.getJSONObject(n).getInt("id");
//				new Thread(new Runnable() {
//
//					public void run() {
//						// TODO Auto-generated method stub
//						try {
//							HttpsDownloadUtility.downloadFileToDest(
//									MOVIE_DETAILS_API + id,
//									PathUtils.getExcutionPath()
//											+ File.separator + "movies"
//											+ File.separator + id
//											+ "/movie.json");
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//					}
//
//				}).start();
//			}
//		}
//		long end = System.currentTimeMillis();
//		System.out.println("total time = " + (end - start));
	}
}
