package blindpew123.cloudscreensaver.imagelistreaders;

public class ImageFileListReaders {
	
	private static final String[] AVALIABLE_CLOUD_PREFIXES = {"https://cloud.mail.ru/public/"};
	
	static ImageFileListReader getReader(String path) {
		ImageFileListReader reader = null;
		if (!preCheck(path)) return reader;
		if(isLocalPath(path)) {
			reader = new LocalFileSystemImageFileListReader(path);
		} else if(isCloudMailRuPath(path)) {
			String[] prefix0path1 = splitPathToPrefixAndVariablePath(path);
			reader = new CloudMailRuImageFileListReader(prefix0path1[0],prefix0path1[1]);
		} // here may be another types of readers
		return reader;
	}	
	
	private static boolean preCheck(String path) {
		return path!=null && path.length()>2;
	}
	
	private static boolean isLocalPath(String path) {
		if (path.charAt(1)!=':' && path.charAt(1)!='/') return false;
		if (!('A'<= path.charAt(0) && path.charAt(0)<='z' || path.charAt(0) =='/' )) return false;
		return true;
	}
	
	private static boolean isCloudMailRuPath(String path) {
		for(String prefix: AVALIABLE_CLOUD_PREFIXES)
			if(path.startsWith(prefix)) return true;
		return false;
	}
	
	private static String[] splitPathToPrefixAndVariablePath(String path) {
		for(String prefix: AVALIABLE_CLOUD_PREFIXES)
			if(path.startsWith(prefix)) return new String[] {prefix, path.replace(prefix,"")};
		throw new IllegalArgumentException(); //Unbelievable!
	}
	
}
