package blindpew123.cloudscreensaver.imagelistreaders;

public abstract class ImageFileListReader {
	
	static final String CLOUD_MAIL_RU_PREFIX = "https://cloud.mail.ru/public/";
	
	public abstract ImageFileList readList();
	
	// получить текущий уровень - на основании текущей реализации
	// для файловой системы получить поток Path с фильтром параллельный и покидать результат в List
	// Для cloud первоначально мы читаем текщий уровень
	// если у нас есть каталоги перебираем и запускаем fork. Все результаты добавляются в ConcurrentHashMap (т.к. будут повторы)
	//
	
}
