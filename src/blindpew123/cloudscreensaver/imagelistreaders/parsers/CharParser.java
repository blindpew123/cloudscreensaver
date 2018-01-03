package blindpew123.cloudscreensaver.imagelistreaders.parsers;

public abstract class  CharParser {
	
	private PageParser context;
	private StringBuilder keyWord = new StringBuilder();
	
	CharParser(PageParser context){
		this.context = context;
	}
	
	CharParser(){} // is used in case processing char directly, without state switching
	
	public abstract boolean process(char c);
	
	protected PageParser getContext() {
		return context;
	}
	
	public StringBuilder getKeyWord() {
		return keyWord;
	}

	protected void setKeyWord(StringBuilder keyWord) {
		this.keyWord = keyWord;
	}	
	
}
