package com.lordroid.cupcake.yify;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lordroid.cupcake.controlers.Watchable;
import com.lordroid.cupcake.controlers.Watcher;
import com.lordroid.cupcake.res.S;
import com.lordroid.cupcake.utils.HttpsDownloadUtility;

public class YifyMovie {
	private static final String YOUTUBE = "https://www.youtube.com/watch?v=";
	private int id;
	private String url;
	private String imdbCode;
	private String title;
	private String titleEnglish;
	private String titleLong;
	private String slug;
	private int year;
	private double rating;
	private int runtime;
	private ArrayList<String> genre = new ArrayList<String>();
	private String descriptionShort;
	private String descriptionFull;
	private String youtubeTrailerURL;
	private String language;
	private String MPARating;
	private String bgImageURL;
	private String bgImageOriginalURL;
	private String coverSmallURL;
	private String coverMediumURL;
	private String coverLargeURL;
	private String state;
	private ArrayList<YifyTorrent> torrents = new ArrayList<YifyTorrent>();
	private File tmpFolder;
	private File bgImageFile;
	private File CoverImageFileMedium;
	// private File coverImageFileLarge;

	private boolean isBGCached = false;
	private boolean isBGBEingCached = false;
	private boolean isCoverMediumCached = false;
	private boolean isCoverMediumBeingCached = false;

	// private boolean isCoverLargeCached = false;
	// private boolean isCoverLargeBeingCached = false;

	public YifyMovie(JSONObject movie) {
		this.id = movie.getInt(YifyS.RESPONSE_ID_KEY);
		this.url = movie.getString(YifyS.RESPONSE_URL_KEY);
		this.imdbCode = movie.getString(YifyS.RESPONSE_IMDB_CODE_KEY);
		this.title = movie.getString(YifyS.RESPONSE_TITLE_KEY);
		this.titleEnglish = movie.getString(YifyS.RESPONSE_TITLE_ENGLISH_KEY);
		this.titleLong = movie.getString(YifyS.RESPONSE_TITLE_LONG_KEY);
		this.slug = movie.getString(YifyS.RESPONSE_SLUG_KEY);
		this.year = movie.getInt(YifyS.RESPONSE_YEAR_KEY);
		this.rating = movie.getDouble(YifyS.RESPONSE_RATING_KEY);
		this.runtime = movie.getInt(YifyS.RESPONSE_RUNTIME_KEY);

		JSONArray genreJSON = movie.getJSONArray(YifyS.RESPONSE_GENRES_KEY);
		for (int i = 0; i < genreJSON.length(); i++) {
			genre.add(genreJSON.getString(i));
		}

		this.descriptionShort = movie.getString(YifyS.RESPONSE_SUMMARY_KEY);
		this.descriptionFull = movie.getString(YifyS.RESPONSE_SUMMARY_FULL_KEY);
		this.youtubeTrailerURL = YOUTUBE
				+ movie.getString(YifyS.RESPONSE_TRAILER_CODE_KEY);
		this.language = movie.getString(YifyS.RESPONSE_LANGUAGE_KEY);
		this.MPARating = movie.getString(YifyS.RESPONSE_MPA_RATING_KEY);
		this.bgImageURL = movie.getString(YifyS.RESPONSE_BG_IMAGE_KEY);
		this.bgImageOriginalURL = movie
				.getString(YifyS.RESPONSE_BG_IMAGE_ORIG_KEY);
		this.coverSmallURL = movie.getString(YifyS.RESPONSE_COVER_IMAGE_S_KEY);
		this.coverMediumURL = movie.getString(YifyS.RESPONSE_COVER_IMAGE_M_KEY);
		this.coverLargeURL = movie.getString(YifyS.RESPONSE_COVER_IMAGE_L_KEY);
		this.state = movie.getString(YifyS.RESPONSE_MOVIE_STATE_KEY);

		JSONArray torrentsJSON = movie
				.getJSONArray(YifyS.RESPONSE_TORRENTS_KEY);
		for (int i = 0; i < torrentsJSON.length(); i++) {
			torrents.add(new YifyTorrent(torrentsJSON.getJSONObject(i)));
		}

		tmpFolder = new File(S.SYSTEM_TMP_FOLDER + this.id + File.separator);
		tmpFolder.mkdirs();

		cacheBgImage();
		cacheCoverImageMedium();
	}

