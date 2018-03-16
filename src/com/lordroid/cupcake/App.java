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
package com.lordroid.cupcake;

import java.io.File;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import bt.Bt;
import bt.data.Storage;
import bt.data.file.FileSystemStorage;
import bt.dht.DHTConfig;
import bt.dht.DHTModule;
import bt.runtime.BtClient;
import bt.runtime.Config;
import bt.torrent.TorrentSessionState;

import com.alee.laf.WebLookAndFeel;
import com.google.inject.Module;
import com.lordroid.cupcake.res.S;
import com.lordroid.cupcake.utils.PathUtils;
import com.sun.jna.NativeLibrary;

/**
 * Hello world!
 * 
 */
public class App {
	private static Logger LOGGER = LoggerFactory.getLogger(App.class);

	private static boolean initVlcJ() {
		String ourLocation = PathUtils.getExcutionPath();
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				ourLocation + "/" + S.NATIVE_LIB_FOLDER);
		LOGGER.info("added " + ourLocation + File.separator
				+ S.NATIVE_LIB_FOLDER + " to search path");
		// boolean found = new NativeDiscovery().discover();

		LOGGER.info("Libvlc found ! " + LibVlc.INSTANCE.libvlc_get_version());

		// LOGGER.error("Libvlc not found player will not work ! make sure native library is present in native folder ! ");
		// TODO : find a better way to report found or not !
		return true;
	}

	public static void main(String[] args) {
		// try {
		// // Set System L&F
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// } catch (UnsupportedLookAndFeelException e) {
		// // handle exception
		// } catch (ClassNotFoundException e) {
		// // handle exception
		// } catch (InstantiationException e) {
		// // handle exception
		// } catch (IllegalAccessException e) {
		// // handle exception
		// }
		WebLookAndFeel.install();
		WebLookAndFeel.initializeManagers();
		new NativeDiscovery().discover();
		LOGGER.info("initializing libvlc...");
		new Frame();
		// initVlcJ();
		// new MainFram();
		// System.setProperty("java.net.preferIPv4Stack", "true");
		// try {
		// testBT();
		// } catch (MalformedURLException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

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
		BtClient client = Bt.client().config(config)
				.storage(storage)
				// .magnet("magnet:?xt=urn:btih:af0d9aa01a9ae123a73802cfa58ccaf355eb19f1")
				.torrent(new File("a.torrent").toURI().toURL())
				.autoLoadModules().module(dhtModule).stopWhenDownloaded()
				.sequentialSelector().build();

		// launch
		// client.startAsync().join();

		client.startAsync(new Consumer<TorrentSessionState>() {

			public void accept(TorrentSessionState arg0) {
				// TODO Auto-generated method stub
				System.out.println("downloaded = "
						+ ((double) arg0.getPiecesComplete() / (double) arg0
								.getPiecesTotal()) * 100 + " %");
				System.out.println("uploaded = " + arg0.getUploaded());
				System.out.println("downloaded  = " + arg0.getDownloaded());

			}

		}, 1000L).join();
	}

}
