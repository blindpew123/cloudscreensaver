package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.CharParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.QuotesParser;

public class QuotesParserTest {

	CharParser parser;
	
	@Before
	public void initInstance() {
		 parser = new QuotesParser();
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
	public void testQuotesLineEndDropSeq() {
		char[] testSeq = "a\"b\nd\"g\"f".toCharArray();
		for(char c:testSeq) {
			boolean result = parser.process(c);
			if(result) break;
		}
		assertThat(parser.getKeyWord().toString(),equalTo("g"));
	}
}
