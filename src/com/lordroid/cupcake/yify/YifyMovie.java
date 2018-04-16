package com.lordroid.cupcake.yify;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lordroid.cupcake.App;
import com.lordroid.cupcake.res.S;
import com.lordroid.cupcake.res.Settings;
import com.lordroid.cupcake.utils.HttpsDownloadUtility;
import com.lordroid.cupcake.utils.PropReader;

public class YifyMovie {
	private static final String YOUTUBE = "https://www.youtube.com/watch?v=";
	private static final String MOVIE_DETAILS_API = "https://yts.am/api/v2/movie_details.json?movie_id=";
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
	private int likeCount;
	private int downloadCount;
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
	private File ImgtmpFolder;
	private File bgImageFile;
	private File CoverImageFileMedium;
	// private File coverImageFileLarge;
	private boolean isBGCached = false;
	private boolean isBGBEingCached = false;
	private boolean isCoverMediumCached = false;
	private boolean isCoverMediumBeingCached = false;

	// private boolean isCoverLargeCached = false;
	// private boolean isCoverLargeBeingCached = false;

	private boolean has720p = false;
	private boolean has1080p = false;
	private boolean has3d = false;

	public YifyMovie(JSONObject movieArg) {
		this.id = movieArg.getInt(YifyS.RESPONSE_ID_KEY);
		JSONObject movie = movieArg;


		this.url = movie.getString(YifyS.RESPONSE_URL_KEY);
		this.imdbCode = movie.getString(YifyS.RESPONSE_IMDB_CODE_KEY);
		this.title = movie.getString(YifyS.RESPONSE_TITLE_KEY);
		this.titleEnglish = movie.getString(YifyS.RESPONSE_TITLE_ENGLISH_KEY);
		this.titleLong = movie.getString(YifyS.RESPONSE_TITLE_LONG_KEY);
		this.slug = movie.getString(YifyS.RESPONSE_SLUG_KEY);
		this.year = movie.getInt(YifyS.RESPONSE_YEAR_KEY);
		this.rating = movie.getDouble(YifyS.RESPONSE_RATING_KEY);

		this.runtime = movie.getInt(YifyS.RESPONSE_RUNTIME_KEY);
		// this.likeCount = movie.getInt(YifyS.RESPONSE_LIKE_COUNT_KEY);
		// this.downloadCount = movie.getInt(YifyS.RESPONSE_DOWNLOAD_COUNT_KEY);
		try {
			JSONArray genreJSON = movieArg
					.getJSONArray(YifyS.RESPONSE_GENRES_KEY);
			for (int i = 0; i < genreJSON.length(); i++) {
				genre.add(genreJSON.getString(i));
			}
		} catch (Exception e) {
			genre.add("unknown");
		}
		try {
			this.descriptionShort = movie.getString(YifyS.RESPONSE_SUMMARY_KEY);
			this.descriptionFull = movie
					.getString(YifyS.RESPONSE_SUMMARY_FULL_KEY);
		} catch (Exception E) {
			this.descriptionShort = movie.getString("description_intro");
			this.descriptionFull = movie.getString("description_full");
		}
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
		// this.state = movieArg.getString(YifyS.RESPONSE_MOVIE_STATE_KEY);
		try {
			JSONArray torrentsJSON = movie
					.getJSONArray(YifyS.RESPONSE_TORRENTS_KEY);
			for (int i = 0; i < torrentsJSON.length(); i++) {
				torrents.add(new YifyTorrent(torrentsJSON.getJSONObject(i)));
			}
		} catch (Exception e) {

		}

		ImgtmpFolder = new File( S.IMAGE_CACHE_TMP_FOLDER + this.id + File.separator);
		ImgtmpFolder.mkdirs();
		cacheBgImage();
		cacheCoverImageMedium();
		sortTorrentsBySize();
		setAvailableQualities();

	}

	private void setAvailableQualities() {
		// TODO Auto-generated method stub
		// no need to look forward there is no torrents
		if (this.torrents.isEmpty())
			return;
		for (YifyTorrent t : torrents) {
			if (t.getQuality().equals(YifyS.QUALITY_720P)) {
				this.has720p = true;
			} else if (t.getQuality().equals(YifyS.QUALITY_1080P)) {
				this.has1080p = true;
			} else if (t.getQuality().equals(YifyS.QUALITY_3D)) {
				this.has3d = true;
			}
		}
	}

