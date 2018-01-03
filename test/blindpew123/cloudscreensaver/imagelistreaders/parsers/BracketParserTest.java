package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.BracketParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.CharParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.PageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.QuotesParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.WordParser;

public class BracketParserTest {
	
	PageParser pp; 
	BracketParser parser;		
			

	@Before
	public void setUp() throws Exception {
		pp = new PageParser(null) {

			@Override
			protected CharParser initCharParser() {
				return null;
			}

			@Override
			protected WordParser initWordParser() {
				return null;
			}			

			@Override
			protected void finalProcessing() {}

			@Override
			protected void processBuffer(char[] buffer) {};
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
