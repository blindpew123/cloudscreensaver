package blindpew123.cloudscreensaver.display.image;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.concurrent.ThreadLocalRandom;

import javax.imageio.ImageIO;

import blindpew123.cloudscreensaver.imagepath.ImagePath;
import blindpew123.cloudscreensaver.settings.SettingsFile;

/** Provides one of five predefined images if no more readers available or next reader returns null 
 * 
 * 
 *
 */

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
	ReadyImageCortege getImage(ImagePath path) {
		ReadyImageCortege result = null;
		try {
			if (nextImageReader == null) {
				return getDefaultImage(false, path);
			}	
			result = nextImageReader.getImage(path);
			if (result == null) {
				result = getDefaultImage(true, path);
			}
		} catch (Exception e) {
			throw new ImageReadingException(e.getMessage(), path);
		}
		return result;
	}
	
	private ReadyImageCortege getDefaultImage(boolean hasError, ImagePath path) throws IOException { 
		int index = ThreadLocalRandom.current().nextInt(4);
		return new ReadyImageCortege(
				ImageIO.read(Paths.get(defaultImages[index]).toAbsolutePath().toFile()), 
				path == null ? new ImagePath("",false) : path, 
				new Properties(), hasError);		
	}
	
	
}