# Centro

Se quiere hacer una pequeÃ±a aplicaciÃ³n que ayude en la gestiÃ³n de un centro universitario. Un centro universitario tiene profesores, asignaturas y alumnos. Las asignaturas pueden tener varios grupos o ninguno. Los profesores son asignados para impartir grupos de asignaturas mientras que los alumnos se matriculan en grupos de algunas de las asignaturas. 

Los datos para gestionar este centro educativo provienen de 5 archivos diferentes:

1.	profesores.txt: datos de los profesores del centro educativo. Cada una de las lÃ­neas del fichero tienen el mismo formato con líneas de la forma: 

```
BadÃ­a Carretero,Felipa,53045701L,1998-10-26 18:31,+34722071515,Avenida de Leonardo Pont 26;Huelva;22501,Doctor
```

Con propiedades: apellidos, nombre, dni, fecha de nacimiento, telÃ©fono, direcciÃ³n y tÃ­tulo 

2.	alumnos.txt: datos de los profesores del centro educativo. Cada una de las lÃ­neas del fichero tienen el mismo formato con lÃ­neas de la forma:
 
```
Girona Gómez,Nieves,92521023G,1998-01-20 02:07,+34721158714,Paseo de Herminio Maestre 71;Ourense;16866,8.3
```

Con propiedades: apellidos, nombre, dni, fecha de nacimiento, telÃ©fono, direcciÃ³n y nota media de entrada al grado.

3.	asignaturas.txt: datos de las asignaturas que se imparten en el centro universitario. Cada una de las lÃ­neas del fichero tienen el mismo formato con lÃ­neas de la forma: 

```
0,Fundamentos bÃ¡sicos de la informÃ¡tica,6,3

```

Con propiedades: id, nombre, nÃºmero de crÃ©ditos y nÃºmero de grupos.


4.	matriculas.txt: datos de las matrÃ­culas que han realizado cada uno de los alumnos en las distintas asignaturas del centro. Cada una de las lÃ­neas del fichero tienen el mismo formato con lÃ­neas de la forma: 

```
72842943B,0,0
```

Con propiedades: dni del alumno, identificador de asignatura, nÃºmero de grupo.


5.	asignaciones.txt: datos de las asignaciones entre los profesores y las asignaturas que imparten. Cada una de las lÃ­neas del fichero tienen el mismo formato con lÃ­neas de la forma: 

```
14218974Y,5,0
```

Con propiedades: dni del profesor, identificador de asignatura, nÃºmero de grupo

# Tipos

## Direccion (inmutable)

Propiedades:

-	calle, de tipo String, bÃ¡sica, consultable. 
-	ciudad, de tipo String, bÃ¡sica, consultable. 
-	zip_code, de tipo Integer, bÃ¡sica, consultable. 

MÃ©todos de factorÃ­a: 
-	parse(String text): Construye un objeto a partir de una cadena de caracteres con la informaciÃ³n de las propiedades bÃ¡sicas. La cadena de caracteres tendrÃ¡ el formato â€œcalle;ciudad;zip_codeâ€�.

## Persona (inmutable)

Propiedades:

-	apellidos, de tipo String, bÃ¡sica, consultable. Los apellidos no pueden estar en blanco.
-	nombre, de tipo String, bÃ¡sica, consultable. El nombre no puede estar en blanco.
-	dni, de tipo String, bÃ¡sica, consultable. El dni debe estar compuesto de 8 nÃºmeros y una letra mayÃºscula al final, ademÃ¡s, esa Ãºltima letra debe corresponder al nÃºmero. En la siguiente url puede observar cuÃ¡l es la metodologÃ­a que se usa en EspaÃ±a para calcular la correspondencia entre nÃºmero y letra del DNI. 
-	fechaDeNacimiento, de tipo LocalDateTime, bÃ¡sica, consultable. La fecha/hora de nacimiento debe estar en el pasado.
-	telefono, de tipo String, bÃ¡sica, consultable. 
-	direccion, de tipo Direccion, bÃ¡sica, consultable. 
-	edad, de tipo Integer, derivada, consultable. 
-	siguienteCumple, de tipo LocalDate, derivada, consultable. Devuelve cuÃ¡ndo serÃ­a el cumpleaÃ±os de dicha persona el aÃ±o que viene.
-	diaSemanaSiguienteCumple, de tipo DayOfWeek, derivada, consultable. Devuelve el dÃ­a de la semana (MONDAY, TUESDAY, WEDNESDAY, etc), del siguiente cumpleaÃ±os.
-	diaSemanaNacimiento, de tipo DayofWeek, derivada. Devuelve el dÃ­a de la semana en el que naciÃ³ la persona (MONDAY, TUESDAY, WEDNESDAY, etc). 
-	mesCumple, de tipo Integer, derivada, consultable. Devuelve el mes en el que naciÃ³ la persona representado como un entero (1 a 12).
-	nombreCompleto, de tipo String, derivada, consultable. Devuelve el nombre completo de la persona en formato â€œnombre apellidosâ€�.

