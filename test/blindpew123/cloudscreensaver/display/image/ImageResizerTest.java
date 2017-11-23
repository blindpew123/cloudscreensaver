package blindpew123.cloudscreensaver.display.image;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.display.image.ImageReader;
import blindpew123.cloudscreensaver.display.image.ImageResizer;
import blindpew123.cloudscreensaver.display.image.LocalImageReader;
import blindpew123.cloudscreensaver.display.image.ReadyImageCortege;

//TODO: Add tests for vertical oriented image and image with size less than screen

public class ImageResizerTest {

	ImageReader reader;
	
	@Before
	public void init() {
		Rectangle newSize = new Rectangle(200,100);
		reader = new LocalImageReader(null);
		reader = new ImageResizer(newSize, reader);		
	}
	
	@Test
	public void testSucssesfullyRead() {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(path.toString());
		assertThat(img.getImage().getWidth(), equalTo(200));
	}
	
	@Test
	public void testUnsucssesfullyRead() {
		ReadyImageCortege img = reader.getImage("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg");
		assertNull(img);
	}
	
	@Test
	public void testWorkWithNullPreliminaryReader() {
		Rectangle newSize = new Rectangle(200,100);
		reader = new ImageResizer(newSize, null);		
		ReadyImageCortege img = reader.getImage("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg");
		assertNull(img);
	}


}
