package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

public class CMRWordParserId extends WordParser {

	public CMRWordParserId(PageParser context) {
		super(context);
	}
	@Override
	public boolean process(String value) {
		if(!isNotNull(value)) return false;
		if(!getContext().getWordParseDeque().isEmpty()) {
			 getContext().getFileMap().put(value, getContext().getWordParseDeque().pop().equals("folder"));		
		}
	    getContext().setStringParser(new CMRWordParserTag(getContext()));
		return false;
	}

}
