package us.lsi.ruta;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.tools.FileTools;

public class Ruta {
	
	private List<Marca> marcas;
	
	public static Ruta parse(String fichero) {
		List<Marca> marcas = FileTools.streamFromFile("ficheros/ruta.csv").map(x->Marca.parse(x)).collect(Collectors.toList());
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
	
	public Double getLongitud2() {
		Integer n = this.marcas.size();
		Double a = 0.;
		for(Integer i=0; i< n-1; i++) {
			Intervalo it = this.getIntervalo(i);
			Double ln = it.getLongitud();
			a = a + ln;
		}
		return a;
	}
	
	public Double VelocidadMedia() {
		return this.getLongitud()/this.getTiempo();
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
	
	public Double getDesnivelCrecienteAcumulado2() {
		Integer n = this.marcas.size();	
		Double a = 0.;
		for(Integer i = 0; i< n-1; i++) {
			Intervalo it = this.getIntervalo(i);
			if(it.getDesnivel()>0) {
				Double ln = it.getLongitud();
				a = a +ln;
			}		
		}
		return a;
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
	
	public Double getDesnivelDecrecienteAcumulado2() {
		Integer n = this.marcas.size();	
		Double a = 0.;
		for(Integer i = 0; i< n-1; i++) {
			Intervalo it = this.getIntervalo(i);
			if(it.getDesnivel()<0) {
				Double ln = it.getLongitud();
				a = a +ln;
			}		
		}
		return a;
	}

	@Override
	public String toString() {
		return marcas.stream().map(m->m.toString()).collect(Collectors.joining("\n"));
	}
	
	public String toString2() {
		String a = "";
		Boolean primero = true;
		for (Marca e : marcas) {
			String es = e.toString();
			if (primero) {
				a = a + es;
				primero = false;
			} else {
				a = a + "\n" + es;
			}
		}
		return a;
	}

}
