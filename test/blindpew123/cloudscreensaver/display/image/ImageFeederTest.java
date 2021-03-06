package blindpew123.cloudscreensaver.display.image;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import blindpew123.cloudscreensaver.display.image.ImageFeeder;
import blindpew123.cloudscreensaver.imagelistreaders.ImageFileList;
import blindpew123.cloudscreensaver.imagepath.ImagePath;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.awt.*;

public class ImageFeederTest {

	ImageFileList fileList;
	Rectangle rectangle;
	ImagePath[] paths = {new ImagePath(Paths.get("src/blindpew123/cloudscreensaver/resources/DSC01594.jpg").toAbsolutePath().toString(), false),
			new ImagePath(Paths.get("src/blindpew123/cloudscreensaver/resources/DSC02143.jpg").toAbsolutePath().toString(), false),
			new ImagePath("https://cloud.mail.ru/public/DQEv/h67e4AAF9/DSC05160.jpg", false)
	};
	@Before
	public void init() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		Constructor<ImageFileList> constructor = ImageFileList.class.getDeclaredConstructor(java.util.Collection.class);
		constructor.setAccessible(true);
		fileList = constructor.newInstance(Arrays.asList(paths));
		rectangle = new Rectangle(200,100);
	}	
	
	
	@Test
	public void testInstantinate() {
		ImageFeeder feeder = new ImageFeeder(rectangle,fileList, ImageReadersManager.getInstance().getReader());
		assertNotNull(feeder);
	}

	@Test
	public void testStartFeeding() {
		ImageFeeder feeder = new ImageFeeder(rectangle,fileList, ImageReadersManager.getInstance().getReader());
		feeder.startFeed();
		assertNotNull(feeder.getReadyImageFromQueue());
	}
	
	@Test
	public void testContinuousFeeding() {
		ImageFeeder feeder = new ImageFeeder(rectangle,fileList, ImageReadersManager.getInstance().getReader());
		feeder.startFeed();
		for(int i = 0; i < 5; i++) {
			assertNotNull(feeder.getReadyImageFromQueue());
		}
	}

	@Test
	public void testGarbageNamesInList() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Constructor<ImageFileList> constructor = ImageFileList.class.getDeclaredConstructor(java.util.Collection.class);
		constructor.setAccessible(true);
		fileList = constructor.newInstance(Arrays.asList(
				new ImagePath[]{new ImagePath("//r", true), null, new ImagePath("ft://", true)}));
		ImageFeeder feeder = new ImageFeeder(rectangle,fileList, ImageReadersManager.getInstance().getReader());
		feeder.startFeed();
		for(int i = 0; i < 5; i++) {
			assertNotNull(feeder.getReadyImageFromQueue());
		}
	}
	
	@Test
	public void testWorkingWithNullist() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Constructor<ImageFileList> constructor = ImageFileList.class.getDeclaredConstructor(java.util.Collection.class);
		constructor.setAccessible(true);
		fileList = constructor.newInstance(Arrays.asList(
				new ImagePath[]{}));
		ImageFeeder feeder = new ImageFeeder(rectangle,fileList, ImageReadersManager.getInstance().getReader());
		feeder.startFeed();
		for(int i = 0; i < 5; i++) {
			assertNotNull(feeder.getReadyImageFromQueue());
		}
	}
	
	@Test
	public void testFirstImageFromList() {
		ImageFeeder feeder = new ImageFeeder(rectangle,fileList, ImageReadersManager.getInstance().getReader());
		feeder.startFeed();
		ReadyImageCortege cortege = feeder.getReadyImageFromQueue();
		String path = cortege.getPath().getPath();
		assertTrue(checkPathFilenameFromList(path));
	}
	
	private boolean checkPathFilenameFromList(String path) {
		for (ImagePath listElement: paths) {
			if (listElement.getPath().contains(getFileNameOnly(path))) {return true;}
		}
		return false;
	}
	
	private String getFileNameOnly(String path) {
		for(String pathElement:path.split("/")) {
			if (pathElement.contains("DSC0")) {
				return (pathElement.substring(0,pathElement.indexOf(".")));
			}
		}
		return "";
	}

}
