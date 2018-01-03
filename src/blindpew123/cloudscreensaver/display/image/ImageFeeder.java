package blindpew123.cloudscreensaver.display.image;

import java.awt.Rectangle;
import java.util.concurrent.*;

import blindpew123.cloudscreensaver.imagelistreaders.ImageFileList;
import blindpew123.cloudscreensaver.imagepath.ImagePath;

/* Имея настройки мы знаем какого размера изображения мы должны выдавать
 * (метод расчета размеров изображения)
 * На основе списока файлов начинаем процесс получения и обработки файлов в отдельном потоке
 * обработанные файлы поступают в очередь. Поток обработки приостанавливается до тех пор пока обработанное изображение не будет извлечено модулем отображения
 * 
 */
public class ImageFeeder {

	/* Настройки размер изображения. Они расчитываются на основе того какой способ показа изображения используется
	 * способы перехода и текущий размер экрана
	 * 
	 */
	private final Rectangle prefferableSize;
	private final ImageFileList fileList;
	private final SynchronousQueue<ReadyImageCortege> readyImage;
	private final ImageReader imgReader; 
	
	private class ImageProcessor implements Runnable {

		
		
		@Override
		public void run() {			
			while(true) {			
				try{
					ImagePath path = fileList.nextImagePath();
					ReadyImageCortege tmpCort = imgReader.getImage(path);
					readyImage.put(tmpCort);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}		
		}
	}
	
	public ImageFeeder(Rectangle prefferableSize, ImageFileList fileList, ImageReader imgReader) {
		this.prefferableSize = prefferableSize;
		this.fileList = fileList;
		this.imgReader = imgReader;
		readyImage = new SynchronousQueue<>();
	}
	
	public void startFeed() {
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(new ImageProcessor());		
	}
	
	public ReadyImageCortege getReadyImageFromQueue() {
		try {
			return readyImage.take();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
		return null;
	}
	
	
	
	
}
