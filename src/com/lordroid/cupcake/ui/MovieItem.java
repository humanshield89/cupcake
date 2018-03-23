package com.lordroid.cupcake.ui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.lordroid.cupcake.res.R;
import com.lordroid.cupcake.utils.GaussianFilter;
import com.lordroid.cupcake.utils.TimeUtils;
import com.lordroid.cupcake.yify.YifyMovie;

public class MovieItem extends JPanel implements MouseListener{
	MovieItem thisPan ;
	private final YifyMovie movie ;
	private BufferedImage coverImage ;
	private boolean isImageCached = false;
	private BufferedImage blurryImage ;
	private boolean isBluredCached = false;
	private JPanel infoPan = new JPanel(){
		public void paintComponent(Graphics g){
			//Graphics2D g2 = (Graphics2D) g;
			//this.repaint();
			BufferedImage img = null;
			try {
				if(entered)
					img = blurryImage.getSubimage(0, 145, 230, 200);
				else
					img = coverImage.getSubimage(0, infoPanY, 230, 200);
			} catch (Exception e) {
				
			}
			Graphics2D g2d = (Graphics2D) g;
	        g2d.setRenderingHint(
	            RenderingHints.KEY_ANTIALIASING,
	            RenderingHints.VALUE_ANTIALIAS_ON);
//	        g2d.setComposite(AlphaComposite.getInstance(
//	            AlphaComposite.SRC_OVER, 0.5f));
	        //g2d.setColor(Color.yellow);
			g2d.setColor(new Color(0,0,0,150));
			//g2d.clearRect(0, 0, this.getWidth(), this.getHeight());
			//super.paintComponent(g);
			if (entered)
			g2d.drawImage(img, 0, 0, this);
			
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
//			if (entered){
//				g2d.setColor(new Color(254,254,254,60));
//				g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
//			}
		}
	};
	private final int infoPanX;
	private final int infoPanY;
	private final int infoPanWitdh;
	//private final int infoPanHeight;
//	private final int infoPanWitdhHovered;
	//private final int infoPanHeighthHovered;
	private boolean entered = false;
	
	private JLabel titleLab = new JLabel();
	private JLabel imdbIcon = new JLabel(new ImageIcon(R.IMDB_ICON));
	private JLabel viewsIcon = new JLabel(new ImageIcon(R.VIEWS_ICON));
	private JLabel imdbRatingLab = new JLabel();
	private JLabel viewCountLab = new JLabel();
	private JLabel genreLAb = new JLabel(new ImageIcon(R.GENRE_ICON));
	private JLabel genreText = new JLabel();
	
	
	private JLabel descriText = new JLabel();
	private JLabel lab720p = new JLabel(new ImageIcon(R.QUALITY_720P));
	private JLabel lab1080p = new JLabel(new ImageIcon(R.QUALITY_1080P));
	private JLabel lab3d = new JLabel(new ImageIcon(R.QUALITY_3D));
	
	private PlayNowBtn playNowbtn = new PlayNowBtn();
	
	//private JLabel ratingIcon ;
	
	//private final int infoPanHoveredX;
	private final int infoPanHoveredY;
	
