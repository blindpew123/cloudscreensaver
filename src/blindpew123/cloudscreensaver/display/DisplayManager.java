package blindpew123.cloudscreensaver.display;

import java.awt.Rectangle;

// Reads the current settings and builds the corresponding Display
// TODO: not fully implemented yet 

public class DisplayManager {
	
	private static DisplayManager instance;
	private Display currentDisplay;
	
	public static synchronized DisplayManager getInstance() {
		if (instance == null) {instance = new DisplayManager();}
		return instance;
	}
	
	private DisplayManager() {}
	
	
	public synchronized Display getDisplay() {
		setDisplay();
		return currentDisplay;
	}
	
	private void setDisplay() {
		if (currentDisplay == null) {
			currentDisplay = new SimpleDisplay();
		}
	}
	
	
}

