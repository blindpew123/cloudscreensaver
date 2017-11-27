package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

public class QuotesParser extends CharParser {
	
	public QuotesParser(PageParser context) {
		super(context);
	}
	
	boolean quotesOpened = false;
	@Override
	protected boolean process(char c) {			
		if(!getContext().getCharParseDeque().isEmpty()){
			getContext().getCharParseDeque().pop();
			quotesOpened = false;	
		}
		if (quotesOpened) {
			if(c == '\"') {
				quotesOpened = false;				
				return true;
			}  else {
				getKeyWord().append(c);
			}
		} else {
			if(c == '\"') {
				quotesOpened = true;
				setKeyWord(new StringBuilder());
			}
		}
		return false;
	}
}