	private void sortTorrentsBySize() {
		if (this.torrents.isEmpty())
			return;
		// TODO Auto-generated method stub
		boolean swaped = false;

		do {
			swaped = false;
			for (int i = 0; i < torrents.size() - 1; i++) {
				YifyTorrent prev = torrents.get(i);
				YifyTorrent next = torrents.get(i + 1);
				if (prev.getSizeInBytes() > next.getSizeInBytes()) {
					torrents.set(i, next);
					torrents.set(i + 1, prev);
					swaped = true;
				}
			}

		} while (swaped);
		for (YifyTorrent t : torrents){
		App.LOGGER.info(t.getQuality()+"size = "+t.getSizeInBytes()+"  "+t.getSize());
		}
	}

	public void loadExtras() {
		// TODO find a faster way to do this
		// 300 to 500 miliseconds to get the downlowd count is expensive
		try {
			JSONObject movie = JSONComunicator
					.readJsonFromUrl(MOVIE_DETAILS_API + id)
					.getJSONObject("data").getJSONObject("movie");
			this.downloadCount = movie
					.getInt(YifyS.RESPONSE_DOWNLOAD_COUNT_KEY);
			this.likeCount = movie.getInt(YifyS.RESPONSE_LIKE_COUNT_KEY);
		} catch (JSONException e) {

			e.printStackTrace();
		} catch (Exception e) {

			e.printStackTrace();
		}
	}

	public void cacheBgImage() {
		if (!isBGBEingCached) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					isBGBEingCached = true;
					if (!new File(ImgtmpFolder.getAbsolutePath()
							+ File.separator + "background.jpg").exists()) {
						try {
							bgImageFile = HttpsDownloadUtility.downloadFile(
									bgImageURL, ImgtmpFolder.getAbsolutePath());
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
						bgImageFile = new File(ImgtmpFolder.getAbsolutePath()
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

				@Override
				public void run() {
					isCoverMediumBeingCached = true;
					// TODO Auto-generated method stub medium-cover.jpg
					isCoverMediumCached = new File(ImgtmpFolder
							.getAbsolutePath()
							+ File.separator
							+ "medium-cover.jpg").exists();
					if (!isCoverMediumCached) {
						try {
							CoverImageFileMedium = HttpsDownloadUtility
									.downloadFile(coverMediumURL,
											ImgtmpFolder.getAbsolutePath());
						} catch (IOException e) {
							CoverImageFileMedium = S.DEFAULT_COVER;
							e.printStackTrace();
						}
						if (!CoverImageFileMedium.exists()) {
							CoverImageFileMedium = S.DEFAULT_COVER;
						}
					} else {
						System.out.println("no need to cache ");
						CoverImageFileMedium = new File(ImgtmpFolder
								.getAbsolutePath()
								+ File.separator
								+ "medium-cover.jpg");
					}
					isCoverMediumCached = new File(ImgtmpFolder
							.getAbsolutePath()
							+ File.separator
							+ "medium-cover.jpg").exists();
					isCoverMediumBeingCached = false;
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
	 * @return the likeCount
	 */
	public int getLikeCount() {
		return likeCount;
	}

	/**
	 * @return the downloadCount
	 */
	public int getDownloadCount() {
		return downloadCount;
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
	public File getImgTmpFolder() {
		return ImgtmpFolder;
	}

	public File getDownlodFolder() {
		return new File(S.MOVIE_DOWNLOAD_FOLDER + File.separator + id);
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

	/**
	 * @return the coverImageFileMedium
	 */
	public File getCoverImageFileMedium() {
		if (!isCoverMediumCached) {
			if (!this.isCoverMediumBeingCached) {
				this.cacheCoverImageMedium();
			}
		}
		return CoverImageFileMedium;
	}

	/**
	 * @return the isBGBEingCached
	 */
	public boolean isBGBEingCached() {
		return isBGBEingCached;
	}

	/**
	 * @return the isCoverMediumCached
	 */
	public boolean isCoverMediumCached() {
		return isCoverMediumCached;
	}

	/**
	 * @return the isCoverMediumBeingCached
	 */
	public boolean isCoverMediumBeingCached() {
		return isCoverMediumBeingCached;
	}

	/**
	 * @return the has720p
	 */
	public boolean isHas720p() {
		return has720p;
	}

	/**
	 * @return the has1080p
	 */
	public boolean isHas1080p() {
		return has1080p;
	}

	/**
	 * @return the has3d
	 */
	public boolean isHas3d() {
		return has3d;
	}

	public YifyTorrent getTorrent(int quality) {
		if (quality == -1) {
			for (YifyTorrent t : torrents) {
				if (t.getQuality().equals(
						Settings.getCurrentDefaultPlayQuality())) {
					return t;
				}
			}
		} else {
			for (YifyTorrent t : torrents) {
				if (t.getQuality().equals(
						Settings.DEFAULT_PLAY_QUALITY[quality])) {
					return t;
				}
			}
		}
		return null;
	}

}
