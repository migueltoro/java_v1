package us.lsi.ejercicios_b1;


public class EjerciciosB1 {
	
//	-----------------------------------------------------------------------------
//	1) NÚMEROS Y EXPRESIONEs
//	-----------------------------------------------------------------------------

	
//	1. Devuelve la parte fraccionaria de un número en [0,1).
//    Idea: parte_fraccionaria = a - floor(a)
//    Ejemplos:
//        3.14 -> 0.14
//       -3.25 -> 0.75  (porque floor(-3.25) = -4; -3.25 - (-4) = 0.75)
    

//	2. Redondea 'a' a 'n' decimales usando round (suficiente para este bloque).


//	# -----------------------------------------------------------------------------
//	# 2) CADENAS DE TEXTO
//	# -----------------------------------------------------------------------------

	
//	3. Quita espacios al principio y al final y deja un solo espacio entre palabras.
//    Estrategia: dividir por espacios (split sin argumentos) y volver a unir con.
    

//	4. Quita tildes comunes de vocales.
//    Nota: Para un tratamiento completo se podría usar 'unicodedata', pero aquí
//    preferimos una sustitución directa y explícita.
		
//	reemplazos= {
//	        "á": "a",
//	        "é": "e",
//	        "í": "i",
//	        "ó": "o",
//	        "ú": "u",
//	        "Á": "a",
//	        "É": "e",
//	        "Í": "i",
//	        "Ó": "o",
//	        "Ú": "u",
//	    }

//	5. Es Isograma: Devuelve True si s no contiene letras normalizadas repetidas.
//    - Ignora espacios y guiones.
//    - Ignora tildes (á->a, etc.).
//    - No diferencia mayúsculas/minúsculas.
//	  Normalizamos la cadena:
//	    - pasamos a minúsculas
//	    - quitamos tildes comunes
//	    - eliminamos espacios y guiones
    

//	6. A Camel Case
//		Convierte 'hola mundo feliz' -> 'holaMundoFeliz'.
//    - Primera palabra en minúscula
//    - Resto de palabras con primera letra en mayúscula y el resto tal cual
	

//	7. Invierte Cadena
//	Devuelve la cadena invertida usando un método sencillo con acumulación.


//	8. Reemplazar_vocales
//	    Sustituye todas las vocales (con y sin tilde) por guiones.
//	    Consideramos a, e, i, o, u y sus versiones acentuadas.
//	   

//	9. Contar Palabras
//	    Cuenta las palabras separadas por uno o más espacios.

//
//	# -----------------------------------------------------------------------------
//	# 3) FECHAS
//	# -----------------------------------------------------------------------------


//	10. Es Laborable
//	Devuelve verdadero si la fecha d es de Lunes(0) a Viernes(4) y no es festivo. 
//	Festivos es un conjunto de fechas festivas dado como parámetro
	
	
//	11. Dias Laborables Entre
//	Cuenta cuántos días laborables hay entre a y b (ambos inclusive)


//	12. Siguiente Laborable
//	Devuelve la primera fecha laborable estrictamente posterior a d.


//	# -----------------------------------------------------------------------------
//	# 4) OPERACIONES BÁSICAS CON LISTAS
//	# -----------------------------------------------------------------------------


//	13. Suma Lista de número reales
//	Suma todos los elementos usando un bucle for
	    

//	14. Media Lista de números reales
//	Media aritmética: suma / número de elementos (asume xs no vacía). Si está vacía, lanza una excepción.


//	15. Máximo Lista de números enteros
//	Devuelve el máximo de forma iterativa (asume xs no vacía). Si está vacía, lanza una excepción.


//	16. Míximo Lista de números enteros
//	Devuelve el míximo de forma iterativa (asume xs no vacía). Si está vacía, lanza una excepción.
	

//	# -----------------------------------------------------------------------------
//	# 5) TRANSFORMACIONES Y FILTRADOS
//	# -----------------------------------------------------------------------------

	
//	17. Diferencias Consecutivas
//	Dada una lista de enteros devuelve otra las diferencias xs[i] - xs[i-1] para i=1..n-1.


//	18. Pares Cuadrados
//	Dada una lista de enteros devuelve otra con los cuadrados de los números pares de la lista.


//	19. Filtrar Mayores
//	Dada una lista de enteros devuelve otra con los elementos que son estrictamente mayores que valor dado


//	# -----------------------------------------------------------------------------
//	# 6) BÚSQUEDA Y SELECCIÓN
//	# -----------------------------------------------------------------------------

	
//	20. Es Primo
//	Comprueba si n es primo mediante un método elemental.

	
//	21. Divisores
//	Dada una lista de cadens de caracteres evuelve la lista de divisores de n en orden ascendente (método simple).
	

//	22. Cadena Mas Larga
//	Dada una lista de cadens de caracteres devuelve la cadena de mayor longitud (si hay empate, la primera).


//	# ---------------------------------------------------------------------
//	# 7. Funciones adicionales
//	# ---------------------------------------------------------------------

//	23. Producto Rango(n: int, k: int) -> int:
//	Calcula el producto de todos los enteros i en el rango [k, n], con n >= k.
//
//	Ejemplos:
//	   producto_rango(5, 3) = 3 * 4 * 5 = 60
//	   producto_rango(4, 4) = 4
	
	
//	24. Capitalizar Palabras
//	Dada una cadena en MAYÚSCULAS, devuelve otra con:
//	    - Primera letra de cada palabra en mayúscula
//	    - Resto de letras en minúscula
//	    - Las palabras están separadas por uno o más espacios.


//	# -----------------------------------------------------------------------------
//	# 8) FICHEROS DE TEXTO Y CSV (básico)
//	# -----------------------------------------------------------------------------

	
//	25. Crear Fichero Texto
//	Crea un fichero de nombre dado con un texto dado. Devuelve verdadero si todo va bien."""

//	26. Leer_fichero_texto(nombre: str) -> None:
//	Muestra por pantalla el contenido completo del fichero dado su nombre.


//	27. Contar Lineas No Vacias
//	Cuenta cuántas líneas no están vacías (tienen algún carácter no espacio) en un fichero dado su ruta.	


//	28. Numero de Palabras
//	Devuelve el número de palabras que contiene el fichero de texto
//	pasado como parámetro de entrada.
//
//	    Estrategia:
//	      1) Abrir el fichero en modo lectura.
//	      2) Recorrer todas sus líneas.
//	      3) Dividir cada línea en palabras usando split() (ignora espacios extra).
//	      4) Acumular el número de palabras encontradas.
//	      5) Devolver el total.
//
//	    Ejemplo:
//	        Si el fichero contiene:
//	            "Hola mundo\n
//	             Esto es una prueba"
//	        Entonces:
//	            numPalabras("fichero.txt")  es  5


//	29. Numero de Repeticiones de Palabras
//	Devuelve el número de veces que aparece una palabra dada en un fichero.
//
//	    Estrategia:
//	      1) Abrir el fichero en modo lectura.
//	      2) Leer línea a línea.
//	      3) Pasar a minúsculas tanto la línea como la palabra buscada
//	         (para que la comparación no dependa de mayúsculas/minúsculas).
//	      4) Dividir la línea en palabras usando split().
//	      5) Contar cuántas veces aparece la palabra en la lista de palabras.
//	      6) Acumular el resultado y devolverlo.
//
//	    Parámetros:
//	      - fichero: ruta del fichero de texto a analizar.
//	      - palabra: palabra que se desea contar.
//
//	    Ejemplo:
//	        Contenido del fichero:
//	            "Hola mundo\n
//	             Hola de nuevo mundo"
//	        numRepeticionesPalabras("fichero.txt", "hola") es 2
//	        numRepeticionesPalabras("fichero.txt", "mundo") es 2
	
//	30. Palabras Mas Frecuentes
//	Devuelve las k palabras más frecuentes de un fichero (minúsculas, separadas por espacios).
	
	   
//	31. Crear Fichero Csv
//	Dada una lista de listas de cadenas y un delimitador crea un fichero CSV.
	
//	32. Leer Fichero Csv
//    Dado un fichero CSV con un delimitador específico, devuelve una lista de listas de cadenas


//	# -----------------------------------------------------------------------------
//	# 9) CONJUNTOS Y COLECCIONES 
//	# -----------------------------------------------------------------------------
	
//	32. Intersección de Conjuntos
//	Dados dos conjuntos genéricos a y b, devuelve su intersección como un nuevo conjunto sin modific a y b.
	
//	33. Unión de Conjuntos
//	Dados dos conjuntos genéricos a y b, devuelve su unión como un nuevo conjunto sin modific a y b.
	
//	34. Diferencia de Conjuntos
//	Dados dos conjuntos genéricos a y b, devuelve su diferencia de como un nuevo conjunto sin modific a y b.
	
//	35. Diferencia simétrica de Conjuntos
//	Dados dos conjuntos genéricos a y b, devuelve su diferencia simétrica como un nuevo conjunto sin modific a y b
	
//	36. Filtrar diccionario:
//	Dado un diccionario genérico y un predicado sobre sus claves devuelve un nuevo diccionario 
//	con el subconjunto de claves que cumplen el predicado.
	
//	36. Transforma diccionario:
//	Dado un diccionario genérico y una función sobre sus valores devuelve un nuevo diccionario 
//	con las mismas claves y sus valores transformados por la función.
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
