package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.net.MalformedURLException;
import java.util.Map;

import org.junit.*;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.CloudMailRuPageParser;

public class CloudMailRuPageParserTest {

	CloudMailRuPageParser parser;
	
	@Before
	public void init() {		
		parser = new CloudMailRuPageParser("https://cloud.mail.ru/public/", "DQEv/h67e4AAF9");
	}
	
	@Test
	public void testNormalProcessing() throws MalformedURLException, InterruptedException {
		Map<?,?> testMap = parser.processPage();
		assertThat(testMap.size(),equalTo(3));
	}

	@Test(expected = MalformedURLException.class)
	public void testWrongURLException() throws MalformedURLException, InterruptedException {
		parser = new CloudMailRuPageParser("","");
		Map<?,?> testMap = parser.processPage();
		fail();
	}
	
	@Test
	public void testNonReadablePage() throws MalformedURLException, InterruptedException {
		parser = new CloudMailRuPageParser("https://cloud.mail.ru/public/", "DQEv/h");
		Map<?,?> testMap = parser.processPage();
		assertThat(testMap.size(),equalTo(0));
	}
}
