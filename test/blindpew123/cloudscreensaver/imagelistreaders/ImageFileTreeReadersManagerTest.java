package blindpew123.cloudscreensaver.imagelistreaders;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;
import org.junit.Before;


public class ImageFileTreeReadersManagerTest {

	ImageFileTreeReadersManager mgrInstance;
	
	@Before
	public void testGetInstance() {
			mgrInstance = ImageFileTreeReadersManager.getInstance();		
	}
	@Test
	public void testGetInstanceNotNull() {
		assertNotNull(mgrInstance);
	}
	@Test
	public void testAllIstanceTheSame() {
		ImageFileTreeReadersManager anotherMgr = ImageFileTreeReadersManager.getInstance();
		assertThat(mgrInstance,equalTo(anotherMgr));
	}

	@Test
	public void testGetFileTrees() {
		fail("Not yet implemented");
	}

}
