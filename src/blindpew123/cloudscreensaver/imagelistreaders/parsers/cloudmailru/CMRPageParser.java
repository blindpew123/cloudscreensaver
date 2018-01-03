package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.*;
import blindpew123.cloudscreensaver.imagepath.ImagePath;

public class CMRPageParser extends PageParser {

	public CMRPageParser(ImagePath currentLevelPath) {
		super(currentLevelPath);
	}

	@Override
	protected CharParser initCharParser() {
		return new QuotesParser(this);
	}

	@Override
	protected WordParser initWordParser() {
		return new CMRWordParserReadyState(this);
	}

	@Override
	protected void processBuffer(char[] buffer) {
		if(getCharParser() instanceof QuotesParser) {
			getCharParseDeque().push('\"'); //new string, resetting Quotes Parser 
		}
		for(int i = 0; i < buffer.length; i++) {
			if(getCharParser().process(buffer[i])) {
				getStringParser().process(getCharParser().getKeyWord().toString());
			}
		}

	}

	@Override
	protected void finalProcessing() {
		// implementation don't need for CMR pages
	}

}
