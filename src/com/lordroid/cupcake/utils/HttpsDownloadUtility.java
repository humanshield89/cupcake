package com.lordroid.cupcake.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.lordroid.cupcake.App;

/**
 * A utility that downloads a file from a URL.
 * 
 * @author www.codejava.net
 * @author humanshield85
 */
public class HttpsDownloadUtility {
	private static final int BUFFER_SIZE = 32768;

	/**
	 * Downloads a file from a URL
	 * 
	 * @param fileURL
	 *            HTTP URL of the file to be downloaded
	 * @param saveDir
	 *            path of the directory to save the file
	 * @throws IOException
	 */
	public static File downloadFile(String fileURL, String saveDir)
			throws IOException {
		String saveFilePath = null;
		URL url = new URL(fileURL);
		HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpsURLConnection.HTTP_OK) {
			String fileName = "";
			String disposition = httpConn.getHeaderField("Content-Disposition");
//			String contentType = httpConn.getContentType();
//			int contentLength = httpConn.getContentLength();

			if (disposition != null) {
				// extracts file name from header field
				int index = disposition.indexOf("filename=");
				if (index > 0) {
					fileName = disposition.substring(index + 10,
							disposition.length() - 1);
				}
			} else {
				// extracts file name from URL
				fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
						fileURL.length());
			}

			// System.out.println("Content-Type = " + contentType);
			// System.out.println("Content-Disposition = " + disposition);
			// System.out.println("Content-Length = " + contentLength);
			// System.out.println("fileName = " + fileName);

			// opens input stream from the HTTP connection
			InputStream inputStream = httpConn.getInputStream();
			saveFilePath = saveDir + File.separator + fileName;

			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(saveFilePath);

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			App.LOGGER.debug("File downloaded");
		} else {

			App.LOGGER.debug("No file to download. Server replied HTTP code: "
					+ responseCode);
		}
		httpConn.disconnect();

		return new File(saveFilePath);
	}
	
	
	public static boolean downloadFileToDest(String fileURL, String saveDir)
			throws IOException {
		new File(saveDir).getParentFile().mkdirs();
		String saveFilePath = null;
		URL url = new URL(fileURL);
		HttpsURLConnection httpConn = (HttpsURLConnection) url.openConnection();
		int responseCode = httpConn.getResponseCode();

		// always check HTTP response code first
		if (responseCode == HttpsURLConnection.HTTP_OK) {

			InputStream inputStream = httpConn.getInputStream();
			saveFilePath = saveDir ;
			System.out.println(saveFilePath);
			// opens an output stream to save into file
			FileOutputStream outputStream = new FileOutputStream(new File(saveFilePath).getAbsolutePath());

			int bytesRead = -1;
			byte[] buffer = new byte[BUFFER_SIZE];
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}

			outputStream.close();
			inputStream.close();

			App.LOGGER.debug("File downloaded");
		} else {

			App.LOGGER.debug("No file to download. Server replied HTTP code: "
					+ responseCode);
		}
		httpConn.disconnect();

		return true;
	}
}