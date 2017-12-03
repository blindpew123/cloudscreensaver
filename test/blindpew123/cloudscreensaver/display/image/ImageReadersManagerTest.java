package blindpew123.cloudscreensaver.display.image;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.lang.reflect.*;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;


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
		assertThat(sb.toString(), equalTo("CMRHTTPLOCALRESIZECACHEDEFAULT"));
	}
	
	@Test
	public void testBuildReaderOK() {
		ImageReader reader = readersManager.getReader();
		assertNotNull(reader);
	}

}
