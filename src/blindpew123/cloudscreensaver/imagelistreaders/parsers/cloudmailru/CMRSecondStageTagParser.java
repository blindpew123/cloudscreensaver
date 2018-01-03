package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.*;

public class CMRSecondStageTagParser extends WordParser {	
	enum State implements WordStateMachine{
		READY_WEBLINK{
			@Override
			public boolean process(String value) {
				if(isNotNull(value) && value.equals("weblink_get")) {
					context.current = State.WEBLINK_VALUE;
				}
				return false;
			}			
		},
		WEBLINK_VALUE{
			@Override
			public boolean process(String value) {
				if(isNotNull(value) && value.startsWith("https:")) {
					context.current = State.READY_DOWNLOAD;
					return true;
				}
				return false;
			}			
		},
		READY_DOWNLOAD{
			@Override
			public boolean process(String value) {
				if(isNotNull(value) && value.equals("download")) {
					context.current = State.DOWNLOAD_KEY;
				}
				return false;
			}			
		},
		DOWNLOAD_KEY {
			@Override
			public boolean process(String value) {
				if(!isNotNull(value)) return false;
				context.current = State.STOP;
				return true;
			}
			
		},
		STOP{
			@Override
			public boolean process(String value) {				
				return false;
			}			
		};		
		static CMRSecondStageTagParser context;
	}
	
	
	State current = State.READY_WEBLINK;
	
	public CMRSecondStageTagParser() {
		State.context = this;
	}
	
	@Override
	public boolean process(String value) {		
		return current.process(value);
	}
}
