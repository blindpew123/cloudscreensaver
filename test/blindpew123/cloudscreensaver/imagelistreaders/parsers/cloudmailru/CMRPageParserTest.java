package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru.CMRPageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru.PageParser;

public class CMRPageParserTest {

	PageParser parser;
	
	@Before
	public void setUp() throws Exception {
		parser =   new CMRPageParser("https://cloud.mail.ru/public/", "DQEv/h67e4AAF9");
	}

	@Test
	public void testNormalProcessing() {
		Map<String, Boolean> testMap = parser.processPage();
		assertThat(testMap.size(),equalTo(3));
		for(String path: testMap.keySet()) {
				assertTrue(!testMap.get(path) && path.toLowerCase().endsWith(".jpg") 
						|| testMap.get(path) && path.toLowerCase().endsWith("more"));
		}
	}

	@Test(expected = RuntimeException.class)
	public void testWrongURLException() {
		parser = new CMRPageParser("","");
		parser.processPage();
		fail();
	}
	
	@Test
	public void testNotFoundPage() {
		parser = new CMRPageParser("https://cloud.mail.ru/public/", "DQEv/h");
		Map<?,?> testMap = parser.processPage();
		assertThat(testMap.size(),equalTo(0));
	}

}
