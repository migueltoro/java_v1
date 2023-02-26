package us.lsi.ruta;

import java.util.List;
import java.util.Map;
import java.util.Set;
import static java.util.stream.Collectors.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.ruta.Intervalo.Type;
import us.lsi.tools.File2;
import us.lsi.tools.Stream2;

public class RutaF extends RutaA implements Ruta {
	
	public static Ruta of(List<Marca> marcas) {
		return new RutaF(marcas);
	}
	
	private RutaF(List<Marca> marcas) {
		super(marcas);
	}

	public static Ruta leeDeFichero(String fichero) {
		List<Marca> marcas = File2.streamDeFichero("ficheros/ruta.csv").map(x->Marca.parse(x)).collect(toList());
		return of(marcas);
	}

	@Override
	public Double getTiempo() {
		Integer n = super.marcas.size();
		return IntStream.range(0,n-1).boxed().map(i->this.getIntervalo(i)).mapToDouble(x->x.tiempo()).sum();
	}
	
	@Override
	public Double getLongitud() {
		Integer n = super.marcas.size();
		return IntStream.range(0,n-1).boxed().map(i->this.getIntervalo(i)).mapToDouble(x->x.longitud()).sum();
	}
	
	
	@Override
	public Double getDesnivelCrecienteAcumulado() {
		Integer n = super.marcas.size();		
		return IntStream.range(0, n-1)
				.boxed()
				.map(i->this.getIntervalo(i))
				.filter(e->e.desnivel()>0)
				.mapToDouble(e->e.longitud())
				.sum();
	}
	
	
	@Override
	public Double getDesnivelDecrecienteAcumulado() {
		Integer n = super.marcas.size();		
		return IntStream.range(0, n-1)
				.boxed()
				.map(i->this.getIntervalo(i))
				.filter(e->e.desnivel()<0)
				.mapToDouble(e->e.longitud())
				.sum();
	}
	
	@Override
	public String toString() {
		return super.marcas.stream().map(m->m.toString()).collect(joining("\n"));
	}

	@Override
	public Map<Type,Integer> getFrecuencias() {
		Stream<Intervalo> st = IntStream.range(0,super.gerNumMarcas()-1).boxed()
		.map(i->super.getIntervalo(i));
		return Stream2.groupingReduce(st,it->it.type(),x->1,(x,y)->x+y);
	}
	
	@Override
	public Set<Intervalo> getLLanos() {
		return IntStream.range(0,super.gerNumMarcas()-1).boxed()
				.map(i->super.getIntervalo(i))
				.filter(in->in.type().equals(Type.Llano))
				.collect(toSet());
	}

}
