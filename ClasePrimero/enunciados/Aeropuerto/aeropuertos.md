# Aeropuertos

Se dispone de los datos de aeropuertos en el fichero _aeropuertos.csv_. Cada l�nea contiene, _nombre, pais, codigo, ciudad_. Las l�neas son de la forma

```
Tirana Airport,Albania,TIA,Tirana
Berl�n Brandeburgo Airport,Alemania,BER,Berlin
Bremen Airport,Alemania,BRE,Bremen
Colonia Bonn Airport,Alemania,CGN,Colonia
```

De aerol�neas dispones del fichero _aerol�neas.csv_. En cada l�nea podemos obtener el c�digo y nombre de la aerol�nea en la forma

```
AA,American Airlines
CO,Continental Airlines,Inc.
DL,Delta Airlines Inc.
N7,National Airlines Inc.
```

El  fichero _vuelosProgramados.csv_ contiene en cada l�nea el _codigoAerolinea, numero, codigoDestino, codigoOrigen, precio, numPlazas, duracion, hora, diaSemana_. Las l�neas son de la forma

```
TP,0705,BER,KTW,294,21,170,287,14:50,FRIDAY
FS,0596,TZX,AAR,761,64,49,45,07:54,THURSDAY
FJ,0612,BHD,TPS,113,98,128,180,16:41,MONDAY
CX,0930,LJU,NCL,741,17,11,159,02:40,WEDNESDAY
```
El fichero _Vuelos.csv_ contiene en cada l�nea _codigoVuelo, fecha, numPasajeros_ de la forma

```
NH0818,2020-04-13 16:43:00,7
PE0174,2020-11-17 09:03:00,89
5Z0373,2020-05-16 01:46:00,64
7F0434,2020-10-01 03:24:00,94
```
Queremos dise�ar un programa que pueda llevar a cabo entre otros posibles los c�lculos sobre los vuelos como los siguientes:

1.	Si existe un vuelo en una fecha dada Dado a un conjunto de destinos dado 
2.	Encontrar los destinos en una fecha dada
3.	Encontrar el n�mero total de pasajeros de los destinos que tienen un prefijo dado
4.	Encontrar una relaci�n ordenada de destinos con su n�mero de pasajeros de llegada en un a�o dado. 
5.	Dado un destino encontrar el c�digo del primer vuelo con plazas libres a ese destino.
6.	Encontrar el destino con mayor n�mero de vuelos de entrada y salida
7.	Encontrar una relaci�n que asocie a cada fecha la lista de los destinos de los n vuelos de la mayor duraci�n 
8.	Obtener un conjunto ordenado con las duraciones de todos los vuelos cuya duraci�n es mayor que un n�mero de minutos dado
9.	Dada una fecha f encontrar el precio medio de los vuelos con salida posterior a f. Si no hubiera vuelos devuelve 0.0
10.	Obtener un conjunto con los n destinos de los vuelos con mayor duraci�n.
11.	Obtener una relaci�n de los destinos junto a la media de los precios de los vuelos a ese destino. 
12.	Obtener una relaci�n de destinos junto con las fechas de los vuelos a ese destino. 
13.	Obtener los n destinos que con m�s vuelos 
14.	Obtener los destinos que tienen m�s de n vuelos 
15.	Obtener una relaci�n de destinos junto con el porcentaje de vuelos que van a ese destino.
16.	Obtener una relaci�n de destinos junto con el vuelo m�s barato a ese destino.
17.	Obtener una relaci�n de destinos junto a las fechas posibles a ese destino.


Los tipos que vamos a necesitar son:

Tipos:

## Aeropuerto

Propiedades:  Inmutable

-	Codigo: Hilera de caracteres, b�sica, se compone de tres caracteres, Identifica el aeropuerto de manera �nica
-	Ciudad: Hilera de caracteres, b�sica
-	Pais: Hilera de caracteres, b�sica
-	Nombre: Hilera de caracteres, b�sica

Representaci�n:

- Codigo, Ciudad, Pa�s, Nombre

