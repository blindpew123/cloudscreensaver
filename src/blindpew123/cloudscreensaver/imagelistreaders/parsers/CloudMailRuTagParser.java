package blindpew123.cloudscreensaver.imagelistreaders.parsers;

public class CloudMailRuTagParser extends WordParser {
	
	
		
	TagsState currentState = TagsState.READY;
	
	private enum TagsState {
		READY {			
			void processTag(String value) {
				if (value.equals("tree")) {
					localcontext.currentState = TagsState.TAG;
		//			System.out.println(Thread.currentThread().getName()+" "+link + " From ready to Tag");
				}				
			}		
		},
		TAG {
		void processTag(String value) {
				if (value.equals("weblink")) {
					localcontext.currentState = TagsState.WEBLINK;
			//		System.out.println(Thread.currentThread().getName()+" "+value + "to Weblink");
				}
				if (value.equals("type")) {
					localcontext.currentState = TagsState.TYPE;
			//		System.out.println(Thread.currentThread().getName()+" "+value + "to Type");
				}				
			}
		},
		WEBLINK {
			void processTag(String value) {
				link = value;
				localcontext.currentState = TagsState.TAG;
			//	System.out.println(Thread.currentThread().getName()+" "+value + "From Weblink to Tag");
			}
		},
		TYPE {
			void processTag(String value) {
				if (value.equals("folder")||value.equals("file")) {
					if (link != null) {
						localcontext.getContext().getFileMap().put(link, value.equals("folder"));
						link = null;
					}
				}
			//	System.out.println(Thread.currentThread().getName()+" "+value + "From Type to Tag");
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
		if(value!=null) {
			currentState.processTag(value);
		}
		return false; // true or false doesn't matter in this implementation 
	}
}
