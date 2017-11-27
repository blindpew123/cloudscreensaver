package blindpew123.cloudscreensaver.imagelistreaders;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;

// TODO Random Element getter

public class ImageFileList {
	
	
	private List<String> imagesList;

	ImageFileList(){
		imagesList = new CopyOnWriteArrayList<>();
	}
	
	ImageFileList(Collection<String> imagesList){
		this.imagesList = new CopyOnWriteArrayList<>(imagesList);
	}
	
	public List<String> getImagesList() {
		return imagesList;
	}
	
	public void put(ImageFileList fileList) {
		imagesList.addAll(fileList.getImagesList());
	}
	
	public int getSize() {
		return imagesList.size();
	}
	
	public String nextImagePath() {
		if(imagesList.size() == 0) return null;
		return imagesList.get(ThreadLocalRandom.current().nextInt(imagesList.size()));
	}
	
}
