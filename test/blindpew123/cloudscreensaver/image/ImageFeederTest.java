package blindpew123.cloudscreensaver.image;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import blindpew123.cloudscreensaver.imagelistreaders.ImageFileList;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.awt.*;

public class ImageFeederTest {

	ImageFileList fileList;
	Rectangle rectangle;
	
	@Before
	public void init() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Constructor<ImageFileList> constructor = ImageFileList.class.getDeclaredConstructor(java.util.Collection.class);
		constructor.setAccessible(true);
		fileList = constructor.newInstance(Arrays.asList(
				new String[]{
						Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath().toString(),
						Paths.get("src/blindpew123/cloudscreensaver/resources/DSC02143.jpg").toAbsolutePath().toString(),
						"https://cloclo41.datacloudmail.ru/weblink/thumb/xw1/DQEv/h67e4AAF9/DSC05252.jpg"
				}));
		rectangle = new Rectangle(200,100);
	}	
	
	
	@Test
	public void testInstantinate() {
		ImageFeeder feeder = new ImageFeeder(rectangle,fileList);
		assertNotNull(feeder);
	}
	
	@Test
	public void testStartFeeding() {
		ImageFeeder feeder = new ImageFeeder(rectangle,fileList);
		feeder.startFeed();
		assertNotNull(feeder.getReadyImageFromQueue());
	}
	
	@Test
	public void testContinuousFeeding() {
		ImageFeeder feeder = new ImageFeeder(rectangle,fileList);
		for(int i = 0; i < 5; i++) {
			feeder.startFeed();
			assertNotNull(feeder.getReadyImageFromQueue());
		}
	}
	
	@Test
	public void testWorkingWithNullist() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Constructor<ImageFileList> constructor = ImageFileList.class.getDeclaredConstructor(java.util.Collection.class);
		constructor.setAccessible(true);
		fileList = constructor.newInstance(Arrays.asList(
				new String[]{}));
		ImageFeeder feeder = new ImageFeeder(rectangle,fileList);
		for(int i = 0; i < 5; i++) {
			feeder.startFeed();
			assertNotNull(feeder.getReadyImageFromQueue());
		}
	}

}
