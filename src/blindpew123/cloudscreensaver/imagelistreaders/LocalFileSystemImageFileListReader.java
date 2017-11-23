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
import java.util.function.Predicate;

import javax.imageio.ImageIO;

class LocalFileSystemImageFileListReader extends ImageFileListReader{
	
	private String startPath;
	
	public LocalFileSystemImageFileListReader(String path) {
		startPath = path;
	}
	
	@Override
	public ImageFileList readList() {
		
		String names[] = ImageIO.getReaderFormatNames();
		
		
		List<String> result = new ArrayList<String>();
		
		if(startPath != null) {		
			try {
				Files.walkFileTree(Paths.get(startPath), new SimpleFileVisitor<Path>() {
		            @Override
		            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		
		                String fileName = file.toString();
		            	if (isFormatSupported(fileName)) {
		                	result.add(fileName);
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
		}
		return new ImageFileList(result);
	}
	

}
