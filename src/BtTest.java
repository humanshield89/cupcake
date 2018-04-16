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
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.runtime.BtClient;
import bt.runtime.Config;
import bt.torrent.TorrentSessionState;

import com.google.inject.Module;

/**
 * Hello world!
 * 
 */
public class BtTest {
	public static Logger LOGGER = LoggerFactory.getLogger(BtTest.class);

	public static void main(String[] args) {

		System.setProperty("java.net.preferIPv4Stack", "true");
		try {
			testBT();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void testBT() throws MalformedURLException {
		Config config = new Config() {
			@Override
			public int getNumOfHashingThreads() {
				return Runtime.getRuntime().availableProcessors() * 2;
			}
		};

		// enable bootstrapping from public routers
		Module dhtModule = new DHTModule(new DHTConfig() {
			@Override
			public boolean shouldUseRouterBootstrap() {
				return true;
			}
		});

		// get download directory
		Path targetDirectory = new File("downloads").toPath();

		// create file system based backend for torrent data
		Storage storage = new FileSystemStorage(targetDirectory);

		// create client with a private runtime
		BtClient client = Bt
				.client()
				.config(config)
				.storage(storage)
				.torrent(
						new URL(
								"https://yts.am/torrent/download/FC5B92079B94D6D3801C763F1BEF021179BBB8C9"))
				.autoLoadModules().module(dhtModule).stopWhenDownloaded()
				.sequentialSelector().build();

		client.startAsync(new Consumer<TorrentSessionState>() {

			@Override
			public void accept(TorrentSessionState arg0) {
				// TODO Auto-generated method stub
				// arg0.
				System.out.println("downloaded = "
						+ ((double) arg0.getPiecesComplete() / (double) arg0
								.getPiecesTotal()) * 100 + " %");
				System.out.println("uploaded = " + arg0.getUploaded());
				System.out.println("downloaded  = " + arg0.getDownloaded());
				System.out.println("peers  = "
						+ arg0.getConnectedPeers().size());

			}

		}, 1000L).join();

	}

}
