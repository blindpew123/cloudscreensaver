package blindpew123.cloudscreensaver.Display;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.Display.HttpImageReader;
import blindpew123.cloudscreensaver.Display.ImageReader;
import blindpew123.cloudscreensaver.Display.LocalImageReader;
import blindpew123.cloudscreensaver.Display.ReadyImageCortege;

public class LocalImageReaderTest {

	ImageReader reader;
	
	@Before
	public void init() {
		reader = new LocalImageReader(null);		
	}
	
	@Test
	public void testSucssesfullyRead() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(path.toString());
		assertThat(img.getImage().getHeight(), equalTo(2552));
	}
	
	@Test
	public void testSucssesfullyReadTiff() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.tif").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(path.toString());
		assertThat(img.getImage().getHeight(), equalTo(2552));
	}
	
	@Test
	public void testSucssesfullyReadPng() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.png").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(path.toString());
		assertThat(img.getImage().getHeight(), equalTo(2552));
	}
	
	@Test
	public void testUnsucssesfullyRead() {
		ReadyImageCortege img = reader.getImage("https://cloud.mail.ru/public/Cloud%20ScreenSaver%20Test%20Images/DSC05252.jpg");
		assertNull(img);
	}
	
	@Test
	public void testBypassImage() {
		reader = new LocalImageReader(new HttpImageReader(null));
		ReadyImageCortege img = reader.getImage("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg");
		assertThat(img.getImage().getHeight(), equalTo(3264));
	}
	
	@Test
	public void testBypassNull() {
		reader = new LocalImageReader(new HttpImageReader(null));
		ReadyImageCortege img = reader.getImage("https://cloclo41.datacloudmail.ru");
		assertNull(img);
	}
	
	@Test
	public void testExifPresent() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(path.toString());
		assertThat(img.getInfo().size(),equalTo(49));
	}
	
}
