package blindpew123.cloudscreensaver.image;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import blindpew123.cloudscreensaver.settings.SettingsFile;

public class DefaultImageReader extends ImageReader {

	private String[] defaultImages = {
		"blindpew123/cloudscreensaver/resources/DSC01594.jpg",
		"blindpew123/cloudscreensaver/resources/DSC02143.jpg",
		"blindpew123/cloudscreensaver/resources/DSC04343.jpg",
		"blindpew123/cloudscreensaver/resources/DSC04485.jpg"
	};	

	DefaultImageReader(ImageReader reader) {
		super(reader);
	}

	@Override
	BufferedImage getImage(String path) {
		BufferedImage result = null;
		try {
			if (imageReader == null) {
				return getDefaultImage();
			}	
			result = imageReader.getImage(path);
			if (result == null) {
				result = addErrorMessage(getDefaultImage(), path);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	private BufferedImage getDefaultImage() throws IOException {
		int index = ThreadLocalRandom.current().nextInt(4);	
		BufferedImage image = ImageIO.read(new File(defaultImages[index]));
		return image;
	}
	
	private BufferedImage addErrorMessage(BufferedImage image, String path) {
		Graphics g = image.getGraphics();
	    g.setFont(g.getFont().deriveFont(30f));
	    String fullErrorMessage  = SettingsFile.getInstance().getResources("imageErrorMessage")+": "+path;
	    int width = g.getFontMetrics().stringWidth(fullErrorMessage);
	    //TODO calculate center horizontal position
	    g.drawString(fullErrorMessage, 100, 100);
	    g.dispose();
		return image;
	}	
}