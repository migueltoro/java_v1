# Matriz

## Matriz \<E\> 

Es un tipo diseñado mediante la interfaz _Matriz\<E\>__ que será implementada mediante la clase abstracta _MatrizA\<E\>_. Esta clase abstracta tendrá a su vez dos implementaciones, una funcional, con la clase _MatrizF\<E\>_ y otra imperativa con la clase _MatrizI\<E\>_. La clase abstracta servirá para diseñar el código que es común a _MatrizF\<E\>_ y _MatrizI\<E\>_. Aquellos métodos que se implementen de formas distintas, según sea funcional o imperativo, deberán incluir el modificador _abstract_ en la cabecera del método.

Atributos:

- datos, de tipo _List\<List\<E\>\>_. No consultable. Contiene los datos de la matriz. Por ejemplo, la matriz 
$$
\begin{bmatrix}
0 && 1 && 4 \\
1 && 2 && 1 \\
4 && 1 && 8
\end{bmatrix}
$$

vendría representada por la lista _\[\[0,1,4\], \[1,2,1\], \[4,1,8\]\]_. 

Se debe tener en cuenta que el número de filas y de columnas tiene que ser mayor que 0. Además, todas las filas tienen que tener el mismo tamaño. 

Propiedades

- _nf_, Integer, derivada. Indica el número de filas, las filas se enumeran de arriba abajo, es decir, la fila cero sería la primera. Consultable.
- _nc_, Integer, derivada. Indica el número de columnas, las columnas se enumeran de izquierda a derecha, es decir, la primera columna sería la que está más a la izquierda. Consultable.
- _get(Integer f, Integer c)_, de tipo E, derivada. Obtiene el elemento que se encuentra en la fila f y columna c con la restricción $0≤f<nf,0≤c<nc$.
- _set(Integer f, Integer c, E e)_. Cambia el valor del elemento que se encuentre en la posición $(f, c)$. El nuevo valor viene determinado por el parámetro $e$.
- _esSimetrica_, de tipo _Boolean_, derivada. Se dice que una matriz es simétrica cuando es una matriz cuadrada y es igual a su traspuesta, es decir: $A= A^T$. Por ejemplo es una matriz simétrica la siguiente:
$$
\begin{bmatrix}
0 && 1 && 4 \\
1 && 2 && 1 \\
4 && 1 && 8
\end{bmatrix}
$$
- _traspuesta_, de tipo _MatrizA\<E\>_, derivada. La matriz transpuesta, está dada por
$$ {A^T}_{ij} = A_{ji}$$
en donde el elemento $a_{ji}$ de la matriz original se convertirá en el elemento $a_{ij}$ de la matriz traspuesta. Por ejemplo, siendo la matriz 
$$
A = \begin{bmatrix}
2 && 1 \\
3 && 2 \\
4 && 5
\end{bmatrix} \ \

A^T = \begin{bmatrix}2 && 3 && 4 \\
1 && 2 && 5 
\end{bmatrix}
$$

Métodos de factoría: 

- _of(List\<List\<E\>\> datos)_: Construye un objeto a partir de una lista de elementos de tipo E. 
- _ofFile(String fichero, String sep, Function<String, E> t)_: Construye un objeto a partir de un fichero de texto cuyos elementos vienen separados por el separador _sep_. El elemento _t_ es una función que transforma cada elemento del fichero en un objeto de tipo E. 

Representación como cadena: 
$$
\begin{matrix}
1 && -1 && 2 \\
3 && 1 && 0 \\
0 && 0 && 1
\end{matrix}
$$
Criterio de igualdad: Dos matrices son iguales si tienen los mismos valores en cada una de las casillas.

Orden natural: No tiene.

## MatrizL 

El tipo _MatrizL_ hereda del tipo _Matriz\<Long\>_. Este tipo tendrá dos implementaciones: _MatrizLF_ (funcional) y _MatrizLI_ (imperativo). 

Propiedades:

- _esAntisimetrica_, de tipo _Boolean_, derivada. Una matriz antisimétrica es una matriz cuadrada A cuya transpuesta es igual a su negativa $A^T = -A$. Por ejemplo 

$$ A =
\begin{bmatrix}
0 && -3 && -2 \\
3 && 0 && 1 \\
2 && -1 && 0
\end{bmatrix} \ \
A^T =
\begin{bmatrix}
0 && 3 && 2 \\
-3 && 0 && -1 \\
-2 && 1 && 0
\end{bmatrix} = A
$$
Métodos de factoría

- _as(MatrizL\<Long\> m)_: Construye un objeto los datos que contiene la matriz m.
- _ofL(List\<List\<Long\>\> datos)_: Construye un objeto a partir de una lista de elementos de tipo Long.

Otros métodos: 

- _add(MatrizL m)_: Realiza la suma de matrices.  Hay que comprobar previamente que el número de filas y de columnas de ambas matrices son iguales, en caso contrario no se puede calcular la suma. Si las matrices $A = [a_{ij}]$ y $B = [b_{ij}]$ tienen la misma dimensión, la matriz suma es: $A+B= [a_{ij}+b_{ij}]$. Por ejemplo, sea 
$$ A =
\begin{bmatrix}
2 && 3 && 4 \\
1 && 2 && 5
\end{bmatrix} \ \ 
B =
\begin{bmatrix}
3 && 0 && 1 \\
8 && 3 && 3
\end{bmatrix} \ \
A+B =
\begin{bmatrix}
5 && 3 && 5 \\
9 && 5 && 7
\end{bmatrix}
$$
- _subtract(MatrizL m)_: Realiza la resta de matrices.  Hay que comprobar previamente que el número de filas y de columnas de ambas matrices son iguales, en caso contrario no se puede calcular la resta.
$$ A =
\begin{bmatrix}
2 && 3 && 4 \\
1 && 2 && 5
\end{bmatrix} \ \ 
B =
\begin{bmatrix}
3 && 0 && 1 \\
8 && 3 && 3
\end{bmatrix} \ \
A-B =
\begin{bmatrix}
-1 && 3 && 3 \\
-7 && -1 && 2
\end{bmatrix}
$$
- _multiply(MatrizL m): Dos matrices  A y B son multiplicables si el número de columnas de A coincide con el número de filas de B. El elemento $c_{ij}$ de la matriz producto se obtiene multiplicando cada elemento de la fila $f$ de la matriz A por cada elemento de la columna $c$ de la matriz B y sumándolos. 
- _negate_: Negar una matriz significa cambiarle el signo a cada uno de sus elementos.
- _pow(Integer n)_: Para calcular la potencia de una matriz, se debe multiplicar la matriz por ella misma tantas veces como indique el exponente. 


