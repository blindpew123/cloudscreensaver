package blindpew123.cloudscreensaver.image;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class CacheImageReaderTest {

	ImageReader reader;
	
	@Before
	public void init() {		
		Rectangle newSize = new Rectangle(200,100);
		reader = new LocalImageReader(null);
		reader = new ImageResizer(newSize, reader);
		reader = new CacheImageReader(reader);
	}
	
	
	@Test
	public void testReturnsNull() {
		reader = new CacheImageReader(null);
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(path.toString());				
		assertNull(img);		
	}
	
	@Test
	public void testNullImageFromPrelimiaryReadersNotGoToCacheAndCurrentReaderBypassNull() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(path.toString());		
		Field f = reader.getClass().getDeclaredField("cache");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<String, BufferedImage> cache = (Map<String, BufferedImage>) f.get(reader);		
		assertNull(img);
		assertFalse(cache.containsKey(path.toString()));
	}
	
	
	@Test
	public void testGetImageFromPrelimiaryReadersAndPutToCache() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(path.toString());		
		Field f = reader.getClass().getDeclaredField("cache");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<String, BufferedImage> cache = (Map<String, BufferedImage>) f.get(reader);		
		assertThat(img.getImage().getWidth(), equalTo(200));
		assertTrue(cache.containsKey(path.toString()));
	}
	
	@Test
	public void testCahedImageWillBeUsed() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(path.toString());		
		Field f = reader.getClass().getDeclaredField("cache");
		f.setAccessible(true);
		@SuppressWarnings("unchecked")
		Map<String, BufferedImage> cache = (Map<String, BufferedImage>) f.get(reader);
		reader = new CacheImageReader(null);
		f.set(reader, cache);
		img = reader.getImage(path.toString());
		assertThat(img.getImage().getWidth(), equalTo(200));		
	}
}
