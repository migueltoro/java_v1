package us.lsi.universo;

import us.lsi.ejemplos_b1_tipos.Vector2D;

public record Orbita2D(
		Double a,
		Double b,
		Double c,
		Double anguloEjeMayor,
		Double excentricidad,
		Double d,
		Double d1,
		Double d2,
		Double periodo,
		Boolean dextrogiro) {
	
	public  static Orbita2D of(Double a, Double excentricidad, Double anguloEjeMayor, Double periodo, Boolean dextrogiro) {
		Double c = a*excentricidad;
		Double b = Math.sqrt(a*a-c*c);
		Double d = a*(1-excentricidad*excentricidad); 
		Double d1 = a - c; // a*(1-c/a) = a - c
		Double d2 = a + c; // a*(1+c/a) = a + c
		return new Orbita2D(a,b,c,anguloEjeMayor,excentricidad,d,d1,d2,periodo,dextrogiro);
	}
	
	public  static Orbita2D random() {
		Double a = Universo2D.valorAleatorioEntre(100., 200.);
		Double excentricidad = Universo2D.valorAleatorioEntre(0.2, 0.9);
		Double anguloEjeMayor = Universo2D.valorAleatorioEntre(0., Math.PI / 2);
		Double periodo = Universo2D.valorAleatorioEntre(62.,125.);
		Double v = Universo2D.valorAleatorioEntre(0.,1.);
		Boolean dextrogiro = v>0.7?true:false;
		return Orbita2D.of(a, excentricidad, anguloEjeMayor, periodo, dextrogiro);
	}
	
	public  static Orbita2D random2() {
		Double a = Universo2D.valorAleatorioEntre(40., 50.);
		Double excentricidad = 0.;
		Double anguloEjeMayor = 0.;
		Double periodo = Universo2D.valorAleatorioEntre(4.1,6.3);
		Double v = Universo2D.valorAleatorioEntre(0.,1.);
		Boolean dextrogiro = v>0.7?true:false;
		return Orbita2D.of(a, excentricidad, anguloEjeMayor, periodo, dextrogiro);
	}
	
	public Vector2D radioVector(Double angulo) {
		Double r = this.d/(1+this.excentricidad*Math.cos(angulo));
		Vector2D v = Vector2D.ofRadianes(r,angulo+this.anguloEjeMayor);
		return v;
	}
	
	public Double velocidadAngular(Double angulo) {
		Double rm = this.radioVector(angulo).modulo();
		Double vg =  2*Math.PI*this.a()/(this.periodo()*rm)*Math.sqrt(2*this.a()/rm-1);
		return this.dextrogiro() ? vg : -vg;
	}
	
	@Override
	public String toString() {
		return "Orbita2D [a=" + a + ", b=" + b + ", c=" + c +", anguloEjeMayor=" + 
				anguloEjeMayor + ", excentricidad="
				+ excentricidad + ", d=" + d 
				+ ", d1=" + d1 + ", d2=" + d2 + "]";
	}
	
	
	
}
