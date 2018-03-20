package com.lordroid.cupcake.yify;

public class YifyTorrent {
	private String url;
	private String hash;
	private String quality;
	private int seeds;
	private int peers;
	private String size;
	private long sizeInBytes;
	private String uploadDate;
	private long dateUploadedMilis;

	public YifyTorrent(JSONObject torrent) {
		this.url = torrent.getString(YifyS.RESPONSE_TORRENT_URL_KEY);
		this.hash = torrent.getString(YifyS.RESPONSE_TORRENT_HASH_KEY);
		this.quality = torrent.getString(YifyS.RESPONSE_TORRENT_QUALITY_KEY);
		this.seeds = torrent.getInt(YifyS.RESPONSE_TORRENT_SEEDS_KEY);
		this.peers = torrent.getInt(YifyS.RESPONSE_TORRENT_PEERS_KEY);
		this.size = torrent.getString(YifyS.RESPONSE_TORRENT_SIZE_STRING_KEY);
		this.sizeInBytes = torrent.getLong(YifyS.RESPONSE_TORRENT_SIZE_BYTE_KEY);
		this.uploadDate = torrent.getString(YifyS.RESPONSE_TORRENT_UPLOADED_STRING_KEY);
		this.dateUploadedMilis = torrent.getLong(YifyS.RESPONSE_TORRENT_UPLOADED_LONG_KEY);
		
	}

	/**
	 * @return the seeds
	 */
	public int getSeeds() {
		return seeds;
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
		return seeds;
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
