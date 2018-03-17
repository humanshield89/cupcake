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

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.TrackDescription;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

import com.lordroid.cupcake.Canvasx;
import com.lordroid.cupcake.controlers.Watchable;
import com.lordroid.cupcake.controlers.Watcher;
import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.S;
import com.lordroid.cupcake.res.Settings;
import com.lordroid.cupcake.utils.FileDrop;
import com.lordroid.cupcake.utils.StringUtils;
import com.lordroid.cupcake.utils.TimeUtils;

public class MediaPlayer extends JPanel implements Watchable, Watcher {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Logger LOGGER = LoggerFactory.getLogger(MediaPlayer.class);


	private final JFrame frame;
	static String[] argument_libvlc = { "--subsdec-encoding="
			+ Settings.getDefaultSubtittleEncoding() };

	private static final MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(
			argument_libvlc);
	/**
	 * @return the mediaplayerfactory
	 */
	public static MediaPlayerFactory getMediaplayerfactory() {
		return mediaPlayerFactory;
	}

	private static final EmbeddedMediaPlayer mediaPlayer = mediaPlayerFactory
			.newEmbeddedMediaPlayer();
	private Canvasx mediaPlayerComponent = new Canvasx(mediaPlayer);
	private CanvasVideoSurface videoSurface = mediaPlayerFactory
			.newVideoSurface(mediaPlayerComponent);

	private ArrayList<Watcher> watchers = new ArrayList<Watcher>();

	private File video;
	private ControlPanel controlPanel = new ControlPanel();
	private int newMedia = 0;
	private long lastSkip = 0L;
	private long lastSkipTime = 0L;
	private long lastRewind = 0L;
	private long lastRewindTime = 0L;

	private String currentTitle = "No media";

	private boolean wasPlaying;
	private boolean mouseOncontrol = false;

	
	public MediaPlayer(JFrame frame) {
		this.frame = frame;

		this.setLayout(new BorderLayout());
		mediaPlayer.setVideoSurface(videoSurface);
		mediaPlayerComponent.setVideoSurface(videoSurface);

		this.add(mediaPlayerComponent, BorderLayout.CENTER);
		this.add(controlPanel, BorderLayout.SOUTH);
		mediaPlayerComponent.getMediaPlayer().setFullScreenStrategy(
				new DefaultAdaptiveRuntimeFullScreenStrategy(frame) {
					@Override
					protected void beforeEnterFullScreen() {
						// controlPanel.setVisible(false);
						// statusBar.setVisible(false);
					}

					@Override
					protected void afterExitFullScreen() {
						controlPanel.setVisible(true);
						// statusBar.setVisible(true);
					}

				});

		initActionListners();
		this.addWatcher(controlPanel);
		controlPanel.addWatcher(this);

		// TODO : fix this srt , mp4
		new FileDrop(this, new FileDrop.Listener() {

			public void filesDropped(File[] files) {
				boolean video = false;
				boolean subtitle = false;
				int i = 0;
				while (i < files.length) {

					if (StringUtils.endsWith(files[i].getName(), S.VID_EXT)) {
						video = true;
						break;
					}
					i++;
				}
				int n = 0;
				while (n < files.length) {
					if (StringUtils.endsWith(files[n].getName(), S.SUB_EXT)) {
						subtitle = true;
						break;
					}
					n++;
				}

				if (video) {
					setVideo(files[i].getAbsolutePath());
					play();
				}
				if (subtitle) {
					setSubtitle(files[n]);
				}
			}

		});
		this.mediaPlayerComponent.getMediaPlayer().setVolume(
				Settings.getCurrentVolume());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.controlers.Watchable#addWatcher(com.lordroid.cupcake
	 * .controlers.Watcher)
	 */
	public void addWatcher(Watcher wcher) {
		this.watchers.add(wcher);
	}

	/**
	 * @return the mediaPlayerComponent
	 */
	public Canvasx getMediaPlayerComponent() {
		return mediaPlayerComponent;
	}

	/**
	 * @return the video
	 */
	public File getVideo() {
		return video;
	}

	private void showControls(boolean bool) {

		controlPanel.setVisible(bool);
	}

