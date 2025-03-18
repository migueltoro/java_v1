package us.lsi.ruta;

import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.*;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.ruta.Intervalo.Type;
import us.lsi.tools.File2;

public class RutaF extends RutaA implements Ruta {
	
	public static Ruta of(List<Marca> marcas) {		
		return new RutaF(marcas);
	}
	
	private RutaF(List<Marca> marcas) {
		super(marcas);
	}

	public static Ruta of(String fichero) {
		List<Marca> marcas = File2.streamDeFichero(fichero).map(x->Marca.parse(x)).collect(toList());
		return new RutaF(marcas);
	}
	
	public static Ruta ofCsv(String fichero) {
		List<Marca> marcas = File2.streamDeCsv("ficheros/ruta.csv").map(x->Marca.parseCsv(x)).collect(toList());
		return of(marcas);
	}

	@Override
	public Double tiempo() {
		Integer n = super.marcas.size();
		return IntStream.range(0,n-1).boxed().map(i->this.intervalo(i)).mapToDouble(x->x.tiempo()).sum();
	}
	
	@Override
	public Double longitud() {
		Integer n = super.marcas.size();
		return IntStream.range(0,n-1).boxed().map(i->this.intervalo(i)).mapToDouble(x->x.longitud()).sum();
	}
	
	
	@Override
	public Double desnivelCrecienteAcumulado() {
		Integer n = super.marcas.size();		
		return IntStream.range(0, n-1)
				.boxed()
				.map(i->this.intervalo(i))
				.filter(e->e.desnivel()>0)
				.mapToDouble(e->e.longitud())
				.sum();
	}
	
	
	@Override
	public Double desnivelDecrecienteAcumulado() {
		Integer n = super.marcas.size();		
		return IntStream.range(0, n-1)
				.boxed()
				.map(i->this.intervalo(i))
				.filter(e->e.desnivel()<0)
				.mapToDouble(e->e.longitud())
				.sum();
	}
	
	@Override
	public String toString() {
		return super.marcas.stream().map(m->m.toString()).collect(joining("\n"));
	}

	@Override
	public Map<Type,Integer> frecuencias() {
		return IntStream.range(0, super.numMarcas() - 1).boxed()
				.map(i -> super.intervalo(i))
				.collect(groupingBy(Intervalo::type, Collectors.summingInt(x -> 1)));
	}
	
	@Override
	public Set<Intervalo> llanos() {
		return IntStream.range(0,super.numMarcas()-1).boxed()
				.map(i->super.intervalo(i))
				.filter(in->in.type().equals(Type.Llano))
				.collect(toSet());
	}

	@Override
	public Map<Type, List<Intervalo>> agrupaPorTipoLista() {
		return IntStream.range(0, super.numMarcas() - 1).boxed()
				.map(i -> super.intervalo(i))
				.collect(groupingBy(Intervalo::type));
	}
	
	@Override
	public Map<Type, List<Intervalo>> limita(Map<Type, List<Intervalo>> m, Integer n) {
		return m.entrySet().stream()
				.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue().stream().limit(n).collect(toList())));
	}

	@Override
	public Map<Type, Set<Intervalo>> agrupaPorTipoConjunto() {
		return IntStream.range(0, super.numMarcas() - 1).boxed()
				.map(i -> super.intervalo(i))
				.collect(groupingBy(Intervalo::type, Collectors.toSet()));
	}

	@Override
	public String imprimeGrupos(Map<Type, List<Intervalo>> m) {
		return m.entrySet().stream()
				.map(e -> e.getKey() + "  -> \n\t" + e.getValue().stream().map(x -> x.toString()).collect(joining("\n\t")))
				.collect(joining("\n"));
	}
	
	

}
