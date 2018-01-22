package blindpew123.cloudscreensaver.display.image;

import java.util.HashMap;
import java.util.Map;

import blindpew123.cloudscreensaver.imagepath.ImagePath;

/** Before reading image from another chained reader, it checks availability this image in its map.
 *  If it will be find - cached image returns, else this reader asking next reader for it. 
 */

//TODO Research Cache Performance


public class CacheImageReader extends ImageReader {

	private Map<ImagePath, ReadyImageCortege> cache = new HashMap<>();
	
	CacheImageReader(ImageReader reader) {
		super(reader);	
	}

	@Override
	ReadyImageCortege getImage(ImagePath path) {
		ReadyImageCortege result = cache.get(path); 
		if (result == null && nextImageReader != null) {
			result = nextImageReader.getImage(path);
			if (result != null && !result.checkError()) {
				cache.put(path, result);
			}
		}
		return result;
	}

}
