package us.lsi.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.InputStreamReader;
import org.mozilla.universalchardet.UniversalDetector;


public class File2 {
	
	public static String root = "C:/Users/migueltoro/git/java_v1/ClasePrimero/";
	
	public static String root_project() {
		return new File("root/Empty.txt").getAbsolutePath().replace("root\\Empty.txt","");
	}
	
	public static String absolute_path(String file, String root_project) {
		return root_project+file;
	}
	/**
	 * Retrieves the character encoding of a given file.
	 *
	 * This method opens the specified file and uses an InputStreamReader
	 * to determine the character encoding.
	 *
	 * @param filePath the path to the file whose encoding is to be determined
	 * @return the character encoding of the file
	 * @throws IllegalArgumentException if the file is not found or cannot be read
	 */
	public static String getFileEncoding(String filePath) {
	    try (InputStreamReader reader = new InputStreamReader(new FileInputStream(filePath))) {
	        return reader.getEncoding();
	    } catch (IOException e) {
	        throw new IllegalArgumentException("File not found: " + filePath, e);
	    }
	}
	/**
	 * Detects the character encoding of a given file.
	 *
	 * This method uses the `UniversalDetector` from the Mozilla Universal Charset Detector
	 * library to determine the encoding of the file. It reads the file in chunks and processes
	 * the data until the encoding is detected or the end of the file is reached.
	 *
	 * @param fileName the path to the file whose encoding is to be detected
	 * @return the detected character encoding of the file, or null if the encoding could not be determined
	 * @throws IllegalArgumentException if the file is not found or cannot be read
	 */
	public static String detectEncoding(String fileName) {
	    byte[] buf = new byte[4096];
	    FileInputStream fis = null;
	    try {
	        fis = new FileInputStream(fileName);
	        UniversalDetector detector = new UniversalDetector(null);
	        int nread;
	        while ((nread = fis.read(buf)) > 0 && !detector.isDone()) {
	            detector.handleData(buf, 0, nread);
	        }
	        detector.dataEnd();
	        String encoding = detector.getDetectedCharset();
	        detector.reset();
	        return encoding;
	    } catch (IOException e) {
	        throw new IllegalArgumentException("File not found: " + fileName, e);
	    } finally {
	        if (fis != null) {
	            try {
	                fis.close();
	            } catch (IOException ignored) {
	            }
	        }
	    }
	}

	public static Stream<String> streamDeFichero(String file) {
		Charset charSet = Charset.defaultCharset();
		return streamDeFichero(file, charSet.toString());
	}
	/**
	 * Reads a file and returns its content as a stream of strings, where each string
	 * represents a line in the file. The file is read using the specified character set.
	 *
	 * @param file the path to the file to be read
	 * @param charSet the character set to use for decoding the file
	 * @return a stream of strings, where each string represents a line in the file
	 * @throws IllegalArgumentException if the file cannot be found or read
	 */
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
	
	/**
	 * Reads all lines from a file using the specified character set.
	 *
	 * @param file the path to the file to be read
	 * @param charSet the character set to use for decoding the file
	 * @return a list of strings, where each string represents a line in the file
	 * @throws IllegalArgumentException if the file cannot be read
	 */
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
		return streamDeCsv(file,",");
	}
	
	/**
	 * Más información sobre la lectura de ficheros CSV en https://commons.apache.org/proper/commons-csv/index.html
	 * @param file Fichero de entrada
	 * @param format Formato del fichero
	 * @return Un Stream donde cada elemento es una lista de campos
	 */
	public static Stream<List<String>> streamDeCsv(String file,String delimiter) {
		CSVParser parser=null;
		try {
			BufferedReader csvData = new BufferedReader(new FileReader(file));
			CSVFormat csvFormat = CSVFormat.Builder.create()
					.setSkipHeaderRecord(false)
					.setDelimiter(delimiter)
					.setTrim(true)
					.build();
			parser = csvFormat.parse(csvData);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return Stream2.of(parser.iterator()).map(r->r.toList());
	}
	
	public static List<List<String>> lineasDeCsv(String file) {
		return lineasDeCsv(file,",");
	}
	
	/**
	 * Reads a CSV file and returns its content as a list of lists of strings.
	 * Each inner list represents a row in the CSV file, and each string in the
	 * inner list represents a cell in that row.
	 *
	 * @param file the path to the CSV file
	 * @param delimiter the delimiter used to separate values in the CSV file
	 * @return a list of lists of strings, where each inner list represents a row
	 * @throws IOException if an I/O error occurs while reading the file
	 */
	public static List<List<String>> lineasDeCsv(String file, String delimiter) {
		CSVParser parser=null;
		try {
			BufferedReader csvData = new BufferedReader(new FileReader(file));
			CSVFormat csvFormat = CSVFormat.Builder.create()
					.setSkipHeaderRecord(false)
					.setDelimiter(delimiter)
					.setTrim(true)
					.build();
			parser = csvFormat.parse(csvData);
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return parser.getRecords().stream().map(r->r.toList()).toList();
	}
	
	public static Map<String,List<String>> mapDeCsv(String file) {
		return mapDeCsv(file,",");
	}
	
	/**
	 * Reads a CSV file and maps its content into a structure where each header
	 * corresponds to a list of values from the respective column.
	 *
	 * @param file the path to the CSV file
	 * @param delimiter the delimiter used to separate values in the CSV file
	 * @return a map where each key is a column header and the value is a list of strings
	 *         containing all the values under that header
	 * @throws IllegalArgumentException if the file is not found or an I/O error occurs
	 */
	public static Map<String,List<String>> mapDeCsv(String file,String delimiter) {
		Map<String,List<String>> rt = new HashMap<>();
		Iterable<CSVRecord> records = null;
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(file));
			CSVFormat csvFormat = CSVFormat.Builder.create()
					.setHeader()
					.setSkipHeaderRecord(true)
					.setDelimiter(delimiter)
					.setTrim(true)
					.build();
			records = csvFormat.parse(in);
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
	    for (CSVRecord rd : records) {
	        for(String name:rd.toMap().keySet()) {	        	
	        	if(rt.keySet().contains(name)) {
	        		rt.get(name).add(rd.get(name));
	        	} else {
	        		List<String> ls = new ArrayList<>();
	        		ls.add(rd.get(name));
	        		rt.put(name,ls);	
	        	}
	        }    
	    }	    
	    return rt;
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
		String s = File2.getFileEncoding("resources/cartasPattern.html");
		System.out.println(s);
		s = File2.detectEncoding("resources/cartasPattern.html");
		System.out.println(s);
		System.out.println(File2.root_project());
		System.out.println(File2.mapDeCsv("datos/pp.csv"));
		System.out.println(File2.lineasDeCsv("datos/pp.csv"));
		System.out.println(File2.streamDeCsv("datos/pp.csv").toList());
	}

}
