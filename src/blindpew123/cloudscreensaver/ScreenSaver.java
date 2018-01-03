package blindpew123.cloudscreensaver;
/* Получить настройки
 * Получить на основе пути список файлов для обработки
 * создать Fetcher на основе настроек
 * Выбрать подходящий модуль отображения на основе настроек
 * запустить процесс изображения, ловя ошибки 
 */

import blindpew123.cloudscreensaver.display.Display;
import blindpew123.cloudscreensaver.display.DisplayManager;
import blindpew123.cloudscreensaver.display.SimpleDisplay;
import blindpew123.cloudscreensaver.display.image.ImageFeeder;
import blindpew123.cloudscreensaver.display.image.ImageReadersManager;
import blindpew123.cloudscreensaver.display.image.ReadyImageCortege;
import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReadersManager;
import blindpew123.cloudscreensaver.settings.SettingsFile;

public class ScreenSaver {

	private boolean initSuccess;
	SettingsFile settingsFile;
	ImageFeeder feeder;
	Display displayModule;	
	
	public void initScreenSaver() {
		// TODO: Обработка runtime Исключений и вывод диалога
		displayModule = DisplayManager.getInstance().getDisplay();
		feeder = new ImageFeeder(displayModule.getPrefferableImageSize(), 
				ImageFileListReadersManager.getInstance().getFileTrees(SettingsFile.getInstance().getStartingPathsList()),
				ImageReadersManager.getInstance().getReader());
		initSuccess = true;
	}
	
	public void startScreenSaver()  {
		try {
		//	Thread.sleep(Integer.parseInt((String)SettingsFile.getInstance().getSettingsValue("timeOutUntilStart"))); // wait, we need some time for partial filling list of images
			Thread.sleep((Integer)SettingsFile.getInstance().getSettingsValue("timeOutUntilStart"));
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		feeder.startFeed();
		while(initSuccess) {  //working loop until key or mouse event will not exit app. Also app may will close by catch Exception by ImageFeeder class  
			
			ReadyImageCortege tmp = feeder.getReadyImageFromQueue(); 
			if (tmp == null) {return;}
			displayModule.setImage(tmp);			
			displayModule.display();
			try{
				Thread.sleep(Integer.parseInt((String)SettingsFile.getInstance().getSettingsValue("imagesTimeOut"))*1000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		
	}
	
}
