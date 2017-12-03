package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class CMRSecondStageTagParserTest {

	PageParser pageParser;
	Field wordParser;
	
	@Before
	public void init() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		pageParser = new PageParser(null, null) {
			@Override
			protected WordParser initWordParser() {return new CMRSecondStageTagParser();}
			@Override
			protected CharParser initCharParser() {return null;}			
			@Override
			protected void finalProcessing() {}
			@Override
			protected void processBuffer(char[] buffer) {}			
		};		
				
		wordParser = pageParser.getClass().getSuperclass().getDeclaredField("wordParser");				
		wordParser.setAccessible(true);
		wordParser.set(pageParser, pageParser.initWordParser());
	}

	@Test
	public void testOK() {
		String[] words = new String[]{"garbage","weblink_get","https://","download","keyValue","trash","https://","garbage"};
		String result = "";
		for(String word:words) {
			if (pageParser.getStringParser().process(word)) {
					result = result+word;					
				}
			}
		assertThat(result,equalTo("https://keyValue"));
			
	}		
	
	
	@Test
	public void testLinkTagIsAbsent() {
		String[] words = new String[]{"garbage","weblink....","https://","download","keyValue","trash","https://","garbage"};
		String result ="";
		for(String word:words) {
			if (pageParser.getStringParser().process(word)) {
					result = result + word;					
				}
			}
		assertThat(result,equalTo(""));
	}
	
	@Test
	public void testDownloadTagIsAbsent() {
		String[] words = new String[]{"garbage","weblink_get","https://","down....","keyValue","trash","https://","garbage"};
		String result ="";
		for(String word:words) {
			if (pageParser.getStringParser().process(word)) {
					result = result + word;					
				}
			}
		assertThat(result,equalTo("https://"));
	}
	
	@Test
	public void testCheckIgnoreNulls(){		
		String[] words = new String[]{"garbage",null, "weblink_get",null, "https://",null, "download", null, "keyValue",null, "trash","https://","garbage"};
		String result ="";
		for(String word : words) {			
			if (pageParser.getStringParser().process(word)) {
				result = result+word;					
			}
		}		
		assertThat(result,equalTo("https://keyValue"));
	}
	
	

}
