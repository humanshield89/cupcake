package com.lordroid.cupcake.player;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.lordroid.cupcake.ui.MainFram;

@SuppressWarnings("serial")
public class MediaBackButtonPan extends JPanel {

	private JButton backBtn = new JButton("<< Back to Movie List");
	
	public MediaBackButtonPan(final MainFram frame) {
		this.setLayout(new BorderLayout());
		this.add(backBtn, BorderLayout.WEST);
		backBtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				frame.initMovieListPan();
			}
			
		});
	}
}
