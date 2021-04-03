package us.lsi.universo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.IntStream;

import us.lsi.tools.Canvas;
import us.lsi.tools.IntPair;
import us.lsi.tools.Preconditions;



public class Universo  {
	
    
	public static Universo empty() {
		return new Universo("Universo vacio",longitudXDeFault,longitudYDeFault,colorDeFondoDefault);
	}

	public static Universo empty(String nombre, int longitudX, int longitudY, Color colorFondo) {
		return new Universo(nombre, longitudX, longitudY, colorFondo);
	}
	
	public static enum Position{Inside,Left,Right,Up,Down,OutSide}

	    
	public static Integer longitudXDeFault = 600;
	public static Integer longitudYDeFault = 600;
	public static Color colorDeFondoDefault = Color.BLUE;
	public static Integer umbralRiesgo = 10;
	
	protected Integer longitudX;
    protected Integer longitudY;
    protected Integer tiempo;
    public Integer tiempoMax = 500;
    
    
    protected Canvas ventana;
    protected List<CuerpoCeleste> cuerposCelestes;
    
    
     protected Universo(String nombre, int longitudX, int longitudY, Color colorFondo) {
    	 Preconditions.checkArgument(longitudX>300,"La anchura de un universo debe ser al menos 300");
    	 Preconditions.checkArgument(longitudY>300,"La altura de un universo debe ser al menos 300");
         this.ventana = Canvas.of(nombre, longitudX, longitudY, colorFondo);
         this.longitudX = longitudX;
         this.longitudY = longitudY;
         this.cuerposCelestes = new ArrayList<CuerpoCeleste>();   
         this.tiempo = 0;
    }  
    
     public int getLongitudY() {
         return longitudY;
     }
     
     public int getLongitudX() {
         return longitudX;
     }
    
     public Integer getTiempo() {
		return tiempo;
	}

	public void pintarCuerpoCeleste(CuerpoCeleste cuerpo) {
    	 ventana.setForegroundColor(cuerpo.color);
    	 ventana.fillCircle(cuerpo.getCoordenadas().x().intValue() - (cuerpo.diametro/2), 
		                   cuerpo.getCoordenadas().y().intValue() - (cuerpo.diametro/2), 
		                   cuerpo.diametro);
     }
    
    
     public void borrarCuerpoCeleste(CuerpoCeleste cuerpo) {
    	 ventana.eraseCircle(cuerpo.getCoordenadas().x().intValue() - (cuerpo.diametro/2), 
    			            cuerpo.getCoordenadas().y().intValue() - (cuerpo.diametro/2), 
    			            cuerpo.diametro);
     }
     
     
     public Position position(CuerpoCeleste cuerpo) {
    	Integer minimoX = cuerpo.getCoordenadas().x().intValue() - (cuerpo.diametro/2);
    	Integer maximoX = cuerpo.getCoordenadas().x().intValue() + (cuerpo.diametro/2);
    	Integer minimoY = cuerpo.getCoordenadas().y().intValue() - (cuerpo.diametro/2);
    	Integer maximoY = cuerpo.getCoordenadas().y().intValue() + (cuerpo.diametro/2);
    	Position r = null;
    	if(minimoX > 0 && maximoX < this.getLongitudX() && minimoY > 0 && maximoY < this.getLongitudY()) 
    		r = Position.Inside;
    	else if(minimoX < 0 && minimoY > 0 && maximoY < this.getLongitudY())
    		r = Position.Left;
    	else if(maximoX > this.getLongitudX() && minimoY > 0 && maximoY < this.getLongitudY()) 
    		r = Position.Right;
    	else if(minimoX > 0 && maximoX < this.getLongitudX() && minimoY < 0) 
    		r = Position.Up;
    	else if(minimoX > 0 && maximoX < this.getLongitudX() && maximoY > this.getLongitudY()) 
    		r = Position.Down;
    	else 
    		r = Position.OutSide;
    	return r;
     }
     
    public void comprobarPosicion(CuerpoCeleste cuerpo) {
    	Position p = position(cuerpo);
    	Preconditions.checkState(p.equals(Position.Inside),String.format("El cuerpo está fuera de la ventana %s",p));
    }
     

    
	public double distanciaMinima() {
		Integer n = cuerposCelestes.size();
		Double distanciaMinima = IntStream.range(0, n).boxed()
				.flatMap(i -> IntStream.range(i + 1, n).boxed().map(j -> IntPair.of(i, j)))
				.map(p -> this.cuerposCelestes.get(p.first()).distanciaA(this.cuerposCelestes.get(p.second())))
				.min(Comparator.naturalOrder()).get();
		return distanciaMinima;
	}
    
   
	public void simular() {
		Double distanciaMinima = 0.;
		Integer vecesEnRiesgo = 0;
		ventana.drawString("Tiempo: " + tiempo, 1, 11);
        ventana.drawString("Veces en riesgo: " + vecesEnRiesgo, 1, longitudY-20);
        ventana.drawString("Distancia minima: " + distanciaMinima, 1, longitudY-5);
		
        while (true) {
        	
        	ventana.wait(100); // Para ralentizar la simulacion
			
			ventana.eraseString("Tiempo: " + tiempo, 1, 11);
            ventana.eraseString("Veces en riesgo: " + vecesEnRiesgo, 1, longitudY-20);
            ventana.eraseString("Distancia minima: " + distanciaMinima, 1, longitudY-5);
            
			
            distanciaMinima = distanciaMinima();
			for (CuerpoCeleste cuerpo : cuerposCelestes) cuerpo.mover();
			if (distanciaMinima < umbralRiesgo) vecesEnRiesgo += 1;

			
			tiempo++;
			ventana.drawString("Tiempo: " + tiempo, 1, 11);
            ventana.drawString("Veces en riesgo: " + vecesEnRiesgo, 1, longitudY-20);
            ventana.drawString("Distancia minima: " + distanciaMinima, 1, longitudY-5);
			
		 } 
//		ventana.close();
	}
}
