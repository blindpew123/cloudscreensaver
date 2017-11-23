package blindpew123.cloudscreensaver.imagelistreaders.parsers;


class CloudMailReady extends WordParser {
	
	public CloudMailReady(PageParser context) {
		super(context);
	}
	@Override
	protected boolean process(String value) {
		if (value.equals("tree")) {
			getContext().setStringParser(new CloudMailTag(getContext()));
		}
		return false; // value not used
	}

}
