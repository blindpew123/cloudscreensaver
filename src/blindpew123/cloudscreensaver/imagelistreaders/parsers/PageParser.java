package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.net.*;

public abstract class PageParser {	
	private final Map<String, Boolean> fileMap = new HashMap<>();
	
	private final String basePath;
	private final String currentLevelPath;
	
	private CharParser charParser;
	private WordParser wordParser;
	
	private char[] buffer;	
	
	private class PageStreamReader implements Runnable {
		static final int BUFFER_SIZE = 8192;
		private char[] tempBuffer = new char[BUFFER_SIZE];
		
		private final URL url;
		
		private SynchronousQueue<Integer> dataLength = new SynchronousQueue<>(); 
		
		private PageStreamReader(URL url) {
			this.url = url;
		}		
		
		@Override
		public void run() {			
			try (Reader s = new BufferedReader(new InputStreamReader(url.openStream()))) {
				int numChars;
				while ((numChars  = s.read(tempBuffer))>0) {
					setBuffer(tempBuffer);					
					dataLength.put(numChars);
				}
				dataLength.put(0);
			} catch (IOException | InterruptedException e) {
				throw new RuntimeException(e);
			}			
		}
		
		private int getDataLength() throws InterruptedException {
			return dataLength.take();
		}
		
	}
	
	public PageParser(String basePath, String currentLevelPath) {
		this.basePath = basePath;
		this.currentLevelPath = currentLevelPath;
	}
	
	public final Map<String, Boolean> processPage() throws InterruptedException, MalformedURLException {
		charParser = initCharParser();
	    wordParser = initWordParser();
	
		PageStreamReader reader = new PageStreamReader(getUrl());
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(reader);		
		
		int limit;
		while ((limit = reader.getDataLength()) > 0) {	
			processBuffer(getBuffer(), limit);
		};
		
		es.shutdown();
		finalProcessing();
		return getFileMap();
	}
		
	protected abstract CharParser initCharParser();
	
	protected abstract WordParser initWordParser();
	
	protected abstract void processBuffer(char[] buffer, int limit);
	
	protected abstract void finalProcessing();
	
	protected final char[] getBuffer() {
		return buffer;
	}

	protected final Map<String, Boolean> getFileMap(){
		return fileMap;
	}
	
	protected final String getBasePath() {
		return basePath;
	}
	
	protected final String getCurrentLevelPath() {
		return currentLevelPath;
	}
	
	protected CharParser getCharParser() {
		return charParser;
	}
	
	protected WordParser getStringParser() {
		return wordParser;
	}
	
	private URL getUrl() throws MalformedURLException {
		return new URL(getBasePath() + getCurrentLevelPath());
	}
	
	private void setBuffer(char[] buffer) {
		this.buffer  =  buffer; 
	}

	
}
