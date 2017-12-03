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

import blindpew123.cloudscreensaver.settings.SettingsFile;

class LocalFileSystemImageFileListReader extends ImageFileListReader{
	
	private String startPath;
	
	public LocalFileSystemImageFileListReader(String path) {
		startPath = path;
	}
	
	public LocalFileSystemImageFileListReader(String path, ImageFileList imageList) {
		super(imageList);
		startPath = path;
	}
	
	@Override
	public ImageFileList readList() {		
		List<String> result = new ArrayList<>();
		
		if(startPath != null) {		
			try {
				Files.walkFileTree(Paths.get(startPath), new SimpleFileVisitor<Path>() {
		            @Override
		            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		
		                String fileName = file.toString();
		            	if (isFormatSupported(fileName)) {result.add(fileName);}
		                return FileVisitResult.CONTINUE;
		            }
		
		            @Override
		            public FileVisitResult visitFileFailed(Path file, IOException exp) {
		                return FileVisitResult.CONTINUE;
		            }
		        });
			}catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		return new ImageFileList(result);
	}

	@Override
	public void readListTo() {			
		if(startPath != null) {
			
			List<String> result = new ArrayList<>();
			
			try {
				Files.walkFileTree(Paths.get(startPath), new SimpleFileVisitor<Path>() {
		            @Override
		            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		
		                String fileName = file.toString();
		            	if (isFormatSupported(fileName)) {
		                	result.add(fileName);
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
				throw new RuntimeException(e);
			}
			imageList.getImagesList().addAll(result);	
		}
		
	}
	

}
