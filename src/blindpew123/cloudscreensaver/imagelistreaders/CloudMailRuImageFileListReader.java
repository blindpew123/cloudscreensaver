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
				System.out.println(partialResult);
				resultImageTree.putAll(partialResult);
				List<ForkJoinTask<?>> tasks = new ArrayList<>();
				for(String path:partialResult.keySet()) {
					if(partialResult.get(path)) {    
						tasks.add(new PageReaderTask(prefix, path));
					} else if(isFormatSupported(path)) {  
						resultImgUrlQueue.offer(prefix+path);
					}
					/*else if(isFormatSupported(path)){
						Thread.sleep(50);
						Map<String, Boolean> reallink = new CloudMailRuSecondStagePageParser(prefix,path).processPage();
						if(reallink!=null && reallink.size()>0) {
							resultImgUrlQueue.add(reallink.keySet().iterator().next()); // real path to full image;
						} else {
						   System.out.println(path+ ": " +reallink);		
						}
					}*/		
				}
				
				if(!tasks.isEmpty())invokeAll(tasks);  // child folders
				
			} catch(MalformedURLException | InterruptedException e) {
				throw new RuntimeException(e);
			}
		}		
	}	
	
	protected CloudMailRuImageFileListReader(String prefix, String startPath) {
		 this.startPath = startPath;
		 this.prefix = prefix;
	}
	
	
	
	
	//@Override
	public ImageFileList _readList() {
		Queue<String> tasks = new ConcurrentLinkedQueue<>();
		Queue<String> files = new ConcurrentLinkedQueue<>();
		try {
			tasks.offer(startPath);
			while (tasks.size() > 0) {
				CloudMailRuPageParser parser = new CloudMailRuPageParser(prefix, tasks.poll());
				Map<String,Boolean> partialResult = parser.processPage();
				for(String path:partialResult.keySet()) {
					if(partialResult.get(path)) {    
						tasks.offer(path);
					} else {
						files.offer(prefix+path);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		System.out.println(files.size());
		return new ImageFileList(files);
	}
	//@Override
	public ImageFileList readList() {		
		ForkJoinPool pool = new ForkJoinPool(1);
		pool.invoke(new PageReaderTask(prefix, startPath));	
		System.out.println("Paths to files " + resultImageTree.keySet().stream().filter((k->{return !resultImageTree.get(k);})).count());
		return new ImageFileList(resultImgUrlQueue);
	}	
	
	
}