Igualdad: Si tienen el mismo c�digo. Aunque con la restricci�n impuesta al c�digo todas las propiedades b�sicas ser�n iguales.

M�todos de Factor�a:

-	parse(text:String): Aeropuerto
-	of(Codigo: String,Ciudad: String,Pais: String,Nombre: String): Aeropuerto

## Aeropuertos

Es un tipo que representa una poblaci�n de aeropuertos. Una poblaci�n de objetos es un conjunto de objetos de un tipo donde cada uno tiene un identificador distinto de los dem�s. En este caso el identificador es la propiedad c�digo del aeropuerto. Tiene otros m�todos espec�ficos para cada poblaci�n.

Una forma de dise�o de una poblaci�n es con factor�a de tipo _singleton_ que explicaremos abajo. El tipo tiene, adem�s, m�todos para recuperar objetos dado un identificador.

Propiedades:

- Aeropuerto aeropuerto(String codigo)
- Aeropuerto get(Integer i)
- String ciudadDeAeropuerto(String codigo)
- Set\<Aeropuerto\> aeropuertosEnCiudad(String ciudad)
- Integer size()
- Set\<Aeropuerto\> todos()

Operaciones:

-	void addAeropuerto(Aeropuerto a), A�ade un aeropuerto
-	void removeAeropuerto(Aeropuerto a), Elimina un aeropuerto

Representaci�n:

- Lista de aeropuertos uno por l�nea

M�todos de Factor�a:

La factor�a devuelve siempre el objeto _gestorAeropuertos_ actualiz�ndolo primero si es null.

```java
private static Aeropuertos gestorAeropuertos = null;
public static Aeropuertos of() {
	if(Aeropuertos.gestorAeropuertos == null)
		Aeropuertos.gestorAeropuertos = 
			Aeropuertos.of("ficheros_aeropuertos/aeropuertos.csv");
	return gestorAeropuertos;
}

public static Aeropuertos of(String fichero) {
	Set<Aeropuerto> aeropuertos = File2.streamDeFichero(fichero,"Windows-1252")
		.map(x -> Aeropuerto.parse(x))
		.collect(Collectors.toSet());
	Aeropuertos.gestorAeropuertos = new Aeropuertos(aeropuertos);
	return Aeropuertos.gestorAeropuertos;
}
```

Invariante

- los c�digos de los aeropuertos deben ser diferentes

## Aerol�nea

Propiedades:  Inmutable

-	C�digo: String, b�sica, se compone de dos caracteres, Identifica el aeropuerto de manera �nica
-	Nombre: String

Representaci�n:

-	Codigo, Nombre

Igualdad: Si tienen el mismo c�digo. Aunque con la restricci�n impuesta al c�digo todas las propiedades b�sicas ser�n iguales

M�todos de Factor�a:

-	parse(text:String): Aerolinea
-	of(Codigo: String,Nombre: String): Aerolinea

## Aerol�neas

Es un tipo que representa una poblaci�n de aerol�neas. Una poblaci�n de objetos es un conjunto de objetos de un tipo donde cada uno tiene un identificador distinto de los dem�s. En este caso el identificador es la propiedad c�digo de la aerol�nea. Tiene otros m�todos espec�ficos para cada poblaci�n.

Propiedades:

 - Aerolinea aerolinea(String codigo), b�sica
 - Aerolinea get(Integer i)
 - Set\<Aerolinea\> todas()
 - size(): Integer, derivada

Operaciones

-	void addAerolinea(Aerolinea a), A�ade una aerolinea
-	void removeAerolinea(Aerolinea a), Elimina una aerolinea

Representaci�n:

- Lista de aerol�neas una por l�nea

M�todos de Factor�a:

 - Aerolineas of()
 - Aerolineas of(String fichero)

Invariante

- los c�digos de los aeropuertos deben ser diferentes

## VueloProgramado

Propiedades: Inmutable

