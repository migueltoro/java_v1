package us.lsi.libro;


import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import us.lsi.tools.File2;

public class LibroI implements Libro {

	private static LibroI libro= null;
	
	public static LibroI of() {
		if(libro==null) {
			libro = new LibroI();
		}
		return libro;
	}
	
	private LibroI() {
		super();
	}
	
	public Integer numeroDeLineas(String file) {
		List<String> ls = File2.lineasDeFichero(file);
		return ls.size();
	}
	
	public Set<String> palabrasHuecas(String file) {
		Set<String> s = new HashSet<>();
		for(String p: File2.lineasDeFichero(file)) {
			s.add(p);
		}
		return s;
	}
	
	public Set<String> palabrasDistintasNoHuecas(String file) {
		Set<String> palabrasHuecas = LibroI.of().palabrasHuecas("ficheros/palabras_huecas.txt");
		List<String> lineas = File2.lineasDeFichero(file);
		Set<String> palabrasDistintas = new HashSet<>();
		for(String linea: lineas) {
			if(!linea.isEmpty()) {
				for(String p: linea.split(separadores)) {
					if(!palabrasHuecas.contains(p)) {
						palabrasDistintas.add(p);
					}
				}
			}
		}
		return palabrasDistintas;
	}
	
	public Integer numeroDePalabrasDistintasNoHuecas(String file) {
		return 	palabrasDistintasNoHuecas(file).size();
	}

	
	public Double longitudMediaDeLineas(String file) {
		Integer numLineas = 0;
		Integer sumTamLineas = 0;
		for(String linea: File2.lineasDeFichero(file)) {
			Integer ln = linea.length();
			numLineas ++;
			sumTamLineas += ln;
		}
		return ((double)sumTamLineas)/numLineas;
	}
	
	public Integer numeroDeLineasVacias(String file) {
		Integer n = 0;
		for(String linea: File2.lineasDeFichero(file)) {
			if(linea.isEmpty()) {
				n++;
			}
		}
		return n;
	}
	
	public String lineaMasLarga(String file) {
		String lineaMaslarga = null;
		Integer lnlineaMaslarga = null;
		for(String linea: File2.lineasDeFichero(file)) {
			Integer nl = linea.length();
			if(lnlineaMaslarga == null || nl > lnlineaMaslarga) {
				lineaMaslarga = linea;
				lnlineaMaslarga = nl;
			}
		}
		return lineaMaslarga;
	}

	
	public Integer numeroDeLineaConPalabra(String file, String palabra) {
		Integer n = 0;
		Integer r = -1;
		for(String linea: File2.lineasDeFichero(file)) {
			if(linea.contains(palabra)) {
				r = n;
				break;
			}
			n++;
		}
		return r;
	}
	
	@Override
	public String lineaNumero(String file, Integer n) {
		Integer i = 0;
		String r = null;
		for(String linea: File2.lineasDeFichero(file)) {
			if(i.equals(n)) {
				r = linea;
				break;
			}
			i++;
		}
		return r;
	}	
	
	public SortedMap<String,Integer> frecuenciasDePalabras(String file) {
		Set<String> palabrasHuecas = LibroI.of().palabrasHuecas("ficheros/palabras_huecas.txt");
		List<String> lineas = File2.lineasDeFichero(file);
		SortedMap<String,Integer> m = new TreeMap<>();
		for(String linea: lineas) {
			if(!linea.isEmpty()) {
				for(String p: linea.split(separadores)) {
					if(!palabrasHuecas.contains(p)) {
						if(!m.containsKey(p)) {
							m.put(p,1);
						} else {
							Integer f = m.get(p);
							m.put(p,f+1);
						}
					}
				}
			}
		}
		return m;
	}
	
	public SortedMap<Integer,Set<String>> palabrasPorFrecuencias(String file){
		SortedMap<String,Integer> fq = LibroI.of().frecuenciasDePalabras(file);
		SortedMap<Integer,Set<String>> r = new TreeMap<>();
		for(String p: fq.keySet()) {
			Integer f = fq.get(p);
			if(!r.containsKey(f)) {
				Set<String> s = new HashSet<>();
				s.add(p);
				r.put(f, s);
			} else {
				r.get(f).add(p);
			}
		}
		return r;
	}
	
	public SortedMap<String,Set<Integer>> lineasDePalabra(String file){
		Set<String> palabrasHuecas = LibroI.of().palabrasHuecas("ficheros/palabras_huecas.txt");
		List<String> lineas = File2.lineasDeFichero(file);
		SortedMap<String,Set<Integer>> r = new TreeMap<>();
		Integer nl = 0;
		for(String linea: lineas) {
			if(!linea.isEmpty()) {
				for(String p: linea.split(separadores)) {
					if(!palabrasHuecas.contains(p)) {
						if(!r.containsKey(p)) {
							Set<Integer> s = new HashSet<>();
							s.add(nl);
							r.put(p, s);
						} else {
							r.get(p).add(nl);
						}
					}
				}
			}
			nl++;
		}
		return r;
	}

	@Override
	public Map<Character, String> lineaMasLargaQueComienzaCon(String file) {
		Map<Character, String> m = new HashMap<>();
		List<String> lineas = File2.lineasDeFichero(file);
		for(String linea: lineas) {
			if(!linea.isEmpty()) {
				Character key = linea.charAt(0);
				String value;
				if(m.containsKey(key)) {
					value = m.get(key);
				}else {
					value = "";
				}
				value = linea.length() > value.length() ? linea :value;
				m.put(key, value);
			}
		}
		return m;
	}

	

}
