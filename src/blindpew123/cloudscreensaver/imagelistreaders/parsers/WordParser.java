package blindpew123.cloudscreensaver.imagelistreaders.parsers;

public abstract class WordParser implements WordStateMachine{

	private PageParser context;
	
	protected WordParser(PageParser context){
		this.context = context;
	}

	protected WordParser(){} // is used in case processing string directly, without state switching 		
	
	protected PageParser getContext() {
		return context;
	}	

}
