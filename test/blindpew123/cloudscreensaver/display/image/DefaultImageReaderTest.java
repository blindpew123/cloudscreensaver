package blindpew123.cloudscreensaver.display.image;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;

import blindpew123.cloudscreensaver.display.image.DefaultImageReader;
import blindpew123.cloudscreensaver.display.image.HttpImageReader;
import blindpew123.cloudscreensaver.display.image.ImageReader;
import blindpew123.cloudscreensaver.display.image.ReadyImageCortege;
import blindpew123.cloudscreensaver.imagepath.ImagePath;

public class DefaultImageReaderTest {
		
	@Test
	public void testBypassImageFromPreliminaryReader() {
		ImageReader reader = new DefaultImageReader(new HttpImageReader(null));
		ReadyImageCortege image = reader.getImage(new ImagePath("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg", false));
		assertThat(image.getImage().getHeight(), equalTo(3264));
		assertFalse(image.checkError());
	}
	
	@Test
	public void testReturnsOneOfDefaultImageIfReaderNotPresented() {
		ImageReader reader = new DefaultImageReader(null);
		ReadyImageCortege image = reader.getImage(new ImagePath("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg", false));
		assertNotNull(image);
		assertFalse(image.checkError());
	}
	
	@Test
	public void testReturnsOneOfDefaultImageIfPreliminaryReaderReturnedNull() {
		ImageReader reader = new DefaultImageReader(new HttpImageReader(null));
		ReadyImageCortege image = reader.getImage(new ImagePath("https://cloclo41.datacloudmail.ru/", false));
		assertNotNull(image);
		assertTrue(image.checkError());
	}
	

}
