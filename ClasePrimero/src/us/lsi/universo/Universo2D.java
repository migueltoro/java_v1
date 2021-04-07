package us.lsi.universo;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import us.lsi.geometria.Punto2D;
import us.lsi.tools.Canvas;
import us.lsi.tools.IntPair;
import us.lsi.tools.Preconditions;



public class Universo2D  {
	
    
	public static Universo2D empty() {
		return new Universo2D("Universo vacio",xMaxDeFault,yMaxDeFault,colorDeFondoDefault);
	}

	public static Universo2D empty(String nombre, int longitudX, int longitudY, Color colorFondo) {
		return new Universo2D(nombre, longitudX, longitudY, colorFondo);
	}
	
	public static enum Location{Inside,Left,Right,Up,Down,OutSide}

	
	public static Integer xMaxDeFault = 600;
	public static Integer yMaxDeFault = 600;
	public static Color colorDeFondoDefault = Color.WHITE;
	public static Integer umbralRiesgo = 10;
	
	protected Integer xMax;
    protected Integer yMax;
    protected Integer tiempo;
    public Integer tiempoMax = 500;
    
    
    protected Canvas ventana;
    protected List<CuerpoCeleste> cuerposCelestes;
    
    
     protected Universo2D(String nombre, int xMax, int yMax, Color colorFondo) {
    	 Preconditions.checkArgument(xMax>300,"La anchura de un universo debe ser al menos 300");
    	 Preconditions.checkArgument(yMax>300,"La altura de un universo debe ser al menos 300");
         this.ventana = Canvas.of(nombre, xMax, yMax, colorFondo);
         this.xMax = xMax;
         this.yMax = yMax;
         this.cuerposCelestes = new ArrayList<CuerpoCeleste>();   
         this.tiempo = 0;
    }  
    
     public int yMax() {
         return yMax;
     }
     
     public int xMax() {
         return xMax;
     }
    
     public Integer tiempo() {
		return tiempo;
	}

	public void pintarCuerpoCeleste(CuerpoCeleste cuerpo) {
    	 ventana.setForegroundColor(cuerpo.color);
    	 ventana.fillCircle(cuerpo.coordenadas().x().intValue() - (cuerpo.diametro/2), 
		                   cuerpo.coordenadas().y().intValue() - (cuerpo.diametro/2), 
		                   cuerpo.diametro);
     }
    
    
     public void borrarCuerpoCeleste(CuerpoCeleste cuerpo) {
    	 ventana.eraseCircle(cuerpo.coordenadas().x().intValue() - (cuerpo.diametro/2), 
    			            cuerpo.coordenadas().y().intValue() - (cuerpo.diametro/2), 
    			            cuerpo.diametro);
     }
     
     public void pintarFinal(Integer x1, Integer y1, Double d, String s1, String s2) {
    	 ventana.setForegroundColor(Color.WHITE);
    	 ventana.drawString(String.format("Choque entre %s y %s a %.2f",s1,s2,d), x1, y1);
     }
     
     
     public Location location(CuerpoCeleste cuerpo) {
    	Integer minimoX = cuerpo.coordenadas().x().intValue() - (cuerpo.diametro/2);
    	Integer maximoX = cuerpo.coordenadas().x().intValue() + (cuerpo.diametro/2);
    	Integer minimoY = cuerpo.coordenadas().y().intValue() - (cuerpo.diametro/2);
    	Integer maximoY = cuerpo.coordenadas().y().intValue() + (cuerpo.diametro/2);
    	Location r = null;
    	if(minimoX > 0 && maximoX < this.xMax() && minimoY > 0 && maximoY < this.yMax()) 
    		r = Location.Inside;
    	else if(minimoX < 0 && minimoY > 0 && maximoY < this.yMax())
    		r = Location.Left;
    	else if(maximoX > this.xMax() && minimoY > 0 && maximoY < this.yMax()) 
    		r = Location.Right;
    	else if(minimoX > 0 && maximoX < this.xMax() && minimoY < 0) 
    		r = Location.Up;
    	else if(minimoX > 0 && maximoX < this.xMax() && maximoY > this.yMax()) 
    		r = Location.Down;
    	else 
    		r = Location.OutSide;
    	return r;
     }
     
    public void comprobarPosicion(CuerpoCeleste cuerpo) {
    	Location p = location(cuerpo);
    	Preconditions.checkState(p.equals(Location.Inside),String.format("El cuerpo está fuera de la ventana %s",p));
    }
     
    private IntPair choque;
    
	public Double distanciaMinima() {
		Integer n = cuerposCelestes.size();
		Double distanciaMinima = Double.MAX_VALUE;
		choque = null;
		for(int i = 0; i< n; i++) {
			for(int j = i+1; j<n; j++) {
				Double d = this.cuerposCelestes.get(i).distanciaA(this.cuerposCelestes.get(j));
				if(d < distanciaMinima) {
					distanciaMinima = d;
					choque = IntPair.of(i,j);
				}
			}
		}
		return distanciaMinima;
	}
    
   
	public void simular() {
		Double distanciaMinima = 0.;
		Integer vecesEnRiesgo = 0;
		ventana.drawString("Tiempo: " + tiempo, 1, 11);
        ventana.drawString("Veces en riesgo: " + vecesEnRiesgo, 1, yMax-20);
        ventana.drawString("Distancia minima: " + distanciaMinima, 1, yMax-5);
		
        while (true) {
        	
        	ventana.wait(100); // Para ralentizar la simulacion
			
			ventana.eraseString("Tiempo: " + tiempo, 1, 11);
            ventana.eraseString("Veces en riesgo: " + vecesEnRiesgo, 1, yMax-20);
            ventana.eraseString("Distancia minima: " + distanciaMinima, 1, yMax-5);
            
			
            distanciaMinima = distanciaMinima();
            
            if(distanciaMinima < 0) {
            	Punto2D p1 = this.cuerposCelestes.get(choque.first()).coordenadas();
            	Punto2D p2 = this.cuerposCelestes.get(choque.second()).coordenadas();
            	Punto2D pm = Punto2D.of((p1.x()+p2.x())/2, (p1.y()+p2.y())/2);
            	pintarFinal(pm.x().intValue(),pm.y().intValue(),
            			distanciaMinima,
            			this.cuerposCelestes.get(choque.first()).nombre(),
            			this.cuerposCelestes.get(choque.second()).nombre());
            	return;
            }
            
			
            for (CuerpoCeleste cuerpo : cuerposCelestes) cuerpo.mover();
			
            if (distanciaMinima < umbralRiesgo) vecesEnRiesgo += 1;

			tiempo++;
			ventana.drawString("Tiempo: " + tiempo, 1, 11);
            ventana.drawString("Veces en riesgo: " + vecesEnRiesgo, 1, yMax-20);
            ventana.drawString("Distancia minima: " + distanciaMinima, 1, yMax-5);
			
		 } 
//		ventana.close();
	}
}
