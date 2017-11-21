package blindpew123.cloudscreensaver.imagelistreaders;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import blindpew123.cloudscreensaver.imagelistreaders.CloudMailRuImageFileListReader;
import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReader;

import org.junit.*;


public class CloudMailRuImageFileListReaderTest {

	CloudMailRuImageFileListReader reader;
	
	@Before
	public void init() {
		reader = new CloudMailRuImageFileListReader(ImageFileListReader.CLOUD_MAIL_RU_PREFIX, "DQEv/h67e4AAF9");
	}
	
	@Test
	public void testReadListOK() {
		List<String> urlList = reader.readList().getImagesList();
		assertThat(urlList.size(),equalTo(3));
		assertTrue(urlList.get(0).endsWith(".jpg") && urlList.get(0).startsWith("https"));
	}
	
	@Test
	public void testWrongUrl() {
		reader = new CloudMailRuImageFileListReader(ImageFileListReader.CLOUD_MAIL_RU_PREFIX, "DQEv/h6");
		List<String> urlList = reader.readList().getImagesList();
		assertThat(urlList.size(),equalTo(0));		
	}
	
	@Test
	public void testNullUrl() {
		reader = new CloudMailRuImageFileListReader(ImageFileListReader.CLOUD_MAIL_RU_PREFIX, "DQEv/h6");
		List<String> urlList = reader.readList().getImagesList();
		assertThat(urlList.size(),equalTo(0));		
	}
	
	@Test(expected = RuntimeException.class)
	public void testWrongPrefix() {
		reader = new CloudMailRuImageFileListReader("", "DQEv/h67e4AAF9");
		List<String> urlList = reader.readList().getImagesList();
		fail();		
	}
	
	@Test(expected = RuntimeException.class)
	public void testNullPrefix() {
		reader = new CloudMailRuImageFileListReader(null, "DQEv/h67e4AAF9");
		List<String> urlList = reader.readList().getImagesList();
		fail();		
	}

}
