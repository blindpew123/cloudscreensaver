package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;


abstract class WordParser implements WordStateMachine{

	private PageParser context;
	
	WordParser(PageParser context){
		this.context = context;
	}

	WordParser(){} // is used in case processing string directly, without state switching 		
	
	protected PageParser getContext() {
		return context;
	}	

}
