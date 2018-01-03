package blindpew123.cloudscreensaver;

import static org.junit.Assert.*;

import org.junit.*;



public class ScreenSaverTest {

	
	
//	@Ignore // just for launch
	@Test
	public void test() throws InterruptedException {
		ScreenSaver ss = new ScreenSaver();
		ss.initScreenSaver();
		ss.startScreenSaver();
		
	}

}
