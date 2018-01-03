package blindpew123.cloudscreensaver.settings;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.imagelistreaders.ImageFileList;

public class SettingsFileTest {

	
	SettingsFile settings;
	
	@Before
	public void setUp() throws Exception {
		settings = SettingsFile.getInstance();
	}

	@Test
	public void testSingleton() {
		SettingsFile anotherInstance = SettingsFile.getInstance();
		assertTrue(anotherInstance.equals(settings));
	}
	@Test
	public void testGetSettingsString() {
		String str = settings.getSettingsStringValue("cloudMailPrefix");
		assertTrue(str.equals("https://cloud.mail.ru/public/"));
	}
	
	@Test
	public void testGetResource() {
		String str = settings.getResource("nameSettingsDialog");
		assertTrue(str.equals("Settings CloudScreenSaver 2.0") || str.equals("Настройки CloudScreenSaver 2.0"));
	}
	
	@Test
	public void testGetSettingsObject() {
		Object num = (Integer)settings.getSettingsValue("maxNumCashedImages");
		assertTrue(num instanceof Integer);		
	}
	
	
}