MÃ©todos de factorÃ­a: 

-	of(String apellidos, String nombre, String dni, LocalDateTime fechaDeNacimiento, String telefono, Direccion direccion) Construye un objeto a partir de un valor para cada una de las propiedades bÃ¡sicas del tipo. 
-	parse(String text, DateTimeFormatter ft) Construye un objeto a partir de una cadena de caracteres con la informaciÃ³n de las propiedades bÃ¡sicas. La cadena de caracteres tendrÃ¡ el formato â€œapellidos,nombre,dni,fecha_de_nacimiento,telefono,calle;ciudad;zip_codeâ€�. La variable ft nos indica el formato de la fecha de nacimiento.
-	parse(String text) Construye un objeto a partir de una cadena de caracteres con la informaciÃ³n de las propiedades bÃ¡sicas. El patrÃ³n de fecha a utilizar para la fecha de nacimiento serÃ¡ "yyyy-MM-dd HH:mm". PISTA: debe llamar al mÃ©todo parse anterior.
-	parseList(List\\<String\> partes, DateTimeFormatter ft) Construye un objeto a partir de una lista de cadenas de caracteres con la informaciÃ³n de las propiedades bÃ¡sicas. La lista de cadena de caracteres tendrÃ¡ el formato:
```
[apellidos, nombre, dni, fechaDeNacimiento, telefono, calle;ciudad;zip_code]. 
```
La variable ft nos indica el formato de la fecha de nacimiento.
-	parseList(List\<String\> partes) Construye un objeto a partir de una lista de cadenas de caracteres con la informaciÃ³n de las propiedades bÃ¡sicas. El patrÃ³n de fecha a utilizar para la fecha de nacimiento serÃ¡ _yyyy-MM-dd HH:mm_. 

RepresentaciÃ³n como cadena: 
nombre apellidos, de aÃ±os, nacido el fecha nacimiento a las hora nacimiento.
Por ejemplo Ramiro Casares Amador, de 19, nacido el 14-06-2003 a las 10:02

Debemos realizar un test al final de la implementaciÃ³n del tipo Persona que dÃ© como resultado la siguiente salida por consola: 

```
Ramiro Casares Amador, de 19, nacido el 14-06-2003 a las 10:02
- La fecha de nacimiento de Ramiro es 14-06-2003
- La edad de Ramiro es 19
- Su próximo cumpleaños será un WEDNESDAY
```
## Alumno 

Propiedades: extiende a Persona  y es inmutable

-	nota, de tipo Double, bÃ¡sica, consultable. Indica la nota media de acceso al grado. La nota debe ser positiva y estar comprendida entre 0 y 14.

MÃ©todos de factorÃ­a: 

-	of(Persona p, Double Nota): Construye un objeto a partir de una objeto de tipo Persona y una nota.
-	parse(String text): Construye un objeto a partir de una cadena de texto con la informaciÃ³n de las propiedades bÃ¡sicas del tipo. La cadena de caracteres tendrÃ¡ el formato 
â€œapellidos,nombre,dni,fecha_de_nacimiento,telefono,calle;ciudad;zip_code,notaâ€�. 

RepresentaciÃ³n como cadena: 

La misma representaciÃ³n que el tipo del que hereda aÃ±adiÃ©ndole ademÃ¡s la nota de entrada al grado.
Por ejemplo
```
Ramiro Casares Amador, de 19, nacido el 14-06-2003 a las 10:02 con nota de entrada 10.5
```

## Alumnos

Es un tipo que representa una poblaciÃ³n de alumnos. Una poblaciÃ³n de objetos es un conjunto de objetos de un tipo donde cada uno tiene un identificador distinto de los demÃ¡s. En este caso el identificador es el dni del alumno. Tiene otros mÃ©todos especÃ­ficos para cada poblaciÃ³n.

