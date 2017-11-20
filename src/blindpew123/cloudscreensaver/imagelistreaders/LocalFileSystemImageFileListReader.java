package blindpew123.cloudscreensaver.imagelistreaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

class LocalFileSystemImageFileListReader extends ImageFileListReader{
	
	private String startPath;
	
	public LocalFileSystemImageFileListReader(String path) {
		startPath = path;
	}
	
	@Override
	public ImageFileList readList() {
		
		String names[] = ImageIO.getReaderFormatNames();
		
		Predicate<String> isFormatSupported = (s)->{
			for (String ext:names) {
				if (s.endsWith(ext)) return true;
			}
			return false;
		};
		
		List<String> result;
		try {
			BiPredicate<Path, BasicFileAttributes> filter = (pth, attr)->{
				return true;
			};		
			 result = Files.find(Paths.get(startPath), Integer.MAX_VALUE, filter)
				.parallel()
				.map(p->p.toString())
				.filter(isFormatSupported)
				.collect(Collectors.toList());			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return new ImageFileList(result);
	}
	

}
