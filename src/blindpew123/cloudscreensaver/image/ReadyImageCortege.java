package blindpew123.cloudscreensaver.image;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ReadyImageCortege {
	private BufferedImage img;
	private Map<String,String> info;	
	
	ReadyImageCortege(BufferedImage image, Map<String, String> info){
		img = image;
		this.info  = copyMap(info);
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public Map<String, String> getInfo(){
		return copyMap(info);
	}
	
	private Map<String, String> copyMap(Map<String, String> map) {
		return map == null ? new HashMap<>():new HashMap<>(map); 
	}
	
}
