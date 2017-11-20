package blindpew123.cloudscreensaver.image;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

public class HttpImageReader extends ImageReader {

	HttpImageReader(ImageReader reader) {
		super(reader);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	BufferedImage getImage(String path) {
		BufferedImage result = null;
		if(imageReader != null) {
			result = imageReader.getImage(path);
			if (result != null) return result;
		}
		try {
			result = ImageIO.read(new URL(path));
		} catch (Exception e) {} //Swallow, chained readers try to get that image or default with error message will be used.
		return result; 
	}

}
