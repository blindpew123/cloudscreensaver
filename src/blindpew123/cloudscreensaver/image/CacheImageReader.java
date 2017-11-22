package blindpew123.cloudscreensaver.image;

import java.util.HashMap;
import java.util.Map;

//TODO Research Cache Performance

public class CacheImageReader extends ImageReader {

	private Map<String, ReadyImageCortege> cache = new HashMap<>();
	
	CacheImageReader(ImageReader reader) {
		super(reader);
		// TODO Auto-generated constructor stub
	}

	@Override
	ReadyImageCortege getImage(String path) {
		ReadyImageCortege result = cache.get(path); 
		if (result == null && imageReader != null) {
			result = imageReader.getImage(path);
			if (result != null) {
				cache.put(path, result);
			}
		}
		return result;
	}

}
