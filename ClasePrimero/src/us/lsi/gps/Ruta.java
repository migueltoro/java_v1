package us.lsi.gps;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tools.FileTools;

public class Ruta {
	
	private List<Marca> marcas;
	
	public static Ruta parse(String fichero) {
		List<Marca> marcas = FileTools.fromFile("ficheros/ruta.csv").map(x->Marca.parse(x)).collect(Collectors.toList());
		return new Ruta(marcas);
	}

	Ruta(List<Marca> marcas) {
		super();
		this.marcas = marcas;
	}
	
	public Double getTiempo() {
		Integer n = this.marcas.size();
		return this.marcas.get(0).getTime().until(this.marcas.get(n-1).getTime(),ChronoUnit.SECONDS)/3600.;
	}
	
	public Double getLongitud() {
		Integer n = this.marcas.size();
		return IntStream.range(0,n-1).boxed().map(i->this.getIntervalo(i)).mapToDouble(x->x.getLongitud()).sum();
	}
	
	public Double VelocidadMedia() {
		return null;
	}
	
	public Intervalo getIntervalo(Integer i) {
		return Intervalo.of(this.marcas.get(i),this.marcas.get(i+1));
	}
	
	public Double getDesnivelEnIntervalo(Integer i) {
		return Intervalo.of(this.marcas.get(i),this.marcas.get(i+1)).getDesnivel();
	}
	
	public Double getVelocidadEnIntervalo(Integer i) {
		return Intervalo.of(this.marcas.get(i),this.marcas.get(i+1)).getVelocidad();
	}
	
	public Double getDesnivelCrecienteAcumulado() {
		Integer n = this.marcas.size();		
		return IntStream.range(0, n-1)
				.boxed()
				.map(i->this.getIntervalo(i))
				.filter(e->e.getDesnivel()>0)
				.mapToDouble(e->e.getLongitud())
				.sum();
	}
	
	public Double getDesnivelDecrecienteAcumulado() {
		Integer n = this.marcas.size();		
		return IntStream.range(0, n-1)
				.boxed()
				.map(i->this.getIntervalo(i))
				.filter(e->e.getDesnivel()<0)
				.mapToDouble(e->e.getLongitud())
				.sum();
	}

	@Override
	public String toString() {
		return marcas.stream().map(m->m.toString()).collect(Collectors.joining("\n"));
	}

}
