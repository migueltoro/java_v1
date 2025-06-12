package us.lsi.ejemplos_b1;

public class Funciones_1 {
		
	public static Integer parteEntera(Double a) {
		return a.intValue();
	}

	public static Integer digitoDecimal(Double a, Integer n) {
		return parteEntera(a * Math.pow(10, n)) % 10;
	}

	public static Integer digitoParteEntera(Double a, Integer n) {
		return (a.intValue() / parteEntera(Math.pow(10, n))) % 10;
	}

	public static Double areaCirculo(Double radio) {
		assert radio >= 0 :
				String.format("El radio debe ser mayor o igual a cero y es %.2f", radio);
		return Math.PI * radio * radio;
	}

    public static Double longitudCircunferencia(Double radio) {
    	assert radio>=0 :
    			String.format("El radio debe ser mayor o igual a cero y es %.2f}",radio);
		return Math.PI*radio;
    }

	public static void main(String[] args) {
		System.out.println(parteEntera(82.345));
		System.out.println(digitoDecimal(82.345,4));
		System.out.println(digitoParteEntera(82457.34509,3));
	    System.out.println(areaCirculo(5.));
	    String s = "1" + "2" + '3';
	    System.out.println(s);
	}

}
