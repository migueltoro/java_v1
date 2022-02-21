package us.lsi.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class FileTools {
	
	public static Stream<String> streamFromFile(String file) {
		Stream<String> r = null;
		try {
			r = Files.lines(Paths.get(file), Charset.defaultCharset());
		} catch (IOException e) {
			throw new IllegalArgumentException("No se ha encontrado el fichero " + file);
		}
		return r;
	}
	
	public static List<String> lineasFromFile(String file) {
		List<String> lineas = null;
		try {		
			lineas = Files.readAllLines(Paths.get(file), Charset.defaultCharset());
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



}
