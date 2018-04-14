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
import java.io.IOException;

import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.binding.LibVlc;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import com.alee.laf.WebLookAndFeel;
import com.lordroid.cupcake.res.S;
import com.lordroid.cupcake.ui.MainFram;
import com.lordroid.cupcake.utils.PathUtils;
import com.sun.jna.NativeLibrary;

import de.sciss.submin.Submin;

/**
 * Hello world!
 * 
 */
public class App {
	public static Logger LOGGER = LoggerFactory.getLogger(App.class);

	private static void setSysPropreties() {
		System.setProperty(
				"http.agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.29 Safari/537.36");
		//WebLookAndFeel.install();
		//WebLookAndFeel.initializeManagers();
		Submin.install(true);
		
	}

	private static boolean initVlcJ() {
		String ourLocation = PathUtils.getExcutionPath();
		System.setProperty(
				"VLC_PLUGIN_PATH",
				new File(ourLocation +  File.separator + S.NATIVE_LIB_FOLDER + File.separator + "plugins").getAbsolutePath());
		NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(),
				new File(ourLocation +  File.separator + S.NATIVE_LIB_FOLDER).getAbsolutePath());
		LOGGER.info("added " + ourLocation + File.separator
				+ S.NATIVE_LIB_FOLDER + " to search path");

		// boolean found = new NativeDiscovery().discover();

		LOGGER.info("Libvlc found ? " + LibVlc.INSTANCE.libvlc_get_version());

		// TODO : find a better way to report found or not !
		return true;
	}

	public static void main(String[] args) throws IOException,
			InterruptedException {
		setSysPropreties();
		System.out.println(PathUtils.getExcutionPath());
		

		new NativeDiscovery().discover();
		// LOGGER.info("initializing libvlc...");
		// TODO
		//initVlcJ();
		 SwingUtilities.invokeLater(new Runnable() {
		
		 public void run() {
		 // TODO Auto-generated method stub
			 	JPopupMenu.setDefaultLightWeightPopupEnabled(false);
				MainFram frame = new MainFram();
				
				//frame.getContentPane().revalidate();

				//frame.movieListPan.revalidate();
				//frame.movieListPan.search();
				}
		
		 });
		 
	}

}
