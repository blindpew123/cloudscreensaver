package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import java.io.IOException;
import java.net.MalformedURLException;

import org.junit.*;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.CloudMailRuPageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.PageParser;

public class CloudMailRuParserSpeedTest {

	PageParser parser;
	
	@Before
	public void init() throws MalformedURLException, InterruptedException{
		parser = new CloudMailRuPageParser("https://cloud.mail.ru/public/", "8CVD/qbYdCdzvn");
		parser.processPage();
	}
	
	@Test
	public void testPerfomance() throws Exception, IOException {
		long result =  System.currentTimeMillis();
		for(int i = 0; i<5;i++)
		parser.processPage();
		System.out.println((System.currentTimeMillis()-result)/5);
	}
	

}