Propiedades:

-	Alumno alumno(String dni)
- Alumno get(Integer index)
- Integer size()
- Set\<Alumnos\> todos()

Operaciones:

-	void addAlumno(Alumno a), AÃ±ade un alumno
-	void removeAlumno(Alumno a), Elimina un alumno

RepresentaciÃ³n:

- Lista de alumnos uno por lÃ­nea

MÃ©todos de FactorÃ­a:

- Alumnos of(String file)
- Alumnos of()

Invariante

- los dnis de los alumnos deben ser diferentes

## Profesor

Propiedades extiende a Persona  y es inmutable

-	titulo, de tipo Titulo(Enum), bÃ¡sica, consultable. Indica la titulaciÃ³n que posee, puede tomar los valores Diplomado, Master o Doctor.

MÃ©todos de factorÃ­a: 

-	of(Persona p, Titulo titulo): Construye un objeto a partir de una objeto de tipo Persona y un tÃ­tulo.
-	parse(String text): Construye un objeto a partir de una cadena de texto con la informaciÃ³n de las propiedades bÃ¡sicas del tipo. La cadena de caracteres tendrÃ¡ el formato â€œapellidos,nombre,dni,fecha_de_nacimiento,telefono,calle;ciudad;zip_code,tituloâ€�. Pista: para su implementaciÃ³n deberÃ¡ usar el mÃ©todo de factorÃ­a of del tipo Profesor y el mÃ©todo de factorÃ­a parseList del tipo Persona.

RepresentaciÃ³n como cadena: 

La misma representaciÃ³n que el tipo del que hereda aÃ±adiÃ©ndole ademÃ¡s la titulaciÃ³n que posee.
Por ejemplo 
```
Felipa BadÃ­a Carretero, de 24, nacido el 25-06-1994 a las 12:45 con titulo Doctor
```

## Profesores

Es un tipo que representa una poblaciÃ³n de profesores. Una poblaciÃ³n de objetos es un conjunto de objetos de un tipo donde cada uno tiene un identificador distinto de los demÃ¡s. En este caso el identificador es el dni del profesor. Tiene otros mÃ©todos especÃ­ficos para cada poblaciÃ³n.

Propiedades:

- Profesor profesor(String dni)
- Profesor get(Integer index)
- Integer size()
- Set\<Profesor\> todos()

Operaciones:

-	void addProfesor(Profesor p), AÃ±ade un profesor
-	void removeProfesor(Profesor a), Elimina un profesor

RepresentaciÃ³n:

- Lista de profesores uno por lÃ­nea

MÃ©todos de FactorÃ­a:

- Profesores of(String file)
- Profesores of()

Invariante

- los dnis de los profesores deben ser diferentes

## Asignatura

Propiedades, inmutable

-	ida, de tipo Integer, bÃ¡sica, consultable. Indica el identificador de la asignatura.
-	nombre, de tipo String, bÃ¡sica, consultable. Indica el nombre de la asignatura.
-	creditos, de tipo Integer, bÃ¡sica, consultable. Indica el nÃºmero de crÃ©ditos de la asignatura. 
-	numGrupos, de tipo Integer, bÃ¡sica, consultable. Indica el nÃºmero de grupos que tiene esa asignatura.

MÃ©todos de factorÃ­a: 

-	parse(String text): Construye un objeto a partir de una cadena de texto con la informaciÃ³n de las propiedades bÃ¡sicas del tipo. La cadena de caracteres tendrÃ¡ el formato 

```
id,nombre,creditos,numMaxGrupos
```

RepresentaciÃ³n como cadena: nombre, crÃ©ditos, numMaxGrupos

## Asignaturas

Es un tipo que representa una poblaciÃ³n de asignaturas. Una poblaciÃ³n de objetos es un conjunto de objetos de un tipo donde cada uno tiene un identificador distinto de los demÃ¡s. En este caso el identificador es el ida de la asignatura. Tiene otros mÃ©todos especÃ­ficos para cada poblaciÃ³n.

Propiedades:

- Asignatura asignatura(Integer ida)
- Asignatura get(Integer index)
- Integer size()
- Set\<Asignatura\> todas()

Operaciones:

-	void addAsignatura(Asignatura a), AÃ±ade una asignatura
-	void removeAsignatura(Asignatura a), Elimina una asignatura

