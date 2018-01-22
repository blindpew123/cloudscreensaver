package blindpew123.cloudscreensaver.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.util.Properties;

import javax.swing.SwingConstants;

import blindpew123.cloudscreensaver.display.image.ReadyImageCortege;
import blindpew123.cloudscreensaver.settings.SettingsFile;

/**
 * Display photo fitted to screen size with filename and EXIF information.
 * 
 */

@SuppressWarnings("serial")
public class SimpleDisplay extends Display {
	
	private final ShadowFormattedTextBlock nameLabel; 
	private final ShadowFormattedTextBlock exifLabel;
	
	
	public SimpleDisplay(){
		
		nameLabel = new ShadowFormattedTextBlock()
			.setPosition(getScreenSize(), SwingConstants.LEFT, SwingConstants.TOP)
			.setFontSize((Integer)SettingsFile.getInstance().getSettingsValue("fontSize"))
			.setForeColor(Color.ORANGE)
			.setBackColor(Color.BLACK);
		nameLabel.placeTo(this);
		exifLabel = new ShadowFormattedTextBlock()
				.setPosition(getScreenSize(), SwingConstants.RIGHT, SwingConstants.BOTTOM)
				.setFontSize((Integer)SettingsFile.getInstance().getSettingsValue("fontSize"))
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
		setFileNameText(nameLabel, getImageCortege());
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
	
	void setFileNameText(ShadowFormattedTextBlock block, ReadyImageCortege cortege) {
		block.setText(cortege.getInfo().getProperty("Date/Time Original","")+" "
		//		+ removeCloudMailUnnecessaryInfo(imgProperties.getProperty("path","")));
				+cortege.getPath().getShortPathForDisplay());		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {}
	
	
	
	
	
}


