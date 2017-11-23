package blindpew123.cloudscreensaver.display.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;

import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifReader;

interface MetaReader {
	
	default Properties readExif(InputStream stream){
		Iterable<JpegSegmentMetadataReader> readers = Arrays.asList(new ExifReader());
		try {
            Metadata metadata = JpegMetadataReader.readMetadata(stream, readers);
            return buildInfo(metadata);
        } catch (ImageProcessingException | IOException e) {} // swallow - no problem just EXIF data will be absent        
		
		return null;		
	}	
	
	private Properties buildInfo(Metadata metadata){
		Properties info = new Properties();
		for (Directory directory : metadata.getDirectories()) { 
			for (Tag tag :directory.getTags()) {
	             info.put(tag.getTagName(), tag.getDescription());
	        }
	     }
		return info;
	}
}
