package blindpew123.cloudscreensaver;

import blindpew123.cloudscreensaver.settings.*;
import blindpew123.cloudscreensaver.imagelistreaders.*;

/** The CloudScreenSaver 2.0 works as Windows screen saver, developed to show randomly choosed images from local or local network devices
 * and also from HTML representation clouds sources. Currently support only Cloud Mail.Ru links(i.e starts with https://cloud.mail.ru/public/).
 * 
 * According <a href="https://support.microsoft.com/en-us/help/182383/info-screen-saver-command-line-arguments">Microsoft's document</a>
 * program supports two argument's keys.  
 * 
 * @author blindpew123
 *
 */

public class CloudScreenSaver {
	public static void main(String[] args) {
			
			if(args.length>0) {
			  switch(args[0].substring(0, 2)) {  // remove don't needed symbols from arguments
			 	  case "/c":
			 		new CloudScreenSaver().openSettingsDialog();
			 		break;
				  case "/s":
				  case "/S":
					  new CloudScreenSaver().startScreenSaver();
					 break;
				  default: 		    	
					break;
			  }
			} else {
				new CloudScreenSaver().openSettingsDialog();
			}		
	}
	
	private void openSettingsDialog() {
		new SettingsDialog(ImageFileListReadersManager.getInstance());
	}
	
	private void startScreenSaver() {
		ScreenSaver scrSaver = new ScreenSaver();
		scrSaver.initScreenSaver();		
		scrSaver.startScreenSaver();
		
	}
}