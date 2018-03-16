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
package com.lordroid.cupcake.player;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingWorker;

import com.lordroid.cupcake.controlers.Watchable;
import com.lordroid.cupcake.controlers.Watcher;

/**
 * @author HumanShield85
 * 
 */
class BackgroundClickHandler extends SwingWorker<Integer, Integer> implements
		Watchable {
	private ArrayList<Watcher> watchers = new ArrayList<Watcher>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.controlers.Watchable#addWatcher(com.lordroid.cupcake
	 * .controlers.Watcher)
	 */
	public void addWatcher(Watcher wcher) {
		// TODO Auto-generated method stub
		this.watchers.add(wcher);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.controlers.Watchable#remove(com.lordroid.cupcake
	 * .controlers.Watcher)
	 */
	public void remove(Watcher w) {
		// TODO Auto-generated method stub
		this.watchers.remove(w);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lordroid.cupcake.controlers.Watchable#updateWatchers(int)
	 */
	public void updateWatchers(int message) {
		// TODO Auto-generated method stub
		for (Watcher w : watchers) {
			w.updateHundler(this, message);
		}
	}

	@Override
	protected Integer doInBackground() throws Exception {
		Thread.sleep(500);
		updateWatchers(9999);
		// Do what you want with single click.
		return 0;
	}

}

public class ClickListener extends MouseAdapter implements Watcher {

	private BackgroundClickHandler clickHandler;
	private MouseEvent eventDummy = null;

	@Override
	public void mouseClicked(MouseEvent e) {
		eventDummy = e;
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (e.getClickCount() == 1) {
				// We create a new instance everytime since
				// the execute() method is designed to be executed once
				clickHandler = new BackgroundClickHandler();
				clickHandler.addWatcher(this);
				try {
					clickHandler.execute();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			if (e.getClickCount() == 2) {
				clickHandler.cancel(true);
				doubleClick(e);
				// Do what you want with double click
			}
		}

	}

	public void singleClick(MouseEvent e) {
	}

	public void doubleClick(MouseEvent e) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.controlers.Watcher#updateHundler(com.lordroid.cupcake
	 * .controlers.Watchable, int)
	 */
	public void updateHundler(Watchable wble, int message) {
		// TODO Auto-generated method stub
		singleClick(eventDummy);
	}

}
