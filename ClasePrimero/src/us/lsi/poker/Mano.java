package us.lsi.poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Mano implements Comparable<Mano>{
	
	public static Integer numeroDeCartas = 5;
	
	public enum Jugada {EscaleraReal,EscaleraDeColor,Poker,Full,Color,Escalera,Trio,DoblePareja,Pareja,CartaAlta}
	
	private static Random rnd   = new Random(System.nanoTime()); 
	
	private List<Card> cartas;
	public static Mano of(List<Card> cartas) {
		return new Mano(cartas);
	}
	
	public static Mano parse(String txt) {
		txt = txt.substring(1,txt.length()-1);
		String[] partes = txt.split(",");		
		List<Card> cartas = Arrays.stream(partes).map(x->Card.parse(x)).collect(Collectors.toList());
		return Mano.of(cartas);
	}
	
	public static Mano random() {
		List<Card> cartas = new ArrayList<>();
		for(int i = 0;i<numeroDeCartas;i++) {
			Integer n = Mano.rnd.nextInt(52);
			Card card = Card.of(n);
			cartas.add(card);
		}		
		return new Mano(cartas);
	}

	private Map<Integer,Long> frecuenciasDeValores = null;
	private List<Integer> valoresOrdenadosPorFrecuencias = null;
	private Boolean son5ValoresConsecutivos = null;
	private Map<Integer,Long> frecuenciasDePalos=null;
	private List<Integer> palosOrdenadosPorFrecuencias= null;
	private Jugada jugada = null;

	private Mano(List<Card> cartas) {
		super();
		this.cartas = cartas;
	}

	public List<Card> getCartas() {
		return cartas;
	}

	public Boolean son5ValoresConsecutivos() {
		Boolean son5 = this.valoresOrdenadosPorFrecuencias().size() == 5;
		if(son5 && this.son5ValoresConsecutivos ==null) {
			List<Integer> ls = cartas.stream().map(c->c.valor()).sorted().collect(Collectors.toList());
			this.son5ValoresConsecutivos=IntStream.range(0,ls.size()-1).allMatch(x->ls.get(x+1)-ls.get(x)==1);
		}
		return son5 && this.son5ValoresConsecutivos;
	}
	
	public Map<Integer,Long> frecuenciasDeValores(){
		if(this.frecuenciasDeValores == null) {
			 this.frecuenciasDeValores = cartas.stream().collect(Collectors.groupingBy(c->c.valor(),Collectors.counting()));
		}
		return this.frecuenciasDeValores;
	}
	
	public List<Integer> valoresOrdenadosPorFrecuencias(){
		if(this.valoresOrdenadosPorFrecuencias == null) {
			this.valoresOrdenadosPorFrecuencias = this.frecuenciasDeValores().entrySet().stream()
				.sorted(Comparator.<Entry<Integer,Long>,Long>comparing(Entry::getValue).reversed())
				.map(Entry::getKey)
				.collect(Collectors.toList());	
		}
		return this.valoresOrdenadosPorFrecuencias;
	}
	
	public Map<Integer,Long> frecuenciasDePalos(){
		if(this.frecuenciasDePalos == null) {
			this.frecuenciasDePalos = cartas.stream().collect(Collectors.groupingBy(c->c.palo(),Collectors.counting()));
		}
		return this.frecuenciasDePalos;
	}
	
	
	public List<Integer> palosOrdenadosPorFrecuencias(){
		if(this.palosOrdenadosPorFrecuencias == null) {
			this.palosOrdenadosPorFrecuencias = this.frecuenciasDePalos().entrySet().stream()
				.sorted(Comparator.<Entry<Integer,Long>,Long>comparing(Entry::getValue).reversed())
				.map(Entry::getKey)
				.collect(Collectors.toList());	
		}
		return this.palosOrdenadosPorFrecuencias;
	}
	
	public Boolean esColor() {
		Integer pal0 = this.palosOrdenadosPorFrecuencias().get(0);
		return frecuenciasDePalos().get(pal0).equals(5L);
	}
	
	public Boolean esEscalera() {
		return this.son5ValoresConsecutivos();
	}
	
	public Boolean esPoker() {
		Integer val0 = this.valoresOrdenadosPorFrecuencias.get(0);
		return this.frecuenciasDeValores().get(val0).equals(4L);
	}
	
	public Boolean esEscaleraDeColor() {
		Integer pal0 = this.palosOrdenadosPorFrecuencias().get(0);
		return this.son5ValoresConsecutivos() && this.frecuenciasDePalos().get(pal0).equals(5L);
	}
	
	public Boolean esFull() {
		if(this.valoresOrdenadosPorFrecuencias().size() != 2) return false;
		Integer val0 = this.valoresOrdenadosPorFrecuencias().get(0);
		Integer val1 = this.valoresOrdenadosPorFrecuencias().get(1);
		return this.frecuenciasDeValores().get(val0).equals(3L) &&
			this.frecuenciasDeValores().get(val1).equals(2L);
	}
	public Boolean esTrio() {
		Integer val0 = this.valoresOrdenadosPorFrecuencias().get(0);
		return this.frecuenciasDeValores().get(val0).equals(3L);
	}
	
	public Boolean esDoblePareja() {
		if(this.valoresOrdenadosPorFrecuencias().size() < 2) return false;
		Integer val0 = this.valoresOrdenadosPorFrecuencias().get(0);
		Integer val1 = this.valoresOrdenadosPorFrecuencias().get(1);
		return this.frecuenciasDeValores().get(val0).equals(2L) &&
			this.frecuenciasDeValores().get(val1).equals(2L);
	}
	
	public Boolean esPareja() {
		Integer val0 = this.valoresOrdenadosPorFrecuencias().get(0);
		return this.frecuenciasDeValores().get(val0).equals(2L);
	}
	public Boolean esEscaleraReal() {
		return this.esEscaleraDeColor() &&
				this.cartas.stream().map(x->x.valor()).collect(Collectors.toSet()).contains(12);
	}
	public Boolean esCartaMasAlta() {
		return true;
	}
	
	private static List<Predicate<Mano>> jugadas = Arrays.asList(
			Mano::esEscaleraReal, 
			Mano::esEscaleraDeColor,
			Mano::esPoker, 
			Mano::esFull, 
			Mano::esColor, 
			Mano::esEscalera, 
			Mano::esTrio,
			Mano::esDoblePareja, 
			Mano::esPareja,
			Mano::esCartaMasAlta);
						
	
	public Jugada getJugada() {
		if(this.jugada==null) {
			Integer r = IntStream.range(0, jugadas.size())
				.filter(i->jugadas.get(i).test(this))
				.findFirst()
				.getAsInt();
			this.jugada = Jugada.values()[r];
		}
		return this.jugada;
	}
	
	public static Double fuerza(Mano mano, Integer n){
		Integer gana = 0;
		Integer pierde = 0;
		Integer empata = 0;
		for(int i=0;i<n;i++) {
			Mano mr = Mano.random();
			if(mano.compareTo(mr)<0) pierde++;
			else if(mano.compareTo(mr)>0) gana++;
			else empata++;
		}
		return ((double)gana)/(gana+pierde+empata);
	}
	
	public Double fuerza(Integer n){
		return fuerza(this,n);
	}

	@Override
	public int compareTo(Mano mano) {
		Integer r = -this.getJugada().compareTo(mano.getJugada());
		if (r == 0)
			r = this.valoresOrdenadosPorFrecuencias().get(0).compareTo(valoresOrdenadosPorFrecuencias().get(0));
		return r;
	}

	@Override
	public String toString() {
		return this.cartas.stream().map(c->c.toString()).collect(Collectors.joining(",","(", ")"));
	}

}
