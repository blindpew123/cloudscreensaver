package blindpew123.cloudscreensaver.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifReader;

interface MetaReader {
	
	default Map<String, String> readExif(InputStream stream){
		Iterable<JpegSegmentMetadataReader> readers = Arrays.asList(new ExifReader());
		try {
            Metadata metadata = JpegMetadataReader.readMetadata(stream, readers);
            return buildInfo(metadata);
        } catch (ImageProcessingException | IOException e) {} // swallow - just EXIF data will be absent        
		
		return null;		
	}	
	
	private Map<String, String> buildInfo(Metadata metadata){
		Map<String, String> info = new HashMap<>();
		for (Directory directory : metadata.getDirectories()) { 
			for (Tag tag :directory.getTags()) {
	             System.out.println(tag); //TODO: Remove
	             info.put(tag.getTagName(), tag.getDescription());
	        }
	     }
		return info;
	}

	/*		switch(tag.getTagName()) {
	             case "Date/Time Original" :
	             case "Exposure Time" :
	             case "F-Number" :
	             case "ISO Speed Ratings" :
	             case "Orientation" :
	             case "Make" :
	             case "Model" :
	             case "Focal Length" :
	             case "Lens Specification" :
	         }
	             */
	
}
