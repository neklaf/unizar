--
-- MODULO DE DECLARACION DEL TAD "CONJUNTO DE NUMEROS NATURALES DISTINTOS"
-- ALMACENADO EN UN ARBOL AVL
--
-- Version: 0.1
-- Fecha: Julio 2004
-- Autores: Acedo Legarre, Aitor y Faci Miguel, Santiago
-- Descripcion: Este fichero contiene el interface y la declaracion de todos los tipos
--				necesarios para la implementacion del conjunto de numeros naturales distintos

package conj_dm is
	type conj is limited private; --conjunto es el nombre dado al TAD conjunto

	MAX_NUM: constant integer := 100000; -- numero maximo de elementos del monticulo

	--OPERACIONES PROPIAS DEL CONJUNTO DE NUMEROS NATURALES

	procedure crear (c: out conj);
	-- devuelve el conjunto vacio

	procedure vaciar (c: out conj);
	-- elimina los datos del conjunto

	procedure insertar (c: in out conj; num: in natural);
	-- inserta un numero natural en el conjunto; si ya estaba, lo deja igual

	procedure borrar (c: in out conj; num: in natural);
	-- borra el natural del conjunto; si no estaba, lo deja igual

	function buscar (c: conj; num: natural) return boolean;
	-- si el numero esta en el conjunto devuleve verdad; en caso contrario falso

	function dm (c: conj) return natural;
	-- devuelve la distancia minima del conjunto


	--PROCEDIMIENTOS AUXILIARES QUE UTILIZAREMOS EN LAS PRUEBAS

	procedure pintar_conj (c: in conj);
	-- pinta el arbol que representa al conjunto c

	procedure put_inorden (c: in conj);
	-- pinta en inorden los elementos del conjunto

	procedure pintar_inorden (c: in conj);
	-- pinta ordenados de menor a mayor los elementos del conjunto

    procedure listar_predecesores (c: in conj);
	-- pinta de mayor a menor los elementos del conjunto

	procedure pintar_monticulo (c: in conj);
	-- pinta el contenido del monticulo
	
private
	
	--Definicion para el heap
	subtype elemento is integer range 0 .. MAX_NUM;
	subtype indice is integer range 1 .. MAX_NUM;
	type nodo;
	type avl is access nodo;

	type nodo_heap is record
		d: natural;
		e: avl; --Elemento del conjunto al que pertenece la distancia
		--Es obligatorio este campo por el algoritmo de borrado del monticulo
		--Ya que descoloca algunas distancias cuando restauramos la estructura del monticulo
		--y tendremos que hacer visibles esos cambios en los elementos del arbol!!
	end record;

    type vector is array (indice) of nodo_heap;
    type min_heap is record
        v: vector;
        num: elemento; --Numero de elementos del monticulo
    end record;


	--Definicion para el arbol AVL
	type factores_balance is (pesado_izq, balanceado, pesado_der);
	type nodo is record
		val: natural; --Elemento del conjunto
		balance: factores_balance; --Factor de balance del nodo
		izq, der: avl; --hijo izquiero, hijo derecho
		pred, succ: avl; --puntero al predecesor y puntero al sucesor
		i_dist: elemento; --Puntero a la distancia de este nodo con su sucesor en el monticulo
	end record;


	--Definicion del tipo conjunto
	type conj is record
		raiz: avl;
		h: min_heap;
	end record;

end conj_dm;
