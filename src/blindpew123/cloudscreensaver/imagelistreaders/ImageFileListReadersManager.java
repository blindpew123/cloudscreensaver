package blindpew123.cloudscreensaver.imagelistreaders;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
		sumOfAllTrees = new ImageFileList();
		readers = new ArrayList<>();
	}
	
	public static synchronized ImageFileListReadersManager getInstance() {
		if(instance == null) instance = new ImageFileListReadersManager();
		return instance;
	}
	
	public ImageFileList getFileTrees(String[] startFoldersPathNames) {
		for(String path:startFoldersPathNames) {
			setReaderForPath(path.trim());	
		}
		ExecutorService es = Executors.newWorkStealingPool();
		for(ImageFileListReader reader : readers) {
			es.execute(()->reader.readListTo());
		}
		es.shutdown();
		return sumOfAllTrees;
	}
	
	private void setReaderForPath(String path) {
		ImageFileListReader currentReader = null;
		currentReader = getReader(path, sumOfAllTrees);
		if(currentReader == null) throw new IllegalArgumentException();
		readers.add(currentReader);
	}
	
	private ImageFileListReader getReader(String path, ImageFileList fileList) {
		ImageFileListReader reader = null;
		if (!preCheck(path)) return reader;
		if(isLocalPath(path)) {
			reader = new LocalFileSystemImageFileListReader(path, fileList);
		} else if(isCloudMailRuPath(path)) {
			String[] prefix0path1 = splitPathToPrefixAndVariablePath(path);
			reader = new CloudMailRuImageFileListReader(prefix0path1[0],prefix0path1[1], fileList);
		} // here may be another types of readers
		return reader;
	}
	
	public boolean isReaderAvailable(String path) {
		return getReader(path,null) != null;
	}
	
	public static boolean isCloudMailRuPath(String path) {		
		if(path.startsWith(getCloudMailRuPrefix())) return true;
		return false;
	}
	
	public static boolean isLocalPath(String path) {
		if (path.charAt(1)!=':' && path.charAt(1)!='/') return false;
		if (!('A'<= path.charAt(0) && path.charAt(0)<='z' || path.charAt(0) =='/' )) return false;
		return true;
	}
	
	public static String getCloudMailRuPrefix() {		
		return SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix").trim();
	}
	
	private static String[] splitPathToPrefixAndVariablePath(String path) {		
		if(path.startsWith(getCloudMailRuPrefix())) {
			return new String[] {getCloudMailRuPrefix(), path.replace(getCloudMailRuPrefix(),"")};
		}
		throw new IllegalArgumentException(); //Unbelievable!
	}
	
	private static boolean preCheck(String path) {
		return path!=null && path.length()>2;
	}
	
	

}
