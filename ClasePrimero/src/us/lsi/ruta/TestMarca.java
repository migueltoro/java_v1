package us.lsi.ruta;

import java.util.Locale;

public class TestMarca {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		Marca m = Marca.parse("00:12:27,36.74770309589803,-5.156646575778723,746.2000122070312");
		System.out.println(m);
	}

}
