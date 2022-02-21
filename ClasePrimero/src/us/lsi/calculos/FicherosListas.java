package us.lsi.calculos;

import java.util.List;
import java.util.stream.Stream;

import us.lsi.tools.FileTools;

public class FicherosListas {
	
	public static void ejemplo1() {
		List<String> ls = FileTools.lineasFromFile("ficheros/numeros.txt");
		Integer s = 0;
		for(String linea:ls) {
			Integer e = Integer.parseInt(linea);
			s += e;
		}
		System.out.println(s);
	}
	
	public static void ejemplo2() {
		Stream<String> ls = FileTools.streamFromFile("ficheros/numeros.txt");
		Integer s = ls.mapToInt(ln->Integer.parseInt(ln)).sum();
		System.out.println(s);
	}

	public static void main(String[] args) {
		ejemplo1();
		ejemplo2();
	}

}
