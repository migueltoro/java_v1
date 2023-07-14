package us.lsi.ejemplos_b3.tipos;

import java.util.ArrayList;
import java.util.List;

import us.lsi.tools.Preconditions;

public class MatrizL2 extends Matriz1<Long> implements MatrizL {
	
	public static MatrizL2 as(Matriz2<Long> m) {
		return new MatrizL2(m.datos);
	}

	public static MatrizL2 ofI(List<List<Long>> datos) {
		return new MatrizL2(datos);
	}
	
	public static MatrizL identity(Integer n) {
		List<List<Long>> datos = new ArrayList<>();
		for(int f=0; f< n; f++) {
			List<Long> fila = new ArrayList<>();
			for(int c=0; c< n; c++) {
				fila.add(c==f?1L:0L);
			}
			datos.add(fila);
		}
		return MatrizL2.ofI(datos);
	}

	private MatrizL2(List<List<Long>> datos) {
		super(datos);
	}

	@Override
	public Boolean esAntisimetrica() {
		Boolean r = true;
		for(int f=0; f<this.nf(); f++) {
			for(int c=0; c<this.nc(); c++) {
				if(!this.get(f,c).equals(-this.get(f,c))) {
					r = false;
					break;
				}
			}
		}
		return r;
	}

	@Override
	public MatrizL add(MatrizL m) {
		List<List<Long>> datos = new ArrayList<>();
		for(int f=0; f<this.nf(); f++) {
			List<Long> fila = new ArrayList<>();
			for(int c=0; c<this.nc(); c++) {
				fila.add(this.get(f,c) + m.get(f,c));
			}
			datos.add(fila);
		}
		return MatrizL2.ofI(datos);
	}

	@Override
	public MatrizL subtract(MatrizL m) {
		List<List<Long>> datos = new ArrayList<>();
		for(int f=0; f<this.nf(); f++) {
			List<Long> fila = new ArrayList<>();
			for(int c=0; c<this.nc(); c++) {
				fila.add(this.get(f,c) - m.get(f,c));
			}
			datos.add(fila);
		}
		return MatrizL2.ofI(datos);
	}

	@Override
	public MatrizL multiply(MatrizL m) {
		List<List<Long>> datos = new ArrayList<>();
		for(int f=0; f<this.nf(); f++) {
			List<Long> fila = new ArrayList<>();
			for(int c=0; c<this.nc(); c++) {
				Long s = 0L;
				for(int k=0;k<this.nc();k++) {
					s = s + this.get(f,k) * m.get(k,c);
				}
				fila.add(s);	
			}
			datos.add(fila);
		}
		return MatrizL2.ofI(datos);
	}

	@Override
	public MatrizL negate() {
		List<List<Long>> datos = new ArrayList<>();
		for(int f=0; f<this.nf(); f++) {
			List<Long> fila = new ArrayList<>();
			for(int c=0; c<this.nc(); c++) {
				fila.add(-this.get(f,c));
			}
			datos.add(fila);
		}
		return MatrizL2.ofI(datos);
	}
	
	@Override
	public MatrizL pow(Integer n) {
		Preconditions.checkArgument(this.nf() == this.nc(), String.format("No se pueden elevar"));
		MatrizL m = MatrizL2.identity(this.nf());
		for(int i=0;i<n;i++) {
			m = m.multiply(this);
		}
		return m;
	}

}

