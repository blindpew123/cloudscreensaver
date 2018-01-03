package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.*;
import blindpew123.cloudscreensaver.imagepath.ImagePath;

public class CMRWordParserId extends WordParser {

	public CMRWordParserId(PageParser context) {
		super(context);
	}
	@Override
	public boolean process(String value) {
		if(!isNotNull(value)) return false;
		if(!getContext().getWordParseDeque().isEmpty()) {
			 getContext().getFileSet().add(new ImagePath(value, getContext().getWordParseDeque().pop().equals("folder")));		
		}
	    getContext().setStringParser(new CMRWordParserTag(getContext()));
		return false;
	}

}
