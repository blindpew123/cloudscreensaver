package blindpew123.cloudscreensaver;

import static org.junit.Assert.*;

import org.junit.Test;

public class ScreenSaverTest {

	@Test
	public void test() throws InterruptedException {
		ScreenSaver ss = new ScreenSaver();
		ss.initScreenSaver();
		ss.startScreenSaver();
		
	}

}
