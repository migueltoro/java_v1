# Diseño de tipos

Antes de codificar es conveniente diseñar los tipos que usaremos en nuestro código. El diseño de un tipo es como un plano del mismo. En él se indica sus propiedades más relevantes y los mecanismos para crear valores de ese tipo. En la programación orientada a objetos los valores del tipo los llamaremos objetos.

Un tipo es un conjunto de valores, cada uno de esos valores tiene un conjunto de propiedades, adicionalmente un conjunto de operaciones para modificar el valor de las propiedades y mecanismos de factoría para crear los valores del tipo. Cada propiedad tiene un identificador y un tipo del conjunto de tipos previamente existente. Los valores de las propiedades pueden estar restringidos por expresiones lógicas que se indican en el _invariante_ del tipo.

Cada tipo tiene asociado una _dominio del tipo_. Este incluye el conjunto de todos los valores posibles del tipo. Este conjunto de valores se define por comprensión: el conjunto de valores que cumplen el invariante. 

Llamaremos _población de un tipo_ a un conjunto de finito de valores del tipo creado específicamente para un fin. En muchos casos el conjunto de valores será leído de un fichero o construido añadiendo objetos al conjunto. 

Un tipo puede ser inmutable, si no se pueden modificar sus propiedades una vez creado el valor, o mutables si se pueden cambiar. 

Un tipo puede _extender_ a otro (diremos que hereda de o extiende a otro)

Cada propiedad puede ser:

- *Básica*: si su valor no depende del valor de otra propiedad
- *Derivada*: si su valor depende del valor de otras propiedades
- *Individual*: Si el valor de la propiedad es específico de cada valor del tipo
- *Compartida*: Si el valor de la propiedad es compartido por todos los valores del tipo

Las propiedades no pueden cambiar el valor de otras propiedades. Las propiedades pueden tener parámetros para indicar un conjunto de propiedades del mismo tipo. En ese caso podemos indicar el rango permitido para los parámetros. Tambien pueden tener restricciones sobre el conjunto de valores que pueden tomar.
LaLas *operaciones* indican la manera de modificar las propiedades del tipo. Si un tipo no tiene operaciones es inmutable. Las operaciones pueden tener parámetros de entrada y restricciones. Restricciones en los parámetros de entrada que especificamos por un predicado que llamamos *precondición* (@pre p(x)). Restricciones entre los valores de los parámetros de entrada y el valor de las propiedades tras aplicar la operación que especificamos por un predicado llamamos *postcondición* (@post p(x,v)).
LaLas factorías son mecanismos para construir valores de un tipo. Generalmente usaremos los siguientes:

- *of(propiedades)*: Crea un valor del tipo a partir de algunas propiedades del mismo
- *of()*: Crea un valor por defecto del tipo
- *parse(texto)*: Crea un valor del tipo a partir de una cadena de caracteres

- *Representación* es el mecanismo para convertir un valor del tipo en una cadena de caracteres que pueda ser presentada en la consola o grabada en un fichero.

- *Igualdad*: Es una expresión booleana que indica cuando dos valores del tipo son iguales. Por defecto dos valores son iguales si tienen las propiedades básicas iguales.

- *Orden natural*: En algunos tipos se puede definir un orden total sobre los valores del tipo que llamaremos orden natural.

- *Restricciones*: Son predicados que deben cumplir los valores de un tipo o el conjunto de valores de la población de un tipo. Aqui podemos acumular las restricciones de las propiedades y de las operaciones de un tipo. Al conjunto de restricciones también se le suele llamar *Invariante*.


## Diseño de poblaciones

La población de un tipo *E* es un  nuevo tipo *P* cuyos valores son conjuntos de valores del tipo *E* anterior. En este conjunto suele existir una o varias propiedades que identifican de manera única a cada valor del tipo *E* en la población.

Las factorías de las poblaciones las diseñaremos generalmente son el patrón de diseño *singleton*. Este es un mecanismo de factoría que nos permite obtener siempre el mismo valor en las sucesivas llamadas a la factoría. Las poblaciones suelen tener mecanismos para leer los valores de un fichero o de la red en general.

## Implementación

Los tipos se pueden implementar usando una *class* o el mecanismo de *record*. El mecanismo de *record* se usa para tipos inmutables y la *class* para tipos mutables aunque también se puede usar para tipos inmutables.

### record

El mecanismo de *record* sirve para implementar tipos que pueden estar dotados de orden natural y ser inmutables. El mecanismo nos proporciona:

- un constructor definido automáticamente
- una igualdad y un hashcode definidos automáticmente
- una representación definida automáticamente


```
public record Vector2D(Double x,Double y) {
	...
}
```
El tipo Vector2D anterior es inmutable y no tiene orden natural.
Las propiedades básicas son las indicadas después de la línea *record Vector2D(...)*.
La igualdad viene definida por la igualdad de las propiedades básicas.
El hashcode ya está implementado.
Si tiene orden natural este se establece implementando el tipo Comparable<E> lo que nos obliga a implementar el método compareTo(...) donde se establece el mecanismo concreto de ordenación.

```
record Direccion(String calle, String ciudad, Integer zipCode) implements Comparable<Direccion> {	
	...
	@Override
	public int compareTo(Direccion other) {
		Integer r = this.zipCode().compareTo(other.zipCode());
		if (r == 0) r = this.ciudad().compareTo(other.ciudad());
		if (r == 0) r = this.calle().compareTo(other.calle());
		return r;
	}
```

El constructor Direccion(...) viene definido, aunque sólo lo usaremos entro de los métodos de factoría.

