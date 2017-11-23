package blindpew123.cloudscreensaver.imagelistreaders;

import javax.imageio.ImageIO;

public abstract class ImageFileListReader {
	
	static final String CLOUD_MAIL_RU_PREFIX = "https://cloud.mail.ru/public/";
	
	public abstract ImageFileList readList();
	
	// получить текущий уровень - на основании текущей реализации
	// для файловой системы получить поток Path с фильтром параллельный и покидать результат в List
	// Для cloud первоначально мы читаем текщий уровень
	// если у нас есть каталоги перебираем и запускаем fork. Все результаты добавляются в ConcurrentHashMap (т.к. будут повторы)
	//
	
	protected boolean isFormatSupported(String strTest) {
		String names[] = ImageIO.getReaderFormatNames();
	
		for (int i = 0; i < names.length; ++i) {
			if (strTest.endsWith(names[i])) return true;
		}
		return false;	
	}
}
