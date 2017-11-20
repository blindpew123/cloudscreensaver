package blindpew123.cloudscreensaver.imagelistreaders;

import static org.junit.Assert.*;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Test {	

	@org.junit.Test
	public void testCb() throws Exception{		
		Path p = Paths.get(new URI("file://server/e/DigiFoto/"));
		Files.list(p).parallel().forEach(System.out::println);
	}
	
}
