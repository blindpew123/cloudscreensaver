package blindpew123.cloudscreensaver.imagelistreaders.parsers;

public class CloudMailRuSecondStagePageParser extends PageParser {
	
	public CloudMailRuSecondStagePageParser(String prefix, String currentLevelPath){
		super(prefix, currentLevelPath);
	}
	
	@Override
	protected CharParser initCharParser() {
		return new QuotesParser();
	}

	@Override
	protected WordParser initWordParser() {
		return new CloudMailRuSecondStageTagParser();
	}

	@Override
	protected void processBuffer(char[] buffer, int limit) {
		for(int i = 0; i < limit; i++) {
			if(getCharParser().process(buffer[i])) {
				String word = getCharParser().getKeyWord().toString();
				if (getStringParser().process(word)) {
					getFileMap().put(word, false);
				}
			}
		}	
	}

	@Override
	protected void finalProcessing() {}
}
