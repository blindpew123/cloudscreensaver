package blindpew123.cloudscreensaver.imagelistreaders.parsers;

import java.io.*;
import java.util.*;

import blindpew123.cloudscreensaver.display.image.ImageReadingException;
import blindpew123.cloudscreensaver.imagepath.ImagePath;

import java.net.*;

public abstract class PageParser {	
	private final Set<ImagePath> fileSet = new HashSet<>();	

	private final ImagePath currentLevelPath; 
	
	
	private CharParser charParser;
	private WordParser wordParser;
	
	private Deque<Character> charParseDeque = new LinkedList<>();
	private Deque<String> wordParseDeque = new LinkedList<>();	

	public PageParser(ImagePath path) {
		this.currentLevelPath = path;
	}
	
	public final Set<ImagePath> processPage(){
		charParser = initCharParser();
	    wordParser = initWordParser();	    
	    try (BufferedReader s = new BufferedReader(new InputStreamReader(getUrl().openStream()))) {
	    	String inputLine;
			while ((inputLine = s.readLine()) != null) {
				char[] buffer = inputLine.toCharArray();
				processBuffer(buffer);
			}
		} catch (IOException e) {
			throw new ImageReadingException(e.getMessage(), currentLevelPath);
		}
	    finalProcessing();
		return getFileSet();
	}
		
	protected abstract CharParser initCharParser();
	
	protected abstract WordParser initWordParser();
	
	protected abstract void processBuffer(char[] buffer);
	
	protected abstract void finalProcessing();	

	public final Set<ImagePath> getFileSet(){
		return fileSet;
	}
	
	protected final ImagePath getCurrentLevelPath() {
		return currentLevelPath;
	}
	
	protected CharParser getCharParser() {
		return charParser;
	}
	
	public void setCharParser(CharParser chParser) {
		charParser = chParser;
	}
	
	protected WordParser getStringParser() {
		return wordParser;
	}
	
	public void setStringParser(WordParser wParser) {
		wordParser = wParser;
	}
	protected Deque<Character> getCharParseDeque() {
		return charParseDeque;
	}

	protected void setCharParseDeque(Deque<Character> charParseDeque) {
		this.charParseDeque = charParseDeque;
	}

	public Deque<String> getWordParseDeque() {
		return wordParseDeque;
	}

	protected void setWordParseDeque(Deque<String> wordParseDeque) {
		this.wordParseDeque = wordParseDeque;
	}

	private URL getUrl() throws MalformedURLException {
		return new URL(currentLevelPath.getAbsolutePath());
	}
	
	

	

	
}
