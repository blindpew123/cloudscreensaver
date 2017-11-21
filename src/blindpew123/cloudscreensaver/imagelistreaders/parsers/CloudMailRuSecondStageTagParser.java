package blindpew123.cloudscreensaver.imagelistreaders.parsers;

public class CloudMailRuSecondStageTagParser extends WordParser {

	private boolean state;
		
	@Override
	protected boolean process(String value) {		
		if(value == null) {
			state = false;
		} else {		
			if(state) {
				state = false;
				return true;
			} else if(value.equals("original")) {
				state = true;
			}
		}
		return false;
	}
}
