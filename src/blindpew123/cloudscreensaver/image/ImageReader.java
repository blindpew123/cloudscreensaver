package blindpew123.cloudscreensaver.image;
/*
 * Этот интерфейс обеспечивает чтение файла по опрееленному пути
 * Если не может получть файл передает следующему по цепочке ридеру. Последний в списке ридер подает одно из дефолтных изображений
 * с выведенным поверх сообщение об ошибке
 */

import java.awt.image.BufferedImage;

public abstract class ImageReader {
	
	ImageReader imageReader; 	
	
	ImageReader(ImageReader reader){
		imageReader  = reader;
	}
	
	abstract BufferedImage getImage(String path);
}
