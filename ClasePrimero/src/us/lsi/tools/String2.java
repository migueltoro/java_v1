package us.lsi.tools;

import java.util.Map;
import java.util.regex.Pattern;

public class String2 {
	
	/**
	 * @param in String de entrada
	 * @param reglas Conjunto de reglas compuestas por un iodentificador y un texto asociado
	 * @return Devuelve le String de entrada donde se ha sustituido {nombre} por el texto asociado a nombre en las reglas
	 */
	public static String transform(String in, Map<String,String> reglas) {
		String out = in;
		Pattern pattern;
		for(String p:reglas.keySet()) {
			pattern = Pattern.compile("\\{"+p+"\\}");
			out = pattern.matcher(out).replaceAll(reglas.get(p));
		}
		return out;
	}
	
	
}

