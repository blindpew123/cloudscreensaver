package blindpew123.cloudscreensaver.image;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

//TODO Research Cache Performance

public class CacheImageReader extends ImageReader {

	Map<String, BufferedImage> cache = new HashMap<>();
	
	CacheImageReader(ImageReader reader) {
		super(reader);
		// TODO Auto-generated constructor stub
	}

	@Override
	BufferedImage getImage(String path) {
		BufferedImage result = cache.get(path); 
		if (result == null && imageReader != null) {
			result = imageReader.getImage(path);
			if (result != null) {
				cache.put(path, result);
			}
		}
		return result;
	}

}
