package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class LocalImageReader extends ImageReader implements MetaReader {

	LocalImageReader(ImageReader reader) {
		super(reader);
	}
	
	@Override
	ReadyImageCortege getImage(String path) {
		ReadyImageCortege result = null;
		if (nextImageReader!=null) {
			result = nextImageReader.getImage(path);
			if (result != null) return result;
		}		
		try {
			BufferedImage image = ImageIO.read(new File(path));
			if (image != null) {
				result = createCortege(image, readExif(new FileInputStream(new File(path))), path);
			}
		} catch(Exception e) {} 
		return result;
	}
}
