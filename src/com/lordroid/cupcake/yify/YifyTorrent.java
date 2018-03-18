package com.lordroid.cupcake.yify;

public class YifyTorrent {
	private String url;
	private String hash;
	private String quality;
	private int seed;
	private int peers;
	private String size;
	private long sizeInBytes;
	private String uploadDate;
	private long dateUploadedMilis;

	public YifyTorrent(JSONObject jsonObject) {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @return the quality
	 */
	public String getQuality() {
		return quality;
	}

	/**
	 * @return the seed
	 */
	public int getSeed() {
		return seed;
	}

	/**
	 * @return the peers
	 */
	public int getPeers() {
		return peers;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @return the sizeInBytes
	 */
	public long getSizeInBytes() {
		return sizeInBytes;
	}

	/**
	 * @return the uploadDate
	 */
	public String getUploadDate() {
		return uploadDate;
	}

	/**
	 * @return the dateUploadedMilis
	 */
	public long getDateUploadedMilis() {
		return dateUploadedMilis;
	}

}
