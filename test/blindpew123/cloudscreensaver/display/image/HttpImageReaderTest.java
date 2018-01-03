package blindpew123.cloudscreensaver.display.image;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;


import java.nio.file.Path;
import java.nio.file.Paths;


import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.display.image.HttpImageReader;
import blindpew123.cloudscreensaver.display.image.ImageReader;
import blindpew123.cloudscreensaver.display.image.LocalImageReader;
import blindpew123.cloudscreensaver.display.image.ReadyImageCortege;
import blindpew123.cloudscreensaver.imagepath.ImagePath;


public class HttpImageReaderTest {

ImageReader reader;
	
	@Before
	public void init() {
		reader = new HttpImageReader(null);		
	}
	
	@Test
	public void testUnsucssesfullyRead() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(new ImagePath(path.toString(), false));
		assertNull(img);
	}
	
	@Test
	public void testSucssesfullyRead() {
		ReadyImageCortege img = reader.getImage(new ImagePath("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg", false));
		assertThat(img.getImage().getHeight(), equalTo(3264));
	}
	
	@Test
	public void testBypassImage() {
		reader = new HttpImageReader(new LocalImageReader(null));
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(new ImagePath(path.toString(), false));
		assertThat(img.getImage().getHeight(), equalTo(2552));
	}
	
	@Test
	public void testBypassNull() {
		reader = new HttpImageReader(new LocalImageReader(null));
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(new ImagePath(path.toString(), false));
		assertNull(img);
	}
	
		
}

