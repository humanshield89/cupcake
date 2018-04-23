/*
 * Copyright 2016  sachin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package Opensubs;

import java.util.HashMap;

/**
 * Created by sachin on 3/4/16.
 */
public class SubtitleInfo {

	String IDSubMovieFile;
	String MovieHash, MovieByteSize, MovieTimeMS, MovieFrames, IDSubtitleFile,
			SubFileName, SubActualCD, SubSize, SubHash;
	String IDSubtitle, UserID, SubLanguageID, SubFormat, SubSumCD, SubAddDate,
			SubDownloadsCnt, SubBad, SubRating;
	String IDMovie, IDMovieImdb, MovieName, MovieNameEng, MovieYear,
			MovieImdbRating, UserNickName, ISO639, LanguageName,
			SubDownloadLink;

	SubtitleInfo(HashMap<?, ?> info) {
		IDSubMovieFile = (String) info.get("IDSubMovieFile");
		MovieHash = (String) info.get("MovieHash");
		MovieByteSize = (String) info.get("MovieByteSize");
		MovieTimeMS = (String) info.get("MovieTimeMS");
		MovieFrames = (String) info.get("MovieFrames");
		IDSubtitleFile = (String) info.get("IDSubtitleFile");
		SubFileName = (String) info.get("SubFileName");
		SubActualCD = (String) info.get("SubActualCD");
		SubSize = (String) info.get("SubSize");
		SubHash = (String) info.get("SubHash");
		IDSubtitle = (String) info.get("IDSubtitle");
		UserID = (String) info.get("UserID");
		SubLanguageID = (String) info.get("SubLanguageID");
		SubFormat = (String) info.get("SubFormat");
		SubSumCD = (String) info.get("SubSumCD");
		SubAddDate = (String) info.get("SubAddDate");
		SubDownloadsCnt = (String) info.get("SubDownloadsCnt");
		SubBad = (String) info.get("SubBad");
		SubRating = (String) info.get("SubRating");
		IDMovie = (String) info.get("IDMovie");
		IDMovieImdb = (String) info.get("IDMovieImdb");
		MovieName = (String) info.get("MovieName");
		MovieNameEng = (String) info.get("MovieNameEng");
		MovieYear = (String) info.get("MovieYear");
		MovieImdbRating = "tt" + (String) info.get("MovieImdbRating");
		UserNickName = (String) info.get("UserNickName");
		ISO639 = (String) info.get("ISO639");
		LanguageName = (String) info.get("LanguageName");
		SubDownloadLink = (String) info.get("SubDownloadLink");

	}

	public String getIDMovie() {
		return IDMovie;
	}

	public String getIDMovieImdb() {
		return IDMovieImdb;
	}

	public String getIDSubMovieFile() {
		return IDSubMovieFile;
	}

	public String getIDSubtitle() {
		return IDSubtitle;
	}

	public String getIDSubtitleFile() {
		return IDSubtitleFile;
	}

	public String getISO639() {
		return ISO639;
	}

	public String getLanguageName() {
		return LanguageName;
	}

	public String getListInfo() {
		return (this.IDSubtitleFile);
	}

	public String getMovieByteSize() {
		return MovieByteSize;
	}

	public String getMovieFrames() {
		return MovieFrames;
	}

	public String getMovieHash() {
		return MovieHash;
	}

	public String getMovieImdbRating() {
		return MovieImdbRating;
	}

	public String getMovieName() {
		return MovieName;
	}

	public String getMovieNameEng() {
		return MovieNameEng;
	}

	public String getMovieTimeMS() {
		return MovieTimeMS;
	}

	public String getMovieYear() {
		return MovieYear;
	}

	public String getSubActualCD() {
		return SubActualCD;
	}

	public String getSubAddDate() {
		return SubAddDate;
	}

	public String getSubBad() {
		return SubBad;
	}

	public String getSubDownloadLink() {
		return SubDownloadLink;
	}

	public String getSubDownloadsCnt() {
		return SubDownloadsCnt;
	}

	public String getSubFileName() {
		return SubFileName;
	}

	public String getSubFormat() {
		return SubFormat;
	}

	public String getSubHash() {
		return SubHash;
	}

	public String getSubLanguageID() {
		return SubLanguageID;
	}

	public String getSubRating() {
		return SubRating;
	}

	public String getSubSize() {
		return SubSize;
	}

	public String getSubSumCD() {
		return SubSumCD;
	}

	public String getUserID() {
		return UserID;
	}

	public String getUserNickName() {
		return UserNickName;
	}

	public void setIDMovie(String IDMovie) {
		this.IDMovie = IDMovie;
	}

	public void setIDMovieImdb(String IDMovieImdb) {
		this.IDMovieImdb = IDMovieImdb;
	}

	public void setIDSubMovieFile(String IDSubMovieFile) {
		this.IDSubMovieFile = IDSubMovieFile;
	}

	public void setIDSubtitle(String IDSubtitle) {
		this.IDSubtitle = IDSubtitle;
	}

	public void setIDSubtitleFile(String IDSubtitleFile) {
		this.IDSubtitleFile = IDSubtitleFile;
	}

	public void setISO639(String ISO639) {
		this.ISO639 = ISO639;
	}

	public void setLanguageName(String languageName) {
		LanguageName = languageName;
	}

	public void setMovieByteSize(String movieByteSize) {
		MovieByteSize = movieByteSize;
	}

	public void setMovieFrames(String movieFrames) {
		MovieFrames = movieFrames;
	}

	public void setMovieHash(String movieHash) {
		MovieHash = movieHash;
	}

	public void setMovieImdbRating(String movieImdbRating) {
		MovieImdbRating = movieImdbRating;
	}

	public void setMovieName(String movieName) {
		MovieName = movieName;
	}

	public void setMovieNameEng(String movieNameEng) {
		MovieNameEng = movieNameEng;
	}

	public void setMovieTimeMS(String movieTimeMS) {
		MovieTimeMS = movieTimeMS;
	}

	public void setMovieYear(String movieYear) {
		MovieYear = movieYear;
	}

	public void setSubActualCD(String subActualCD) {
		SubActualCD = subActualCD;
	}

	public void setSubAddDate(String subAddDate) {
		SubAddDate = subAddDate;
	}

	public void setSubBad(String subBad) {
		SubBad = subBad;
	}

	public void setSubDownloadLink(String subDownloadLink) {
		SubDownloadLink = subDownloadLink;
	}

	public void setSubDownloadsCnt(String subDownloadsCnt) {
		SubDownloadsCnt = subDownloadsCnt;
	}

	public void setSubFileName(String subFileName) {
		SubFileName = subFileName;
	}

	public void setSubFormat(String subFormat) {
		SubFormat = subFormat;
	}

	public void setSubHash(String subHash) {
		SubHash = subHash;
	}

	public void setSubLanguageID(String subLanguageID) {
		SubLanguageID = subLanguageID;
	}

	public void setSubRating(String subRating) {
		SubRating = subRating;
	}

	public void setSubSize(String subSize) {
		SubSize = subSize;
	}

	public void setSubSumCD(String subSumCD) {
		SubSumCD = subSumCD;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public void setUserNickName(String userNickName) {
		UserNickName = userNickName;
	}
}
