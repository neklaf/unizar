--
-- MODULO DE DECLARACION DEL TAD "CONJUNTO DE PALABRAS"
-- ALMACENADO EN UN PATRICIA
--
-- Version: 1.0
-- Fecha: Julio 2004
-- Autores: Acedo Legarre, Aitor y Faci Miguel, Santiago
-- Descripcion: Este fichero contiene el interface y la declaracion del tipo de datos cojunto de palabras
--

with ustrings, ristras_bits; use ustrings, ristras_bits;

package patricia is

	--Definicion del tipo item
	type tp_item is record
		clave: ristra_bits; --ristra de bits utilizada en la busqueda y la insercion
		p: ustring; --la palabra almacenada
	end record;

	type nodo_patricia;
	type ptr_nodo_patricia is access nodo_patricia;

	--Definicion del nodo que forma el Patricia
	type nodo_patricia is record
		it: tp_item; --encapsulamos la clave con su valor por la implementacion de Sedgewick
		bit: integer; --El orden del bit que tenemos que mirar en cada comparacion
		i, d: ptr_nodo_patricia; --enlaces internos y externos
	end record;

	--Definicion del tipo conjunto de palabras
	type conj is record
		raiz: ptr_nodo_patricia; --Raiz del Patricia
	end record;


	NULL_ITEM: tp_item := (((Others => 0),0),U("$")); --El item nulo con una cadena imposible


	function crea_conj return ptr_nodo_patricia;
	--Inicializa el nodo raiz con una cadena que no se puede dar en el arbol

	function buscar (h: ptr_nodo_patricia; v: ristra_bits) return tp_item;
	--Devuelve el item que corresponde con la clave buscada y si no 

	procedure insertar (h: in out ptr_nodo_patricia; x: in tp_item);
	--Inserta un item en el lugar correspondiente siempre y cuando ese item no estuviera en el Patricia

 	procedure pintar_conjunto (r: ptr_nodo_patricia);
	--Pinta por pantalla el contenido del Patricia


end patricia;
