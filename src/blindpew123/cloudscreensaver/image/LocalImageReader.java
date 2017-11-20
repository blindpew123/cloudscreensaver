package blindpew123.cloudscreensaver.image;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class LocalImageReader extends ImageReader {

	LocalImageReader(ImageReader reader) {
		super(reader);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	BufferedImage getImage(String path) {
		BufferedImage result = null;
		if (imageReader!=null) {
			result = imageReader.getImage(path);
			if (result != null) return result;
		}		
		try {
			result = ImageIO.read(new File(path));
		} catch(Exception e) { } 
		return result;
	}
}
