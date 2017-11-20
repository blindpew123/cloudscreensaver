package blindpew123.cloudscreensaver.image;

import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/* Абстрактный класс для описывающий шаблонный метод 
 * TODO: Реализовать СлайдШоу в отдельном классе 
 */



@SuppressWarnings("serial")
public class Display extends JPanel implements ActionListener{
	
	private JFrame frame = new JFrame("FullScreen");
	private Rectangle rectangle; // try to get size
	private Color color=Color.BLACK;
	private KeyListener keyHandler;
	private MouseListener mouseHandler;
	private BufferedImage image;
	private ImageFeeder feeder;
	
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
	
	public Display(ImageFeeder feeder) {
		this.feeder = feeder;
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
		// TODO Auto-generated method stub
		
	}
	
	public final void paintComponent (Graphics g) {
		super.paintComponent(g);
		paintImage(g,image);
	}
	
	public void paintImage(Graphics g, BufferedImage img) {
		// TODO: Remove BufferedImage resizedImg = new ImageResizer(rectangle).resize(img); 
		g.drawImage(img, getImagePosition(img).width, getImagePosition(img).height, null);
	}
	
	public void display() {
		// TODO: получить изображение из очереди
		setImage(img);
		repaint();
		finalProcessing();
	}
	
	public void finalProcessing() {
		//TODO: Вывод текста в соответствии с настройками
	}
	
	public Rectangle getPrefferableImageSize() {
		return rectangle;
	}
	
	protected Rectangle getImagePosition(BufferedImage img) {
		return new Rectangle(rectangle.width/2-img.getWidth()/2, rectangle.height/2-img.getHeight()/2);
	}
	
	/* TODO: Remove
	public static void main(String...args) throws IOException{		
		BufferedImage image = ImageIO.read(new File("c:/DSC02817.jpg"));		
		Display p= new Display(image);
		p.repaint();
	}*/
	
	protected final void setImage(BufferedImage image) {
		this.image = image;
	}
	
	protected final void setKeyHandler(KeyListener keyHandler) {
		frame.addKeyListener(keyHandler);		
	}
	
	protected final void setMouseHandler(MouseListener mouseHandler) {
		frame.addMouseListener(mouseHandler); 
	}
}
