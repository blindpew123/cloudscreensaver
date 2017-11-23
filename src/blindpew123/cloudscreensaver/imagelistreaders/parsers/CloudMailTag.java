package blindpew123.cloudscreensaver.imagelistreaders.parsers;


class CloudMailTag extends WordParser {

	CloudMailTag(PageParser context){
		super(context);
	}
	@Override
	protected boolean process(String value) {
		if (value.equals("weblink")) {
			getContext().setStringParser(new CloudMailWebLink(getContext()));
	//		System.out.println(Thread.currentThread().getName()+" "+value + "to Weblink");
		}
		if (value.equals("type")) {
			getContext().setStringParser(new CloudMailType(getContext()));
	//		System.out.println(Thread.currentThread().getName()+" "+value + "to Type");
		}
		return false; // value not used
	}

}
