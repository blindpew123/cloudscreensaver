package blindpew123.cloudscreensaver.imagelistreaders;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class LocalFileSystemImageFileListReaderTest {

	ImageFileListReader reader;
	
	@Before
	public void setUp() throws Exception {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/").toAbsolutePath();
		reader = new LocalFileSystemImageFileListReader(path.toString());
	}

	@Test
	public void testLocalPathResultOK() {
		ImageFileList list = reader.readList();
		assertThat(list.getSize(),equalTo(4));
	}

	@Ignore //use your own network path
	@Test
	public void testLocalNetworkPathResultOK() {
		reader = new LocalFileSystemImageFileListReader("//server/e/digifoto"); 
		ImageFileList list = reader.readList();
		assertThat(list.getSize(),equalTo(20136));
	}
	
	@Test
	public void testWrongPath() {
		reader = new LocalFileSystemImageFileListReader("t:/"); 
		ImageFileList list = reader.readList();
		assertThat(list.getSize(),equalTo(0));
	}
	
	@Test
	public void testNullPath() {
		reader = new LocalFileSystemImageFileListReader(null); 
		ImageFileList list = reader.readList();
		assertThat(list.getSize(),equalTo(0));
	}
	
}
