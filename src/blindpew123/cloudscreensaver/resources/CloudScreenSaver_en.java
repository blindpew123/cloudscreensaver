package blindpew123.cloudscreensaver.resources;

import java.util.ListResourceBundle;

public class CloudScreenSaver_en extends ListResourceBundle {

	@Override
	protected Object[][] getContents() {
		return new Object[][] {
				{"nameSettingsDialog","Settings CloudScreenSaver 2.0"},
				{"pathName","Paths to images files:"},
				{"showFileNames","Show file names"},
				{"textFilesPathsTooltip","Names of folders or URL to clouds HTML pages that contains images. Values must be separated ';'"},
				{"textWarning","Warning"},
				{"showExif","Show EXIF"},
				{"cancelText","Cancel"},
				{"imageErrorMessage","Can't read image"}
			};
	}

}
