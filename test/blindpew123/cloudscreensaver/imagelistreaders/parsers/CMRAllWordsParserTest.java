package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import static org.junit.Assert.*;

import java.lang.reflect.Field;

import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru.CMRWordParserReadyState;

public class CMRAllWordsParserTest {
	
	PageParser pageParser;
	Field wordParser;
	
	@Before
	public void init() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		pageParser = new PageParser(null) {
			@Override
			protected WordParser initWordParser() {return new CMRWordParserReadyState(this);}
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
	public void testCheckNormalProcessTag() {
		String[] words = new String[]{"tree","type","folder","garbage","trash","id","http://","garbage"};
		for(String word:words) {
			pageParser.getStringParser().process(word);
		}
		assertThat(pageParser.getFileSet().size(),equalTo(1));
		assertThat(pageParser.getFileSet().iterator().next().getPath(),equalTo("http://"));		
	}	
	
	@Test
	public void testCheckDontSwitchesUntilTreeTag(){
		String[] words = new String[]{"oak","type","folder","garbage","trash","id","http://","garbage"};
		for(String word:words) {
			pageParser.getStringParser().process(word);
		}
		assertThat(pageParser.getFileSet().size(),equalTo(0));
	}
	
	@Test
	public void testCheckIgnoreNulls(){		
		String[] words = new String[]{"tree","type","folder","garbage","trash","id","http://","garbage"};
		for(String word : words) {
			processNull();
			pageParser.getStringParser().process(word);
			processNull();
		}
		assertThat(pageParser.getFileSet().size(),equalTo(1));
		assertThat(pageParser.getFileSet().iterator().next().getPath(),equalTo("http://"));		
	}
	
	void processNull() {
		pageParser.getStringParser().process(null);
	}

}
