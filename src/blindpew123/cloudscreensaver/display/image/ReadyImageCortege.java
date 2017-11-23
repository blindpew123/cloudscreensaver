package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
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
		return map == null ? new Properties():new Properties(map); 
	}
	
}
