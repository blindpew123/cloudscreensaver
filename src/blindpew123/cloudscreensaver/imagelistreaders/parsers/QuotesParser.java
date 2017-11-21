package blindpew123.cloudscreensaver.imagelistreaders.parsers;

/** It is a state machine in two states: The quotes are open and the quotes are closed.
 *  Finds the sequence enclosed in quotation marks among the sent characters and not interrupted by the character "\n". 
 *  If this sequence was found, the result of the <b>process(char c)</b> method is true, after receiving the trailing character. 
 *  In this case, StringBuilder object with the sequence within can be retrieved  by calling the inherited method getKeyWord().
 *  In other case, the result of call getKeyWord() is undetermined.
*/

public class QuotesParser extends CharParser {
	
	private boolean quotesOpened = false;	

	@Override
	public boolean process(char c) {
		if (quotesOpened) {
			if(c == '\"') {
				quotesOpened = false;				
				return true;
			} else if(c =='\n') {
				quotesOpened = false;
			} else {
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
