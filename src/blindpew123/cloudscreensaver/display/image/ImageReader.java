package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
import java.util.Properties;

/*
 * Этот интерфейс обеспечивает чтение файла по опрееленному пути
 * Если не может получть файл передает следующему по цепочке ридеру. Последний в списке ридер подает одно из дефолтных изображений
 * с выведенным поверх сообщение об ошибке
 */


public abstract class ImageReader {	
	
	ImageReader imageReader; 	
	
	ImageReader(ImageReader reader){
		imageReader  = reader;
	}
	
	abstract ReadyImageCortege getImage(String path);
	
	ReadyImageCortege createCortege(BufferedImage image, Properties map, String path) {
		Properties mapForCortege = map == null? new Properties() : map;
		mapForCortege.put("path", path);
		return new ReadyImageCortege(image, mapForCortege);
	}
	
	
	
}
