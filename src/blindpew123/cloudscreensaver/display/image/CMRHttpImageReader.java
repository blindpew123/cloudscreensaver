package blindpew123.cloudscreensaver.display.image;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReadersManager;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru.CMRSecondStagePageParser;
import blindpew123.cloudscreensaver.settings.SettingsFile;

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
	ReadyImageCortege getImage(String path) {
		if (path == null) {return null;} 
		if (!ImageFileListReadersManager.isCloudMailRuPath(path)) {
				return nextImageReader == null ? null : nextImageReader.getImage(path);				
		}
		Map<String, Boolean> map = new CMRSecondStagePageParser(path, "").processPage();
		if(map.size() == 0) {return null;} // TODO: or may be throw ?
		
		splitPath(path);
		addPathParts(map);
		ReadyImageCortege result = null;
		ImageReader reader = new HttpImageReader(null);
		do {
			result = reader.getImage(buildPath(currentShard)); //default shard, already in the list
			if (result != null) {break;}
			shards.remove(currentShard);
			if (shards.size()==0) {break;}
			currentShard = shards.iterator().next();
		} while(true); //TODO: Здесь условие проверки времени запросов
		return result;		
	}
	
	private void addPathParts(Map<String, Boolean> map) { // true - shard, false - key
		for(String str:map.keySet()) {
			if (map.get(str)) {
				String[] fullShardElements = str.split("/");
				for(int i = fullShardElements.length-1 ; i>=0; i--) {
					pathParts.push(fullShardElements[i]);
				}
				shards.add(fullShardElements[SHARD_POSITION]); // fill up collection of shards
				currentShard = fullShardElements[SHARD_POSITION];
			}
			else  pathParts.add("?key="+str);
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
