package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.Map;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.CharParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.PageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.WordParser;
import blindpew123.cloudscreensaver.imagepath.ImagePath;

public class PageParserTest {

	PageParser parser;
	
	@Test
	public void testProcessingOK() {
		initParser("https://www.w3.org/", "MarkUp/Test/");	
		Set<ImagePath> map =  parser.processPage();
		assertThat(map.iterator().next().getPath(), equalTo("<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>"));
	}	
		
	private void initParser(String prefix, String endPath) {
		parser = new PageParser(new ImagePath(prefix+endPath,true)) {
			
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
				getFileSet().add(new ImagePath(result,false));				
			}		
		};
	}
}

