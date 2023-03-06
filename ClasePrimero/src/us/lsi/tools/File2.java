package us.lsi.tools;

import java.io.BufferedInputStream;
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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.mozilla.universalchardet.UniversalDetector;


public class File2 {
	
	public static String getFileCharset(String file) {
		try {
			byte[] buf = new byte[4096];
			final UniversalDetector universalDetector;
			try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file))) {
				universalDetector = new UniversalDetector(null);
				int numberOfBytesRead;
				while ((numberOfBytesRead = bufferedInputStream.read(buf)) > 0 && !universalDetector.isDone()) {
					universalDetector.handleData(buf, 0, numberOfBytesRead);
				}
			}
			universalDetector.dataEnd();
			String encoding = universalDetector.getDetectedCharset();

			universalDetector.reset();

			return encoding;
		} catch (IOException e) {
			throw new IllegalArgumentException("No se ha encontrado el fichero " + file);
		}
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
	
	public static Stream<List<String>> streamDeCsv(String file) {
		return streamDeCsv(file,CSVFormat.RFC4180);
	}
	
	/**
	 * Más información sobre la lectura de ficheros CSV en https://commons.apache.org/proper/commons-csv/index.html
	 * @param file Fichero de entrada
	 * @param format Formato del fichero
	 * @return Un Stream donde cada elemento es una lista de campos
	 */
	public static Stream<List<String>> streamDeCsv(String file,CSVFormat format) {
		CSVParser parser=null;
		try {
			BufferedReader csvData = new BufferedReader(new FileReader(file));
			parser = CSVParser.parse(csvData, format);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return Stream2.of(parser.iterator()).map(r->r.toList());
	}
	
	public static List<List<String>> lineasDeCsv(String file) {
		return lineasDeCsv(file,CSVFormat.RFC4180);
	}
	
	public static List<List<String>> lineasDeCsv(String file, CSVFormat format) {
		CSVParser parser=null;
		try {
			BufferedReader csvData = new BufferedReader(new FileReader(file));
			parser = CSVParser.parse(csvData, CSVFormat.RFC4180);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return parser.getRecords().stream().map(r->r.toList()).toList();
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
		String s = File2.getFileCharset("ficheros_aeropuertos/aeropuertos.csv");
		System.out.println(s);
	}

}
