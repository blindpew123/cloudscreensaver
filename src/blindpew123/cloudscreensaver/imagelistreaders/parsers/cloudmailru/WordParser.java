package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;


abstract class WordParser{

	private PageParser context;
	
	WordParser(PageParser context){
		this.context = context;
	}

	WordParser(){} // is used in case processing string directly, without state switching 	
	
	protected abstract boolean process(String value); 
	
	protected PageParser getContext() {
		return context;
	}
}
