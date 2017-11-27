package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import blindpew123.cloudscreensaver.settings.SettingsFile;

public class HttpImageReader extends ImageReader implements MetaReader {

	HttpImageReader(ImageReader reader) {
		super(reader);
	}
	
	@Override
	ReadyImageCortege getImage(String path) {		
		ReadyImageCortege result = null;
		if(imageReader != null) {
			result = imageReader.getImage(path);
			if (result != null) return result;
		}
		try {
			byte[] localImage = createMemorycopy(path);
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(localImage));
			if (image!=null) {
				result = createCortege(image, readExif(new ByteArrayInputStream(localImage)), path);				
			} 
		} catch (Exception e) {} //Swallow, chained readers try to get that image or default with error message will be used.
		return result; 
	}
	
	private byte[] createMemorycopy(String path) throws FileNotFoundException, IOException {
		byte[] result = null;
		try (BufferedInputStream from = new BufferedInputStream(new URL(path).openStream())) {
			result = from.readAllBytes(); // Java 9 
		}
		return result;
	}

}
