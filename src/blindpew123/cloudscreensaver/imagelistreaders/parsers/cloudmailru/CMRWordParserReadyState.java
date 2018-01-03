package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.BracketParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.PageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.WordParser;

public class CMRWordParserReadyState extends WordParser {

	public CMRWordParserReadyState(PageParser context) {
		super(context);
	}
	
	@Override
	public boolean process(String value) {
		if (isNotNull(value) && value.equals("tree")) {
			getContext().setStringParser(new CMRWordParserTag(getContext()));
			getContext().setCharParser(new BracketParser(getContext()));
		}
		return false; //Nvm
	}
}
