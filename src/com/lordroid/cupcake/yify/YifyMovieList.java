package com.lordroid.cupcake.yify;

public class YifyMovieList {
	private String status;
	private String statusMessage;
	private int movieCount;
	private int limitPerPage;
	private int pageNumber;

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return statusMessage;
	}

	/**
	 * @return the movieCount
	 */
	public int getMovieCount() {
		return movieCount;
	}

	/**
	 * @return the limitPerPage
	 */
	public int getLimitPerPage() {
		return limitPerPage;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

}
