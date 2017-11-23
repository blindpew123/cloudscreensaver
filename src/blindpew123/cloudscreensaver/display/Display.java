package blindpew123.cloudscreensaver.display;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import blindpew123.cloudscreensaver.display.image.ReadyImageCortege;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/* Абстрактный класс для описывающий шаблонный метод 
 * TODO: Реализовать СлайдШоу в отдельном классе 
 * */

@SuppressWarnings("serial")
public abstract class Display extends JPanel implements ActionListener{
	
	private JFrame frame = new JFrame();
	private Rectangle rectangle; // try to get size
	private Color color=Color.BLACK;
	private KeyListener keyHandler;
	private MouseListener mouseHandler;
	private ReadyImageCortege imageCortege;
	private boolean readyToShow = false;
	int  ttt = 0;
	
	
	private class KeyHandler extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			System.exit(0);
		}
	}
	
	private class MouseHandler extends MouseAdapter { // TODO: Нужна реализация нормально работающая с мышью
		@Override
		public void mouseMoved(MouseEvent e) {
			System.exit(0);
		}
	}	
	public Display() {
		super(null);
		//TODO: make it better;
		setKeyHandler(new KeyHandler());
		setMouseHandler(new MouseHandler());
		
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
	public void actionPerformed(ActionEvent arg0) {
		System.exit(0);		//TODO: What's happened here?
	}
	
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
		frame.addMouseListener(mouseHandler); 
	}
	
	protected ReadyImageCortege getImageCortege() {
		return imageCortege;
	}
	
	protected Rectangle getScreenSize() {
		return rectangle;
	}
	

	
}
