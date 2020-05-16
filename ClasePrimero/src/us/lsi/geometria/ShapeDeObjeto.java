package us.lsi.geometria;

import java.awt.Shape;
import java.util.function.Function;

public interface ShapeDeObjeto {
	
	Shape shape(Function<Double,Double> xt, Function<Double,Double> yt);

}
