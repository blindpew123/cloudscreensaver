package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

public class CMRWordParserTag extends WordParser {

	public CMRWordParserTag(PageParser context) {
		super(context);
	}
	@Override
	protected boolean process(String value) {
		if (value.equals("id")) {
			getContext().setStringParser(new CMRWordParserId(getContext()));
		} else if(value.equals("type")) {
				getContext().setStringParser(new CMRWordParserType(getContext()));
		}
		return false;
	}

}
