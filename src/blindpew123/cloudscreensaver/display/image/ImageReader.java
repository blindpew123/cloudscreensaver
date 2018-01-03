package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
import java.util.Properties;

import blindpew123.cloudscreensaver.imagepath.ImagePath;

/*
 * Этот интерфейс обеспечивает чтение файла по опрееленному пути
 * Если не может получть файл передает следующему по цепочке ридеру. Последний в списке ридер подает одно из дефолтных изображений
 * с выведенным поверх сообщение об ошибке
 */


public abstract class ImageReader {	
	
	ImageReader nextImageReader; 	
	
	ImageReader(ImageReader reader){
		nextImageReader  = reader;
	}
	
	abstract ReadyImageCortege getImage(ImagePath path);	
	
}
