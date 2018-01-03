package blindpew123.cloudscreensaver.imagepath;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.settings.SettingsFile;

public class ImagePathTest {

	
	ImagePath path;
		
	@Before
	public void setUp() throws Exception {
		path = new ImagePath("C:/local", true);
	}
	
	@Test(expected = RuntimeException.class)
	public void testNullPathExcepetion() {
		path = new ImagePath(null, true);
		path.getAbsolutePath();
		fail();
	}
	
	@Test(expected = RuntimeException.class)
	public void testWithPrefixNullPathExcepetion() {
		path = new ImagePath(null, true);
		path.setUrlPrefix(SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix"));
		path.getAbsolutePath();
		fail();
	}
	
	@Test
	public void testLocalPathCreatedOK() {
		assertThat(path.toString(), equalTo("C:/local"));
	}
	
	@Test
	public void testAbsolutePathReturnsOK() {
		assertThat(path.getAbsolutePath(), equalTo("C:/local"));
	}
	
	@Test
	public void testAbsolutePathReturnsWithPrefixAvailiable() {
		path.setUrlPrefix(SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix"));
		assertThat(path.getAbsolutePath(), equalTo("https://cloud.mail.ru/public/C:/local"));
	}
	
	@Test
	public void testAbsoluteHttpPathReturnsOKWhenPrefixAvailiable() {
		path = new ImagePath("ddsf/fhfghfgh", true);
		path.setUrlPrefix(SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix"));
		assertThat(path.getAbsolutePath(), equalTo("https://cloud.mail.ru/public/ddsf/fhfghfgh"));
	}
	

	public void testAbsoluteHttpPathWithoutPrefix() {
		path = new ImagePath("ddsf/fhfghfgh", true);
		assertThat(path.getAbsolutePath(), equalTo("ddsf/fhfghfgh"));
	}
	
	@Test
	public void testIsCloudMailRuPathTrue() {
		path = new ImagePath("https://cloud.mail.ru/public/ddsf/fhfghfgh", true);
		assertTrue(path.isCloudMailRuPath());
		path = new ImagePath("https://cloud.mail.ru/public/", "ddsf/fhfghfgh", true);
		assertTrue(path.isCloudMailRuPath());
		path = new ImagePath("ddsf/fhfghfgh", true);
		path.setUrlPrefix("https://cloud.mail.ru/public/");
		assertTrue(path.isCloudMailRuPath());
	}
	
	@Test
	public void testIsCloudMailRuPathFalse() {		
		path = new ImagePath("ddsf/fhfghfgh", true);
		path.setUrlPrefix("http://cloud.mail.ru/public/");
		assertFalse(path.isCloudMailRuPath());
	}
	
	@Test
	public void testCheckCMRWithoutPrefixTestPath() {
		path = new ImagePath("ddsf/fhfghfgh", true);
		assertFalse(path.isCloudMailRuPath());
	}
	
	@Test(expected = RuntimeException.class)
	public void testNullPathException() {
		path = new ImagePath(null, true);
		path.isCloudMailRuPath();
		fail();
	}
	
	@Test
	public void testIsLocalOrNetworkPathTrue() {
		path = new ImagePath("c:/images", true);
		assertTrue(path.isLocalOrLocalNetworkPath());
		path = new ImagePath("c:\\images", true);
		assertTrue(path.isLocalOrLocalNetworkPath());
		path = new ImagePath("//ddsf/fhfghfgh", true);
	}
	
	@Test
	public void testIsLocalOrNetworkPathFalse() {
		path = new ImagePath("", true);
		assertFalse(path.isLocalOrLocalNetworkPath());		
		path = new ImagePath("https://cloud.mail.ru/public/", "//ddsf/fhfghfgh", true);
		assertFalse(path.isLocalOrLocalNetworkPath());
		path = new ImagePath("https://cloud.mail.ru/public/", "", true);
		assertFalse(path.isLocalOrLocalNetworkPath());
		path = new ImagePath("//ddsf/fhfghfgh", true);
		path.setUrlPrefix("https://cloud.mail.ru/public/");
		assertFalse(path.isLocalOrLocalNetworkPath());
	}
	
	@Test
	public void testEqualTwoImagePath() {
		assertTrue(path.equals(new ImagePath("C:/local", true)));
	}
	
}
