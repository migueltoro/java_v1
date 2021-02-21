package us.lsi.poker;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.poker.Mano.Jugada;
import us.lsi.tools.Graphics;

public class TestPoker {
	
	public static Map<Jugada, Long> random(Integer n){
		Map<Jugada,Long> m = IntStream.range(0,n)
				   .boxed()
				   .map(x->Mano.random())
				   .collect(Collectors.groupingBy(x->x.getJugada(),Collectors.counting()));
		return m;
	}

	public static void main(String[] args) {
		Mano m = Mano.random();
		Double fuerza = Mano.fuerza(m, 5000);
		Graphics.cartas("ficheros/CartasOut.html",m.getCartas(),fuerza,m.getJugada().toString());
		Map<Jugada,Long> tj = TestPoker.random(5000);
		System.out.println(tj.entrySet().stream()
				.sorted(Comparator.comparing(x->x.getValue()))
				.map(x->String.format("(%s,%d)",x.getKey(),x.getValue()))
				.collect(Collectors.joining("\n")));
		List<String> campos = Arrays.asList("Tipo de Jugada","Numero");
		List<String> nombres = tj.keySet().stream().map(x->x.toString()).collect(Collectors.toList());
		List<Long> datos = tj.keySet().stream().map(x->tj.get(x)).collect(Collectors.toList());
		Graphics.pieChart("ficheros/PieChartOut.html","Tipos de jugadas",campos, nombres, datos);
	}

	

}
