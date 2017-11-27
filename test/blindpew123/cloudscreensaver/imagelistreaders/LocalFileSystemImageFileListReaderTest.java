package blindpew123.cloudscreensaver.imagelistreaders;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class LocalFileSystemImageFileListReaderTest {

	private ImageFileListReader reader;
	
	private class MultiReader implements Runnable {

		ImageFileListReader reader;
		public MultiReader(ImageFileListReader reader) {
			this.reader = reader;
		}
		@Override
		public void run() {
			 reader.readListTo();			
		}
		
	}
	
	@Before
	public void setUp() throws Exception {
		Path path = Paths.get("src/blindpew123/cloudscreensaver/").toAbsolutePath();
		reader = new LocalFileSystemImageFileListReader(path.toString());
	}

	@Test
	public void testSingleThreadLocalPathResultOK() {
		ImageFileList list = reader.readList();
		assertThat(list.getSize(),equalTo(6));
	}
	
	@Test
	public void testMultiThreadsLocalPathResultOK() throws InterruptedException {
		long start = System.currentTimeMillis();
		ImageFileList list = new ImageFileList();
		
		ExecutorService es = Executors.newWorkStealingPool();
		Future<?> task = es.submit(new MultiReader(new LocalFileSystemImageFileListReader(Paths.get("src/blindpew123/cloudscreensaver/").toAbsolutePath().toString(),list)));
		boolean flag = false;		
		while(!task.isDone()) {
			Thread.yield();
			if(!flag && list.getSize() == 10) {
				flag = true;
				System.out.println(System.currentTimeMillis()-start);
			}		
		}
		es.shutdown();
		assertThat(list.getSize(),equalTo(6));
	}

//	@Ignore //use your own network path
	@Test
	public void testSingleThreadLocalNetworkPathResultOK() {
		reader = new LocalFileSystemImageFileListReader("//server/e/digifoto"); 
		ImageFileList list = reader.readList();
		assertThat(list.getSize(),equalTo(20136));
	}
	
	@Ignore //use your own network path
	@Test
	public void testMultiThreadsLocalNetworkPathResultOK() throws InterruptedException {
		long start = System.currentTimeMillis();
		ImageFileList list = new ImageFileList();
		new Thread(new MultiReader(new LocalFileSystemImageFileListReader("//server/e/digifoto",list))).start();
		boolean flag = false;
		while(true) {
			Thread.yield();
			if(!flag && list.getSize() == 10) {
				flag = true;
				System.out.println(System.currentTimeMillis()-start);
			}
			if(list.getSize() > 10) break;
		}
		assertThat(list.getSize(),equalTo(20136));
	}
	
	@Test
	public void testSingleThreadWrongPath() {
		reader = new LocalFileSystemImageFileListReader("t:/"); 
		ImageFileList list = reader.readList();
		assertThat(list.getSize(),equalTo(0));
	}
	
	@Test
	public void testSingleThreadNullPath() {
		reader = new LocalFileSystemImageFileListReader(null); 
		ImageFileList list = reader.readList();
		assertThat(list.getSize(),equalTo(0));
	}
	
}
