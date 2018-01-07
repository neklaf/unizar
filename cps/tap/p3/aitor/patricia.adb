--
-- MODULO DE IMPLEMENTACION DEL TAD "CONJUNTO DE PALABRAS"
-- ALMACENADO EN UN PATRICIA
--
-- Version: 1.0
-- Fecha: Agosto 2004
-- Autores: Acedo Legarre, Aitor y Faci Miguel, Santiago
-- Descripcion: Implementacion de un conjunto de palabras con
--              las operaciones de busqueda e insercion.
--
-- Nota: La implementacion realizada de los algoritmos de busqueda e insercion esta basada en el codigo de 
-- 		Sedgewick proporcionado en clase.


with Ada.Text_IO, ustrings, Ada.Strings.Unbounded, ristras_bits; 
use Ada.Text_IO, ustrings, Ada.Strings.Unbounded, ristras_bits;

package body patricia is

	package ent_io is new integer_io(integer);
	use ent_io; --Implementacion del modulo integer_io para pintar enteros por pantalla

	function crea_conj return ptr_nodo_patricia is
	--Devuelve un puntero a un nodo de Patricia que encabezara el arbol
		r: ptr_nodo_patricia;
	begin
		r := new nodo_patricia'(NULL_ITEM, 1, null, null); --inicializamos el puntero a la raiz
		r.i := r;
		r.d := r;
		r.bit := 1;
		put_line("Patricia incializado.");
		return r;
	end crea_conj;


	function digit(s: ristra_bits; n: integer) return integer is
	--Devuelve el valor del bit indicado por el entero pasado como parametro de la ristra de bits
	begin
		return s.ristra(n);
	end digit;
	
	
	function buscarR(h: in ptr_nodo_patricia; v: in ristra_bits; d: integer) return tp_item is
	--Esta funcion es la representacion en Ada de la funcion presentada por Sedgewick (pagina 639)
	--Basicamente es el recorrido recursivo del Patricia observando los bits de decision
	begin
		if (h.bit <= d) then
			return h.it; --Devolvemos el item del nodo
		end if;
		if (digit(v, h.bit) = 0) then
			return buscarR(h.i, v, h.bit);
		else
			return buscarR(h.d, v, h.bit);
		end if;
	end buscarR;

	
    function buscar (h: ptr_nodo_patricia; v: ristra_bits) return tp_item is
	--Esta funcion es la implementacion en Ada 95 de la funcion presentada por Sedgewick (pagina 639)
		t: tp_item;
	begin
		t := buscarR(h.i, v, -1); -- -1 es un valor que no podra cumplir la condicion del primer if de buscarR
		if (v = t.clave) then
			return t;
		else
		--Estamos en el caso de que no hemos encontrado la clave que buscabamos
			return NULL_ITEM;
		end if;
	end buscar;


	function insertarR (h: ptr_nodo_patricia; x: tp_item; d: integer; p: ptr_nodo_patricia) 
																			return ptr_nodo_patricia is
	--Esta funcion es la implementacion en Ada95 de la funcion presentada por Sedgewick (pagina 641)
		v: ristra_bits;
		t: ptr_nodo_patricia;
	begin
		v := x.clave;
		if ((h.bit >= d) OR (h.bit <= p.bit)) then
			t := new nodo_patricia;
			t.it := x;
			t.bit := d;
			if (digit(v, t.bit) = 1) then
				t.i := h;
				t.d := t;
			else
				t.i := t;
				t.d := h;
			end if;
			return t;
		end if;

		if (digit(v, h.bit) = 0) then
			h.i := insertarR(h.i, x, d, h);
		else
			h.d := insertarR(h.d, x, d, h);
		end if;
		return h;
	end insertarR;


	procedure insertar (h: in out ptr_nodo_patricia; x: in tp_item) is
	--Este procedimiento es la implementacion en Ada95 del presentado por Sedgewick (pagina 641)
		v, w: ristra_bits;
		i: integer;
	begin
		v := x.clave;
		w := buscarR(h.i, v, -1).clave; --buscamos la clave mas proxima a el nuevo item a insertar
		if (v = w) then	return;	end if; --No hay cadenas repetidas en nuestro Patricia
		
		
			i := diferencia(v, w); --Devolvemos el orden del primer bit en el que se diferencian las dos secuencias
	

		h.i := insertarR(h.i, x, i, h); --insertaremos el nuevo item atendiendo al bit de la posicion i
	end insertar;

	
	procedure pintarR (h: ptr_nodo_patricia; d: integer) is
	--Procedimiento recursivo que recorre ordenadamente el Patricia
	begin
		if (h.bit <= d) then
			put_line(h.it.p); return;
		end if;
		pintarR(h.i, h.bit);
		pintarR(h.d, h.bit);
	end pintarR;


	procedure pintar_conjunto (r: ptr_nodo_patricia) is
	--Pinta el conjunto por pantalla gracias a la funcion recursiva anterior que recorre el arbol
	begin
		pintarR(r.i, -1);
	end pintar_conjunto;

end patricia;
