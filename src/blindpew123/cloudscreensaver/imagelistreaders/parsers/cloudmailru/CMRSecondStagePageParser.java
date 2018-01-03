package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.CharParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.PageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.QuotesParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.WordParser;
import blindpew123.cloudscreensaver.imagepath.ImagePath;

public class CMRSecondStagePageParser extends PageParser {
	
	public CMRSecondStagePageParser(ImagePath path){
		super(path);
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
					getFileSet().add(new ImagePath(word, false));
				}
			}
		}	
	}

	@Override
	protected void finalProcessing() {
		// implementation don't need for CMR pages
	}
}
