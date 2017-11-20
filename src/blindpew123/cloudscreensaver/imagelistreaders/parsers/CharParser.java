package blindpew123.cloudscreensaver.imagelistreaders.parsers;

abstract class  CharParser {
	
	private PageParser context;
	private StringBuilder keyWord = new StringBuilder();
	
	CharParser(PageParser context){
		this.context = context;
	}
	
	CharParser(){} // is used in case processing char directly, without state switching
	
	protected abstract boolean process(char c);
	
	protected PageParser getContext() {
		return context;
	}
	
	protected StringBuilder getKeyWord() {
		return keyWord;
	}

	protected void setKeyWord(StringBuilder keyWord) {
		this.keyWord = keyWord;
	}	
	
}
