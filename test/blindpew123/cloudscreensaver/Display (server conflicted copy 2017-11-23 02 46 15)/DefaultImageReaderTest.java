package blindpew123.cloudscreensaver.Display;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import org.junit.Test;

import blindpew123.cloudscreensaver.display.DefaultImageReader;
import blindpew123.cloudscreensaver.display.HttpImageReader;
import blindpew123.cloudscreensaver.display.ImageReader;
import blindpew123.cloudscreensaver.display.ReadyImageCortege;

public class DefaultImageReaderTest {
		
	@Test
	public void testBypassImageFromPreliminaryReader() {
		ImageReader reader = new DefaultImageReader(new HttpImageReader(null));
		ReadyImageCortege image = reader.getImage("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg");
		assertThat(image.getImage().getHeight(), equalTo(3264));		
	}
	
	@Test
	public void testReturnsOneOfDefaultImageIfReaderNotPresented() {
		ImageReader reader = new DefaultImageReader(null);
		ReadyImageCortege image = reader.getImage("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg");
		assertNotNull(image);		
	}
	
	@Test
	public void testReturnsOneOfDefaultImageIfPreliminaryReaderReturnedNull() {
		ImageReader reader = new DefaultImageReader(new HttpImageReader(null));
		ReadyImageCortege image = reader.getImage("https://cloclo41.datacloudmail.ru/");
		assertNotNull(image);		
	}
	

}
