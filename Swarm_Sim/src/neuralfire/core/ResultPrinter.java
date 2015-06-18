package neuralfire.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResultPrinter {
	
	private static AtomicBoolean writerInUse;

	private static PrintWriter writer;
	
	public static String filenamePrefix = "";
	public static String filename;
	
	ResultPrinter(){
		if(writerInUse == null){
			writerInUse = new AtomicBoolean(false);
		}
		
		if(writer == null){
			try {
				filename = filenamePrefix + "SimResults-"+System.currentTimeMillis()+".txt"; 
				writer = new PrintWriter(filename, "UTF-8");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean WriteLine(String str){
		boolean result = false;
		if(writerInUse.compareAndSet(false, true)){
			writer.write(str);
			writer.flush();
			result = true;
			writerInUse.set(false);
		}
		return result;
	}
	
	public boolean getWriterInUse(){
		return writerInUse.get();
	}
	
	public static boolean finishFile(){
		File file = new File(filename);

		String finFilename  = "COM"+filename.substring(3, filename.length());
	    File file2 = new File(finFilename);

	    return file.renameTo(file2);
	}
}
