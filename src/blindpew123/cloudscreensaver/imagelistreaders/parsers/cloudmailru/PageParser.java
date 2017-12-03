package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import java.io.*;
import java.util.*;
import java.net.*;

public abstract class PageParser {	
	private final Map<String, Boolean> fileMap = new HashMap<>();	

	private final String basePath;
	private final String currentLevelPath;
	
	private CharParser charParser;
	private WordParser wordParser;
	
	private Deque<Character> charParseDeque = new LinkedList<>();
	private Deque<String> wordParseDeque = new LinkedList<>();	

	public PageParser(String basePath, String currentLevelPath) {
		this.basePath = basePath;
		this.currentLevelPath = currentLevelPath;
	}
	
	public final Map<String, Boolean> processPage() {
		charParser = initCharParser();
	    wordParser = initWordParser();
	    try (BufferedReader s = new BufferedReader(new InputStreamReader(getUrl().openStream()))) {
			String inputLine;
			while ((inputLine = s.readLine()) != null) {
				char[] buffer = inputLine.toCharArray();
				processBuffer(buffer);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	    finalProcessing();
		return getFileMap();
	}
		
	protected abstract CharParser initCharParser();
	
	protected abstract WordParser initWordParser();
	
	protected abstract void processBuffer(char[] buffer);
	
	protected abstract void finalProcessing();	

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
	
	protected void setCharParser(CharParser chParser) {
		charParser = chParser;
	}
	
	protected WordParser getStringParser() {
		return wordParser;
	}
	
	protected void setStringParser(WordParser wParser) {
		wordParser = wParser;
	}
	protected Deque<Character> getCharParseDeque() {
		return charParseDeque;
	}

	protected void setCharParseDeque(Deque<Character> charParseDeque) {
		this.charParseDeque = charParseDeque;
	}

	protected Deque<String> getWordParseDeque() {
		return wordParseDeque;
	}

	protected void setWordParseDeque(Deque<String> wordParseDeque) {
		this.wordParseDeque = wordParseDeque;
	}

	private URL getUrl() throws MalformedURLException {
		return new URL(getBasePath() + getCurrentLevelPath());
	}
	
	

	

	
}
