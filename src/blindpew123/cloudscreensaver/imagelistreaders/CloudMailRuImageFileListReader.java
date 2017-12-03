package blindpew123.cloudscreensaver.imagelistreaders;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru.CMRPageParser;



class CloudMailRuImageFileListReader extends ImageFileListReader {
	private final String startPath;
	private final String prefix;
	
	//	private final Map<String, Boolean> resultImageTree = new ConcurrentHashMap<>(32,0.75f,4);
	// private Queue<String> resultImgUrlQueue = new ConcurrentLinkedQueue<>();
	
	@SuppressWarnings("serial")
	private class PageReaderTask extends RecursiveAction {
		private CMRPageParser parser;
		
		PageReaderTask(String prefix, String path){
			parser = new CMRPageParser(prefix, path);
		}
		
		@Override
		protected void compute() {					
				Map<String,Boolean> partialResult = parser.processPage(); // All child pages
				List<ForkJoinTask<?>> tasks = partialResult.keySet()
					.stream()
					.filter((k)->partialResult.get(k))
					.map(s->new PageReaderTask(prefix, s))
					.collect(Collectors.toList()); // Sub-folders to process
				
				imageList.getImagesList().addAll(partialResult.keySet()
						.stream()
						.filter(k->!partialResult.get(k))						
						.map(s->ImageFileListReadersManager.getCloudMailRuPrefix()+s)
						.filter(CloudMailRuImageFileListReader.this::isFormatSupported)
						.collect(Collectors.toList()));
				
				if(!tasks.isEmpty())invokeAll(tasks);  // child folders
				
			} 
		}		
	
	protected CloudMailRuImageFileListReader(String prefix, String startPath, ImageFileList fileList) {
		super(fileList);
		this.startPath = startPath;
		this.prefix = prefix;
	}
	
	//@Override
	public void readListTo() {		
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new PageReaderTask(prefix, startPath));	
	}

	@Override
	public ImageFileList readList() {
		throw new UnsupportedOperationException("Use void readListTo() instead");
	}	
	
	
}
