package blindpew123.cloudscreensaver.imagelistreaders;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import blindpew123.cloudscreensaver.imagelistreaders.CloudMailRuImageFileListReader;
import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReader;

import org.junit.*;


public class CloudMailRuImageFileListReaderTest {

	CloudMailRuImageFileListReader reader;
	
	@Before
	public void init() {
		reader = new CloudMailRuImageFileListReader(ImageFileListReader.CLOUD_MAIL_RU_PREFIX, "Mjop/i97UmDGtY");
	}
	
	@Test
	public void testReadList() {
		System.out.println(reader.readList().getImagesList());
	}

}
