package blindpew123.cloudscreensaver.imagelistreaders.parsers;

public class CloudMailRuTagParser extends WordParser {
	
	TagsState currentState = TagsState.READY;
	
	private enum TagsState {
		READY {			
			void processTag(String value) {
				if (value.equals("tree")) {
					localcontext.currentState = TagsState.TAG;
				}				
			}		
		},
		TAG {
		void processTag(String value) {
				if (value.equals("weblink")) {
					localcontext.currentState = TagsState.WEBLINK;
				}
				if (value.equals("type")) {
					localcontext.currentState = TagsState.TYPE;
				}				
			}
		},
		WEBLINK {
			void processTag(String value) {
				link = value;
				localcontext.currentState = TagsState.TAG;
			}
		},
		TYPE {
			void processTag(String value) {
				if (value.equals("folder")||value.equals("file")) {
					if (link == null) throw new IllegalArgumentException("Отсутствует рабочая ссылка для данного типа");
					localcontext.getContext().getFileMap().put(link, value.equals("folder"));
					link = null;
				}
				localcontext.currentState = TagsState.TAG;
			}
		};		
		private static CloudMailRuTagParser localcontext;
		private static String link;
		
		abstract void processTag(String value);
		void setLocalContext(CloudMailRuTagParser context) {
			localcontext = context;
		}		
	}
	
	public CloudMailRuTagParser(PageParser context) {
		super(context);
		currentState.setLocalContext(this);
	}
	
	@Override
	protected boolean process(String value) {
		currentState.processTag(value);
		return false; // true or false doesn't matter in this implementation 
	}
}
