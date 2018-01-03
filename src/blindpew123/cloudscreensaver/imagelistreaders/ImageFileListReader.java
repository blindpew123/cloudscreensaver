package blindpew123.cloudscreensaver.imagelistreaders;

import javax.imageio.ImageIO;

import blindpew123.cloudscreensaver.imagepath.ImagePath;

public abstract class ImageFileListReader {
	
	final ImageFileList imageList;
	final ImagePath startPath;
	
	public ImageFileListReader(ImagePath startPath, ImageFileList imageList) {
		this.startPath = startPath;
		this.imageList = imageList;
	}
	
	boolean isMinRecordsCopied = false;
	
	public abstract ImageFileList readList();
	
	public abstract void readListTo(); // 
		
	protected boolean isFormatSupported(ImagePath path) {
		String[] names = ImageIO.getReaderFormatNames();
	
		for (int i = 0; i < names.length; ++i) {
			if (path.toString().endsWith(names[i])) return true;
		}
		return false;	
	}
}
