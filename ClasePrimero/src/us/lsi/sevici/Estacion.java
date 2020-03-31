package us.lsi.sevici;

import us.lsi.coordenadas.Coordenadas2D;
import us.lsi.tools.Preconditions;

public class Estacion {
	
	private Integer numero;
	private String name;	
	private Integer slots;
	private Integer empty_slots;
	private Integer free_bikes;
	private Coordenadas2D coordenadas;

// 149_CALLE ARROYO,20,11,9,37.397829929383,-5.97567172039552	
	
	public static Estacion parse(String linea) {
		String[] partes = linea.split(",");
		String[] sp = partes[0].split("_");
		Integer numero = Integer.parseInt(sp[0]);
		String name = sp[1];
		Integer slots= Integer.parseInt(partes[1]);
		Integer empty_slots = Integer.parseInt(partes[2]);
		Integer free_bikes = Integer.parseInt(partes[3]);
		Coordenadas2D coordenadas = Coordenadas2D.of(Double.parseDouble(partes[4]), Double.parseDouble(partes[5]));
		return new Estacion(numero,name,slots,empty_slots,free_bikes,coordenadas);
	}
	
	
	private Estacion(Integer numero, String name, Integer slots, Integer empty_slots, Integer free_bikes,
			Coordenadas2D coordenadas) {
		super();
		Preconditions.checkArgument(slots>=0,String.format("Slots deben ser mayor o igual que cero y es %d",slots));
		Preconditions.checkArgument(empty_slots>=0,String.format("Empty_Slots deben ser mayor o igual que cero y es %d",empty_slots));
		Preconditions.checkArgument(free_bikes>=0,String.format("Free_Bikes deben ser mayor o igual que cero y es %d",free_bikes));
		this.numero = numero;
		this.name = name;
		this.slots = slots;
		this.empty_slots = empty_slots;
		this.free_bikes = free_bikes;
		this.coordenadas = coordenadas;
	}


	@Override
	public String toString() {
		return String.format("(%3d,%30s,%2d,%2d,%2d,%s)",numero,name,slots,empty_slots,free_bikes,coordenadas.toString());
	}
	

}
