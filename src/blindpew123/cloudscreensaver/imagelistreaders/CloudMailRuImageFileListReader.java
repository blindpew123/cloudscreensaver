package blindpew123.cloudscreensaver.imagelistreaders;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import javax.naming.OperationNotSupportedException;

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
						.filter((k)->{return !partialResult.get(k);})						
						.map(s->ImageFileListReadersManager.getCloudMailRuPrefix()+s)
						.filter(s->isFormatSupported(s))
						.collect(Collectors.toList()));
				
				/*for(String path:partialResult.keySet()) {
					if(partialResult.get(path)) {    
						tasks.add(new PageReaderTask(prefix, path));
					} else if(isFormatSupported(path)) {  
						resultImgUrlQueue.offer(prefix+path);
					}
					else if(isFormatSupported(path)){
					//	Thread.sleep(50);
						Map<String, Boolean> reallink = new CloudMailRuSecondStagePageParser(prefix,path).processPage();
						if(reallink!=null && reallink.size()>0) {
							resultImgUrlQueue.add(reallink.keySet().iterator().next()); // real path to full image;
						} else {
						   System.out.println(path+ ": " +reallink);		
						}
					} 		
				} */
				
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
	//	System.out.println("Paths to files " + resultImageTree.keySet().stream().filter((k->{return !resultImageTree.get(k);})).count());
	//	System.out.println(resultImgUrlQueue.size());
	//	return new ImageFileList(resultImgUrlQueue);
	}

	@Override
	public ImageFileList readList() {
		throw new UnsupportedOperationException("Use void readListTo() instead");
	}	
	
	
}
