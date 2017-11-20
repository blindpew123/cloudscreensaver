package blindpew123.cloudscreensaver.resources;

import java.util.ListResourceBundle;

public class CloudScreenSaver_ru extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] {
			{"nameSettingsDialog","Настройки CloudScreenSaver 2.0"},
			{"pathName","Пути к файлам изображений:"},
			{"showFileNames","Показывать имена файлов"},
			{"textFilesPathsTooltip","Имена папок или URL страниц, содержащих файлы изображений. Значения должны быть разделены ';'"},
			{"textWarning","Предупреждение"},
			{"showExif","Показывать EXIF"},
			{"cancelText","Отмена"},
			{"imageErrorMessage","Не удалось прочитать изображение"}
		};
	}

}
