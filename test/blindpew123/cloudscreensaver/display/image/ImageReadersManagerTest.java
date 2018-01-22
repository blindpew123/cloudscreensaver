package blindpew123.cloudscreensaver.display.image;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.lang.reflect.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.imagepath.ImagePath;


public class ImageReadersManagerTest {

	ImageReadersManager readersManager;
	

	@Before
	public void setUp() throws Exception {
		readersManager = ImageReadersManager.getInstance();
	}	

	@SuppressWarnings("unchecked")
	@Test
	public void testFillBuildMap() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchFieldException {
		Method fillBuildMap = readersManager.getClass().getDeclaredMethod("fillBuildMap");
		fillBuildMap.setAccessible(true);
		fillBuildMap.invoke(readersManager);
		Field map = readersManager.getClass().getDeclaredField("buildMap");
		map.setAccessible(true);
		StringBuilder sb = new StringBuilder();
		for(Entry<Object,Boolean> entry:((Map<Object, Boolean>)map.get(readersManager)).entrySet()) {
			assertTrue(entry.getValue());
			sb.append(((Enum)entry.getKey()).name());
		}
		assertThat(sb.toString(), equalTo("CMRHTTPLOCALDEFAULTRESIZECACHE"));
	}
	
	@Test
	public void testBuildReaderOK() {
		ImageReader reader = readersManager.getReader();
		assertNotNull(reader);
	}
	
	@Test
	public void testReadersChainWorksOk() throws NoSuchFieldException, SecurityException {
		ImageReader currentReader = ImageReadersManager.getInstance().getReader();
		
		//LocalReader Test
		Path path = Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath();
		ReadyImageCortege img = currentReader.getImage(new ImagePath(path.toString(), false));
		assertFalse(img.checkError());
		assertThat(img.getImage().getWidth(), equalTo(1366)); //your screen width;
		assertThat(img.getInfo().size(), equalTo(49));
		assertThat(img.getPath().getPath(), equalTo(path.toAbsolutePath().toString()));
		
		//CMR Test
		
		ImagePath remotePath = new ImagePath("https://cloud.mail.ru/public/DQEv/h67e4AAF9/DSC05160.jpg", false);
		img = currentReader.getImage(remotePath);
		assertFalse(img.checkError());
		assertThat(img.getImage().getWidth(), equalTo(1366)); //your screen width;
		assertThat(img.getInfo().size(), equalTo(38));
		assertTrue(img.getPath().getPath().contains("cloclo") && img.getPath().getPath().contains("DSC05160.jpg"));	
		
		//Default
		
		remotePath = new ImagePath("https://cloud.mail.ru/public/DQEv/h67e4AAF9/DSC.jpg", false);
		img = currentReader.getImage(remotePath);
		assertTrue(img.checkError());
		assertThat(img.getImage().getWidth(), equalTo(1366)); //your screen width;		
	}

}
