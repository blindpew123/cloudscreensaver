package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import blindpew123.cloudscreensaver.imagepath.ImagePath;

public class HttpImageReader extends ImageReader implements MetaReader {

	HttpImageReader(ImageReader reader) {
		super(reader);
	}
	
	@Override
	ReadyImageCortege getImage(ImagePath path) {		
		ReadyImageCortege result = null;
		if(nextImageReader != null) {
			result = nextImageReader.getImage(path);
			if (result != null) return result;
		}
		try {
			byte[] localImage = createMemoryCopy(path.getAbsolutePath());
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(localImage));
			if (image!=null) {
				result = new ReadyImageCortege(image, path, readExif(new ByteArrayInputStream(localImage)));				
			} 
		} catch (Exception e) {} //Swallow, chained readers try to get that image or default with error message will be used.
		return result; 
	}
	
	private byte[] createMemoryCopy(String path) throws IOException {
		byte[] result = null;
		try (BufferedInputStream from = new BufferedInputStream(new URL(path).openStream())) {
			result = from.readAllBytes(); // Java 9 
		}
		return result;
	}

}
