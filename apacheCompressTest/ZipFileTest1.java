package apacheCompressTest;

import java.io.*;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;

public class ZipFileTest1 {  
        public static void main(String[] args) throws Exception{
                /* Create Output Stream that will have final zip files */
                OutputStream zip_output = new FileOutputStream(new File("zip_output.zip"));
                
                /* Create Archive Output Stream that attaches File Output Stream / and specifies type of compression */
                ArchiveOutputStream logical_zip = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, zip_output);
                
                /* Create Archieve entry - write header information*/
                logical_zip.putArchiveEntry(new ZipArchiveEntry("test_file_1.xml"));
                
                /* Copy input file */
                IOUtils.copy(new FileInputStream(new File("test_file_1.xml")), logical_zip);
                
                /* Close Archieve entry, write trailer information */
                logical_zip.closeArchiveEntry();
                
                /* Repeat steps for file - 2 */
                logical_zip.putArchiveEntry(new ZipArchiveEntry("test_file_2.xml"));
                IOUtils.copy(new FileInputStream(new File("test_file_2.xml")), logical_zip);
                logical_zip.closeArchiveEntry();
                
                /* Finish addition of entries to the file */
                logical_zip.finish(); 
                
                /* Close output stream, our files are zipped */
                zip_output.close();
        }
}