```
record Direccion(String calle, String ciudad, Integer zipCode) implements Comparable<Direccion> {

	public static Direccion of(String calle, String ciudad, Integer zipCode) {
		return new Direccion(calle, ciudad, zipCode);
	}
```

El constructor puede ser ajustado a las necesidades del usuario para acumular comprobación de restricciones.

```
record Circulo2D(Punto2D centro,Double radio)  {
	
	public Circulo2D  {
		assert radio >= 0 : String.format("El radio debe ser mayor o igual a cero y es %.2f", radio);
		assert centro != null : "El centro no puede ser nulo";
	}
```

La representación viene definida aunque se puede sobrescribir diseñando el método *toString*.

```
public String toString() {
	return String.format("%s,%s,%d", this.calle(), this.ciudad(), this.zipCode());
}
```

El uso de *record* es útil para tipos inmutables, pero tiene alguns restricciones: los tipos impelementados de esta manera pueden implentar un *interface*, pero no pueden heredar de otroa clase ni ser heredados.

### class

La implementación de tipos mediante *class* es más flexible que con record, pero tenemos que implementar más detalles.

Sirve par implementar tipos mutables e inmutables.

En una *class* hay que tener en cuanta atributos y métodos. Estos, a su vez, pueden ser públicos, privados y compartidos. También pueden ser individuales y compartidos.

Los *atributos* son un conjunto de valores que forman el *estado* del objeto. Usualmente los declaramos privados (*private*) o protegidos (*protected*). También pueden ser publicos (*public*) pero no se recomienda. 

Cada clase debe tener un método *__init__*.

```
public class Persona implements Comparable<Persona> {
	
	private String apellidos;
	private String nombre;
	private LocalDateTime fechaDeNacimiento;
	private String dni;
	private String telefono;
	private Direccion direccion;
```

El el constructor se usa para construir un objeto. El constructor del tipo *Persona* es:

```
protected Persona(String apellidos, String nombre, LocalDateTime fechaDeNacimiento, String dni, 
		String telefono,Direccion direccion) {
		super();
		assert  apellidos.strip().length() > 0: 
				String.format("Los apellidos no pueden estar en blanco");
		assert  nombre.strip().length() > 0: 
				String.format("El nombre no puede estar en blanco");
		assert  fechaDeNacimiento.isBefore(LocalDateTime.now()): 
				String.format("La fecha debe estar en el pasado");
		assert  Persona.checkDni(dni): String.format("El dni no es correcto");
		this.apellidos = apellidos;
		this.nombre = nombre;
		this.fechaDeNacimiento = fechaDeNacimiento;
		this.dni = dni;
		this.telefono = telefono;
		this.direccion = direccion;
	}
```

En el constructor acumulan la comprobaciópn de restricciones y normalizaciones de datos:

Las propiedades son métodos públicos sin parámetros con la anotación como en:

```
public Integer edad() {
		LocalDateTime now = LocalDateTime.now();
		Period p = Period.between(this.fechaDeNacimiento.toLocalDate(), now.toLocalDate());
		return p.getYears();
}
```
Los métodos pueden ser clasificados en observadores y modificadores. Los métodos obervadores no modifican los atributos, los modificadores si y se llaman *operaciones*. Si el tipo no tiene operaciones y sus atributos son privados o pretegidos el tipo es inmutable.

La igualdad y el hashcode se definen con los métodos *equals, hashCode*.

```
@Override
public int hashCode() {
	return Objects.hash(apellidos, direccion, dni, fechaDeNacimiento, nombre, telefono);
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	Persona other = (Persona) obj;
	return Objects.equals(apellidos, other.apellidos) && Objects.equals(direccion, other.direccion)
			&& Objects.equals(dni, other.dni) && Objects.equals(fechaDeNacimiento, 				other.fechaDeNacimiento)
			&& Objects.equals(nombre, other.nombre) && Objects.equals(telefono, other.telefono);
	}
```
Un tipo sin *hashCode* no puede ser la clace de un Map. Ambos métodos *equals, hashCode* es conveniente hacerlos automáticamente con las herramientas proporcionadas por entornos como *Eclipse*.

En Java no hay métodos predefinidos para que representen operadores. 

## Herencia 

En Java una clase puede heredar de otra (*extend*). Esto implica que dispone de sus atributos y métodos públicos y protegidos a los que puede refererise con el prefijo *super...*. Tambien dispone del contructor si es público o pretegido al que se refiere con *super(...)*.

```
public class Alumno extends Persona {
    
	private Double nota;

    private Alumno(String apellidos, String nombre, LocalDateTime fechaDeNacimiento, 
    	String dni, String telefono,Direccion direccion, Double nota) {
		super(apellidos, nombre, fechaDeNacimiento, dni, telefono, direccion);
		assert  0 <= nota && nota <= 14 : 
				String.format("La nota debe estar comprendida entre 0 y 14 y es %.2f", nota);
		this.nota = nota;
	}
	...
```
## Ejemplos

[Direccion](https://github.com/migueltoro/java_v1/blob/master/ClasePrimero/src/us/lsi/ejemplos_b1_tipos/Direccion.java)
[Persona](https://github.com/migueltoro/java_v1/blob/master/ClasePrimero/enunciados/Persona/persona.md)
[Alumno](https://github.com/migueltoro/java_v1/blob/master/ClasePrimero/enunciados/Persona/persona.md)
[Coordenadas](https://github.com/migueltoro/java_v1/blob/master/ClasePrimero/enunciados/Coordenadas/coordenadas.md)


