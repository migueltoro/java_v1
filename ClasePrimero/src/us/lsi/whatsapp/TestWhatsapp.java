package us.lsi.whatsapp;

import java.util.Comparator;
import java.util.Map;

public class TestWhatsapp {

	public static void main(String[] args) {
		Conversacion m = Conversacion.ofFile("resources/bigbangtheory_es.txt");
//		System.out.println(m);
		Comparator<Map.Entry<PalabraUsuario,Integer>> cmp = Comparator.comparing(e->e.getKey().usuario());
		var cmp2 = cmp.thenComparing(Comparator.comparing(e->e.getValue()));
		@SuppressWarnings("unused")
		var cmp3 = cmp2.reversed();
//		CollectionTools.toConsole(
//				m.getFrecuenciasDePalabrasPorRestoDeUsuarios().entrySet()
//				.stream()
//				.filter(e->e.getValue()>2)
//				.sorted(cmp3).limit(5),"\n");
//		CollectionTools.toConsole(m.palabrasCaracteristicasDeUsuario("Sheldon",2).entrySet()
//				.stream()
//				.sorted(Comparator.comparing((Map.Entry<String,Double> x)->x.getValue()).reversed())
//				.limit(15),"\n");
		
		m.drawNumeroDeMensajesPorDiaDeSemana("ficheros/Mensajes.html");
	}
	

}
