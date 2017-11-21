package blindpew123.cloudscreensaver.settings;

import static org.junit.Assert.*;

import org.junit.Test;

import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReadersManager;

public class SettingsDialogTest {

	@Test
	public void test() {
		SettingsDialog app = new SettingsDialog(ImageFileListReadersManager.getInstance());
		app.setVisible(true);
	}

}
