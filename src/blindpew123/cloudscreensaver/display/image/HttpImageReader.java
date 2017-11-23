package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class HttpImageReader extends ImageReader implements MetaReader {

	HttpImageReader(ImageReader reader) {
		super(reader);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	ReadyImageCortege getImage(String path) {		
		
		ReadyImageCortege result = null;
		if(imageReader != null) {
			result = imageReader.getImage(path);
			if (result != null) return result;
		}
		try {			
			BufferedImage image = ImageIO.read(new URL(path));
			if (image!=null) {
				result = createCortege(image, readExif(new URL(path).openStream()), path);
			}
		} catch (Exception e) {} //Swallow, chained readers try to get that image or default with error message will be used.
		return result; 
	}

}
