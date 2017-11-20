package blindpew123.cloudscreensaver.imagelistreaders;

import java.util.ArrayList;

class NullImageFileListReader extends ImageFileListReader {
	@Override
	public ImageFileList readList() {
		return new ImageFileList(new ArrayList<String>());
	}
}
