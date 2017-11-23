package blindpew123.cloudscreensaver.imagelistreaders.parsers;

class CloudMailWebLink extends WordParser {

	CloudMailWebLink(PageParser context) {
		super(context);
	}
	@Override
	protected boolean process(String value) {
		getContext().putToWordParseDeque(value);
		getContext().setStringParser(new CloudMailTag(getContext()));
		return false;
	}

}
