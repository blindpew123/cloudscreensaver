package blindpew123.cloudscreensaver.imagelistreaders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import blindpew123.cloudscreensaver.imagepath.ImagePath;
import blindpew123.cloudscreensaver.settings.SettingsFile;

/**
 * @author silve
 *
 */
public class ImageFileListReadersManager {
	
	static private ImageFileListReadersManager instance;
	
	private List<ImageFileListReader> readers;
	private ImageFileList sumOfAllTrees;
	
	private ImageFileListReadersManager() {
		
	}
	
	public static synchronized ImageFileListReadersManager getInstance() {
		if(instance == null) instance = new ImageFileListReadersManager();
		return instance;
	}
	
	public ImageFileList getFileTrees(ImageFileList startFoldersPathNames) {
		initLists();
		if(startFoldersPathNames != null) { 
			for(ImagePath path:startFoldersPathNames) {
				setReaderForPath(path);	
			}
			ExecutorService es = Executors.newWorkStealingPool();
			for(ImageFileListReader reader : readers) {
				es.execute(reader::readListTo);
			}
			es.shutdown();
		}
		return sumOfAllTrees;
	}
	
	private void setReaderForPath(ImagePath path) {
		ImageFileListReader currentReader = null;
		currentReader = getReader(path, sumOfAllTrees);
		if(currentReader == null) throw new IllegalArgumentException();
		readers.add(currentReader);
	}
	
	private ImageFileListReader getReader(ImagePath path, ImageFileList fileList) {
		ImageFileListReader reader = null;
		if(path.isLocalOrLocalNetworkPath()) {
			reader = new LocalFileSystemImageFileListReader(path, fileList);
		} else if(path.isCloudMailRuPath()) {
			reader = new CloudMailRuImageFileListReader(path, fileList);
		} // here may be another types of readers
		return reader;
	}
	
	public boolean isReaderAvailable(ImagePath path) {
		return path == null ? false : getReader(path,null) != null;
	}
	
	private void initLists() {
		sumOfAllTrees = new ImageFileList();
		readers = new ArrayList<>();
	}
	
	

}
