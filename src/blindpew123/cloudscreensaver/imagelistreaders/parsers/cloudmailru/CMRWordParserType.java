package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.PageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.WordParser;

public class CMRWordParserType extends WordParser {

	public CMRWordParserType(PageParser context) {
		super(context);
	}
	@Override
	public boolean process(String value) {
		if(isNotNull(value)) {
			getContext().getWordParseDeque().clear();
			getContext().getWordParseDeque().push(value);
			getContext().setStringParser(new CMRWordParserTag(getContext()));
		}
		return false;
	}

}
