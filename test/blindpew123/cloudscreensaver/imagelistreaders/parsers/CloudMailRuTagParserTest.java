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
			protected void processBuffer(char[] buffer, int limit) {
				// TODO Auto-generated method stub
				
			}

			@Override
			protected void finalProcessing() {
				// TODO Auto-generated method stub
				
			}
			
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
	
	@Test(expected = IllegalArgumentException.class)
	public void testCheckExceptionWhenLinkIsNotPresentBeforeType(){
		String[] words = new String[]{"tree","linkname","type","folder","file"};
		for(String word : words) {
			testParser.process(word);
		}
		fail(":(");
	}

}