	public void cacheBgImage() {
		if (!isBGBEingCached) {
			new Thread(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					isBGBEingCached = true;
					if (!new File(tmpFolder.getAbsolutePath() + File.separator
							+ "background.jpg").exists()) {
						try {
							bgImageFile = HttpsDownloadUtility.downloadFile(
									bgImageURL, tmpFolder.getAbsolutePath());
							isBGCached = true;
						} catch (IOException e) {
							bgImageFile = S.DEFAULT_BG;
							isBGCached = false;
							e.printStackTrace();
						}
						if (!bgImageFile.exists()) {
							bgImageFile = S.DEFAULT_BG;
							isBGCached = false;
						}
					} else {
						// System.out.println("no need to cache ");
						bgImageFile = new File(tmpFolder.getAbsolutePath()
								+ File.separator + "background.jpg");
						isBGCached = true;
					}
					isBGBEingCached = false;
					System.out.println("End Time " + System.currentTimeMillis());
				}
			}).start();
		}

	}

	public void cacheCoverImageMedium() {

		if (!isCoverMediumBeingCached) {
			new Thread(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub medium-cover.jpg
					isCoverMediumCached = new File(tmpFolder.getAbsolutePath()
							+ File.separator + "medium-cover.jpg").exists();
					if (!isCoverMediumCached) {
						try {
							CoverImageFileMedium = HttpsDownloadUtility
									.downloadFile(coverMediumURL,
											tmpFolder.getAbsolutePath());
						} catch (IOException e) {
							CoverImageFileMedium = S.DEFAULT_BG;
							e.printStackTrace();
						}
						if (!CoverImageFileMedium.exists()) {
							CoverImageFileMedium = S.DEFAULT_BG;
						}
					} else {
						System.out.println("no need to cache ");
						CoverImageFileMedium = new File(tmpFolder
								.getAbsolutePath()
								+ File.separator
								+ "medium-cover.jpg");
					}
					isCoverMediumCached = new File(tmpFolder.getAbsolutePath()
							+ File.separator + "medium-cover.jpg").exists();
				}

			}).start();
		}

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the imdbCode
	 */
	public String getImdbCode() {
		return imdbCode;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the titleEnglish
	 */
	public String getTitleEnglish() {
		return titleEnglish;
	}

	/**
	 * @return the titleLong
	 */
	public String getTitleLong() {
		return titleLong;
	}

	/**
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the rating
	 */
	public double getRating() {
		return rating;
	}

	/**
	 * @return the runtime
	 */
	public int getRuntime() {
		return runtime;
	}

	/**
	 * @return the genre
	 */
	public List<String> getGenre() {
		return genre;
	}

	/**
	 * @return the descriptionShort
	 */
	public String getDescriptionShort() {
		return descriptionShort;
	}

	/**
	 * @return the descriptionFull
	 */
	public String getDescriptionFull() {
		return descriptionFull;
	}

	/**
	 * @return the youtubeTrailerCode
	 */
	public String getYoutubeTrailerURL() {
		return youtubeTrailerURL;
	}

	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return the mPARating
	 */
	public String getMPARating() {
		return MPARating;
	}

	/**
	 * @return the bgImage
	 */
	public String getBgImageURL() {
		return bgImageURL;
	}

	/**
	 * @return the bgImageOriginal
	 */
	public String getBgImageOriginalURL() {
		return bgImageOriginalURL;
	}

	/**
	 * @return the coverSmall
	 */
	public String getCoverSmallURL() {
		return coverSmallURL;
	}

	/**
	 * @return the torrents
	 */
	public ArrayList<YifyTorrent> getTorrents() {
		return torrents;
	}

	/**
	 * @return the coverMedium
	 */
	public String getCoverMediumURL() {
		return coverMediumURL;
	}

	/**
	 * @return the coverLarge
	 */
	public String getCoverLargeURL() {
		return coverLargeURL;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @return the youtube
	 */
	public static String getYoutube() {
		return YOUTUBE;
	}

	/**
	 * @return the tmpFolder
	 */
	public File getTmpFolder() {
		return tmpFolder;
	}

	/**
	 * @return the bgImageFile
	 */
	public File getBgImageFile() {
		return bgImageFile;
	}

	/**
	 * @return the isBGCached
	 */
	public Boolean getIsBGCached() {
		return isBGCached;
	}

}
