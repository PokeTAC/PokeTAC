Guia de archivos

Arch. pokedex
Es la lista de los pokemones disponibles para escoger
cada pokemon ocupa 3 lineas del archivo las cuales son las sgts:
1)Nombre
2)Tipo-> un pokemon puede tener mas de 1 tipo,estos estan separados por un '/'
3)Stats-> linea que tiene 6 valores los cuales son:
	HitPoints(vida) Ataque Defensa At.Especial Def. Especial Velocidad
todos los valores estan separados por un espacio

Arch. types

Es la lista de los tipos de pokemon y ataques que existen y las relaciones que tienen entre ellos(debilidades o ventajas)
existe una linea por cada tipo con las siguiente disposicion:
 tipo tipo1(#) tipo2(#) 
La forma de utilizarlo es la sgt:
	el primer tipo de la linea corresponde al tipo de ataque del pokemon atacante en la batalla,los siguientes tipos en esa linea son los diferentes tipos que puede ser el pokemon defensor y el numero que se encuentra entre los parentesis es el multiplicador del daño que se hara,este puede aumentar,disminuir o anular el daño .

Arch. de ataques

estos archivos tienen la sgt estructura de nombre a_nombrepokemon
para poder cargar el pool de 8 ataques posibles de cada pokemon usando su nombre
la estructura de esos archivos es la siguiente
1 linea= nombre del ataque
2 linea =tipo del ataque
3 linea= se divide en 3 campos separados por espacios
los cuales son
daño accuracy estado
Ahora una nota sobre estado..si el ataque causaria un cambio de estado en el poquemon que recibe el ataque este estara definido por la letra que salga en esa posicion siendo:
 P=paralizado 
 D=dormido
 E=envenenado
 

Para aclarar un ejemplo de la tercera linea
 considerando 100 100 P
		se leeria como ..un ataque que causa 100 de daño,con 100 de accuracy y que causa paralisis 

para no complicarlo solo consideremos esos estados algunos ataques no tiene daño pero causan estados y otros tienen valor de 999 en accuracy para que al ser remplazado en la formula no fallen
