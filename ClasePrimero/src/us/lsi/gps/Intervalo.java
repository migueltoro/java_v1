package us.lsi.gps;

import java.time.temporal.ChronoUnit;

public class Intervalo {
	
	public static Intervalo of(Marca principio, Marca fin) {
		return new Intervalo(principio, fin);
	}

	private Marca principio;
	private Marca fin;
	
	private Intervalo(Marca principio, Marca fin) {
		super();
		this.principio = principio;
		this.fin = fin;
	}

	public Marca getPrincipio() {
		return this.principio;
	}

	public Marca getFin() {
		return this.fin;
	}

	@Override
	public String toString() {
		return String.format("(%s,%s)", this.principio.toString(),this.fin.toString());
	}
	
	public Double getDesnivel() {
		return this.fin.getCoordenadas().getAltitud()-this.principio.getCoordenadas().getAltitud();
	}
	
	public Double getLongitud() {
		return this.getPrincipio().getCoordenadas().distancia(this.getFin().getCoordenadas());
	}
	
	public Double getTiempo() {
		return this.getPrincipio().getTime().until(this.getFin().getTime(), ChronoUnit.SECONDS)/3600.;
	}
	
	public Double getVelocidad() {
		return this.getLongitud()/this.getTiempo();
	}

}
