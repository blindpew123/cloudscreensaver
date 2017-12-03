package blindpew123.cloudscreensaver.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class SettingsFile {

	private static final String SETTINGS_FILENAME = "cloudscreensaver20.settings";
	private static final String APP_DIRECTORYNAME = "cloudscreensaver20";
	private static final String LOCALE_PROPERTIES_FILENAME = "blindpew123.cloudscreensaver.resources.CloudScreenSaver";
	
	private ResourceBundle resources = ResourceBundle.getBundle(LOCALE_PROPERTIES_FILENAME);
	private Properties settings;
	
	static private SettingsFile instance;
	
	public static synchronized SettingsFile getInstance(){
		if (instance == null) {
			instance = new SettingsFile();
		}
		return instance;
	}	
	
	public synchronized String getResource(String name) {
		return (String) resources.getObject(name);
	}	

	public synchronized String getSettingsStringValue(String name) {
		return (String) settings.getOrDefault(name,"");
	}
	
	public synchronized Object getSettingsValue(String name) {
		return settings.get(name);
	}
	
	public synchronized File getAppDirectory() {
		File thisAppDir = new File(new File(System.getProperty("user.home")),APP_DIRECTORYNAME);
		if(!thisAppDir.exists()) {
			thisAppDir.mkdir();
		}
		return thisAppDir;
	}
		
	synchronized void  saveSettings(Properties props) {
		File settingsFile = getSettingFile();
		try (FileOutputStream fileIn = new FileOutputStream(settingsFile)){
			props.store(fileIn,"Settings Saved");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		settings = getSettings();
	}
	
	private SettingsFile() {
		settings = getSettings();
		// All that will be included in Settings Dialog in the future
		settings.put("minNumPathsForQuickstart",10);
		settings.put("maxNumCashedImages", 50);
		settings.put("cloudMailPrefix", "https://cloud.mail.ru/public/");
		settings.put("useDefaultImages",true);
		settings.put("useCacheForImages",true);
	}
	
	private Properties getSettings() {
		Properties props = new Properties();
		File settingsFile = getSettingFile();
		if (settingsFile.exists()) {		
			try (FileInputStream fileIn = new FileInputStream(settingsFile)){
				props.load(fileIn);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}			
		}
		return props;
	}

	private File getSettingFile() {		
		return new File(getAppDirectory(), SETTINGS_FILENAME);
	}
		
		
}
