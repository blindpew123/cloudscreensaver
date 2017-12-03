package blindpew123.cloudscreensaver.display.image;

import java.awt.Rectangle;
import java.util.concurrent.*;

import blindpew123.cloudscreensaver.imagelistreaders.ImageFileList;

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
	private Rectangle prefferableSize;
	private ImageFileList fileList;
	private SynchronousQueue<ReadyImageCortege> readyImage = new SynchronousQueue<>();
	
	private class ImageProcessor implements Runnable {

		//TODO: 1. ReadersManager 
		ImageReader imgReader =  ImageReadersManager.getInstance().getReader();
		
		@Override
		public void run() {			
			while(true) {			
				try{
					String path = fileList.nextImagePath();
					readyImage.put(imgReader.getImage(path));
				} catch (InterruptedException e) {
					// TODO: RuntimeError - Message For User???
					throw new RuntimeException(e);
				}
			}		
		}
	}
	
	public ImageFeeder(Rectangle prefferableSize, ImageFileList fileList) {
		this.prefferableSize = prefferableSize;
		this.fileList = fileList;		
	}
	
	public void startFeed() {
		ExecutorService es = Executors.newSingleThreadExecutor();
		es.execute(new ImageProcessor());		
	}
	
	public ReadyImageCortege getReadyImageFromQueue() {
		try {
			return readyImage.take();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	
	
}
