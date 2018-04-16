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
package com.lordroid.cupcake.bt;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.Consumer;

import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.peerexchange.PeerExchangeConfig;
import bt.peerexchange.PeerExchangeModule;
import bt.runtime.BtClient;
import bt.runtime.Config;
import bt.torrent.TorrentSessionState;

import com.google.inject.Module;
import com.lordroid.cupcake.App;
import com.lordroid.cupcake.utils.FileUtils;
import com.lordroid.cupcake.yify.YifyMovie;
import com.lordroid.cupcake.yify.YifyTorrent;

public class YifyMovieTorrent implements Runnable {
	public static final int USE_DEFAULT_SETTINGS = -1;
	public static final int USE_720P = 0;
	public static final int USE_1080P = 1;
	public static final int USE_3D = 2;
	Consumer<TorrentSessionState> watcher;
	BtClient client;
	private final Config config = new Config() {
		@Override
		public int getNumOfHashingThreads() {
			return Runtime.getRuntime().availableProcessors() * 2;
		}
	};

	Module dhtModule = new DHTModule(new DHTConfig() {
		@Override
		public boolean shouldUseRouterBootstrap() {
			return true;
		}
	});
	private final YifyTorrent yiFyTorrent;
	private final YifyMovie movie;

	/**
	 * @return the yiFyTorrent
	 */
	public YifyTorrent getYiFyTorrent() {
		return yiFyTorrent;
	}

	/**
	 * @return the movie
	 */
	public YifyMovie getMovie() {
		return movie;
	}

	/**
	 * @return the downloadFolder
	 */
	public File getDownloadFolder() {
		return downloadFolder;
	}

	private final File downloadFolder;

	// components
	public YifyMovieTorrent(YifyMovie movieArg, int quality) {
		this.movie = movieArg;
		this.yiFyTorrent = movie.getTorrent(quality);
		downloadFolder = movie.getDownlodFolder();

	}

	public File getVideo() {
		File video;

		try {
			video = FileUtils.searchrecursively(downloadFolder, ".mp4").get(0);
		} catch (Exception e) {
			try {
				video = FileUtils.searchrecursively(downloadFolder, ".mkv")
						.get(0);
			} catch (Exception ex) {
				video = FileUtils.searchrecursively(downloadFolder, ".avi")
						.get(0);
			}
		}
		return video;

	}

	public void start(Consumer<TorrentSessionState> watcher) {
		this.watcher = watcher;
		run();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		PeerExchangeConfig configEx = new PeerExchangeConfig() {
			@Override
			public int getMinEventsPerMessage() {
				// don't send PEX message if there are less than 50
				// added/dropped peer events
				return 50;
			}
		};

		PeerExchangeModule customModule = new PeerExchangeModule(configEx);

		Storage storage = new FileSystemStorage(downloadFolder.toPath());
		try {
			client = Bt.client().config(config).storage(storage)
					.torrent(new URL(yiFyTorrent.getUrl())).autoLoadModules()
					.module(dhtModule).module(customModule)
					.stopWhenDownloaded().sequentialSelector().build();
			client.startAsync(watcher, 1000L);

		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			// mostly because the url isn't good
			//
			App.LOGGER
					.error("Error while trying URL = " + yiFyTorrent.getUrl());
			e.printStackTrace();
		}

	}

	public void stopTorrent() {
		client.stop();

	}
}
