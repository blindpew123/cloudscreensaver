package blindpew123.cloudscreensaver.imagelistreaders;

import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import blindpew123.cloudscreensaver.imagelistreaders.parsers.CloudMailRuPageParser;


class CloudMailRuImageFileListReader extends ImageFileListReader {
	private final String startPath;
	private final String prefix;
	
	private final Map<String, Boolean> resultImageTree = new ConcurrentHashMap<>(32,0.75f,4);	
	
	@SuppressWarnings("serial")
	private class PageReaderTask extends RecursiveAction {
		private CloudMailRuPageParser parser;
		
		PageReaderTask(String prefix, String path){
			parser = new CloudMailRuPageParser(prefix, path);
		}
		
		@Override
		protected void compute() {
			try {				
				Map<String,Boolean> partialResult = parser.processPage();
				resultImageTree.putAll(partialResult);
				for(String path:partialResult.keySet()) {
					if(partialResult.get(path)) {
						invokeAll(new PageReaderTask(prefix, path));
					}
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
		return new ImageFileList(getFileListOnly());
	}
	
	private List<String> getFileListOnly(){
		return resultImageTree.keySet()
			.parallelStream()
			.filter(k->(!resultImageTree.get(k))).collect(Collectors.toList());
	}
	
	
}