RepresentaciÃ³n:

- Lista de asignaturas uno por lÃ­nea

MÃ©todos de FactorÃ­a:

- Asignaturas of(String file)
- Asignaturas of()

Invariante

- los ida de las asignaturas deben ser diferentes

## Asignacion

Indica una asignaciÃ³n de un profesor a un grupo

Propiedades, inmutable

-	dni, de tipo String, bÃ¡sica, consultable. Indica el dni del profesor.
-	ida, de tipo Integer, bÃ¡sica, consultable. Identificador de la asignatura.
-	idg, de tipo Integer, bÃ¡sica, consultable. Identificador del grupo que imparte el profesor. 

MÃ©todos de factorÃ­a: 

-	parse(String text): Construye un objeto a partir de una cadena de texto con la informaciÃ³n de las propiedades bÃ¡sicas del tipo. La cadena de caracteres tendrÃ¡ el formato _dni,ida,idg_. 

## Asignaciones

Es un tipo que representa una poblaciÃ³n de asignaciones. 

Propiedades:

- Asignacion get(Integer index)
- Integer size()
- Set\<Asignacion\> todas()

Operaciones:

-	void addAsignacion(Asignacion a), AÃ±ade una asignacion
-	void removeAsignacionAsignacion a), Elimina una asignacion

RepresentaciÃ³n:

- Lista de asignaciones uno por lÃ­nea

MÃ©todos de FactorÃ­a:

- Asignaciones of(String file)
- Asignaciones of()

## Matricula 

Indica una asignaciÃ³n de un alumno a un grupo

Propiedades, inmutable

-	dni, de tipo String, bÃ¡sica, consultable. Indica el dni del profesor.
-	ida, de tipo Integer, bÃ¡sica, consultable. Identificador de la asignatura.
-	idg, de tipo Integer, bÃ¡sica, consultable. Identificador del subgrupo que se le ha asignado al alumno dentro de esa asignatura. 

MÃ©todos de factorÃ­a: 

-	of(String dni,Integer ida,Integer idg): Construye un objeto a partir de una variable por cada propiedad bÃ¡sica del tipo.
-	parse(String text): Construye un objeto a partir de una cadena de texto con la informaciÃ³n de las propiedades bÃ¡sicas del tipo. La cadena de caracteres tendrÃ¡ el formato _dni,ida,idg_. 

RepresentaciÃ³n como cadena:  dni, ida, idg

## Matriculas

Es un tipo que representa una poblaciÃ³n de matriculas. 

Propiedades:

- Matricula get(Integer index)
- Integer size()
- Set\<Matricula\> todas()

Operaciones:

-	void addMatricula(Matricula a), AÃ±ade una matricula
-	void removeMatricula(Matricula a), Elimina una matricula

RepresentaciÃ³n:

- Lista de matriculas uno por lÃ­nea

MÃ©todos de FactorÃ­a:

- Matriculas of(String file)
- Matriculas of()

## Grupo

Propiedades: Inmutable

- ida, de tipo Integer: identificador de asignatura 
- idg, de tipo Integer identificador de grupo de una asignatura

MÃ©todo de factorÃ­a

- Grupo of(Integer ida, Integer idg)

## Grupos

Es un tipo que representa una poblaciÃ³n de grupos. 

Propiedades

- Integer size() 
- Set\<Grupo\> todos() 

MÃ©todos de factorÃ­a

- Grupos of(Centro centro)

## Centro

Representa un centro escolar

Propiedades:

-	alumnos, de tipo Alumnos, bÃ¡sica. Los alumnos que hay en el centro.
-	profesores, de tipo Profesores, bÃ¡sica. Los profesores del centro. 
-	asignaturas, Asignaturas, bÃ¡sica. Las asignaturas que se imparten en el centro. 
-	matriculas, de tipo Matriculas, bÃ¡sica. Las matrÃ­culas que se han hecho en ese centro.
-	asignaciones, de tipo Asignaciones, bÃ¡sica. Las asignaciones entre profesores y grupos que se han llevado a cabo en el centro. 

MÃ©todos de factorÃ­a: 

-	Centro of(): Construye un objeto a partir de los ficheros por defecto
-	Centro of(String alumnos, String profesores, String asignaturas, String matriculas, String asignaciones): Construye un objeto a partir de los ficheros de texto indicados




