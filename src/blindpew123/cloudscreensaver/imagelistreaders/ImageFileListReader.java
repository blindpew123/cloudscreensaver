package blindpew123.cloudscreensaver.imagelistreaders;

import javax.imageio.ImageIO;

public abstract class ImageFileListReader {
	
	final ImageFileList imageList;
	
	public ImageFileListReader() {
		imageList = null;
	};
	
	public ImageFileListReader(ImageFileList imageList) {
		this.imageList = imageList;
	}
	
	boolean isMinRecordsCopied = false;
	
	public abstract ImageFileList readList();
	
	public abstract void readListTo(); // 
		
	protected boolean isFormatSupported(String strTest) {
		String[] names = ImageIO.getReaderFormatNames();
	
		for (int i = 0; i < names.length; ++i) {
			if (strTest.endsWith(names[i])) return true;
		}
		return false;	
	}
}
