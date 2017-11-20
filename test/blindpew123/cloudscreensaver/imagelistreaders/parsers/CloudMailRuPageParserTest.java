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
		parser = new CloudMailRuPageParser("https://cloud.mail.ru/public/", "8CVD/qbYdCdzvn");
	}
	
	@Test
	public void test() throws MalformedURLException, InterruptedException {
		Map<?,?> testMap = parser.processPage();
		assertThat(testMap.size(),equalTo(10));
	}

}
