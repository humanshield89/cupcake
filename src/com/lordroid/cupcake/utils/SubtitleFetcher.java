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
package com.lordroid.cupcake.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.xmlrpc.XmlRpcException;

import Opensubs.OpenSubtitle;
import Opensubs.SubtitleInfo;

import com.lordroid.cupcake.yify.JSONException;
import com.lordroid.cupcake.yify.YifyMovie;

/**
 * @author HumanShield85
 * 
 */
public class SubtitleFetcher {
	public static final String[] SUBTITLE_LANGUAGES_NAMES = {"English","Deutsch","Arabic","French","Espanol","Italiano","Português","rusian"};
	public static final String[] SUBTITLE_LANGUAGES_CODES = {"eng","ger","ara","fre","spa","ita","por","rus"};
	
	public static File unGzip(File infile, boolean deleteGzipfileOnSuccess)
			throws IOException {
		GZIPInputStream gin = new GZIPInputStream(new FileInputStream(infile));
		FileOutputStream fos = null;
		try {
			File outFile = new File(infile.getParent(), infile.getName()
					.replaceAll("\\.gz$", ""));
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

	
	public static List<SubtitleInfo> getSubtitleList(YifyMovie movie , String lang) throws XmlRpcException{
		 OpenSubtitle openSubtitle=new OpenSubtitle();
		 openSubtitle.login();
		 List <SubtitleInfo> subs = openSubtitle.getMovieSubsByName(movie.getTitle(),"20",lang);
		 openSubtitle.logOut();
		return subs;
	}
	
	public static List<SubtitleInfo> getSubtitleList(File video ,String lang) throws XmlRpcException{
		 OpenSubtitle openSubtitle=new OpenSubtitle();
		 openSubtitle.login();
		 List <SubtitleInfo> subs = openSubtitle.Search(video.getAbsolutePath(),lang);
		 openSubtitle.logOut();
		return subs;
	}
	
	public static File getSubtitle(SubtitleInfo sub , YifyMovie movie) throws IOException{
		String url = sub.getSubDownloadLink();
		
		return unGzip(HttpsDownloadUtility.HTTPdownloadFile(url, movie.getDownlodFolder().getAbsolutePath()), true);
	}
	
	public static File getSubtitle(SubtitleInfo sub , File movie) throws IOException{
		String url = sub.getSubDownloadLink();
		
		return unGzip(HttpsDownloadUtility.HTTPdownloadFile(url, movie.getAbsolutePath()), true);
	}
	
	public static void main(String args[]) {
		File f = new File("G:/Insidious.2010.720p.BrRip.x264.YIFY.mp4");
		try {
			for(SubtitleInfo sub : getSubtitleList(f,"ar")){
				System.out.println(sub.getISO639());
			}
		} catch (XmlRpcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static File main(YifyMovie movie) throws JSONException,
			IOException, Exception {
		 OpenSubtitle openSubtitle=new OpenSubtitle();
		 openSubtitle.login();

		String str = null;

		try {
		 str =	openSubtitle.getMovieSubsByName(movie.getTitle(),"20","ara").get(0).getSubDownloadLink();
		 System.out.println("search by title arabic count = "+openSubtitle.getMovieSubsByName(movie.getTitle(),"20","ara").size());
		 System.out.println("search by title english count = "+openSubtitle.getMovieSubsByName(movie.getTitle(),"20","eng").size());
		 List<SubtitleInfo> list = openSubtitle.getMovieSubsByName(movie.getTitle(),"20","ara");
		 for (SubtitleInfo sub : list){
			 str = sub.getSubDownloadLink();
			 System.out.println("==== sub");
			 System.out.println("language name  "+sub.getLanguageName());
			 System.out.println("iso639  "+sub.getISO639());
			 System.out.println("language id "+sub.getSubLanguageID());
			 System.out.println("rating  "+	 sub.getSubRating());
			 System.out.println("==== end  sub");
			 File gz = HttpsDownloadUtility.HTTPdownloadFile(str, movie.getDownlodFolder().getAbsolutePath());
			 unGzip(gz, true);
		 }

		 } catch (Exception e) {
			 str =	 openSubtitle.getMovieSubsByName(movie.getTitle(),"20","eng").get(0).getSubDownloadLink();
		 }

		File gz = HttpsDownloadUtility.HTTPdownloadFile(str, movie.getDownlodFolder().getAbsolutePath());

		System.out.println(str);
		openSubtitle.logOut();
		return unGzip(gz, true);

	}
}
