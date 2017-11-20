package blindpew123.cloudscreensaver.imagelistreaders;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class ImageFileListReadersManagerTest {

	ImageFileListReadersManager manager; 
	
	@Before
	public void init() {
		manager = ImageFileListReadersManager.getInstance();
	}
	@Test
	public void testInit() {
		assertThat(manager, is(notNullValue()));
	}
	@Test
	public void testOnlyOneInstance() {
		assertThat(manager, equalTo(ImageFileListReadersManager.getInstance()));
	}
	@Test
	public void testLocalReaderAvailable() {
		assertTrue(manager.isReaderAvailable("c:/"));
	}
	@Test
	public void testCloudMailReaderAvailable() {
		assertTrue(manager.isReaderAvailable("https://cloud.mail.ru/public/"));
	}
	@Test
	public void testLocalNetworkReaderAvailable() {
		assertTrue(manager.isReaderAvailable("//S"));
	}
	@Test
	public void testGarbage_1_PathReaderNotAvailable() {
		assertFalse(manager.isReaderAvailable("df://"));
	}
	@Test
	public void testGarbage_2_PathReaderNotAvailable() {
		assertFalse(manager.isReaderAvailable("Ж:/"));
	}
	@Test
	public void testNullPathReaderNotAvailable() {
		assertFalse(manager.isReaderAvailable(null));
	}
	@Test
	public void testEmptyPathReaderNotAvailable() {
		assertFalse(manager.isReaderAvailable(""));
	}
	
	@Test
	public void testListImagesForLocalPath() { //Check path and images before this test
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/");
		ImageFileList list= manager.getFileTrees(new String[]{path.toAbsolutePath().toString()});
		assertThat(list.getSize(),equalTo(4));
	}
	
	@Test
	public void testListImagesForCloudMailPath() {
		ImageFileList list= manager.getFileTrees(new String[]{"https://cloud.mail.ru/public/DQEv/h67e4AAF9"});
		assertThat(list.getSize(),equalTo(3));
	}
	
	
}
