package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

public class CMRSecondStageTagParser extends WordParser {	
	enum State {
		READY_WEBLINK{
			@Override
			boolean process(String value) {
				if(value.equals("weblink_get")) {
					context.current = State.WEBLINK_VALUE;
				}
				return false;
			}			
		},
		WEBLINK_VALUE{
			@Override
			boolean process(String value) {
				if(value.startsWith("https:")) {
					context.current = State.READY_DOWNLOAD;
					return true;
				}
				return false;
			}			
		},
		READY_DOWNLOAD{
			@Override
			boolean process(String value) {
				if(value.equals("download")) {
					context.current = State.DOWNLOAD_KEY;
				}
				return false;
			}			
		},
		DOWNLOAD_KEY {
			@Override
			boolean process(String value) {
				context.current = State.STOP;
				return true;
			}
			
		},
		STOP{
			@Override
			boolean process(String value) {				
				return false;
			}			
		};		
		abstract boolean process(String value);
		static CMRSecondStageTagParser context;
	}
	
	
	State current = State.READY_WEBLINK;
	
	public CMRSecondStageTagParser() {
		State.context = this;
	}
	
	@Override
	protected boolean process(String value) {		
		return current.process(value);
	}
}
