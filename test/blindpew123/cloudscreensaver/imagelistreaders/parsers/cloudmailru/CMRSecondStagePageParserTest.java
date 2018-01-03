package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.imagepath.ImagePath;

public class CMRSecondStagePageParserTest {

	final static String PREFIX = "https://cloud.mail.ru/public/";
	final static String IMAGEPATH = "DQEv/h67e4AAF9/DSC05160.jpg";
	
	CMRSecondStagePageParser parser;
	
	
	
	@Before
	public void setUp() throws Exception {		
		parser = new CMRSecondStagePageParser(new ImagePath(PREFIX+IMAGEPATH, true));	
	}

	@Test
	public void testOK() throws IOException {
		Set<ImagePath> set = parser.processPage();
		
		String url = getParts(set)[0]+"/"+IMAGEPATH+"?key="+getParts(set)[1];
		BufferedImage image = ImageIO.read(new URL(url));
		assertThat(set.size(), equalTo(2));
		assertThat(image.getHeight(), equalTo(3264));
	}
	
	@Test
	public void testWrongPathPartButResultOK() throws IOException {		
		parser = new CMRSecondStagePageParser(new ImagePath(PREFIX+"DQEv/h67e4AAF9/DSC05160",false));		
		Set<ImagePath> set = parser.processPage();
		assertThat(set.size(), equalTo(2));		
	}
	
	@Test(expected = IOException.class)
	public void testWrongPathNotAllowUseResult() throws IOException {		
		parser = new CMRSecondStagePageParser(new ImagePath(PREFIX+"DQEv/h67e4AAF9/DSC05160",false));		
		Set<ImagePath> set = parser.processPage();
		String url = getParts(set)[0]+"/"+"DQEv/h67e4AAF9/DSC05160"+"?key="+getParts(set)[1];
		ImageIO.read(new URL(url));
		fail();
	}
	
	
	private String[] getParts(Set<ImagePath> map) {
		String[] result = new String[2];
		for(ImagePath str:map) {
			if (str.getPath().startsWith("https:")) result[0] = str.getPath();
			else result[1] = str.getPath();
		}
		return result;
	}

}
