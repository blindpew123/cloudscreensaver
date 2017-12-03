package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

interface WordStateMachine {
	boolean process(String value); 
	default boolean isNotNull(String value) {
		return value != null;
	}
}
