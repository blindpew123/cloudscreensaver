package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.PageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru.CMRPageParser;
import blindpew123.cloudscreensaver.imagepath.ImagePath;
import blindpew123.cloudscreensaver.settings.SettingsFile;

public class CMRPageParserTest {

	PageParser parser;
	
	@Before
	public void setUp() throws Exception {
		parser =   new CMRPageParser(new ImagePath(SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix"),"DQEv/h67e4AAF9", true));
	}

	@Test
	public void testNormalProcessing() {
		Set<ImagePath> testSet = parser.processPage();
		assertThat(testSet.size(),equalTo(3));
		for(ImagePath path: testSet) {
				assertTrue(!path.isFolder() && path.getPath().toLowerCase().endsWith(".jpg") 
						|| path.isFolder() && path.getPath().toLowerCase().endsWith("more"));
		}
	}

	@Test(expected = RuntimeException.class)
	public void testWrongURLException() {
		parser = new CMRPageParser(new ImagePath("", true));
		parser.processPage();
		fail();
	}
	
	@Test
	public void testNotFoundPage() {
		parser = new CMRPageParser(new ImagePath(SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix"), "DQEv/h", true));
		Set<?> testMap = parser.processPage();
		assertThat(testMap.size(),equalTo(0));
	}

}
