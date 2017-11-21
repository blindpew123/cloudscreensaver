package blindpew123.cloudscreensaver.image;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

public class HttpImageReaderTest {

ImageReader reader;
	
	@Before
	public void init() {
		reader = new HttpImageReader(null);		
	}
	
	@Test
	public void testUnsucssesfullyRead() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		BufferedImage img = reader.getImage(path.toString());
		assertNull(img);
	}
	@Test
	public void testSucssesfullyRead() {
		BufferedImage img = reader.getImage("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg");
		assertThat(img.getHeight(), equalTo(3264));
	}
	
	@Test
	public void testBypassImage() {
		reader = new HttpImageReader(new LocalImageReader(null));
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		BufferedImage img = reader.getImage(path.toString());
		assertThat(img.getHeight(), equalTo(2552));
	}
	
	@Test
	public void testBypassNull() {
		reader = new HttpImageReader(new LocalImageReader(null));
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC.jpg").toAbsolutePath();
		BufferedImage img = reader.getImage(path.toString());
		assertNull(img);
	}
	
}

