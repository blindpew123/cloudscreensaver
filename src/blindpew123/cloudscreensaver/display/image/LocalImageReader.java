package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

import blindpew123.cloudscreensaver.imagepath.ImagePath;

/**
 * 
 * Reads image from local path
 *
 */

public class LocalImageReader extends ImageReader implements MetaReader {

	LocalImageReader(ImageReader reader) {
		super(reader);
	}
	
	@Override
	ReadyImageCortege getImage(ImagePath path) {
		ReadyImageCortege result = null;
		if (nextImageReader!=null) {
			result = nextImageReader.getImage(path);
			if (result != null) return result;
		}		
		try {
			BufferedImage image = ImageIO.read(new File(path.getPath()));
			if (image != null) {
				result = new ReadyImageCortege(image, path, readExif(new FileInputStream(new File(path.getPath()))));
			}
		} catch(Exception e) {} 
		return result;
	}
}
