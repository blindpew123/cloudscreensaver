package blindpew123.cloudscreensaver.imagelistreaders;

import static org.junit.Assert.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import blindpew123.cloudscreensaver.imagelistreaders.CloudMailRuImageFileListReader;
import blindpew123.cloudscreensaver.imagelistreaders.ImageFileListReader;

import org.junit.*;


public class CloudMailRuImageFileListReaderTest {

	static final String prefix = "https://cloud.mail.ru/public/";
	
	CloudMailRuImageFileListReader reader;
	
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
	//https://cloud.mail.ru/public/Cw2G/Vkvwnmq9p - my cloud
	//https://cloud.mail.ru/public/DQEv/h67e4AAF9/ - test cloud
		
	@Test
	public void testMultiThreadsLocalPathResultOK() throws InterruptedException {
			
			String levelPath = "/Cw2G/Vkvwnmq9p";
		
			long start = System.currentTimeMillis();
			ImageFileList list = new ImageFileList();
			
			ExecutorService es = Executors.newWorkStealingPool();
			Future<?> task = es.submit(new MultiReader(new CloudMailRuImageFileListReader(prefix,levelPath ,list)));
			boolean flag = false;		
			while(!task.isDone()) {
				Thread.yield();
				if(!flag && list.getSize() >= 10) {
					flag = true;
					System.out.println(System.currentTimeMillis()-start);
				}		
			}
			es.shutdown();
			assertThat(list.getSize(),equalTo(3));
			assertTrue(list.getImagesList().get(0).endsWith(".jpg") && list.getImagesList().get(0).startsWith(levelPath));
		}		

	/*
	@Ignore
	@Test
	public void testWrongUrl() {
		reader = new CloudMailRuImageFileListReader(ImageFileListReader.CLOUD_MAIL_RU_PREFIX, "DQEv/h6");
		List<String> urlList = reader.readList().getImagesList();
		assertThat(urlList.size(),equalTo(0));		
	}
	@Ignore
	@Test
	public void testNullUrl() {
		reader = new CloudMailRuImageFileListReader(ImageFileListReader.CLOUD_MAIL_RU_PREFIX, "DQEv/h6");
		List<String> urlList = reader.readList().getImagesList();
		assertThat(urlList.size(),equalTo(0));		
	}
	
	@Ignore
	@Test(expected = RuntimeException.class)
	public void testWrongPrefix() {
		reader = new CloudMailRuImageFileListReader("", "DQEv/h67e4AAF9");
		reader.readList().getImagesList();
		fail();		
	}
	
	@Ignore
	@Test(expected = RuntimeException.class)
	public void testNullPrefix() {
		reader = new CloudMailRuImageFileListReader(null, "DQEv/h67e4AAF9");
		reader.readList().getImagesList();
		fail();		
	}
*/
}
