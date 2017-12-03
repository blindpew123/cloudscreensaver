package blindpew123.cloudscreensaver.display.image;

import static org.junit.Assert.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class CMRHttpImageReaderTest {

	ImageReader reader;
	
	@Before
	public void setUp() throws Exception {
		reader = new CMRHttpImageReader(null);
	}

	@Test
	public void testOK() {
		ReadyImageCortege cortege = reader.getImage("https://cloud.mail.ru/public/DQEv/h67e4AAF9/DSC05160.jpg");
		assertThat(cortege.getImage().getHeight(),equalTo(3264));
	}
	
	@Test
	public void testExif() {
		ReadyImageCortege cortege = reader.getImage("https://cloud.mail.ru/public/DQEv/h67e4AAF9/DSC05160.jpg");
		assertThat(cortege.getInfo().size(), equalTo(39));		
	}
	
	
	@Test
	public void testWrongURLNullResult() {
		ReadyImageCortege cortege = reader.getImage("https://cloud.mail.ru/public/DQEv/h67e4AAF9/DSC05161.jpg");
		assertNull(cortege);
	}
	
	@Test
	public void testByPass() {
		reader = new CMRHttpImageReader(new LocalImageReader(null));
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.png").toAbsolutePath();
		ReadyImageCortege img = reader.getImage(path.toString());
		assertThat(img.getImage().getHeight(), equalTo(2552));		
	}
	

}
