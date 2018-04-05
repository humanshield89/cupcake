package com.lordroid.cupcake;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;

public class Tutorial {

	public static void main( String[] args) {
//		new NativeDiscovery().discover();
//		SwingUtilities.invokeLater(new Runnable() {
//			public void run() {
//				new Tutorial(args);
//			}
//		});
		printInfo();
		
	}

	/**
	 * 
	 */
	private static void printInfo() {
		// TODO Auto-generated method stub
		// while (mediaPlayerComponent.getMediaPlayer().is)
		System.out.println(System.getProperty("os.arch"));
		
		
	}

	private final JFrame frame;

	private final EmbeddedMediaPlayerComponent mediaPlayerComponent;

	public Tutorial(String[] args) {
		frame = new JFrame("My First Media Player");
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mediaPlayerComponent.release();
				System.exit(0);
			}
		});
		mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
		frame.setContentPane(mediaPlayerComponent);
		frame.setVisible(true);
		mediaPlayerComponent.getMediaPlayer().playMedia(args[0]);
		// mediaPlayerComponent.getMediaPlayer().set
		mediaPlayerComponent.getMediaPlayer().addMediaPlayerEventListener(
				new MediaPlayerEventListener() {

					public void audioDeviceChanged(MediaPlayer arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					public void backward(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void buffering(MediaPlayer arg0, float arg1) {
						// TODO Auto-generated method stub

					}

					public void chapterChanged(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void corked(MediaPlayer arg0, boolean arg1) {
						// TODO Auto-generated method stub

					}

					public void elementaryStreamAdded(MediaPlayer arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					public void elementaryStreamDeleted(MediaPlayer arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					public void elementaryStreamSelected(MediaPlayer arg0,
							int arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					public void endOfSubItems(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void error(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void finished(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void forward(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void lengthChanged(MediaPlayer arg0, long arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaChanged(MediaPlayer arg0,
							libvlc_media_t arg1, String arg2) {
						// TODO Auto-generated method stub

					}

					public void mediaDurationChanged(MediaPlayer arg0, long arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaFreed(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void mediaMetaChanged(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaParsedChanged(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaStateChanged(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaSubItemAdded(MediaPlayer arg0,
							libvlc_media_t arg1) {
						// TODO Auto-generated method stub

					}

					public void mediaSubItemTreeAdded(MediaPlayer arg0,
							libvlc_media_t arg1) {
						// TODO Auto-generated method stub

					}

					public void muted(MediaPlayer arg0, boolean arg1) {
						// TODO Auto-generated method stub

					}

					public void newMedia(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void opening(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void pausableChanged(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void paused(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void playing(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void positionChanged(MediaPlayer arg0, float arg1) {
						// TODO Auto-generated method stub
						System.out.println("fps = " + arg0.getFps());

					}

					public void scrambledChanged(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void seekableChanged(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void snapshotTaken(MediaPlayer arg0, String arg1) {
						// TODO Auto-generated method stub

					}

					public void stopped(MediaPlayer arg0) {
						// TODO Auto-generated method stub

					}

					public void subItemFinished(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void subItemPlayed(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void timeChanged(MediaPlayer arg0, long arg1) {
						// TODO Auto-generated method stub

					}

					public void titleChanged(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void videoOutput(MediaPlayer arg0, int arg1) {
						// TODO Auto-generated method stub

					}

					public void volumeChanged(MediaPlayer arg0, float arg1) {
						// TODO Auto-generated method stub

					}

				});

	}
}