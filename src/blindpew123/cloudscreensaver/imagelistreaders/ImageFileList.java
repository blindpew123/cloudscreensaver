package blindpew123.cloudscreensaver.imagelistreaders;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

import blindpew123.cloudscreensaver.imagepath.ImagePath;


public class ImageFileList implements Iterable<ImagePath> {
	
	
	private List<ImagePath> imagesList;

	public ImageFileList(){
		imagesList = new CopyOnWriteArrayList<>();
	}
	
	public ImageFileList(Collection<ImagePath> imagesList){
		this.imagesList = new CopyOnWriteArrayList<>(imagesList);
	}
	
	public List<ImagePath> getImagesList() {
		return imagesList;
	}
	
	public void put(ImageFileList fileList) {
		imagesList.addAll(fileList.getImagesList());
	}
	
	public int getSize() {
		return imagesList.size();
	}
	
	public ImagePath nextImagePath() {
		if(imagesList.isEmpty()) return null;
		return imagesList.get(ThreadLocalRandom.current().nextInt(imagesList.size()));
	}

	@Override
	public Iterator<ImagePath> iterator() {		
		return imagesList.iterator();
	}
	
}
