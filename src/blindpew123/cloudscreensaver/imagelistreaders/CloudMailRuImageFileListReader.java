package blindpew123.cloudscreensaver.imagelistreaders;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.CloudMailRuPageParser;
import blindpew123.cloudscreensaver.imagelistreaders.parsers.CloudMailRuSecondStagePageParser;


class CloudMailRuImageFileListReader extends ImageFileListReader {
	private final String startPath;
	private final String prefix;
	
	private final Map<String, Boolean> resultImageTree = new ConcurrentHashMap<>(32,0.75f,4);
	private Queue<String> resultImgUrlQueue = new ConcurrentLinkedQueue<>();
	
	@SuppressWarnings("serial")
	private class PageReaderTask extends RecursiveAction {
		private CloudMailRuPageParser parser;
		
		PageReaderTask(String prefix, String path){
			parser = new CloudMailRuPageParser(prefix, path);
		}
		
		@Override
		protected void compute() {
			try {				
				Map<String,Boolean> partialResult = parser.processPage(); // All child pages
				resultImageTree.putAll(partialResult);
				for(String path:partialResult.keySet()) {
					if(partialResult.get(path)) {    
						invokeAll(new PageReaderTask(prefix, path));  // child folders
					} else resultImgUrlQueue.add(
							new CloudMailRuSecondStagePageParser(prefix,path)
							.processPage()
							.keySet()
							.iterator()
							.next()
							); // real path to full image;
				}
			} catch(MalformedURLException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}		
	}	
	
	protected CloudMailRuImageFileListReader(String prefix, String startPath) {
		 this.startPath = startPath;
		 this.prefix = prefix;
	}
	
	@Override
	public ImageFileList readList() {		
		ForkJoinPool pool = new ForkJoinPool();
		pool.invoke(new PageReaderTask(prefix, startPath));		
		return new ImageFileList(resultImgUrlQueue);
	}	
	
	
}