-	C�digoAerolinea: String, b�sica
-	Numero: String, b�sica, cuatro caracteres que representan un n�mero de vuelo de una aerolinea
-	CodigoDestino: String, b�sica, c�digo del aeropuerto destino
-	CodigoOrigen: String, b�sica, c�digo del aeropuerto origen
-	Precio: Integer, b�sica, debe ser mayor que cero
-	NumPlazas: Integer, b�sica, debe ser mayor que cero	
-	Duraci�n: Duration, b�sica, duraci�n del vuelo
-	Hora: LocalTime, b�sica,
-	DiaSemana, DayOfWeek, b�sica
-	CiudadDestino, String, derivada
-	CiudadOrigen, String, derivada
-	Codigo, String, derivada, identifica de manera �nica un vuelo y se compone de la concatenaci�n del CodigoAerolinea y el Numero.

Representaci�n:

-	Codigo,Nombre

Igualdad: Si tienen el mismo c�digo. Aunque con la restricci�n impuesta al c�digo todas las propiedades b�sicas ser�n iguales

M�todos de Factor�a:

-	parse(text:String): Vuelo
-	of(Codigo: String,Nombre: String): Aerol�nea
-	random(): Vuelo, un vuelo construido aleatoriamente con los aeropuertos y las aerol�neas disponibles. 

## VuelosProgramados

Es un tipo que representa una poblaci�n de vuelos. Una poblaci�n de objetos es un conjunto de objetos de un tipo donde cada uno tiene un identificador distinto de los dem�s. En este caso el identificador es la propiedad c�digo del vuelo que es una propiedad derivada. Tiene otros m�todos espec�ficos para cada poblaci�n.

Propiedades:

- Set\<Vuelo\> todos()
- Vuelo vuelo(String codigo) 
- Vuelo get(Integer index)
- Integer size() 

Operaciones:

-	void addVuelo(Vuelo v), A�ade un vuelo
-	void removeVuelo(Vuelo v), Elimina un vuelo

Representaci�n:

-	 Lista de vuelos un por l�nea

M�todos de Factor�a:

 - Vuelos of()
 - Vuelos of(String fichero)

Invariante

- los c�digos de los aeropuertos deben ser diferentes

## Vuelo

Propiedades: Inmutable

-	C�digoVuelo: String, b�sica
-	Fecha: LocalDateTime, b�sica, fecha-hora de salida
-	NumPasajeros: Integer, b�sica, debe ser mayor o igual a cero
-	Vuelo: Vuelo, derivada
-	Llegada: LocalDateTime, fecha-hora de llegada
-	FechaSalida: LocalDate, fecha de salida
-	HoraSalida: LocalTime, hora de salida

Representaci�n:

-	CodigoVuelo, FechaSalida, HoraSalida

Igualdad: Si tienen el mismo c�digo. Aunque con la restricci�n impuesta al c�digo todas las propiedades b�sicas ser�n iguales

M�todos de Factor�a:

-	parse(text:String): Vuelo
-	of(Codigo: String,Nombre: String): Aerol�nea
-	random(): Vuelo, un vuelo construido aleatoriamente con los aeropuertos y las aerol�neas disponibles. 

## Vuelos

Es un tipo que representa una poblaci�n de ocupacionVuelo. Una poblaci�n de objetos es un conjunto de objetos de un tipo donde cada uno tiene un identificador distinto de los dem�s. En este caso el identificador es el par c�digo del vuelo, fecha que es una propiedad derivada. Tiene otros m�todos espec�ficos para cada poblaci�n.

Propiedades:

- Set\<OcupacionVuelo\> todas()
- OcupacionVuelo ocupacionVuelo(String codigoVuelo, LocalDateTime fecha) 
- OcupacionVuelo get(Integer index)
- Integer size() 

Operaciones:

-	void addOcupacionVuelo(OcupacionVuelo oc), A�ade una ocupaci�n de un vuelo
-	void removeOcupacionVuelo(OcupacionVuelo oc), Elimina una ocupaci�n de un vuelo

Representaci�n:

-	 Lista de ocupaciones una por l�nea

M�todos de Factor�a:

 - OcupacionesVuelos of()
 - OcupacionesVuelos of(String fichero)

Invariante

- los c�digos de los aeropuertos deben ser diferentes




