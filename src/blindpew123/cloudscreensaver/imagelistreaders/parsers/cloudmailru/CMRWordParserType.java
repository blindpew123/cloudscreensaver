package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

public class CMRWordParserType extends WordParser {

	public CMRWordParserType(PageParser context) {
		super(context);
	}
	@Override
	protected boolean process(String value) {
		getContext().getWordParseDeque().clear();
		getContext().getWordParseDeque().push(value.toString());
		getContext().setStringParser(new CMRWordParserTag(getContext()));
		return false;
	}

}
