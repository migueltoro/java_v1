package us.lsi.ruta;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tools.FileTools;

public class RutaF extends RutaA implements Ruta {
	
	public static Ruta of(List<Marca> marcas) {
		return new RutaF(marcas);
	}
	
	private RutaF(List<Marca> marcas) {
		super(marcas);
	}

	public static Ruta leeDeFichero(String fichero) {
		List<Marca> marcas = FileTools.streamFromFile("ficheros/ruta.csv").map(x->Marca.parse(x)).collect(Collectors.toList());
		return of(marcas);
	}

	@Override
	public Double getTiempo() {
		Integer n = this.marcas.size();
		return IntStream.range(0,n-1).boxed().map(i->this.getIntervalo(i)).mapToDouble(x->x.tiempo()).sum();
	}
	
	@Override
	public Double getLongitud() {
		Integer n = this.marcas.size();
		return IntStream.range(0,n-1).boxed().map(i->this.getIntervalo(i)).mapToDouble(x->x.longitud()).sum();
	}
	
	
	@Override
	public Double getDesnivelCrecienteAcumulado() {
		Integer n = this.marcas.size();		
		return IntStream.range(0, n-1)
				.boxed()
				.map(i->this.getIntervalo(i))
				.filter(e->e.desnivel()>0)
				.mapToDouble(e->e.longitud())
				.sum();
	}
	
	
	@Override
	public Double getDesnivelDecrecienteAcumulado() {
		Integer n = this.marcas.size();		
		return IntStream.range(0, n-1)
				.boxed()
				.map(i->this.getIntervalo(i))
				.filter(e->e.desnivel()<0)
				.mapToDouble(e->e.longitud())
				.sum();
	}
	
	@Override
	public String toString() {
		return marcas.stream().map(m->m.toString()).collect(Collectors.joining("\n"));
	}

}
