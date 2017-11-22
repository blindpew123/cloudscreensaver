package blindpew123.cloudscreensaver;
/* Получить настройки
 * Получить на основе пути список файлов для обработки
 * создать Fetcher на основе настроек
 * Выбрать подходящий модуль отображения на основе настроек
 * запустить процесс изображения, ловя ошибки 
 */

import java.awt.image.BufferedImage;

import blindpew123.cloudscreensaver.image.Display;
import blindpew123.cloudscreensaver.image.ImageFeeder;
import blindpew123.cloudscreensaver.image.SimpleDisplay;
import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReaders;
import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReadersManager;
import blindpew123.cloudscreensaver.settings.SettingsFile;

public class ScreenSaver {

	private boolean initSuccess;
	SettingsFile settingsFile;
	ImageFeeder feeder;
	Display displayModule;	
	
	public void initScreenSaver() {
		// TODO: Обработка runtime Исключений и вывод диалога
		settingsFile = SettingsFile.getInstance();
		String startFoldersPathNames = settingsFile.getSettingsValue("pathsValues");
		if (startFoldersPathNames == null) {
			System.out.println("TODO: переделать на диалог об ошибке. Не указан путь");
			return;
		}
		System.out.println("1. "+startFoldersPathNames); //TODO: Remove
		
		//TODO: создание и выбор демонстратора на основе настроек.
		displayModule = new SimpleDisplay();
		displayModule.addLabels();
		feeder = new ImageFeeder(displayModule.getPrefferableImageSize(), ImageFileListReadersManager.getInstance().getFileTrees(startFoldersPathNames.split(";")));
		initSuccess = true;
	}
	
	public void startScreenSaver() throws InterruptedException {
		feeder.startFeed();
		// TODO Решить что делать с Interrupted и другими
		while(initSuccess) {  //working loop until key or mouse event will not exit app. Also app may will close by catch Exception by ImageFeeder class  
			displayModule.setImage(feeder.getReadyImageFromQueue());
			displayModule.display();
			Thread.sleep(4000); //Таймаут
		}
		
	}
	
}
