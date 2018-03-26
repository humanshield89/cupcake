package com.lordroid.cupcake.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import bt.torrent.TorrentSessionState;

import com.alee.laf.progressbar.WebProgressBar;

public class BufferingPanel extends JPanel{
	
	/**
	 * @return the peersLab
	 */
	public JLabel getPeersLab() {
		return peersLab;
	}

	/**
	 * @return the currentDownSpeed
	 */
	public JLabel getCurrentDownSpeed() {
		return currentDownSpeed;
	}

	/**
	 * @return the buffred
	 */
	public JLabel getBuffred() {
		return buffred;
	}

	/**
	 * @return the progress
	 */
	public WebProgressBar getProgress() {
		return progress;
	}
	
	private JLabel peersLab = new JLabel("00"){
	
		@Override
		public void setText(String text){
			super.setText("Number of peers : "+text);
		}
	};

	private JLabel currentDownSpeed = new JLabel("0 kB/s"){
		@Override
		public void setText(String text){
			super.setText("Average Speed : "+text+" KB/S");
		}
	};
	private JLabel buffred = new JLabel("0 MB"){
		@Override
		public void setText(String text){
			super.setText("Buffred : "+text+" MB");
		}
	};
	private WebProgressBar progress = new WebProgressBar();
	private JPanel labelContainer = new JPanel();
	public BufferingPanel()  {
		super();
		progress.setIndeterminate(true);
		progress.setStringPainted(true);
		progress.setString("Loading your movie this may take up to a minute ");
		labelContainer.setLayout(new FlowLayout());
		labelContainer.add(peersLab);
		labelContainer.add(currentDownSpeed);
		labelContainer.add(buffred);
		
		
		this.setLayout(new BorderLayout());
		this.add(progress, BorderLayout.CENTER);
		this.add(labelContainer, BorderLayout.SOUTH);
	}
	
	public void resetValues() {
		peersLab.setText("0");
		currentDownSpeed.setText("0");
		buffred.setText("0");
		
	}
	
	public void updateValues(String[] state){
		peersLab.setText(state[0]);
		currentDownSpeed.setText(state[1]);
		buffred.setText(state[2]);
	}
}
