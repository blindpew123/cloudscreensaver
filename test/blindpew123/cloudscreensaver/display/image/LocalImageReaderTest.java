package blindpew123.cloudscreensaver.display.image;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.display.image.HttpImageReader;
import blindpew123.cloudscreensaver.display.image.ImageReader;
import blindpew123.cloudscreensaver.display.image.LocalImageReader;
import blindpew123.cloudscreensaver.display.image.ReadyImageCortege;
import blindpew123.cloudscreensaver.imagepath.ImagePath;

public class LocalImageReaderTest {

	ImageReader reader;
	
	@Before
	public void init() {
		reader = new LocalImageReader(null);		
	}
	
	@Test
	public void testSucssesfullyRead() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(new ImagePath(path.toString(), false));
		assertThat(img.getImage().getHeight(), equalTo(2552));
	}
	
	@Test
	public void testSucssesfullyReadTiff() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.tif").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(new ImagePath(path.toString(), false));
		assertThat(img.getImage().getHeight(), equalTo(2552));
	}
	
	@Test
	public void testSucssesfullyReadPng() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.png").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(new ImagePath(path.toString(), false));
		assertThat(img.getImage().getHeight(), equalTo(2552));
	}
	
	@Test
	public void testUnsucssesfullyRead() {
		ReadyImageCortege img = reader.getImage(new ImagePath("https://cloud.mail.ru/public/Cloud%20ScreenSaver%20Test%20Images/DSC05252.jpg", false));
		assertNull(img);
	}
	
	@Test
	public void testBypassImage() {
		reader = new LocalImageReader(new HttpImageReader(null));
		ReadyImageCortege img = reader.getImage(new ImagePath("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg", false));
		assertThat(img.getImage().getHeight(), equalTo(3264));
	}
	
	@Test
	public void testBypassNull() {
		reader = new LocalImageReader(new HttpImageReader(null));
		ReadyImageCortege img = reader.getImage(new ImagePath("https://cloclo41.datacloudmail.ru", false));
		assertNull(img);
	}
	
	@Test
	public void testExifPresent() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(new ImagePath(path.toString(), false));
		assertThat(img.getInfo().size(),equalTo(49));
	}
	
}
