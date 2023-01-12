package us.lsi.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mozilla.universalchardet.UniversalDetector;


public class FileTools {
	
	public static String encoding(String file) {  
	
	    String encoding;  
	  
	    try {  
	    	
	        final FileInputStream fis = new FileInputStream(file);  
	  
	        final UniversalDetector detector = new UniversalDetector(null);  
	        handleData(fis, detector);  
	        encoding = getEncoding(detector);  
	        detector.reset();  
	          
	        fis.close();  
	  
	    } catch (IOException e) {  
	        encoding = "";  
	    }  
	  
	    return encoding;  
	}  
	  
	private static String getEncoding(UniversalDetector detector) {  
	    if(detector.isDone()) {  
	        return detector.getDetectedCharset();  
	    }  
	    return "";  
	}  
	  
	private static void handleData(FileInputStream fis, UniversalDetector detector) throws IOException {  
	    int nread;  
	    final byte[] buf = new byte[4096];  
	    while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {  
	        detector.handleData(buf, 0, nread);  
	    }  
	    detector.dataEnd();  
	} 
	
	public static Stream<String> streamDeFichero(String file) {
		Charset charSet = Charset.defaultCharset();
		return streamDeFichero(file, charSet.toString());
	}
	
	public static Stream<String> streamDeFichero(String file, String charSet) {
		Stream<String> r = null;
		try {
			r = Files.lines(Paths.get(file), Charset.forName(charSet));
		} catch (IOException e) {
			throw new IllegalArgumentException("No se ha encontrado el fichero " + file);
		}
		return r;
	}
	
	public static List<String> lineasDeFichero(String file) {
		Charset charSet = Charset.defaultCharset();
		return lineasDeFichero(file, charSet.toString());
	}
	
	public static List<String> lineasDeFichero(String file, String charSet) {
		List<String> lineas = null;
		try {		
			lineas = Files.readAllLines(Paths.get(file), Charset.forName(charSet));
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return lineas;
	}
	
	public static void writeStream(Stream<String> s, String file) {
		Iterable<String> it = ()->s.iterator();
		try {
			Files.write(Paths.get(file),it);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	}
	
	public static void write(String file, String text){
		try {
			final PrintWriter f =  new PrintWriter(new BufferedWriter(new FileWriter(file)));
			f.println(text);
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException("No se ha podido crear el fichero " + file);
		}
	}
	
	public static String text(String file){
		List<String> lineas = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			lineas = bufferedReader.lines().collect(Collectors.toList());
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return lineas.stream().collect(Collectors.joining("\n"));
	}

	public static void main(String[] args) {
		String s = FileTools.encoding("ficheros/peliculas.csv");
		System.out.println(s);
	}

}
