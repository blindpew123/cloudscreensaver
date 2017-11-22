package blindpew123.cloudscreensaver.settings;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class SettingsFile {

	private final String SETTINGS_FILENAME = "cloudscreensaver20.settings";
	private final String SETTINGS_FOLDERNAME = "cloudscreensaver20";
	private final String LOCALE_PROPERTIES_FILENAME = "blindpew123.cloudscreensaver.resources.CloudScreenSaver";
	
	private ResourceBundle resources = ResourceBundle.getBundle(LOCALE_PROPERTIES_FILENAME);
	private Properties settings;
	
	static private SettingsFile instance;
	
	public static synchronized SettingsFile getInstance(){
		if (instance == null) {
			instance = new SettingsFile();
		}
		return instance;
	}	
	
	public String getResources(String name) {
		return (String) resources.getObject(name);
	}	

	public String getSettingsValue(String name) {
		return (String) settings.getOrDefault(name,"");
	}
		
	void saveSettings(Properties props) {
		File settingsFile = getSettingFile();
		try (FileOutputStream fileIn = new FileOutputStream(settingsFile)){
			props.store(fileIn,"Settings Saved");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}	
	}
	
	private SettingsFile() {
		settings = getSettings();
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
		File userDir = new File(System.getProperty("user.home"));
		File thisProgramDir = new File(userDir,SETTINGS_FOLDERNAME);
		if(!thisProgramDir.exists()) {
			thisProgramDir.mkdir();
		}
		return new File(thisProgramDir, SETTINGS_FILENAME);
	}
		
		
}
