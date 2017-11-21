package blindpew123.cloudscreensaver.image;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;

import org.junit.Test;

public class DefaultImageReaderTest {
		
	@Test
	public void testBypassImageFromPreliminaryReader() {
		ImageReader reader = new DefaultImageReader(new HttpImageReader(null));
		BufferedImage image = reader.getImage("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg");
		assertThat(image.getHeight(), equalTo(3264));		
	}
	
	@Test
	public void testReturnsOneOfDefaultImageIfReaderNotPresented() {
		ImageReader reader = new DefaultImageReader(null);
		BufferedImage image = reader.getImage("https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg");
		assertNotNull(image);		
	}
	
	@Test
	public void testReturnsOneOfDefaultImageIfPreliminaryReaderReturnedNull() {
		ImageReader reader = new DefaultImageReader(new HttpImageReader(null));
		BufferedImage image = reader.getImage("https://cloclo41.datacloudmail.ru/");
		assertNotNull(image);		
	}
	

}