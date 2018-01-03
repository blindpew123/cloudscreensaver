package blindpew123.cloudscreensaver.display.image;

import blindpew123.cloudscreensaver.imagepath.ImagePath;

@SuppressWarnings("serial")
public class ImageReadingException extends RuntimeException {
	ImagePath errorPath;
	public ImageReadingException(String message, ImagePath path) {
		super(message);
		errorPath = path;
	}
}
