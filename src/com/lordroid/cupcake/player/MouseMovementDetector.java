/*
 * This file is part of VLCJ.
 *
 * VLCJ is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * VLCJ is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with VLCJ.  If not, see <http://www.gnu.org/licenses/>.
 *
 * Copyright 2015 Caprica Software Limited.
 */

package com.lordroid.cupcake.player;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

import javax.swing.Timer;

public abstract class MouseMovementDetector {

	private class ActivityListener extends MouseMotionAdapter {
		int lastX = 0;
		int lastY = 0;

		@Override
		public void mouseMoved(MouseEvent e) {
			int x = e.getXOnScreen();
			int y = e.getYOnScreen();
			int dX;
			int dY;
			dX = (x > lastX) ? (x - lastX) : (lastX - x);
			dY = (y > lastY) ? (y - lastY) : (lastY - x);
			if (dX > 5 || dY > 5) {
				movement();
			}
			lastX = x;
			lastY = y;
		}
	}

	private final Component component;

	private final int timeout;

	private final MouseMotionListener mouseMotionListener = new ActivityListener();

	private Timer timer;

	private boolean started;

	private boolean moving;

	public MouseMovementDetector(Component component, int timeout) {
		this.component = component;
		this.timeout = timeout;
	}

	private void movement() {
		if (!moving) {
			moving = true;
			onMouseMoved();
		}
		timer.restart();
	}

	protected void onMouseAtRest() {
	}

	protected void onMouseMoved() {
	}

	protected void onStarted() {
	}

	protected void onStopped() {
	}

	public void start() {
		if (!started) {
			timer = new Timer(timeout, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					timeout();
				}
			});
			component.addMouseMotionListener(mouseMotionListener);
			timer.start();
			timer.setRepeats(false);
			started = true;
			onStarted();
		} else {
			throw new IllegalStateException("Already started");
		}
	}

	public void stop() {
		if (started) {
			component.removeMouseMotionListener(mouseMotionListener);
			timer.stop();
			timer = null;
			started = false;
			onStopped();
		}
	}

	private void timeout() {
		moving = false;
		onMouseAtRest();
	}
}
