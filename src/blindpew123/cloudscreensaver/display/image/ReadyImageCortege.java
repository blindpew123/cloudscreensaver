package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
import java.util.Map.Entry;
import java.util.Properties;

public class ReadyImageCortege {
	private BufferedImage img;
	private Properties info;	
	
	ReadyImageCortege(BufferedImage image, Properties info){
		img = image;
		this.info  = copyMap(info);
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public Properties getInfo(){
		return copyMap(info);
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
