package us.lsi.ruta;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tools.FileTools;

public record Ruta(List<Marca> marcas) {
	
	public static Ruta parse(String fichero) {
		List<Marca> marcas = FileTools.streamFromFile("ficheros/ruta.csv").map(x->Marca.parse(x)).collect(Collectors.toList());
		return new Ruta(marcas);
	}
	
	public Double tiempo() {
		Integer n = this.marcas.size();
		return this.marcas.get(0).time().until(this.marcas.get(n-1).time(),ChronoUnit.SECONDS)/3600.;
	}
	
	public Double getLongitud() {
		Integer n = this.marcas.size();
		return IntStream.range(0,n-1).boxed().map(i->this.intervalo(i)).mapToDouble(x->x.longitud()).sum();
	}
	
	public Double getLongitud2() {
		Integer n = this.marcas.size();
		Double a = 0.;
		for(Integer i=0; i< n-1; i++) {
			Intervalo it = this.intervalo(i);
			Double ln = it.longitud();
			a = a + ln;
		}
		return a;
	}
	
	public Double velocidadMedia() {
		return this.getLongitud()/this.tiempo();
	}
	
	public Intervalo intervalo(Integer i) {
		return Intervalo.of(this.marcas.get(i),this.marcas.get(i+1));
	}
	
	public Double desnivelEnIntervalo(Integer i) {
		return Intervalo.of(this.marcas.get(i),this.marcas.get(i+1)).desnivel();
	}
	
	public Double velocidadEnIntervalo(Integer i) {
		return Intervalo.of(this.marcas.get(i),this.marcas.get(i+1)).velocidad();
	}
	
	public Double desnivelCrecienteAcumulado() {
		Integer n = this.marcas.size();		
		return IntStream.range(0, n-1)
				.boxed()
				.map(i->this.intervalo(i))
				.filter(e->e.desnivel()>0)
				.mapToDouble(e->e.longitud())
				.sum();
	}
	
	
	public Double desnivelDecrecienteAcumulado() {
		Integer n = this.marcas.size();		
		return IntStream.range(0, n-1)
				.boxed()
				.map(i->this.intervalo(i))
				.filter(e->e.desnivel()<0)
				.mapToDouble(e->e.longitud())
				.sum();
	}
	
	@Override
	public String toString() {
		return marcas.stream().map(m->m.toString()).collect(Collectors.joining("\n"));
	}

}