	private void initActionListners() {

		mediaPlayer.setEnableMouseInputHandling(false);
		mediaPlayer.setEnableKeyInputHandling(false);
		this.mediaPlayerComponent.addMouseListener(new ClickListener() {
			@Override
			public void singleClick(MouseEvent e) {
				LOGGER.info("click");
				if (mediaPlayerComponent.getMediaPlayer().isPlaying()) {
					pause();
				} else {
					play();
				}
			}

			@Override
			public void doubleClick(MouseEvent e) {
				LOGGER.info("D-click");
				fullScreen();
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mouseOncontrol = false;
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				mouseOncontrol = true;
			}

		});

		new MouseMovementDetector(mediaPlayerComponent, 3000) {
			@Override
			protected void onStarted() {
			}

			@Override
			protected void onMouseAtRest() {
				if (mediaPlayer.isPlaying() && !mouseOncontrol) {
					showControls(false);
					BufferedImage cursorImg = new BufferedImage(16, 16,
							BufferedImage.TYPE_INT_ARGB);

					Cursor blankCursor = Toolkit.getDefaultToolkit()
							.createCustomCursor(cursorImg, new Point(0, 0),
									"blank cursor");
					mediaPlayerComponent.setCursor(blankCursor);
				}
			}

			@Override
			protected void onMouseMoved() {
				showControls(true);
				mediaPlayerComponent.setCursor(Cursor.getDefaultCursor());
			}

			@Override
			protected void onStopped() {
			}
		}.start();

		mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(
				new MediaPlayerEventListener() {

					public void audioDeviceChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							String arg1) {
						// TODO Auto-generated method stub

					}

					public void backward(
							uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void buffering(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							float arg1) {
						// TODO Auto-generated method stub

					}

					public void chapterChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void corked(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							boolean arg1) {
						// TODO Auto-generated method stub

					}

					public void elementaryStreamAdded(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					public void elementaryStreamDeleted(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					public void elementaryStreamSelected(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					public void endOfSubItems(
							uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void error(uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void finished(
							uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub
						stop();
					}

					public void forward(
							uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void lengthChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							long arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							libvlc_media_t arg1, String arg2) {
						// TODO Auto-generated method stub
						// App.LOGGER.info("total time is  "+mediaPlayerComponent.getMediaPlayer().getLength());

					}

					public void mediaDurationChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							long arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaFreed(
							uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void mediaMetaChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub
						currentTitle = " | " + arg0.getMediaMeta().getTitle();
						frame.setTitle("Cupcake " + currentTitle);
					}

					public void mediaParsedChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaStateChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaSubItemAdded(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							libvlc_media_t arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaSubItemTreeAdded(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							libvlc_media_t arg1) {
						// TODO Auto-generated method stub

					}

					public void muted(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							boolean arg1) {
						// TODO Auto-generated method stub

					}

					public void newMedia(
							uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void opening(
							uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub
						currentTitle = arg0.getMediaMeta().getTitle();
						frame.setTitle("Cupcake " + currentTitle + " | Opening");
					}

					public void pausableChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void paused(
							uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub
						controlPanel.getPlayBtn().setIcon(
								new ImageIcon(R.PLAY_BTN_ICON));
						frame.setTitle("Cupcake " + currentTitle
								+ S.MEDIA_PAUSED);

					}

					public void playing(
							uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub
						controlPanel.getPlayBtn().setIcon(
								new ImageIcon(R.PAUSE_BTN_ICON));
						frame.setTitle("Cupcake " + currentTitle
								+ S.MEDIA_PLAYED);
					}

					public void positionChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							float arg1) {
						// TODO Auto-generated method stub
						LOGGER.debug("current value in slider is "
								+ controlPanel.getProgress().getValue());

					}

					public void scrambledChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void seekableChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void snapshotTaken(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							String arg1) {
						// TODO Auto-generated method stub

					}

					public void stopped(
							uk.co.caprica.vlcj.player.MediaPlayer arg0) {
						// TODO Auto-generated method stub
						frame.setTitle("Cupcake " + currentTitle
								+ S.MEDIA_STOPED);
					}

					public void subItemFinished(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void subItemPlayed(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public synchronized void timeChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							long arg1) {
						// TODO Auto-generated method stub
						// long mili = arg0.getTime();
						// int minutes = (int) ((mili/60));.
						int seconds = (int) (mediaPlayerComponent
								.getMediaPlayer().getTime() / 1000);
						// App.LOGGER.info("total time is  "+mediaPlayerComponent.getMediaPlayer().getLength());
						// App.LOGGER.info("current time is  "+mediaPlayerComponent.getMediaPlayer().getTime());
						controlPanel.getProgress().setValue(seconds);
						controlPanel
								.getCurrentTimeLab()
								.setText(
										TimeUtils
												.getLabelFormatedTime(mediaPlayerComponent
														.getMediaPlayer()
														.getTime()));
					}

					public void titleChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void videoOutput(
							uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void volumeChanged(
							uk.co.caprica.vlcj.player.MediaPlayer arg0,
							float arg1) {
						// TODO Auto-generated method stub
						controlPanel.getCurrentVolume().setText(
								"" + arg0.getVolume());
					}

				});
	}

//	private boolean isMouseOnControl() {
//		for (int i = 0 ; i < mouseOncontrol.length ; i++) {
//			if(mouseOncontrol[i]){
//				return true;
////			}
//		}
//		return false;
//	}
	
	private void pause() {
		this.mediaPlayerComponent.getMediaPlayer().pause();
	}

	private void stop() {
		this.mediaPlayerComponent.getMediaPlayer().stop();
		controlPanel.getProgress().setValue(0);
		controlPanel.getPlayBtn().setIcon(new ImageIcon(R.PLAY_BTN_ICON));
		controlPanel.getCurrentTimeLab().setText("  00:00  ");
	}

	private void play() {
		mediaPlayerComponent.getMediaPlayer().setVolume(
				controlPanel.getVolumeControl().getValue() * 2);
		if (this.newMedia == 0) {
			// this.mediaPlayerComponent.getMediaPlayer().
			LOGGER.info(new File("G:/a.srt").getAbsolutePath());
			this.mediaPlayerComponent.getMediaPlayer().playMedia(
					this.video.getAbsolutePath());
			// this.mediaPlayerComponent.getMediaPlayer().setSubTitleFile(new
			// File("G:/a.srt"));

			// need this to get the length for some reason ?
			// need to wait for the vlcj to get media informations
			// the wait varries depending on the media size and encoding
			long i = this.mediaPlayerComponent.getMediaPlayer().getLength();
			while (i <= 0) {
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i = this.mediaPlayerComponent.getMediaPlayer().getLength();
			}

			LOGGER.info("total time is  "
					+ mediaPlayerComponent.getMediaPlayer().getLength());
			int seconds = (int) (this.mediaPlayerComponent.getMediaPlayer()
					.getLength() / 1000);
			controlPanel.getProgress().setMinimum(0);
			controlPanel.getProgress().setValue(0);
			LOGGER.info("setting max to " + seconds);
			controlPanel.getProgress().setMaximum(seconds);
			// controlPanel.getCurrentTimeLab().setText("  00:00  ");
			// controlPanel.getTotalTimeLab().setText(TimeUtils.getLabelFormatedTime(seconds*1000));
			controlPanel.getTotalTimeLab().setText(
					TimeUtils.getLabelFormatedTime(mediaPlayerComponent
							.getMediaPlayer().getLength()));

			newMedia = 1;

		} else {
			this.mediaPlayerComponent.getMediaPlayer().play();
		}
				
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.controlers.Watchable#remove(com.lordroid.cupcake
	 * .controlers.Watcher)
	 */
	public void remove(Watcher w) {
		this.watchers.remove(w);
	}

	/**
	 * 
	 */
	private void rewind() {
		// TODO Auto-generated method stub
		long rewindBack = 1000;
		if (System.currentTimeMillis() - this.lastRewindTime < 800)
			rewindBack = (this.lastRewind * 6) / 5;

		this.mediaPlayerComponent.getMediaPlayer().skip(-rewindBack);
		this.lastRewind = rewindBack;
		this.lastRewindTime = System.currentTimeMillis();
	}

	/**
	 * @param str
	 *            the video to set
	 */
	public void setVideo(String str) {
		this.newMedia = 0;
		this.video = new File(str);
		LOGGER.info("video set to " + str);
		// this.mediaPlayerComponent.getMediaPlayer().setMedia(this.video.getAbsolutePath());
		// App.LOGGER.info("total time is  "+mediaPlayerComponent.getMediaPlayer().getLength());

		// this.mediaPlayerComponent.getMediaPlayer().setV
	}

	/**
	 * sets the subtitle file ;
	 * 
	 * @param file
	 */
	private void setSubtitle(File file) {
		// TODO Auto-generated method stub
		mediaPlayerComponent.getMediaPlayer().setSubTitleFile(file);
		for (MediaMeta m : mediaPlayerComponent.getMediaPlayer()
				.getSubItemMediaMeta()) {
			LOGGER.info(m.getLanguage());
		}
		for (TrackDescription o : mediaPlayerComponent.getMediaPlayer()
				.getSpuDescriptions()) {
			LOGGER.info(o.description());
		}
	}

	/**
	 * 
	 */
	private void skip() {
		// TODO Auto-generated method stub
		long skipAhead = 1000;
		if (System.currentTimeMillis() - this.lastSkipTime < 800)
			skipAhead = (this.lastSkip * 6) / 5;

		this.mediaPlayerComponent.getMediaPlayer().skip(skipAhead);
		this.lastSkip = skipAhead;
		this.lastSkipTime = System.currentTimeMillis();
	}

	/**
	 * @param wble
	 *            : the source of the update
	 * @param message
	 *            : message of update see @com.lordroid.cupcake.res.S.java for
	 *            messages infos
	 * 
	 * @see com.lordroid.cupcake.controlers.Watcher#updateHundler(com.lordroid.cupcake
	 *      .controlers.Watchable, java.lang.String)
	 */
	public void updateHundler(Watchable wble, int message) {
		if (message == S.PLAY_BTN_PRESSED) {
			if (!mediaPlayerComponent.getMediaPlayer().isPlaying())
				play();
			else
				pause();
		} else if (message == S.SKIP_BTN_PRESSED) {

			skip();
		} else if (message == S.REWIND_BTN_PRESSED) {

			rewind();

		} else if (message == S.TIMER_SLIDED) {
			pause();
			setTime();
			//play();
		} else if (message == S.TIMER_PRESSED) {
			if (mediaPlayerComponent.getMediaPlayer().isPlaying()) {
				pause();
				wasPlaying = true;
			} else {
				wasPlaying = false;
			}
			setTime();
		} else if (message == S.TIMER_RELEASED) {
			setTime();
			if (wasPlaying)
				play();
		} else if (message == S.TIMER_CLICKED) {
			if (mediaPlayerComponent.getMediaPlayer().isPlaying()) {
				pause();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				setTime();
				play();
			} else {
				setTime();
			}
		} else if (message == S.VOLUME_MUTE_PRESSED) {
			mediaPlayerComponent.getMediaPlayer().mute(
					!mediaPlayerComponent.getMediaPlayer().isMute());
			setVolumeIcon();
		} else if (message == S.VOLUME_UP_DOWN_PRESSED) {
			mediaPlayerComponent.getMediaPlayer().setVolume(
					controlPanel.getVolumeControl().getValue() * 2);
			mediaPlayerComponent.getMediaPlayer().mute(false);
			setVolumeIcon();
		} else if (message == S.FULL_SCREEN_BTN_PRESSED) {
			fullScreen();
		}
	}

	/**
	 * 
	 */
	private void setVolumeIcon() {
		// mediaPlayerComponent.getMediaPlayer().setVolume(200);
		// TODO Auto-generated method stub
		int volume = mediaPlayerComponent.getMediaPlayer().getVolume();
		LOGGER.info("current volume =  " + volume);
		if (mediaPlayerComponent.getMediaPlayer().isMute()) {
			controlPanel.getVolumeBtn().setIcon(new ImageIcon(R.VOLUME_MUTED));
		} else {

			if (volume >= 200) {
				controlPanel.getVolumeBtn().setIcon(
						new ImageIcon(R.VOLUME_FULL));
			} else if (volume >= 100) {
				controlPanel.getVolumeBtn().setIcon(
						new ImageIcon(R.VOLUME_MEDIUM));
			} else if(volume > 0){
				controlPanel.getVolumeBtn().setIcon(
						new ImageIcon(R.VOLUME_LOW));
			} else {
				controlPanel.getVolumeBtn().setIcon(
						new ImageIcon(R.VOLUME_MUTED));
			}
		}
		controlPanel.getCurrentVolume().setText("" + (volume / 2));
		Settings.setCurrentVolume();
	}

	/**
	 * 
	 */
	private void setTime() {
		// TODO Auto-generated method stub
		LOGGER.info(this.controlPanel.getProgress().getValue() + "");
		this.mediaPlayerComponent.getMediaPlayer().setTime(
				this.controlPanel.getProgress().getValue() * 1000);
		this.controlPanel.getCurrentTimeLab().setText(
				TimeUtils.getLabelFormatedTime(this.mediaPlayerComponent
						.getMediaPlayer().getTime()));
	}

	private void fullScreen() {
		// TODO Auto-generated method stub
		if (this.mediaPlayerComponent.getMediaPlayer().isFullScreen()) {
			controlPanel.getFullScreen().setIcon(new ImageIcon(R.FULL_SCREEN));
			this.mediaPlayerComponent.getMediaPlayer().setFullScreen(false);
		} else {
			controlPanel.getFullScreen().setIcon(
					new ImageIcon(R.EXIT_FULL_SCREEN));
			this.mediaPlayerComponent.getMediaPlayer().setFullScreen(true);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lordroid.cupcake.controlers.Watchable#updateWatchers()
	 */
	public void updateWatchers(int message) {
		// TODO Auto-generated method stub

	}

}
