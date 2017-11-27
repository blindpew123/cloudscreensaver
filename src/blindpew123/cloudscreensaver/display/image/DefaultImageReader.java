package blindpew123.cloudscreensaver.display.image;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Properties;
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
				return getDefaultImage(false, path);
			}	
			result = imageReader.getImage(path);
			if (result == null) {
				result = getDefaultImage(true, path);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	private ReadyImageCortege getDefaultImage(boolean hasError, String path) throws IOException {
		int index = ThreadLocalRandom.current().nextInt(4);
		Properties map = new Properties();
		map.put("path", hasError ? SettingsFile.getInstance().getResources("imageErrorMessage")+" "+path : "");
		if(hasError) System.out.println(path);
		return new ReadyImageCortege(
				ImageIO.read(Paths.get(defaultImages[index]).toAbsolutePath().toFile()), map);		
	}
	
	
}