	public MovieItem(YifyMovie moviearg) {
		 thisPan = this;
		this.movie = moviearg;
		try {
			coverImage = ImageIO.read(movie.getCoverImageFileMedium());
			this.isImageCached = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			this.isImageCached = false;
			try {
				coverImage = ImageIO.read(Thread.currentThread().getContextClassLoader().getResourceAsStream("default_cover_medium.jpg"));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		if (isImageCached){
			blurryImage = new GaussianFilter(10).filter(coverImage, blurryImage);
			isBluredCached = true;
		}
		this.setSize(230, 345);
		this.setPreferredSize(new Dimension(230,345));
		this.setMinimumSize(new Dimension(230,345));
		this.infoPan.setSize(230, 200);
		this.infoPan.setBounds(0, this.getHeight()-71, 230, 200);
		infoPan.setLayout(null);
		
		titleLab.setText("<html><u><b>"+movie.getTitleLong()+"<b></u></html>");
		titleLab.setForeground(Color.WHITE);
		titleLab.setFont(R.MOVIE_TITLE_FONT);
		titleLab.setBounds(2, 2, 200, 45);
		
		imdbIcon.setBounds(12, 49, 60, 20);
		imdbRatingLab.setText("<html><b>"+movie.getRating()+"</b></html>");
		imdbRatingLab.setForeground(Color.WHITE);
		imdbRatingLab.setBounds(50, 49, 20, 20);
		
		this.viewsIcon.setBounds(88,49,60,20);
		this.viewCountLab.setText(TimeUtils.getFormatedViewCount(movie.getDownloadCount()));
		viewCountLab.setFont(R.VIEWS_FONT);
		this.viewCountLab.setForeground(Color.WHITE);
		this.viewCountLab.setBounds(113	, 49, 32, 20);
		
		this.genreLAb.setBounds(160, 49, 60, 20);
		this.genreText.setBounds(165, 49, 50, 20);
		genreText.setText(movie.getGenre().get(0));
		genreText.setForeground(Color.WHITE);
		genreText.setFont(R.GENRE_FONT);
		descriText.setBounds(5, 71, 220, 93);
		descriText.setForeground(Color.WHITE);
		String sum = movie.getDescriptionShort();
		if (sum.length() > 200) {
			sum = sum.substring(0, 199);
			sum.substring(0, sum.lastIndexOf(" "));
		}
		descriText.setText("<html><p>"+sum+"...</p></html>");
		descriText.setFont(R.DESCRIPTION_FONT);

		// TODO : 
		this.lab720p.setBounds(12, 166, 60, 20);
		
		this.lab1080p.setBounds(84, 166, 60, 20);
		
		this.lab3d.setBounds(156, 166, 60, 20);
		
		//System.out.println(descriText.getText());
		
		infoPan.add(titleLab);
		infoPan.add(imdbIcon);
		infoPan.add(imdbRatingLab);
		infoPan.add(viewsIcon);
		infoPan.add(this.viewCountLab);
		infoPan.add(this.genreLAb);
		infoPan.add(this.genreText);
		infoPan.add(descriText);
		if(movie.isHas720p())
		infoPan.add(this.lab720p);
		if(movie.isHas1080p())
		infoPan.add(this.lab1080p);
		if(movie.isHas3d())
		infoPan.add(this.lab3d);
		
		infoPanY = this.getHeight()-71;
		infoPanX = 0;
		infoPanHoveredY = 145;
		infoPanWitdh = 230;
		
		playNowbtn.setBounds(15, 40, 200, 81);
		playNowbtn.setVisible(false);
		//infoPanHeight = 629;
		//infoPanHeighthHovered = this.getHeight();
		//infoPan.setBackground(new Color(0,0,0,122));
		this.setLayout(null);
		this.add(infoPan);
		this.add(playNowbtn);
		//this.setBackground(Color.BLACK);
		
		this.addMouseListener(this);
		playNowbtn.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		if (!this.isImageCached) {
			System.out.println("updating image");
			try {
				coverImage = ImageIO.read(movie.getCoverImageFileMedium());
				isImageCached =true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				isImageCached =false;

				e.printStackTrace();
			}
		}
		if (isImageCached){
			if (!isBluredCached){
				long start = System.currentTimeMillis();
				blurryImage = new GaussianFilter(10).filter(coverImage, blurryImage);
				isBluredCached = true;
				long end = System.currentTimeMillis();
				System.out.println("total bluring time = "+(end-start));
			}
		}

		if (!entered){
			//g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
			g2d.drawImage(coverImage, 0, 0, this);
		} else {
			g2d.drawImage(blurryImage, 0, 0, this);
		}
		g2d.drawImage(R.BORDER_IMAGE, 0, 0,this);
		//infoPan.setBackground(new Color(0,0,0,122));
		//revalidate();
	}

	
	
	
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		entered = true;

		if (entered ) {
			infoPan.setBounds(infoPan.getBounds().x, infoPanHoveredY, 230, 200);
			if(!playNowbtn.isVisible())
			playNowbtn.setVisible(true);
			infoPan.repaint();
			repaint();
			//revalidate();
		}
		if (arg0.getSource().equals(playNowbtn)) {
			playNowbtn.hovered = true;
			playNowbtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
			playNowbtn.repaint();
		}
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
		entered = false;


		if (arg0.getSource().equals(playNowbtn)) {
			playNowbtn.hovered = false;
			playNowbtn.setCursor(Cursor.getDefaultCursor());
			playNowbtn.repaint();
		} else {
			if (!entered ){
				playNowbtn.setVisible(false);
				infoPan.setBounds(0, infoPanY, 230, 200);
				//revalidate();
			}
		}
		infoPan.repaint();
		repaint();
		//revalidate();
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
