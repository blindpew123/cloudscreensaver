package blindpew123.cloudscreensaver.imagelistreaders;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.imagepath.ImagePath;

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
		assertTrue(manager.isReaderAvailable(new ImagePath("c:/", true)));
	}
	@Test
	public void testCloudMailReaderAvailable() {
		assertTrue(manager.isReaderAvailable(new ImagePath("https://cloud.mail.ru/public/", true)));
	}
	@Test
	public void testLocalNetworkReaderAvailable() {
		assertTrue(manager.isReaderAvailable(new ImagePath("//S", true)));
	}
	@Test
	public void testGarbage_1_PathReaderNotAvailable() {
		assertFalse(manager.isReaderAvailable(new ImagePath("df://", true)));
	}
	@Test
	public void testGarbage_2_PathReaderNotAvailable() {
		assertFalse(manager.isReaderAvailable(new ImagePath("Ж:/", true)));
	}
	@Test
	public void testNullPathReaderNotAvailable() {
		assertFalse(manager.isReaderAvailable(null));
	}
	@Test
	public void testEmptyPathReaderNotAvailable() {
		assertFalse(manager.isReaderAvailable(new ImagePath("", true)));
	}
	
	@Test
	public void testListImagesForLocalPath() throws InterruptedException { //Check path and images before this test
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/").toAbsolutePath();
		ImageFileList paths = new ImageFileList(Arrays.asList(new ImagePath(path.toString(),true)));
		ImageFileList list = manager.getFileTrees(paths);
		Thread.sleep(1000); // 
		assertThat(list.getSize(),equalTo(6));
	}
	
	@Test
	public void testListImagesForCloudMailPath() throws InterruptedException {
		ImageFileList paths = new ImageFileList(Arrays.asList(new ImagePath("https://cloud.mail.ru/public/DQEv/h67e4AAF9",true)));
		ImageFileList list = manager.getFileTrees(paths);
		Thread.sleep(5000); //
		assertThat(list.getSize(),equalTo(3));
	}
	@Test(expected = IllegalArgumentException.class)
	public void testListImagesForGarbagePath() throws InterruptedException {
		ImageFileList paths = new ImageFileList(Arrays.asList(new ImagePath("htt",true)));
		ImageFileList list = manager.getFileTrees(paths);
		fail();
	}
	
	
}
