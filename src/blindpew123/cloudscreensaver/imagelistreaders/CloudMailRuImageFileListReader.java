package blindpew123.cloudscreensaver.imagelistreaders;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru.CMRPageParser;
import blindpew123.cloudscreensaver.imagepath.ImagePath;
import blindpew123.cloudscreensaver.settings.SettingsFile;



class CloudMailRuImageFileListReader extends ImageFileListReader {

	@SuppressWarnings("serial")
	private class PageReaderTask extends RecursiveAction {
		private CMRPageParser parser;
		
		PageReaderTask(ImagePath path){
			if(!path.getPath().startsWith(SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix"))
				&& (path.getUrlPrefix().isEmpty())) {
					path.setUrlPrefix(SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix"));
				}			
			parser = new CMRPageParser(path);
		}
		
		@Override
		protected void compute() {
				Set<ImagePath> partialResult = parser.processPage(); // All child pages
				List<ForkJoinTask<?>> tasks = partialResult
					.stream()
					.filter(ImagePath::isFolder)
					.map(PageReaderTask::new)
					.collect(Collectors.toList()); // Sub-folders to process
				
				imageList.getImagesList().addAll(partialResult
						.stream()
						.filter(k->!k.isFolder())						
						.filter(CloudMailRuImageFileListReader.this::isFormatSupported)
						.map(p->{p.setUrlPrefix(SettingsFile.getInstance().getSettingsStringValue("cloudMailPrefix"));
								return p;})
						.collect(Collectors.toList()));
				
				if(!tasks.isEmpty()) {invokeAll(tasks);}  // child folders
				
			} 
		}		
	
	protected CloudMailRuImageFileListReader(ImagePath path, ImageFileList fileList) {
		super(path, fileList);
	}
	
	//@Override
	public void readListTo() {		
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new PageReaderTask(startPath));	
	}

	@Override
	public ImageFileList readList() {
		throw new UnsupportedOperationException("Use void readListTo() instead");
	}	
	
	
}
