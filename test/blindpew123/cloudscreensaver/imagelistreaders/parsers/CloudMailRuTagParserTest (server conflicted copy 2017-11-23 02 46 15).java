package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.*;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.CharParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.CloudMailRuTagParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.PageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.WordParser;

public class CloudMailRuTagParserTest {

	
	PageParser pageParser;
	WordParser testParser;
	
	@Before
	public void init() {
		pageParser = new PageParser("no matter", "no matter") {
			@Override
			protected void processBuffer(char[] buffer, int limit) {}
			@Override
			protected WordParser initWordParser() {return null;}
			@Override
			protected CharParser initCharParser() {return null;}			
			@Override
			protected void finalProcessing() {}
		};
		testParser = new CloudMailRuTagParser(pageParser);
	}
	
	@Test
	public void testCheckNormalProcessTag(){
		String[] words = new String[]{"tree","weblink","linkname","type","folder","file"};
		for(String word:words) {
			testParser.process(word);
		}
		assertThat(pageParser.getFileMap().size(),equalTo(1));
	}	
	
	@Test
	public void testCheckDontSwitchesUntilTreeTag(){
		String[] words = new String[]{"oak","weblink","linkname","type","folder","file"};
		for(String word:words) {
			testParser.process(word);
		}
		assertThat(pageParser.getFileMap().size(),equalTo(0));
	}
	
	@Test
	public void testCheckIgnoreNull(){
		String[] words = new String[]{"oak","weblink","linkname","type","folder","file"};
		for(String word:words) {
			testParser.process(null);
		}
		assertThat(pageParser.getFileMap().size(),equalTo(0));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testCheckExceptionWhenLinkIsNotPresentBeforeType(){
		String[] words = new String[]{"tree","linkname","type","folder","file"};
		for(String word : words) {
			testParser.process(word);
		}
		fail(":(");
	}

}
