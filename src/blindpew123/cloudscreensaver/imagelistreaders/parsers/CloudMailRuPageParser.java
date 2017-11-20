package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import java.util.Iterator;
import java.util.Map;

public class CloudMailRuPageParser extends PageParser {

	public CloudMailRuPageParser(String basePath, String currentLevelPath) {
		super(basePath, currentLevelPath);		
	}

	@Override
	protected CharParser initCharParser() {
		return new QuotesParser();
	}

	@Override
	protected WordParser initWordParser() {
		return new CloudMailRuTagParser(this);
	}

	@Override
	protected void processBuffer(char[] buffer, int limit) {
		for(int i = 0; i < limit; i++) {
			if(getCharParser().process(buffer[i])) {
				getStringParser().process(getCharParser().getKeyWord().toString());
			}
		}	
	}

	@Override
	protected void finalProcessing() { // Remove links to top-level pages
		Map<String, Boolean> map = getFileMap();
		Iterator<Map.Entry<String, Boolean>> it = map.entrySet().iterator();
		while(it.hasNext()) {
			String path = it.next().getKey();
			if(map.get(path) && isTopLevel(path)) {
				it.remove();
			}
		}
	}		
	
	private boolean isTopLevel(String path) {		
		if(getCurrentLevelPath().length()>path.length() || getCurrentLevelPath().equals(path)) {
			return true;
		}		
		return false;
	}
}
