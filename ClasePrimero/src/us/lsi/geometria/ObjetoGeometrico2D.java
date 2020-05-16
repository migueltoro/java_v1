package us.lsi.geometria;

import java.util.function.Function;

public interface ObjetoGeometrico2D {
	/**
	 * @param p 
	 * @param angulo
	 * @return El objeto geométrico rotado el ángulo dado con respecto al punto p
	 */
	ObjetoGeometrico2D rota(Punto2D p, Double angulo);
	/**
	 * @param v
	 * @return El objeto geométrico trasladado según el vector v
	 */
	ObjetoGeometrico2D traslada(Vector2D v);
	/**
	 * @param p
	 * @param factor
	 * @return El objeto geométrico homotético con respecto a p y el factor dado
	 */
	ObjetoGeometrico2D homotecia(Punto2D p, Double factor);
	/**
	 * @param r
	 * @return La proyección del objeto geométrico sobre la recta r
	 */
	ObjetoGeometrico2D proyectaSobre(Recta2D r);
	/**
	 * @param r
	 * @return El objeto geométrico simetrico con respecto a la recta r
	 */
	ObjetoGeometrico2D simetrico(Recta2D r);
	/**
	 * @return El objeto geométrico transformado al sistema de coordenadas de la ventana
	 */
	ObjetoGeometrico2D transform(Function<Double,Double> xt, Function<Double,Double> yt);
		
}
