package blindpew123.cloudscreensaver.imagelistreaders;

/**
 * @author silve
 *
 */
public class ImageFileListReadersManager {
	
	static private ImageFileListReadersManager instance;
	
	private ImageFileListReader currentReader;	
	private ImageFileListReadersManager() {
		
	}
	
	public static synchronized ImageFileListReadersManager getInstance() {
		if(instance == null) instance = new ImageFileListReadersManager();
		return instance;
	}
	
	public ImageFileList getFileTrees(String[] startFoldersPathNames) {
		ImageFileList sumOfAllTrees = new ImageFileList();
		for(String path:startFoldersPathNames) {
			setReaderForPath(path.trim());
			sumOfAllTrees.put(currentReader.readList());
		}		
		return sumOfAllTrees;
	}
	
	private void setReaderForPath(String path) {
		currentReader = null;
		currentReader = ImageFileListReaders.getReader(path.trim());
		if(currentReader == null) throw new IllegalArgumentException();
	}
	
	public boolean isReaderAvailable(String path) {
		return ImageFileListReaders.getReader(path) != null;
	}

}
