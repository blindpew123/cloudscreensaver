package blindpew123.cloudscreensaver.display.image;

import java.awt.image.BufferedImage;
import java.util.Properties;

import blindpew123.cloudscreensaver.imagepath.ImagePath;

/** Abstract class ImageReader
 *   
 */


public abstract class ImageReader {	
	
	ImageReader nextImageReader; 	
	
	ImageReader(ImageReader reader){
		nextImageReader  = reader;
	}
	
	abstract ReadyImageCortege getImage(ImagePath path);	
	
}
