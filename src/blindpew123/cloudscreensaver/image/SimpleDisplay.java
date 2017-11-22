package blindpew123.cloudscreensaver.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class SimpleDisplay extends Display {

	@Override
	public void paintImage(Graphics g, BufferedImage img) {
			if(img == null) return;
			g.drawImage(img, getImagePosition(img).width, getImagePosition(img).height, null);			
	}

	@Override
	public void display() {
		repaint();
		label1.setText("Test " + ttt++);
	}

	
}


