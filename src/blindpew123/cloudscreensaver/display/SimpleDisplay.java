package blindpew123.cloudscreensaver.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.Properties;

import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class SimpleDisplay extends Display {
	
	private final ShadowFormattedTextBlock nameLabel; 
	private final ShadowFormattedTextBlock exifLabel;
	private static final int FONT_SIZE = 18; // TODO: Move to Settings Dialog
	
	public SimpleDisplay(){
		
		nameLabel = new ShadowFormattedTextBlock()
			.setPosition(getScreenSize(), SwingConstants.LEFT, SwingConstants.TOP)
			.setFontSize(FONT_SIZE)
			.setForeColor(Color.ORANGE)
			.setBackColor(Color.BLACK);
		nameLabel.placeTo(this);
		exifLabel = new ShadowFormattedTextBlock()
				.setPosition(getScreenSize(), SwingConstants.RIGHT, SwingConstants.BOTTOM)
				.setFontSize(FONT_SIZE)
				.setForeColor(Color.ORANGE)
				.setBackColor(Color.BLACK);
		exifLabel.placeTo(this);
	}
	
	@Override
	public void paintImage(Graphics g, BufferedImage img) {
			if(img == null) return;
			g.drawImage(img, getImagePosition(img).width, getImagePosition(img).height, null);
			
	}

	@Override
	public void display() {
		setFileNameText(nameLabel, getImageCortege().getInfo());
		setExifText(exifLabel, getImageCortege().getInfo());
		repaint();
	}
	
	void setExifText(ShadowFormattedTextBlock block, Properties imgProperties) {
		block.setText(new StringBuilder()
				.append(imgProperties.getProperty("Make", "")).append(" ")
				.append(imgProperties.getProperty("Model", "")).append(" | F: ")
				.append(imgProperties.getProperty("F-Number", "--")).append(" | Shutter: ")
				.append(imgProperties.getProperty("Exposure Time", "--")).append(" | ISO: ")
				.append(imgProperties.getProperty("ISO Speed Ratings", "--")).append(" | Lens :")
				.append(imgProperties.getProperty("Lens Specification", "--")).append(" | ")
				.append(imgProperties.getProperty("Focal Length", "--")).toString());
	}
	
	void setFileNameText(ShadowFormattedTextBlock block, Properties imgProperties) {
		block.setText(imgProperties.getProperty("Date/Time Original","")+" "
		//		+ removeCloudMailUnnecessaryInfo(imgProperties.getProperty("path","")));
				+ imgProperties.getProperty("path",""));		
	}
	
	/** I don't like long recondite links provided Cloud.Mail.ru.
	 *  This is sample: https://cloclo5.cldmail.ru/QZWMepdZ2AJTvYHHJUV/G/DQEv/h67e4AAF9/DSC05160.jpg?key=b37e69e9353792c7787643818335c36548b3b94d
	 *  I want to leave only host and that part, which starts from folder's name
	 * @param string - path to check and remove if it needed
	 * @return processed path
	 */
	private String removeCloudMailUnnecessaryInfo(String string){ 
		if(!string.contains("mail.ru")) return string;
		
		int counter = 0;
		String[] parts = string.split("?")[0].split("/");
		StringBuilder result = new StringBuilder("https://cloud.mail.ru...");
		for(String part : parts) {
			if(7 < parts.length &&  counter > 6) result.append(part);
			if(counter == 0) result.append("//");
			if(counter < parts.length-1) result.append("/");
			
			counter++;
		}
		return result.toString();
	}
	
}


