package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

public class CMRSecondStagePageParser extends PageParser {
	
	private boolean isPath = false;

	
	public CMRSecondStagePageParser(String prefix, String currentLevelPath){
		super(prefix, currentLevelPath);
	}
	
	@Override
	protected CharParser initCharParser() {
		return new QuotesParser(this);
	}

	@Override
	protected WordParser initWordParser() {
		return new CMRSecondStageTagParser();
	}
	
	@Override
	protected void processBuffer(char[] buffer) {
		if(getCharParser() instanceof QuotesParser) {
			getCharParseDeque().push('\"'); //new string, resetting Quotes Parser 
		}
		for(int i = 0; i < buffer.length; i++) {
			if(getCharParser().process(buffer[i])) {
				String word = getCharParser().getKeyWord().toString();
				if (getStringParser().process(word)) {
					getFileMap().put(word, isPath=!isPath);
				}
			}
		}	
	}

	@Override
	protected void finalProcessing() {
		// implementation don't need for CMR pages
	}
}
