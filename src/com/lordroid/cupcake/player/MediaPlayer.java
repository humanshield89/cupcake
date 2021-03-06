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
import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;

import org.apache.xmlrpc.XmlRpcException;

import uk.co.caprica.vlcj.binding.internal.libvlc_marquee_position_e;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.DefaultAdaptiveRuntimeFullScreenStrategy;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import Opensubs.SubtitleInfo;
import bt.torrent.TorrentSessionState;

import com.lordroid.cupcake.App;
import com.lordroid.cupcake.bt.YifyMovieTorrent;
import com.lordroid.cupcake.controlers.Watchable;
import com.lordroid.cupcake.controlers.Watcher;
import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.res.S;
import com.lordroid.cupcake.res.Settings;
import com.lordroid.cupcake.ui.BufferingPanel;
import com.lordroid.cupcake.utils.FileDrop;
import com.lordroid.cupcake.utils.FileUtils;
import com.lordroid.cupcake.utils.StringUtils;
import com.lordroid.cupcake.utils.SubtitleFetcher;
import com.lordroid.cupcake.utils.TimeUtils;
import com.lordroid.cupcake.yify.YifyMovie;

public class MediaPlayer extends JPanel implements Watchable, Watcher,
		Consumer<TorrentSessionState>, MediaPlayerEventListener, MediaPlayerImp {
	/**
	 * 
	 */
	public static final int SOURCE_NO_MEDIA = 0;
	public static final int SOURCE_YIFY_TORRENT = 1;
	public static final int SOURCE_LOCAL_TORRENT = 2;
	public static final int SOURCE_VIDEO = 3;
	public static final String CARD_PLAYER = "Media Player";
	public static final String CARD_BUFFER_TORRENT = "BUFERING TORRENT";

	public YifyMovieTorrent torrent;
	boolean TorrentStartedPlaying = false;

	private static final long serialVersionUID = 1L;
	// private static Logger LOGGER =
	// LoggerFactory.getLogger(MediaPlayer.class);
	private final BufferingPanel buffPan = new BufferingPanel();
	private JFrame frame;
	private boolean fullScreenStrategySet = false;
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

	private int currentlyPlayingSource = 0;
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

	private long torrentStartTime = 0L;

	private String currentTitle = "No media";

	private boolean wasPlaying;
	private boolean mouseOncontrol = false;

	// cardlayourt
	JPanel cards = new JPanel();

	// POPUP MENU
	JPopupMenu menu = new JPopupMenu();
	JMenu subtitlesMenu = new JMenu("Subtitles");
	JMenu onlineSubMenu = new JMenu("Online Subtitles");
	JMenu localSubsMenu = new JMenu("Local Subtitles");
	JMenu lang1Menu = new JMenu(
			SubtitleFetcher.SUBTITLE_LANGUAGES_NAMES[Settings
					.getSubtitlesLang1()]);
	JMenu lang2Menu = new JMenu(
			SubtitleFetcher.SUBTITLE_LANGUAGES_NAMES[Settings
					.getSubtitlesLang2()]);
	JMenu lang3Menu = new JMenu(
			SubtitleFetcher.SUBTITLE_LANGUAGES_NAMES[Settings
					.getSubtitlesLang3()]);
	JMenuItem localSubPicker = new JMenuItem("Choose local Subtitle file");
	@SuppressWarnings("serial")
	JRadioButtonMenuItem disableSubsMenuItem = new JRadioButtonMenuItem(
			"Disable Subtitles") {
		{
			this.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					setSubtitle(null);
				}

			});
		}
	};
	ButtonGroup subtitlesGroup = new ButtonGroup();

	Thread subtitleWorker;

	public MediaPlayer(JFrame frame) {
		this.frame = frame;
		menu.setLightWeightPopupEnabled(false);
		menu.add(subtitlesMenu);
		subtitlesMenu.add(onlineSubMenu);
		onlineSubMenu.add(lang1Menu);
		onlineSubMenu.add(lang2Menu);
		onlineSubMenu.add(lang3Menu);
		subtitlesMenu.add(localSubsMenu);
		subtitlesMenu.add(localSubPicker);
		subtitlesMenu.add(disableSubsMenuItem);

		disableSubsMenuItem.setSelected(true);
		rebuildSubGroupButtons();

		this.setLayout(new BorderLayout());
		mediaPlayer.setVideoSurface(videoSurface);
		mediaPlayerComponent.setVideoSurface(videoSurface);

		JPanel mediaPlayerContainerPan = new JPanel();
		mediaPlayerContainerPan.setLayout(new BorderLayout());
		JPanel bufferingPanelCOntainer = new JPanel();
		bufferingPanelCOntainer.setLayout(new BorderLayout());
		bufferingPanelCOntainer.add(buffPan, BorderLayout.CENTER);
		mediaPlayerContainerPan.add(mediaPlayerComponent, BorderLayout.CENTER);
		mediaPlayerContainerPan.add(controlPanel, BorderLayout.SOUTH);
		cards.setLayout(new CardLayout());

		cards.add(mediaPlayerContainerPan, CARD_PLAYER);
		cards.add(bufferingPanelCOntainer, CARD_BUFFER_TORRENT);
		this.add(cards, BorderLayout.CENTER);

		initActionListners();
		this.addWatcher(controlPanel);
		controlPanel.addWatcher(this);

		// TODO : fix this srt , mp4
		new FileDrop(this, new FileDrop.Listener() {

			@Override
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
					setMediaFromLocalVideo(files[i]);
					play();
				}
				if (subtitle) {
					setSubtitle(files[n]);
				}
			}

		});
		this.mediaPlayerComponent.getMediaPlayer().setVolume(
				Settings.getCurrentVolume() * 2);

		setPlayerView();
	}

	@Override
	public void accept(TorrentSessionState arg0) {

		// arg0.get
		long totalSize = this.torrent.getYiFyTorrent().getSizeInBytes();
		int totalNumberOfpeices = arg0.getPiecesTotal();
		int downloadedPeices = arg0.getPiecesComplete();
		long downloadedSize = (downloadedPeices * totalSize)
				/ totalNumberOfpeices;
		long downSizeForSpeedCal = arg0.getDownloaded();
		int peers = arg0.getConnectedPeers().size();
		long totalTime = System.currentTimeMillis() - this.torrentStartTime;
		double speed = ((double) downSizeForSpeedCal / 1024)
				/ (totalTime / 1000);
		String[] values = { "" + peers, "" + speed,
				"" + downloadedSize / 1024 / 1024 };
		this.buffPan.updateValues(values);
		// System.out.println("peers = "+peers+"    speed = "+speed+"    downloaded = "+downloadedSize+" time = "+totalTime);
		// total peices ===> torrent.getTorrent.getSize(
		// all downloaded ===> ?
		// / ? = downloaded * size / peices;

		// System.out.println("total peices : "+arg0.getPiecesTotal()+"downloaded  "+arg0.getDownloaded());
		if (!TorrentStartedPlaying) {
			if ((totalTime >= 60000 && downloadedSize >= totalSize * 4 / 100)
					|| (downloadedSize / 1024 / 1024 >= 20)) {
				this.setCurrentVideo(this.torrent.getVideo().getAbsolutePath());

				this.setPlayerView();
				this.play();
				TorrentStartedPlaying = true;
				loadRemoteSubtitles();
			}
		} else {
			// remining = total - downloaded
			// speed
			// needed time = reminging / speed
			// time remining on movie = player.getSIze
			int movieLenght = torrent.getMovie().getRuntime() * 60; // seconds
			long remining = totalSize - downloadedSize;
			long neededTime = (long) (remining / (speed * 1024));
			long timeleftonTheMovie = movieLenght
					- mediaPlayerComponent.getMediaPlayer().getTime() / 1000;
			if (neededTime > timeleftonTheMovie) {
				App.LOGGER.info("we should pause" + "    remining time = "
						+ remining + "   remining on movie = "
						+ timeleftonTheMovie);
			} else {
				App.LOGGER.info("no need to pause" + "    remining time = "
						+ remining + "   remining on movie = "
						+ timeleftonTheMovie);
			}

		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.controlers.Watchable#addWatcher(com.lordroid.cupcake
	 * .controlers.Watcher)
	 */
	@Override
	public void addWatcher(Watcher wcher) {
		this.watchers.add(wcher);
	}

	@Override
	public void audioDeviceChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			String arg1) {

	}

	@Override
	public void backward(uk.co.caprica.vlcj.player.MediaPlayer arg0) {

	}

	@Override
	public void buffering(uk.co.caprica.vlcj.player.MediaPlayer arg0, float arg1) {

	}

	/**
	 * @param i
	 */
	protected void changeVolume(int value) {
		setDefaultMarqueeProperties();
		controlPanel.getVolumeControl().setValue(value);
		mediaPlayerComponent.getMediaPlayer().setMarqueeText(
				"Volume : " + controlPanel.getVolumeControl().getValue());
		mediaPlayerComponent.getMediaPlayer().enableMarquee(true);
		setVolumeIcon();
	}

	@Override
	public void chapterChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			int arg1) {

	}

	@Override
	public void corked(uk.co.caprica.vlcj.player.MediaPlayer arg0, boolean arg1) {

	}

	@Override
	public void elementaryStreamAdded(
			uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1, int arg2) {

	}

	@Override
	public void elementaryStreamDeleted(
			uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1, int arg2) {

	}

	@Override
	public void elementaryStreamSelected(
			uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1, int arg2) {

	}

	@Override
	public void endOfSubItems(uk.co.caprica.vlcj.player.MediaPlayer arg0) {

	}

	@Override
	public void error(uk.co.caprica.vlcj.player.MediaPlayer arg0) {
		frame.setTitle("Cupcake " + "| PlayBack Error");
		stop();
	}

	@Override
	public void finished(uk.co.caprica.vlcj.player.MediaPlayer arg0) {
		stop();
	}

	@Override
	public void forward(uk.co.caprica.vlcj.player.MediaPlayer arg0) {

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

	/**
	 * @return the mediaPlayerComponent
	 */
	public Canvasx getMediaPlayerComponent() {
		return mediaPlayerComponent;
	}

	private void getRemoteSubs(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		final MediaPlayer mediaPlayer2 = arg0;
		try {
			subtitleWorker.interrupt();
		} catch (Exception e) {

		}

		subtitleWorker = new Thread(new Runnable() {
			boolean subtitleSet = false;

			@Override
			public void run() {

				List<SubtitleInfo> subInfoList1 = null;
				List<SubtitleInfo> subInfoList2 = null;
				List<SubtitleInfo> subInfoList3 = null;

				// lang 1
				try {
					subInfoList1 = SubtitleFetcher.getSubtitleList(video,
							SubtitleFetcher.SUBTITLE_LANGUAGES_CODES[Settings
									.getSubtitlesLang1()]);
				} catch (XmlRpcException e) {
					e.printStackTrace();
				}
				if (subInfoList1 != null)
					for (int i = 0; i < subInfoList1.size(); i++) {
						SubtitleInfo subInfo = subInfoList1.get(i);
						SubtitleMenuItem subItem = new SubtitleMenuItem(
								subInfo, mediaPlayer2);
						lang1Menu.add(subItem);
						subtitlesGroup.add(subItem);
						if (i == 0 && !subtitleSet
								&& Settings.autoLoadSubtitles()) {
							subItem.actionPerformed(new ActionEvent(subItem, 0,
									"dummy"));
							subtitleSet = true;
						}
					}
				// lang 2
				try {
					subInfoList2 = SubtitleFetcher.getSubtitleList(video,
							SubtitleFetcher.SUBTITLE_LANGUAGES_CODES[Settings
									.getSubtitlesLang2()]);
				} catch (XmlRpcException e) {
					e.printStackTrace();
				}
				if (subInfoList2 != null)
					for (int i = 0; i < subInfoList2.size(); i++) {
						SubtitleInfo subInfo = subInfoList2.get(i);
						SubtitleMenuItem subItem = new SubtitleMenuItem(
								subInfo, mediaPlayer2);
						lang2Menu.add(subItem);
						subtitlesGroup.add(subItem);
						if (i == 0 && !subtitleSet
								&& Settings.autoLoadSubtitles()) {
							subItem.actionPerformed(new ActionEvent(subItem, 0,
									"dummy"));
							subtitleSet = true;
						}
					}
				// lang3
				try {
					subInfoList3 = SubtitleFetcher.getSubtitleList(video,
							SubtitleFetcher.SUBTITLE_LANGUAGES_CODES[Settings
									.getSubtitlesLang3()]);
				} catch (XmlRpcException e) {
					e.printStackTrace();
				}
				if (subInfoList3 != null)
					for (int i = 0; i < subInfoList3.size(); i++) {
						SubtitleInfo subInfo = subInfoList3.get(i);
						SubtitleMenuItem subItem = new SubtitleMenuItem(
								subInfo, mediaPlayer2);
						lang3Menu.add(subItem);
						subtitlesGroup.add(subItem);
						if (i == 0 && !subtitleSet
								&& Settings.autoLoadSubtitles()) {
							subItem.actionPerformed(new ActionEvent(subItem, 0,
									"dummy"));
							subtitleSet = true;
						}
					}
			}

		});
		subtitleWorker.start();
	}

	private void getSubtitle(YifyMovieTorrent arg) {
		// TODO
		try {
			boolean defaultSubSelected = false;
			List<SubtitleInfo> list1 = SubtitleFetcher.getSubtitleList(arg
					.getMovie(),
					SubtitleFetcher.SUBTITLE_LANGUAGES_CODES[Settings
							.getSubtitlesLang1()]);

			List<SubtitleInfo> list2 = SubtitleFetcher.getSubtitleList(arg
					.getMovie(),
					SubtitleFetcher.SUBTITLE_LANGUAGES_CODES[Settings
							.getSubtitlesLang2()]);

			List<SubtitleInfo> list3 = SubtitleFetcher.getSubtitleList(arg
					.getMovie(),
					SubtitleFetcher.SUBTITLE_LANGUAGES_CODES[Settings
							.getSubtitlesLang3()]);

			if (list1 != null)
				for (int i = 0; i < list1.size(); i++) {
					SubtitleMenuItem item = new SubtitleMenuItem(list1.get(i),
							this);
					this.lang1Menu.add(item);
					subtitlesGroup.add(item);
					if (i == 0 && !defaultSubSelected
							&& Settings.autoLoadSubtitles()) {
						item.actionPerformed(new ActionEvent(item, 0, "dummy"));
						defaultSubSelected = true;
					}
				}
			if (list2 != null)
				for (int i = 0; i < list2.size(); i++) {
					SubtitleMenuItem item = new SubtitleMenuItem(list2.get(i),
							this);
					this.lang2Menu.add(item);
					subtitlesGroup.add(item);
					if (i == 0 && !defaultSubSelected
							&& Settings.autoLoadSubtitles()) {
						item.actionPerformed(new ActionEvent(item, 0, "dummy"));
						defaultSubSelected = true;
					}
				}
			if (list3 != null)
				for (int i = 0; i < list3.size(); i++) {
					SubtitleMenuItem item = new SubtitleMenuItem(list3.get(i),
							this);
					this.lang3Menu.add(item);
					subtitlesGroup.add(item);
					if (i == 0 && !defaultSubSelected
							&& Settings.autoLoadSubtitles()) {
						item.actionPerformed(new ActionEvent(item, 0, "dummy"));
						defaultSubSelected = true;
					}
				}

		} catch (XmlRpcException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @return the video
	 */
	public File getVideo() {
		return video;
	}

	private void initActionListners() {

		mediaPlayer.setEnableMouseInputHandling(false);
		mediaPlayer.setEnableKeyInputHandling(false);
		this.mediaPlayerComponent.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (arg0.getClickCount() >= 2) {
					fullScreen();
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				mouseOncontrol = false;
				mediaPlayerComponent.requestFocus();

			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				mediaPlayerComponent.transferFocus();

			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub

				if (arg0.getButton() == MouseEvent.BUTTON3) {
					menu.show(mediaPlayerComponent, arg0.getX(), arg0.getY());
				}
			}

		});

		mediaPlayerComponent.addKeyListener(new KeyListener() {
			long firstEnter = 0L;

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyCode() == KeyEvent.VK_UP) {
					changeVolume(controlPanel.getVolumeControl().getValue() + 1);
				} else if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
					changeVolume(controlPanel.getVolumeControl().getValue() - 1);
				} else if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
					mediaPlayer.skip(-1000);
					showSkipMarquee(false);
				} else if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
					mediaPlayer.skip(1000);
					showSkipMarquee(true);
				}
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				if (arg0.getKeyChar() == ' ') {
					if (mediaPlayer.isPlaying())
						pause();
					else
						play();
				} else if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
					App.LOGGER.info("escape key pressed " + "  char = "
							+ arg0.getKeyChar() + " code = "
							+ arg0.getKeyCode());
					if (mediaPlayer.isFullScreen()) {
						fullScreen();
					}
				} else if (arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					App.LOGGER.info("enter key pressed " + "  char = "
							+ arg0.getKeyChar() + " code = "
							+ arg0.getKeyCode());
					if ((arg0.getWhen() - firstEnter) < 500) {
						fullScreen();
						firstEnter = 0;
					} else {
						firstEnter = arg0.getWhen();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		mediaPlayerComponent.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				// TODO Auto-generated method stub
				int i = arg0.getWheelRotation();
				changeVolume(controlPanel.getVolumeControl().getValue() - i * 5);

			}

		});

		this.controlPanel.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {

			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				mouseOncontrol = true;
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				mouseOncontrol = false;
			}

			@Override
			public void mousePressed(MouseEvent arg0) {

			}

			@Override
			public void mouseReleased(MouseEvent arg0) {

			}

		});

		new MouseMovementDetector(mediaPlayerComponent, 3000) {

			@Override
			protected void onMouseAtRest() {
				App.LOGGER.debug("mouse is at rest ");
				if (mediaPlayer.isPlaying() && !mouseOncontrol) {
					App.LOGGER.info("mouse is at rest conditions met ");
					showControls(false);
				}
				App.LOGGER.debug("mouse is at rest conditions met ?");

			}

			@Override
			protected void onMouseMoved() {
				App.LOGGER.info("mouse moved condition met ");
				showControls(true);
			}

			@Override
			protected void onStarted() {
			}

			@Override
			protected void onStopped() {

			}
		}.start();

		mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(this);
	}

	@Override
	public void lengthChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			long arg1) {

	}

	private void loadRemoteSubtitles() {
		if (!Settings.LoadOsSubtitles()) {

			return;
		}

		if (this.currentlyPlayingSource == SOURCE_YIFY_TORRENT) {
			getSubtitle(torrent);
		} else if (this.currentlyPlayingSource == SOURCE_VIDEO) {
			getRemoteSubs(this);
		}

	}

	@Override
	public void mediaChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			libvlc_media_t arg1, String arg2) {

	}

	@Override
	public void mediaDurationChanged(
			uk.co.caprica.vlcj.player.MediaPlayer arg0, long arg1) {

	}

	// TODO

	@Override
	public void mediaFreed(uk.co.caprica.vlcj.player.MediaPlayer arg0) {

	}

	@Override
	public void mediaMetaChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			int arg1) {
		currentTitle = " | " + arg0.getMediaMeta().getTitle();
		frame.setTitle("Cupcake " + currentTitle);
	}

	@Override
	public void mediaParsedChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			int arg1) {

	}

	@Override
	public void mediaStateChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			int arg1) {

	}

	@Override
	public void mediaSubItemAdded(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			libvlc_media_t arg1) {

	}

	@Override
	public void mediaSubItemTreeAdded(
			uk.co.caprica.vlcj.player.MediaPlayer arg0, libvlc_media_t arg1) {

	}

	@Override
	public void muted(uk.co.caprica.vlcj.player.MediaPlayer arg0, boolean arg1) {

	}

	@Override
	public void newMedia(uk.co.caprica.vlcj.player.MediaPlayer arg0) {

	}

	@Override
	public void opening(uk.co.caprica.vlcj.player.MediaPlayer arg0) {
		currentTitle = arg0.getMediaMeta().getTitle();
		frame.setTitle("Cupcake " + currentTitle + " | Opening");
	}

	@Override
	public void pausableChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			int arg1) {

	}

	public void pause() {
		if (mediaPlayerComponent.getMediaPlayer().isPlaying())
			this.mediaPlayerComponent.getMediaPlayer().pause();
	}

	@Override
	public void paused(uk.co.caprica.vlcj.player.MediaPlayer arg0) {
		controlPanel.getPlayBtn().setIcon(new ImageIcon(R.PLAY_BTN_ICON));
		frame.setTitle("Cupcake " + currentTitle + S.MEDIA_PAUSED);

	}

	private void play() {
		mediaPlayerComponent.getMediaPlayer().setVolume(
				controlPanel.getVolumeControl().getValue() * 2);
		if (this.newMedia == 0) {
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

			App.LOGGER.info("total time is  "
					+ mediaPlayerComponent.getMediaPlayer().getLength());
			int seconds = (int) (this.mediaPlayerComponent.getMediaPlayer()
					.getLength() / 1000);
			controlPanel.getProgress().setMinimum(0);
			controlPanel.getProgress().setValue(0);
			App.LOGGER.info("setting max to " + seconds);
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

	@Override
	public void playing(uk.co.caprica.vlcj.player.MediaPlayer arg0) {
		controlPanel.getPlayBtn().setIcon(new ImageIcon(R.PAUSE_BTN_ICON));
		frame.setTitle("Cupcake " + currentTitle + S.MEDIA_PLAYED);
	}

	@Override
	public void positionChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			float arg1) {
		App.LOGGER.debug("current value in slider is "
				+ controlPanel.getProgress().getValue());

	}

	private void rebuildSubGroupButtons() {
		subtitlesGroup = new ButtonGroup();
		subtitlesGroup.add(disableSubsMenuItem);
	}

	private void removeAllSubtitleItems() {
		localSubsMenu.removeAll();
		lang1Menu.removeAll();
		lang2Menu.removeAll();
		lang3Menu.removeAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.controlers.Watchable#remove(com.lordroid.cupcake
	 * .controlers.Watcher)
	 */
	@Override
	public void removeWatcher(Watcher w) {
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

	@Override
	public void scrambledChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			int arg1) {

	}

	@Override
	public void seekableChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			int arg1) {

	}

	public void setBufferingView() {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, CARD_BUFFER_TORRENT);
		repaint();
	}

	/**
	 * @param str
	 *            the video to set
	 */
	public void setCurrentVideo(String str) {
		this.newMedia = 0;
		this.video = new File(str);
		App.LOGGER.debug("video set to " + str);
	}

	protected void setDefaultMarqueeProperties() {
		mediaPlayerComponent.getMediaPlayer().enableMarquee(false);
		mediaPlayerComponent.getMediaPlayer().setMarqueeSize(20);
		mediaPlayerComponent.getMediaPlayer().setMarqueePosition(
				libvlc_marquee_position_e.top_right);
		mediaPlayerComponent.getMediaPlayer().setMarqueeTimeout(1000);

	}

	/**
	 * @param frame2
	 */
	public void setFullScreenStrategy(JFrame frame2) {
		// TODO Auto-generated method stub

		if (!fullScreenStrategySet) {
			frame = frame2;
			mediaPlayerComponent.getMediaPlayer().setFullScreenStrategy(
					new DefaultAdaptiveRuntimeFullScreenStrategy(frame) {
						@Override
						protected void afterExitFullScreen() {
							// controlPanel.setVisible(true);
							// statusBar.setVisible(true);
						}

						@Override
						protected void beforeEnterFullScreen() {
							// controlPanel.setVisible(false);
							// statusBar.setVisible(false);
							// Window overlayWin = new Window();
							// overlay.
						}

					});
			fullScreenStrategySet = true;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.player.MediaPlayerImp#setMediaFromLocalTorrent(java
	 * .lang.Object)
	 */
	@Override
	public void setMediaFromLocalTorrent(Object torrent) {
		// TODO Auto-generated method stub
		this.currentlyPlayingSource = SOURCE_LOCAL_TORRENT;
		removeAllSubtitleItems();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.player.MediaPlayerImp#setMediaFromLocalVideo(java
	 * .io.File)
	 */
	@Override
	public void setMediaFromLocalVideo(File videoArgs) {
		setCurrentVideo(videoArgs.getAbsolutePath());
		this.currentlyPlayingSource = SOURCE_VIDEO;
		rebuildSubGroupButtons();
		removeAllSubtitleItems();
		// local subs
		ArrayList<File> localSubs = FileUtils.searchrecursively(
				video.getParentFile(), "srt");
		for (File f : localSubs) {
			LocalSubtitleFileMenuItem item = new LocalSubtitleFileMenuItem(f,
					this);
			localSubsMenu.add(item);
		}
		// remote subs

		loadRemoteSubtitles();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.lordroid.cupcake.player.MediaPlayerImp#setMediaFromYifyTorrent(com
	 * .lordroid.cupcake.yify.YifyTorrent)
	 */
	@Override
	public void setMediaFromYifyTorrent(YifyMovie yifyMovie) {
		this.currentlyPlayingSource = SOURCE_YIFY_TORRENT;
		this.setBufferingView();
		this.torrent = new YifyMovieTorrent(yifyMovie, -1);
		TorrentStartedPlaying = false;
		removeAllSubtitleItems();
		rebuildSubGroupButtons();
		torrent.start(this);
		torrentStartTime = System.currentTimeMillis();
	}

	public void setPlayerView() {
		CardLayout cl = (CardLayout) (cards.getLayout());
		cl.show(cards, CARD_PLAYER);
		repaint();
	}

	/**
	 * sets the subtitle file ;
	 * 
	 * @param file
	 */
	public void setSubtitle(File file) {
		// TODO
		if (file != null)
			mediaPlayerComponent.getMediaPlayer().setSubTitleFile(file);
		else
			mediaPlayerComponent.getMediaPlayer().setSpu(-1);

		mediaPlayerComponent.getMediaPlayer().setMarqueeSize(20);
		mediaPlayerComponent.getMediaPlayer().setMarqueePosition(
				libvlc_marquee_position_e.top_right);
		mediaPlayerComponent.getMediaPlayer().setMarqueeTimeout(3000);
		mediaPlayerComponent.getMediaPlayer().setMarqueeText(
				(file != null ? "Subtitle Loaded : " + file.getName()
						: "Subtitle Disabled"));
		mediaPlayerComponent.getMediaPlayer().enableMarquee(true);
		// mediaPlayerComponent.getMediaPlayer().setM
	}

	/**
	 * this is called from Progress Bar
	 */
	private void setTime() {
		// TODO Auto-generated method stub
		App.LOGGER.debug(this.controlPanel.getProgress().getValue() + "");
		this.mediaPlayerComponent.getMediaPlayer().setTime(
				this.controlPanel.getProgress().getValue() * 1000);
		this.controlPanel.getCurrentTimeLab().setText(
				TimeUtils.getLabelFormatedTime(this.mediaPlayerComponent
						.getMediaPlayer().getTime()));
	}

	/**
	 * 
	 */
	private void setVolumeIcon() {
		// TODO Auto-generated method stub
		int volume = mediaPlayerComponent.getMediaPlayer().getVolume();
		App.LOGGER.debug("current volume =  " + volume);
		if (mediaPlayerComponent.getMediaPlayer().isMute()) {
			controlPanel.getVolumeBtn().setIcon(new ImageIcon(R.VOLUME_MUTED));
		} else {

			if (volume >= 200) {
				controlPanel.getVolumeBtn().setIcon(
						new ImageIcon(R.VOLUME_FULL));
			} else if (volume >= 100) {
				controlPanel.getVolumeBtn().setIcon(
						new ImageIcon(R.VOLUME_MEDIUM));
			} else if (volume > 0) {
				controlPanel.getVolumeBtn()
						.setIcon(new ImageIcon(R.VOLUME_LOW));
			} else {
				controlPanel.getVolumeBtn().setIcon(
						new ImageIcon(R.VOLUME_MUTED));
			}
		}
		controlPanel.getCurrentVolume().setText("" + (volume / 2));
		Settings.setCurrentVolume(volume / 2);
	}

	private void showControls(boolean bool) {

		controlPanel.setVisible(bool);
		// TODO make a special menu
		frame.getJMenuBar().setVisible(bool);
		if (!bool) {
			BufferedImage cursorImg = new BufferedImage(16, 16,
					BufferedImage.TYPE_INT_ARGB);

			Cursor blankCursor = Toolkit.getDefaultToolkit()
					.createCustomCursor(cursorImg, new Point(0, 0),
							"blank cursor");
			mediaPlayerComponent.setCursor(blankCursor);
		} else {
			mediaPlayerComponent.setCursor(Cursor.getDefaultCursor());
		}

	}

	/**
	 * 
	 */
	protected void showSkipMarquee(boolean skiping) {
		// TODO Auto-generated method stub
		String str = (skiping ? "FastForward >> " : "<< Rewind ");
		setDefaultMarqueeProperties();
		mediaPlayerComponent.getMediaPlayer().setMarqueeText(
				str + TimeUtils.getLabelFormatedTime(mediaPlayer.getTime()));
		mediaPlayerComponent.getMediaPlayer().enableMarquee(true);
	}

	/**
	 * 
	 */
	private void skip() {
		long skipAhead = 1000;
		if (System.currentTimeMillis() - this.lastSkipTime < 800)
			skipAhead = (this.lastSkip * 6) / 5;

		this.mediaPlayerComponent.getMediaPlayer().skip(skipAhead);
		this.lastSkip = skipAhead;
		this.lastSkipTime = System.currentTimeMillis();
	}

	@Override
	public void snapshotTaken(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			String arg1) {

	}

	private void stop() {
		this.mediaPlayerComponent.getMediaPlayer().stop();
		controlPanel.getProgress().setValue(0);
		controlPanel.getPlayBtn().setIcon(new ImageIcon(R.PLAY_BTN_ICON));
		controlPanel.getCurrentTimeLab().setText("  00:00:00  ");
	}

	@Override
	public void stopped(uk.co.caprica.vlcj.player.MediaPlayer arg0) {
		frame.setTitle("Cupcake " + currentTitle + S.MEDIA_STOPED);
	}

	@Override
	public void subItemFinished(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			int arg1) {

	}

	@Override
	public void subItemPlayed(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			int arg1) {

	}

	@Override
	public synchronized void timeChanged(
			uk.co.caprica.vlcj.player.MediaPlayer arg0, long arg1) {
		int seconds = (int) (mediaPlayerComponent.getMediaPlayer().getTime() / 1000);
		controlPanel.getProgress().setValue(seconds);
		controlPanel.getCurrentTimeLab().setText(
				TimeUtils.getLabelFormatedTime(mediaPlayerComponent
						.getMediaPlayer().getTime()));
	}

	@Override
	public void titleChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			int arg1) {

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
	@Override
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
			// play();
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
		} else if (message == S.STOP_BTN_PRESSED) {
			stop();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.lordroid.cupcake.controlers.Watchable#updateWatchers()
	 */
	@Override
	public void updateWatchers(int message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void videoOutput(uk.co.caprica.vlcj.player.MediaPlayer arg0, int arg1) {

	}

	@Override
	public void volumeChanged(uk.co.caprica.vlcj.player.MediaPlayer arg0,
			float arg1) {
		controlPanel.getCurrentVolume().setText("" + arg0.getVolume());
	}
}
