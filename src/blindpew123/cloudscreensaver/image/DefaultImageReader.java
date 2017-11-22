package blindpew123.cloudscreensaver.image;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import blindpew123.cloudscreensaver.settings.SettingsFile;

public class DefaultImageReader extends ImageReader {

	//TODO: check real path after deploy
	private String[] defaultImages = {
		"src/blindpew123/cloudscreensaver/resources/DSC01594.jpg",
		"src/blindpew123/cloudscreensaver/resources/DSC02143.jpg",
		"src/blindpew123/cloudscreensaver/resources/DSC04343.jpg",
		"src/blindpew123/cloudscreensaver/resources/DSC04485.jpg"
	};	

	DefaultImageReader(ImageReader reader) {
		super(reader);
	}

	@Override
	ReadyImageCortege getImage(String path) {
		ReadyImageCortege result = null;
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
	
	private ReadyImageCortege getDefaultImage() throws IOException {
		int index = ThreadLocalRandom.current().nextInt(4);
		return new ReadyImageCortege(
				ImageIO.read(Paths.get(defaultImages[index]).toAbsolutePath().toFile()), null);		
	}
	
	private ReadyImageCortege addErrorMessage(ReadyImageCortege image, String path) {
		// TODO: need implementation Change Info
		/*
		Graphics g = image.getGraphics();
	    g.setFont(g.getFont().deriveFont(30f));
	    String fullErrorMessage  = SettingsFile.getInstance().getResources("imageErrorMessage")+": "+path;
	    int width = g.getFontMetrics().stringWidth(fullErrorMessage);
	    //TODO calculate center horizontal position
	    g.drawString(fullErrorMessage, 100, 100);
	    g.dispose(); */
		return image;
	}	
}