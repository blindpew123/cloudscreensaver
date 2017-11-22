package blindpew123.cloudscreensaver.image;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import com.mortennobel.imagescaling.*;

class ImageResizer extends ImageReader{
	
	Rectangle screenSize;
	
	public ImageResizer(Rectangle screenSize, ImageReader reader) {
		super(reader);
		this.screenSize = screenSize;
	}
	
	private BufferedImage resize (BufferedImage img) {
		Rectangle newSize = calcSize(img);
		ResampleOp resampleOp = new ResampleOp (newSize.width, newSize.height); 
		resampleOp.setUnsharpenMask(AdvancedResizeOp.UnsharpenMask.Normal); 
		return resampleOp.filter(img, null);
	}
	
	private Rectangle calcSize(BufferedImage img){
		Rectangle result = new Rectangle();
		if (img.getHeight()>=img.getWidth()) {
			result.height = img.getHeight()>screenSize.height ? screenSize.height : img.getHeight();
			double scale = (double)result.height/img.getHeight();
			result.width = (int)(img.getWidth()*scale);
		} else {
			result.width = img.getWidth()>screenSize.width ? screenSize.width : img.getWidth();
			double scale = (double)result.width/img.getWidth();
			result.height = (int)(img.getHeight()*scale);
		}
		System.out.println(img.getWidth()+ " "+img.getHeight()); //TODO: Remove
		System.out.println(result.width+ " "+result.height); // TODO: Remove
		return result;
	}

	@Override
	ReadyImageCortege getImage(String path) {
		ReadyImageCortege result = null;
		if (imageReader!=null) {
			result = imageReader.getImage(path);
			if (result != null) {
				result = new ReadyImageCortege(resize(result.getImage()),result.getInfo());				
			}
		}
		return result;
	}
}
