module java_v1 {
	exports us.lsi.ejemplos_b1;
	exports us.lsi.ejemplos_b1_tipos;
	exports us.lsi.ejemplos_b2;
	exports us.lsi.geometria;
	exports us.lsi.libro;
	exports us.lsi.tools;
	exports us.lsi.biblioteca;
	exports us.lsi.bancos;
	exports us.lsi.centro;
	exports us.lsi.aeropuerto;
	exports us.lsi.ruta;
	exports us.lsi.sevici;
	exports us.lsi.coordenadas;
	exports us.lsi.universo;
	exports us.lsi.poker;
	exports us.lsi.whatsapp;
	
	requires transitive org.apache.commons.lang3;
	requires transitive java.desktop;
	requires transitive juniversalchardet;
	requires transitive commons.math3;
	requires transitive org.apache.commons.csv;
	requires transitive org.junit.jupiter.api;
	requires org.mockito;
}