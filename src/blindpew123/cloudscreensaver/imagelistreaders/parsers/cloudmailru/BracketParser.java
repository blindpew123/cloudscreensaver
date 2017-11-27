package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

public class BracketParser extends CharParser {

	public BracketParser(PageParser context) {
		super(context);		
	}
	
	@Override
	protected boolean process(char c) {
		switch (c) {
			case '[': getContext().getCharParseDeque().push(c);					  
					  break;
			case ']': getContext().getCharParseDeque().pop();
					  if(getContext().getCharParseDeque().isEmpty()) {
						  getContext().setCharParser(new QuotesParser(getContext()));
					  }
					  break;
			default:   
		}
		return false; 
	}

}
