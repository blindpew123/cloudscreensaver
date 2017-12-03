package blindpew123.cloudscreensaver.display.image;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class ReadyImageCortegeTest {

	BufferedImage image;
	Properties prop;
	@Before
	public void setUp() throws Exception {
		image = new BufferedImage(1, 1, 1);
		prop = new Properties();
		prop.put("name","param");
	}

	@Test
	public void testInstatiate() {		
		ReadyImageCortege cortege = new ReadyImageCortege(image, prop);
		assertTrue(cortege.getImage( )== image);
		assertFalse(prop == cortege.getInfo());
		assertTrue(cortege.getInfo().get("name").equals("param"));
	}
	
	@Test
	public void testUsingNullAsParameter() {
		ReadyImageCortege cortege = new ReadyImageCortege(null, null);
		assertTrue(cortege.getImage() == null);
		assertFalse(cortege.getInfo() == null);
	}
	
	@Test
	public void testImmutabilityImageCortegeInfo() {
		ReadyImageCortege cortege = new ReadyImageCortege(image, prop);
		cortege.getInfo().put("name","new");
		assertTrue(cortege.getInfo().get("name").equals("param"));
	}

}
