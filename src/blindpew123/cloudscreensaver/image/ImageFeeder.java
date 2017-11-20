package blindpew123.cloudscreensaver.image;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.*;

import blindpew123.cloudscreensaver.imagelistreaders.ImageFileList;

/* Имея настройки мы знаем какого размера изображения мы должны выдавать
 * (метод расчета размеров изображения)
 * На основе списока файлов начинаем процесс получения и обработки файлов в отдельном потоке
 * обработанные файлы поступают в очередь. Поток обработки приостанавливается до тех пор пока обработанное изображение не будет извлечено модулем отображения
 * 
 */
public class ImageFeeder {

	/* Настройки размер изображения. Они раситываются на основе того какой способ показа изображения используется
	 * способы перехода и текущий размер экрана
	 * 
	 */
	private Rectangle prefferableSize;
	private ImageFileList fileList;
	private BlockingQueue<BufferedImage> readyImage;
	
	private class ImageProcessor implements Runnable {

		ImageReader imgReader = new CacheImageReader(
				new ImageResizer(						
				prefferableSize, new DefaultImageReader(
				new HttpImageReader(
				new LocalImageReader(null
				)))));
		
		@Override
		public void run() {
			while(true) {
			// Получить очередное имя файла
			// запросить у ридера файл по имени
			// передать его ресайзеру
			// запихнуть в очередь
			// TODO: Мы можем полуить рантайм - нужно вывести диалог	
				readyImage.put(imgReader.getImage(fileList.nextImage()));
			}		
		}
	}
	
	public ImageFeeder(Rectangle prefferableSize, ImageFileList fileList) {
		this.prefferableSize = prefferableSize;
		this.fileList = fileList;
		System.out.printn("3. "+fileList.getSize());
		new Thread(new ImageProcessor()).start();
	}
	
	
	
}
