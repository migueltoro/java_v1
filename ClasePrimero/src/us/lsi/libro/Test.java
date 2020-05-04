package us.lsi.libro;

import java.util.stream.Stream;

import us.lsi.tools.Enumerate;
import us.lsi.tools.FileTools;
import us.lsi.tools.StreamTools;

public class Test {

	public static void main(String[] args) {
		Stream<String> lineas = FileTools.streamFromFile("ficheros/quijote.txt");
		Stream<Enumerate<String>> enumerate = StreamTools.enumerate(lineas).limit(15);
		enumerate.forEach(e->System.out.println(e));
	}

}
