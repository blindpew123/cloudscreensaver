package blindpew123.cloudscreensaver.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import javax.imageio.ImageIO;

public class LocalImageReader extends ImageReader implements MetaReader {

	LocalImageReader(ImageReader reader) {
		super(reader);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	ReadyImageCortege getImage(String path) {
		ReadyImageCortege result = null;
		if (imageReader!=null) {
			result = imageReader.getImage(path);
			if (result != null) return result;
		}		
		try {
			BufferedImage image = ImageIO.read(new File(path));
			if (image != null) {
				result = new ReadyImageCortege(image, readExif(new FileInputStream(new File(path))));
			}
		} catch(Exception e) {} 
		return result;
	}
}
