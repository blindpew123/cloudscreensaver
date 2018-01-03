package blindpew123.cloudscreensaver.imagelistreaders.parsers;

public interface WordStateMachine {
	boolean process(String value); 
	default boolean isNotNull(String value) {
		return value != null;
	}
}
