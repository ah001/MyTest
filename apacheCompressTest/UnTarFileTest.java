package apacheCompressTest;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

public class UnTarFileTest {
 final static int BUFFER = 2048;

 /**
  * Command line arguments :
  * argv[0]-----> Source tar.gz file.
  * argv[1]-----> DestarInation directory.
  **/
 public static void main(String[] args) throws IOException {

  /** create a TarArchiveInputStream object. **/

  FileInputStream fin = new FileInputStream(args[0]);
  BufferedInputStream in = new BufferedInputStream(fin);
  GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
  TarArchiveInputStream tarIn = new TarArchiveInputStream(gzIn);

  TarArchiveEntry entry = null;
  
  File destDir = new File(args[1]);
  if (!destDir.exists()) {
	    System.out.println("Input destination directory does not exist..");
	    System.exit(0);
  }

  /** Read the tar entries using the getNextEntry method **/

  while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) 
  {

	   System.out.println("Extracting: " + entry.getName());
	
	   /** If the entry is a directory, create the directory. **/
	
	   //if (entry.isDirectory()) {
	   File f = new File(destDir.getPath() +"\\"+ entry.getName());
	   if (!f.exists())
	   {
	    //File f = new File(destDir.getPath() + entry.getName());
	    f.getParentFile().mkdirs();
	   }
	   /**
	    * If the entry is a file,write the decompressed file to the disk
	    * and close destination stream.
	    **/
	    int count;
	    byte data[] = new byte[BUFFER];
	    
	    //System.out.println("args[1]:"+args[1]);
	    //System.out.println("destDir.getParent():"+destDir.getPath());
	    FileOutputStream fos = new FileOutputStream(destDir.getPath() +"\\"+ entry.getName());
	    
	    BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
	    while ((count = tarIn.read(data, 0, BUFFER)) != -1) 
	    {
	     dest.write(data, 0, count);
	    }
	    dest.close();
   
  }

  /** Close the input stream **/

  tarIn.close();
  System.out.println("untar completed successfully!!");
 }

}
