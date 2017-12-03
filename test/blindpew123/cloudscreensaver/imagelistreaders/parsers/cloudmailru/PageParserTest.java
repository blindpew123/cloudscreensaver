package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

public class PageParserTest {

	PageParser parser;
	
	@Test
	public void testProcessingOK() {
		initParser("https://www.w3.org/", "MarkUp/Test/");	
		Map<?,?> map =  parser.processPage();
		assertThat(map.keySet().iterator().next(), equalTo("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>"));
	}
	
	@Test(expected = RuntimeException.class)
	public void testExceptionWithWrongURL() {		
		initParser(null, null);
		parser.processPage();
		fail();
	}
	
	private void initParser(String prefix, String endPath) {
		parser = new PageParser(prefix, endPath) {
			
			@Override
			protected CharParser initCharParser() {return null;}

			@Override
			protected WordParser initWordParser() {return null;}

			@Override
			protected void processBuffer(char[] buffer) {
				StringBuilder testStringBuf= new StringBuilder();
				for(int i = 0; i< buffer.length; i++) {testStringBuf.append(buffer[i]);}
				getWordParseDeque().push(testStringBuf.toString());
			}

			@Override
			protected void finalProcessing() {
				String result = null;
				while(getWordParseDeque().size() > 0) {result = getWordParseDeque().pop();}
				getFileMap().put(result,true);				
			}		
		};
	}
}

