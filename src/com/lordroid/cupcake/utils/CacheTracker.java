package com.lordroid.cupcake.utils;

import java.io.File;

import com.lordroid.cupcake.res.S;

public class CacheTracker {
	

	public static void main (String args[]){
		System.out.println(getTotalDownFolder()/1024/1024+"  mb");
	}
	
	
	public static long getTotalDownFolder(){
		long size = 0L;
		for (File f : FileUtils.listAllFiles(new File(S.MOVIE_DOWNLOAD_FOLDER))){
			if(f.isFile()){
				
				size = size + f.length();
			}
		}
		return size;
	}
	
	
	
}
