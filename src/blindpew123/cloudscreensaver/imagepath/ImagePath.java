package blindpew123.cloudscreensaver.imagepath;

import java.net.MalformedURLException;
import java.net.URL;

import blindpew123.cloudscreensaver.display.image.ImageReadingException;
import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReadersManager;
import blindpew123.cloudscreensaver.settings.SettingsFile;

public class ImagePath {

	private final String path;
	private final boolean isFolder;
	private String urlPrefix;
	
	public ImagePath(String path, boolean isFolder) {
		this.path = path;
		this.isFolder = isFolder;
		urlPrefix="";		
	}
	
	public ImagePath(String prefix, String path, boolean isFolder) {
		this(path,isFolder);
		setUrlPrefix(prefix);
	}
	
	public String getPath() {
		return path;
	}
	
	public boolean isFolder() {
		return isFolder;
	}
	
	public String getAbsolutePath() {
		if (getPath() == null) {throw new ImageReadingException("Null path", this);} 
		return getUrlPrefix() == null ? "" : getUrlPrefix() + getPath();
	}
	
	public String getUrlPrefix() {
		return urlPrefix;
	}
	
	public void setUrlPrefix(String prefix) {
		urlPrefix = prefix; 
	}
	
	public boolean isCloudMailRuPath() {		
		return getAbsolutePath().startsWith(getCloudMailRuPrefix());		
	}
	
	public boolean isLocalOrLocalNetworkPath() {
		if (getAbsolutePath().length() < 2) return false;
		String temp = getAbsolutePath().replace("\\", "/");		
		return ((temp.charAt(1) == ':' && 'A'<= temp.charAt(0) && temp.charAt(0)<='z' )
				|| (temp.charAt(1)=='/'  &&  temp.charAt(0) =='/' ));
	}
	
	public static String getCloudMailRuPrefix() {		
		return SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix").trim();
	}
	
	public String getShortPathForDisplay() {
		return removeCloudMailUnnecessaryInfo(getAbsolutePath());
	}
	
	/** I don't like long recondite links provided Cloud.Mail.ru.
	 *  This is sample: https://cloclo5.cldmail.ru/QZWMepdZ2AJTvYHHJUV/G/DQEv/h67e4AAF9/DSC05160.jpg?key=b37e69e9353792c7787643818335c36548b3b94d
	 *  I want to leave only host and that part, which starts from folder's name
	 * @param string - path to check and remove if it needed
	 * @return processed path
	 */
		
	private String removeCloudMailUnnecessaryInfo(String string){ 
		if(!string.contains("mail.ru")) return string;
		int counter = 0;
		String[] parts = string.split("\\?")[0].split("/");
		StringBuilder result = new StringBuilder("https://cloud.mail.ru");
		for(String part : parts) {
			if(7 < parts.length &&  counter > 6) result.append(part);
			if(counter == 0) result.append("->");
			if( 6 < counter&&  counter < parts.length-1) result.append("/");			
			counter++;
		}
		return result.toString();
	}
		
	@Override
	public String toString() {
		return path;
	}
	
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ImagePath){
			return toString().equals(((ImagePath)obj).toString());
		}
		return false;
	}
}
