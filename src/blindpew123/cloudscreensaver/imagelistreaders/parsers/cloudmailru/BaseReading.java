package blindpew123.cloudscreensaver.imagelistreaders.parsers.cloudmailru;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.spi.CharsetProvider;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

class CharParser2 {
	StringBuilder keyWord = new StringBuilder();
	boolean quotesOpened = false;
	
	boolean processQuotes (char c) {
		if (quotesOpened) {
			if(c == '\"') {
				quotesOpened = false;				
				return true;
			} else if(c =='\n') {
				quotesOpened = false;
			} else {
				keyWord.append(c);
			}
		} else {
			if(c == '\"') {
				quotesOpened = true;
				keyWord = new StringBuilder();
			}
		}
		return false;
	}
}

public class BaseReading {

	static final String prefix = "https://cloud.mail.ru/public/";
	static final String currentPath = "Cw2G/Vkvwnmq9p";
	static final int BUFFER_SIZE = 8192;
	static private char[] tempBuffer = new char[BUFFER_SIZE];
	
	static Deque<Character> qqq= new LinkedList<>();
	static Deque<String> www= new LinkedList<>();
	
	static boolean isProcessWords = false;
	
	static boolean isProcessTag = false;
	static boolean isId = false;
	static boolean isType = false;
	
	static boolean isProcessQuotes = true;
	static boolean isProcessBrackets = false;
	
	public static void main(String... args) throws MalformedURLException, IOException {
		
		long start = System.currentTimeMillis();
		BufferedReader s = new BufferedReader(new InputStreamReader((new URL(prefix+currentPath)).openStream()));
		int counter = 0;
		String inputLine;
		while ((inputLine = s.readLine()) != null) {
				tempBuffer = inputLine.toCharArray();
				counter+=tempBuffer.length;
				CharParser2 cParser = new CharParser2();
				for(int i = 0; i < tempBuffer.length; i++) {				
					char c = tempBuffer[i];
					if(isProcessQuotes) {
						if(cParser.processQuotes(c)) {
							processWord(cParser.keyWord);
							if(isProcessWords) {
								if(isProcessTag) {
									processTag(cParser.keyWord);
								} else if(isId) {
									processId(cParser.keyWord);
								} else if(isType) {
									processType(cParser.keyWord);
								}
							}
						}
					} else if(isProcessBrackets) {
						processBrack(c);
					}
				}
			}
		s.close();	
		System.out.println(counter);
		System.out.println(System.currentTimeMillis() - start);
	}
		
	
	static boolean processWord(StringBuilder word) {
		
		if (word.toString().equals("tree")) {
			isProcessWords = true;
			isProcessQuotes = false;
			isProcessBrackets = true;
		}
		return false;
	}
	
	static boolean processBrack(char c) {
		switch (c) {
			case '[': qqq.push(c);
					  break;
			case ']': qqq.pop();
					  if(qqq.isEmpty()) {
						  isProcessQuotes = true;
						  isProcessBrackets = false;
						  isProcessTag = true;
						  System.out.println("---Bracket Complete, swithed to Quotes");						  
					  }
					  break;
			default: 	  
		}
		return false; 
	}
	
	static boolean processTag(StringBuilder word) {
		if (word.toString().equals("id")) {
		//	System.out.println("id");
			isId = true;
			isType = false;
			isProcessTag = false;
			
		} else if(word.toString().equals("type")) {
		//	System.out.println("type");
			isId = false;
			isType = true;
			isProcessTag = false;
		}
		return false;
	}
	
	static boolean processType(StringBuilder word) {
		www.push(word.toString());
		isId = false;
		isType = false;
		isProcessTag = true;		
		return false;
	}
	
	static boolean processId(StringBuilder word) {
		System.out.println(www.pop() + " "+word);
		isId = false;
		isType = false;
		isProcessTag = true;
		return false;
	}
} 
	

