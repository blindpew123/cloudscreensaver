package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

public class CMRWordParserReadyState extends WordParser {

	public CMRWordParserReadyState(PageParser context) {
		super(context);
	}
	
	@Override
	protected boolean process(String value) {
		if (value.equals("tree")) {
			getContext().setStringParser(new CMRWordParserTag(getContext()));
			getContext().setCharParser(new BracketParser(getContext()));
		}
		return false; //Nvm
	}
}
