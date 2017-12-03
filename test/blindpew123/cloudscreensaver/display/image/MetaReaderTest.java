package blindpew123.cloudscreensaver.display.image;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class MetaReaderTest {

	MetaReader metaReader;
	
	@Before
	public void setUp() throws Exception {
		metaReader = new MetaReader() {
		};
	}

	@Test
	public void test() throws FileNotFoundException {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		Properties exif = metaReader.readExif(new FileInputStream(path.toFile()));
		assertTrue(exif.get("Make").equals("SONY"));
		
	}
	
	@Test
	public void testNullStreamReturnsNullExif() {
		Properties exif = metaReader.readExif(null);
		assertNull(exif);
		
	}
	
	@Test
	public void testFileContainsNoExifReturnsNull() throws FileNotFoundException {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.png").toAbsolutePath();
		Properties exif = metaReader.readExif(new FileInputStream(path.toFile()));
		assertNull(exif);
		
	}

}
