package blindpew123.cloudscreensaver.display.image;

import java.awt.Rectangle;
import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import blindpew123.cloudscreensaver.display.DisplayManager;
import blindpew123.cloudscreensaver.settings.SettingsFile;

/*
 * Builds various chains of Image readers
 */
public class ImageReadersManager {	
	
	private Map<ReaderName, Boolean> buildMap;
	private static ImageReadersManager instance;
	private enum ReaderName {
		CACHE{
			ImageReader reader(ImageReader nextReader) {
				return new CacheImageReader(nextReader);
			}
		},
		RESIZE{
			ImageReader reader(ImageReader nextReader) {
				return new ImageResizer(DisplayManager
						.getInstance()
						.getDisplay()
						.getPrefferableImageSize(),nextReader);
			}
		},
		DEFAULT{
			ImageReader reader(ImageReader nextReader) {
				return new DefaultImageReader(nextReader);
			}
		},
		LOCAL{
			ImageReader reader(ImageReader nextReader) {
				return new LocalImageReader(nextReader);
			}
		},
		HTTP{
			ImageReader reader(ImageReader nextReader) {
				return new HttpImageReader(nextReader);
			}
		},
		CMR{
			ImageReader reader(ImageReader nextReader) {
				return new CMRHttpImageReader(nextReader);
			}
		};	
		abstract ImageReader reader(ImageReader nextReader);
	}
	
	public static synchronized ImageReadersManager getInstance() {
		if (instance == null) {instance = new ImageReadersManager();}
		return instance;
	}
	
	/* Returns reader, that able reads images from all presented paths
	*/
	public synchronized ImageReader getReader() {
		fillBuildMap();
		ImageReader result = null;
		for(Entry<ReaderName, Boolean> entry: buildMap.entrySet()) {
			result = entry.getValue() ? entry.getKey().reader(result) : result;
		}
		return result;
	}
	
	private void fillBuildMap(){
		buildMap = new TreeMap<>(Comparator.<ReaderName>reverseOrder());
		buildMap.put(ReaderName.RESIZE, true); // we always have to resize image to fit in screen
		checkSettingOptions();
		buildMap.put(ReaderName.LOCAL,true); // TODO: Replace to paths parsing
		buildMap.put(ReaderName.HTTP,true);
		buildMap.put(ReaderName.CMR,true);
	}
	
	private void checkSettingOptions() {
		buildMap.put(ReaderName.DEFAULT, (Boolean)SettingsFile.getInstance().getSettingsValue("useDefaultImages"));
		buildMap.put(ReaderName.CACHE, (Boolean)SettingsFile.getInstance().getSettingsValue("useCacheForImages"));
	}	
	
}
