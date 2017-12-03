package blindpew123.cloudscreensaver.settings;

import blindpew123.cloudscreensaver.imagelistreaders.*;
import javax.swing.JFrame;


@SuppressWarnings("serial")
public class SettingsDialog extends JFrame {	

	public SettingsDialog(ImageFileListReadersManager readersManager) {
		setTitle(SettingsFile.getInstance().getResource("nameSettingsDialog"));
		setSize(400, 150);
		getContentPane().add(new SettingsPanel(this, readersManager));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}	
}

