package us.lsi.aeropuerto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import us.lsi.tools.FileTools;
import us.lsi.tools.Preconditions;

public class EmpresasAereas {
	
	public static List<EmpresaAerea> empresas;
	public static Map<String,EmpresaAerea> codigosEmpresas;
	public static Integer numeroEmpresas;
	
	public static void leeFicheroEmpresas(String fichero) {
		EmpresasAereas.empresas = FileTools.streamFromFile(fichero)
				.map(x ->EmpresaAerea.parse(x))
				.collect(Collectors.toList());
		try {
			EmpresasAereas.codigosEmpresas = EmpresasAereas.empresas.stream()
					.collect(Collectors.toMap(EmpresaAerea::codigo,x->x));
		} catch (IllegalStateException e) {
			Preconditions.checkState(false,e.toString());
		}
		EmpresasAereas.numeroEmpresas = EmpresasAereas.codigosEmpresas.size();
	}

}
