package blindpew123.cloudscreensaver.imagelistreaders;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import blindpew123.cloudscreensaver.display.image.ImageReadingException;
import blindpew123.cloudscreensaver.imagepath.ImagePath;
import blindpew123.cloudscreensaver.settings.SettingsFile;

class LocalFileSystemImageFileListReader extends ImageFileListReader{
	

	public LocalFileSystemImageFileListReader(ImagePath path, ImageFileList imageList) {
		super(path, imageList);		
	}
	
	@Override
	public ImageFileList readList() {		
		List<ImagePath> result = new ArrayList<>();
		
		if(startPath != null) {		
			try {
				Files.walkFileTree(Paths.get(startPath.getPath()), new SimpleFileVisitor<Path>() {
		            @Override
		            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		
		                ImagePath foundPath = new ImagePath(file.toString(), false);
		            	if (isFormatSupported(foundPath)) {result.add(foundPath);}
		                return FileVisitResult.CONTINUE;
		            }
		
		            @Override
		            public FileVisitResult visitFileFailed(Path file, IOException exp) {
		                return FileVisitResult.CONTINUE;
		            }
		        });
			}catch (IOException e) {
				throw new ImageReadingException(e.getMessage(), startPath);
			}
		}
		return new ImageFileList(result);
	}

	@Override
	public void readListTo() {			
		if(startPath != null) {
			
			List<ImagePath> result = new ArrayList<>();
			
			try {
				Files.walkFileTree(Paths.get(startPath.getPath()), new SimpleFileVisitor<Path>() {
		            @Override
		            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		
		            	ImagePath foundPath = new ImagePath(file.toString(), false);
		            	if (isFormatSupported(foundPath)) {
		                	result.add(foundPath);
		                	if (!isMinRecordsCopied && result.size() >= (int)SettingsFile.getInstance().getSettingsValue("minNumPathsForQuickstart")) {
		                		imageList.getImagesList().addAll(result);
		                		result.clear();
		                		isMinRecordsCopied = true;
		                	}
		                }
		                return FileVisitResult.CONTINUE;
		            }
		
		            @Override
		            public FileVisitResult visitFileFailed(Path file, IOException exp) {
		                return FileVisitResult.CONTINUE;
		            }
		        });
			}catch (IOException e) {
				throw new ImageReadingException(e.getMessage(), startPath);
			}
			imageList.getImagesList().addAll(result);	
		}
		
	}
	

}
