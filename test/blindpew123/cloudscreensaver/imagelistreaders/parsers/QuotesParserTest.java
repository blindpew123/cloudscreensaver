package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.CharParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.PageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.QuotesParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.WordParser;


public class QuotesParserTest {

	CharParser parser;
	PageParser pageParser;
	
	@Before
	public void initInstance() {
		 pageParser = new PageParser(null) {

			@Override
			protected CharParser initCharParser() {
				return new QuotesParser(this);
			}

			@Override
			protected WordParser initWordParser() {
				return null;
			}

			@Override
			protected void processBuffer(char[] buffer) {}

			@Override
			protected void finalProcessing() {}
			 
		 };
		 
		 parser = new QuotesParser(pageParser);
	}
	
	@Test
	public void testQuotesBaseStateReturnsFalse() {
		boolean result = parser.process('a');
		assertFalse(result);
	}
	
	@Test
	public void testQuotesProcessSeq() {
		char[] testSeq = "a\"b\"d".toCharArray();
		for(char c:testSeq) {
			boolean result = parser.process(c);
			if(result) break;
		}
		assertThat(parser.getKeyWord().toString(),equalTo("b"));
	}
	
	@Test
	public void testResettingParser() {
		char[] testSeq = "a\"b\"d".toCharArray();
		for(char c:testSeq) {
			if(c == 'b') pageParser.getCharParseDeque().push('\"');
			boolean result = parser.process(c);
			if(result) break;
		}
		assertThat(parser.getKeyWord().toString(),equalTo("d"));
	}
	
}
