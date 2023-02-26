package us.lsi.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.poker.Card;

public class Graphics {

	/**
	 * Muestra una grafica representando los datos proporcionados frente al primero
	 * @pre Las listas de datos tienen todas la misma longitud
	 * @pre Las listas de campos tiene de longitud el n�mero de listas de datos 
	 * @param fileOut Ficheo de salida
	 * @param title Titulo 
	 * @param campos Campos 
	 * @param datos Datos
	 */
	public static <E extends Number>void lineChart(String fileOut, String title, List<String> campos, @SuppressWarnings("unchecked") List<E>... datos) {
		
		String result = File2.text("resources/LineChartPattern.html");
		String camposText = campos.stream().map(x->"'"+x+"'").collect(Collectors.joining(",","[","]"));
		String dataText = IntStream.range(0,datos[0].size())
									.boxed()
									.map(e->Graphics.filaLineChart(e,datos))
									.collect(Collectors.joining(",\n","","\n"));
		Map<String,String> reglas = new HashMap<>();
		reglas.put("title","'"+title+"'");
		reglas.put("campos",camposText);
		reglas.put("data",dataText);
		result = String2.transform(result,reglas);
		File2.write(fileOut,result);
	}

	private static <E extends Number> String filaLineChart(Integer e, @SuppressWarnings("unchecked") List<E>... datos) {
		return IntStream.range(0,datos.length).boxed()
				.map(i->datos[i].get(e).toString()).collect(Collectors.joining(",","[","]"));
	}

	/**
	 * 
	 * Muestra un diagrama de tarta
	 * @pre La lista de nombres tiene la misma longitud que la de de datos
	 * @pre la lista de campos tiene longitud dos
	 * @param fileOut Fichero de salida
	 * @param title Titulo 
	 * @param campos Campos 
	 * @param nombres Nombres de datos
	 * @param datos Datos
	 */
	public static <E extends Number> void pieChart(String fileOut, String title, List<String> campos, List<String> nombres, List<E> datos) {
		String result = File2.text("resources/PieChartPattern.html");
		String camposText = campos.stream().map(x->"'"+x+"'").collect(Collectors.joining(",","[","]"));
		String dataText = IntStream.range(0,datos.size())
									.boxed()
									.map(e->Graphics.filaPieChart(e,nombres,datos))
									.collect(Collectors.joining(",\n","","\n"));
		Map<String,String> reglas = new HashMap<>();
		reglas.put("title","'"+title+"'");
		reglas.put("campos",camposText);
		reglas.put("data",dataText);
		result = String2.transform(result,reglas);
		File2.write(fileOut,result);
	}

	private static <E extends Number> String filaPieChart(Integer e, List<String> nombres, List<E> datos) {
		return String.format("['%s',%s]",nombres.get(e),datos.get(e).toString());
	}
	
	/**
	 * 
	 * Muestra un diagrama columnas de barras
	 * @pre La lista de nombresDatos tiene tiene longitud dos
	 * @param fileOut Fichero de salida
	 * @param title Titulo 
	 * @param nombresDatos Nombres de los datos 
	 * @param nombresColumnas Nombres de las columnas 
	 * @param datos Datos
	 */
	public static <E extends Number> void columnsBarChart(String fileOut, String title, List<String> nombresDatos, List<String> nombresColumna, List<E> datos) {
		String result = File2.text("resources/ColumnsBarPattern.html");
		String nombresDatosText = "["+"'"+title+"' ,"+nombresDatos.stream().map(x->"'"+x+"'").collect(Collectors.joining(",","","]"));
		String columnasText = IntStream.range(0,datos.size())
									.boxed()
									.map(e->Graphics.columnaBarChart(e,nombresColumna,datos))
									.collect(Collectors.joining(",\n","","\n"));
		Map<String,String> reglas = new HashMap<>();
		reglas.put("title","'"+title+"'");
		reglas.put("nombresDatos",nombresDatosText);
		reglas.put("columnas",columnasText);
		result = String2.transform(result,reglas);
		File2.write(fileOut,result);
	}

	private static <E extends Number> String columnaBarChart(Integer e, List<String> nombresColumna, List<E> datos) {
		return String.format("['%s',%s]",nombresColumna.get(e),datos.get(e).toString());
	}
	
	public static void cartas(String fileOut, List<Card> cartas, Double fuerza, String tipo) {
		String result = File2.text("resources/CartasPattern.html");
		String cartasText = cartas.stream()
				.map(c->String.format("<img src=\"../%s\" width=\"120px\" height=\"180px\">",c.nameFile()))
				.collect(Collectors.joining("\n","\n","\n"));
		Map<String,String> reglas = new HashMap<>();
		reglas.put("cartas",cartasText);
		reglas.put("fuerza",String.format("%.3f", fuerza));
		reglas.put("tipo",tipo);
		result = String2.transform(result,reglas);
		File2.write(fileOut,result);
	}

}

