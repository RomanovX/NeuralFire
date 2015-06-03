package neuralfire.core;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicBoolean;

public class ResultPrinter {
	
	private static AtomicBoolean writerInUse;

	private static PrintWriter writer;
	
	ResultPrinter(){
		if(writerInUse == null){
			writerInUse = new AtomicBoolean(false);
		}
		
		if(writer == null){
			try {
				writer = new PrintWriter("SimResults-"+System.currentTimeMillis()+".txt", "UTF-8");
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
			result = true;
			writerInUse.set(false);
		}
		return result;
	}
	
	public boolean getWriterInUse(){
		return writerInUse.get();
	}
}
