package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

public class CMRSecondStagePageParserTest {

	final static String PREFIX = "https://cloud.mail.ru/public/";
	final static String IMAGEPATH = "DQEv/h67e4AAF9/DSC05160.jpg";
	
	CMRSecondStagePageParser parser;
	
	
	
	@Before
	public void setUp() throws Exception {		
		parser = new CMRSecondStagePageParser(PREFIX, IMAGEPATH );	
	}

	@Test
	public void testOK() throws IOException {
		Map<String, Boolean> map = parser.processPage();
		
		String url = getParts(map)[0]+"/"+IMAGEPATH+"?key="+getParts(map)[1];
		BufferedImage image = ImageIO.read(new URL(url));
		assertThat(map.size(), equalTo(2));
		assertThat(image.getHeight(), equalTo(3264));
	}
	
	@Test
	public void testWrongPathPartButResultOK() throws IOException {		
		parser = new CMRSecondStagePageParser(PREFIX, "DQEv/h67e4AAF9/DSC05160" );		
		Map<String, Boolean> map = parser.processPage();
		assertThat(map.size(), equalTo(2));		
	}
	
	@Test(expected = IOException.class)
	public void testWrongPathNotAllowUseResult() throws IOException {		
		parser = new CMRSecondStagePageParser(PREFIX, "DQEv/h67e4AAF9/DSC05160" );		
		Map<String, Boolean> map = parser.processPage();
		String url = getParts(map)[0]+"/"+"DQEv/h67e4AAF9/DSC05160"+"?key="+getParts(map)[1];
		ImageIO.read(new URL(url));
		fail();
	}
	
	
	private String[] getParts(Map<String, Boolean> map) {
		String[] result = new String[2];
		for(String str:map.keySet()) {
			if (map.get(str)) result[0] = str;
			else result[1] = str;
		}
		return result;
	}

}
