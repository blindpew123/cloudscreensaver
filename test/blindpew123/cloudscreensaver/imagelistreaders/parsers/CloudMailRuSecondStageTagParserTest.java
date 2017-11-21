package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CloudMailRuSecondStageTagParserTest {

	 WordParser testParser;
	
	@Before
	public void setUp() throws Exception {		 
		
		testParser = new CloudMailRuSecondStageTagParser();
	}

	@Test
	public void testCheckNormalProcessTag(){
		String result = "";
		String[] words = new String[]{"tree","original","linkname","type","folder","file"};
		for(String word:words) {
			if(testParser.process(word)) {
				result = word;
			}
		}
		assertThat(result,equalTo("linkname"));
	}
	
	@Test
	public void testCheckSkipPageWithoutTag(){
		String result = "";
		String[] words = new String[]{"tree","nooriginal","linkname","type","folder","file"};
		for(String word:words) {
			if(testParser.process(word)) {
				result = word;
			}
		}
		assertThat(result,equalTo(""));
	}

	
	@Test
	public void testCheckIgnoreNull(){
		String result = "";
		String[] words = new String[]{"tree","original","linkname","type","folder","file"};
		for(String word:words) {
			if(testParser.process(null)) {
				result = word;
			}
		}
		assertThat(result,equalTo(""));
	}
}

