package blindpew123.cloudscreensaver.imagelistreaders.parsers;


class CloudMailType extends WordParser {

	public CloudMailType(PageParser context) {
		super(context);
	}
	
	@Override
	protected boolean process(String value) {
		String link = getContext().getFromWordParseDeque();
		
		if (link != null && (value.equals("folder")||value.equals("file"))) {
			    getContext().getFileMap().put(link, value.equals("folder"));
			}
		getContext().setStringParser(new CloudMailTag(getContext()));
		return false;
	}
}

		

