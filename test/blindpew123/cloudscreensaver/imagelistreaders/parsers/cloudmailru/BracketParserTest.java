package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BracketParserTest {
	
	PageParser pp; 
	BracketParser parser;		
			

	@Before
	public void setUp() throws Exception {
		pp = new PageParser(null, null) {

			@Override
			protected CharParser initCharParser() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			protected WordParser initWordParser() {
				// TODO Auto-generated method stub
				return null;
			}			

			@Override
			protected void finalProcessing() {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void processBuffer(char[] buffer) {
				// TODO Auto-generated method stub
				
			};
		};
		parser = new BracketParser(pp);		
	}
	

	@Test
	public void testSucssefullySwitch() {
		char[] testSeq = "[a\"b[\n]d\"g\"f]f".toCharArray();
		for(char c:testSeq) {
			boolean result = parser.process(c);
			if(result) break;
		}
		assertTrue(pp.getCharParser() instanceof QuotesParser);
	}
	
	@Test
	public void testNotSucssefullySwitch() {
		char[] testSeq = "[a\"b[\n]d\"g\"ff".toCharArray();
		for(char c:testSeq) {
			boolean result = parser.process(c);
			if(result) break;
		}
		assertFalse(pp.getCharParser() instanceof QuotesParser);
	}
}
