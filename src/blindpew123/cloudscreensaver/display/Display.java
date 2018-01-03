package blindpew123.cloudscreensaver.display;

import java.awt.*;

import javax.swing.*;

import blindpew123.cloudscreensaver.display.image.ReadyImageCortege;

import java.awt.event.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public abstract class Display extends JPanel implements ActionListener{
	
	private JFrame frame = new JFrame();
	private Rectangle rectangle; // try to get image size
	private Color color=Color.BLACK;
	private KeyListener keyHandler;
	private MouseListener mouseHandler;
	private ReadyImageCortege imageCortege;
	private boolean readyToShow = false;
	int  ttt = 0;
	private Point startLocation;
	
	
	private class KeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			System.exit(0);
		}
	}
	
	private class MouseHandler extends MouseAdapter { 
		@Override
		public void mousePressed(MouseEvent e) {
			System.exit(0);
		}
	}	
	
	private class Listener implements AWTEventListener {
        public void eventDispatched(AWTEvent event) {
        	if(!startLocation.equals(MouseInfo.getPointerInfo().getLocation())) {System.exit(0);}
        }
    }
	public Display() {
		super(null);
		
		setEventListeners();
		
		frame.setUndecorated(true);
		rectangle = GraphicsEnvironment.getLocalGraphicsEnvironment()
				.getDefaultScreenDevice().getDefaultConfiguration().getBounds();
		frame.setSize(rectangle.width, rectangle.height);	    
	    frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    setBackground(color);
	    frame.add(this);    
		frame.setVisible(true);		
	}	

	@Override
	public final void paintComponent (Graphics g) {	
		super.paintComponent(g);
		if(readyToShow) paintImage(g,getImageCortege().getImage());
	}
	
	public abstract void paintImage(Graphics g, BufferedImage img);
	
	public abstract void display();	
	
	
	public Rectangle getPrefferableImageSize() {
		return rectangle;
	}
	
	Rectangle getImagePosition(BufferedImage img) {
		return new Rectangle(rectangle.width/2-img.getWidth()/2, rectangle.height/2-img.getHeight()/2);
	}
		
	public final void setImage(ReadyImageCortege image) {
		this.imageCortege = image;
		readyToShow = true;
	}
	
	public final void setKeyHandler(KeyListener keyHandler) {
		frame.addKeyListener(keyHandler);		
	}
	
	public final void setMouseHandler(MouseListener mouseHandler) {
		this.addMouseListener(mouseHandler); 
	}
	
	protected ReadyImageCortege getImageCortege() {
		return imageCortege;
	}
	
	protected Rectangle getScreenSize() {
		return rectangle;
	}
	
	protected void setEventListeners() {
		startLocation = MouseInfo.getPointerInfo().getLocation();
		setKeyHandler(new KeyHandler());
		setMouseHandler(new MouseHandler());
		Toolkit.getDefaultToolkit().addAWTEventListener(
		          new Listener(), AWTEvent.MOUSE_MOTION_EVENT_MASK);			
	}
	

	
}
