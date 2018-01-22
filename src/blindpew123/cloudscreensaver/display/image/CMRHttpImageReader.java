package blindpew123.cloudscreensaver.display.image;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReadersManager;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru.CMRSecondStagePageParser;
import blindpew123.cloudscreensaver.imagepath.ImagePath;
import blindpew123.cloudscreensaver.settings.SettingsFile;

/** Reads image from Cloud Mail Ru public image url.
 *  It tying to get http address of shard, that keeps image. 
 *  If impossile to get image from this shard, it is removed and reader uses another shard name until it will get image, 
 *  or while shard queue has at least one element
 */

public class CMRHttpImageReader extends ImageReader {
	
	static final int SHARD_POSITION = 2;
	static final int LAST_PREFIX_POSITION
		= SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix").split("/").length-1;
	String currentShard = "";
	
	private Set<String> shards = new HashSet<>();
	LinkedList<String> pathParts; 

	CMRHttpImageReader(ImageReader reader) {
		super(reader);	
	}

	@Override
	ReadyImageCortege getImage(ImagePath path) {
		if (path == null) {return null;} 
		if (!path.isCloudMailRuPath()) {
				return nextImageReader == null ? null : nextImageReader.getImage(path);				
		}
		Set<ImagePath> set = new CMRSecondStagePageParser(path).processPage();
		if(set.isEmpty()) {return null;} 
		
		splitPath(path.getAbsolutePath());
		addPathParts(set);
		ReadyImageCortege result = null;
		ImageReader reader = new HttpImageReader(null);
		do {
			result = reader.getImage(new ImagePath(buildPath(currentShard),false)); //default shard, already in the list
			if (result != null) {break;}
			shards.remove(currentShard);
			if (shards.isEmpty()) {break;}
			currentShard = shards.iterator().next();
		} while(true); //TODO: Make break when timeout between images is exceeded
		return result;		
	}
	
	private void addPathParts(Set<ImagePath> set) { // true - shard, false - key
		for(ImagePath part : set) {
			if (part.getPath().startsWith("https:")) {
				String[] fullShardElements = part.getPath().split("/");
				for(int i = fullShardElements.length-1 ; i>=0; i--) {
					pathParts.push(fullShardElements[i]);
				}
				shards.add(fullShardElements[SHARD_POSITION]); // fill up collection of shards
				currentShard = fullShardElements[SHARD_POSITION];
			}
			else  pathParts.add("?key="+part);
		}		
	}
	
	
	private String buildPath(String shard) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < pathParts.size(); i++) {
			result.append(i==SHARD_POSITION ? shard : pathParts.get(i));
			result.append(i<pathParts.size()-2 ? '/' : "");	// ...filename and parameter
		}
		return result.toString();
	}	
	
	private void splitPath(String path) {
		pathParts = new LinkedList<>(Arrays.asList(path.split("/"))); //0-"https:",1-"",2 - cloud web-server or shard,3....
		for(int i = 0; i<=LAST_PREFIX_POSITION;i++) {pathParts.remove();} // don't need standard CloudMailRu prefix elements
	}
	
	
}
