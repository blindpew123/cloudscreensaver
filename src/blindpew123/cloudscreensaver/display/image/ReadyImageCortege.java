package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
import java.util.Map.Entry;
import java.util.Properties;

import blindpew123.cloudscreensaver.imagepath.ImagePath;

/** 
 * Combines image, path and EXIF
 */

public class ReadyImageCortege {
	
	private BufferedImage img;
	private Properties info;
	private ImagePath path;
	private boolean error = false;
	
	ReadyImageCortege(BufferedImage image, ImagePath path, Properties info){
		img = image;
		this.info  = copyMap(info);
		this.path = new ImagePath(path.getPath(), false); // it's image file
	}
	
	ReadyImageCortege(BufferedImage image, ImagePath path, Properties info, boolean error){
		this(image, path, info);
		this.error = error;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public Properties getInfo(){
		return copyMap(info);
	}
	
	public ImagePath getPath() {
		return new ImagePath(path.getPath(), false);
	}
	
	public boolean checkError() {
		return error;
	}
	
	private Properties copyMap(Properties map) {
		Properties result = new Properties();
		if (map != null) { 
			for(Entry<Object, Object> entry: map.entrySet()) {
				result.put(entry.getKey(),entry.getValue());
			}
		}
		return result; 
	}
	
}
