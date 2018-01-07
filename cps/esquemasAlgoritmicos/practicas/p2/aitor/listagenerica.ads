--|:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
--| MODULO DE DECLARACION DEL TAD 'lista generica con acceso por posicion'
--|:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
generic
  type elemento is private;
package listaGenerica is

  type lista is limited private;
  --No se si tiene importancia el hecho de que la lista sea limitada
  --Buscar el significado del modificador 'limited' ??
  --Creo que no se pueden haceer operaciones de asignacion aunque no lo entendi muy bien!
  --(volver a leer la explicacion del modificador 'limited')

  
  procedure creaVacia(l:out lista);
  --Asigan los valores iniciales para la lista

  procedure aniadePrincipio(e:in elemento; l:in out lista);
  --

  procedure concatena(l1:in out lista; l2:in lista);
  --

  procedure creaUnitaria(e:in elemento; l:out lista);
  --

  procedure aniadeFinal(l:in out lista; e:in elemento);
  --

  procedure eliminaPrimero(l:in out lista);
  --

  procedure eliminaUltimo(l:in out lista);
  --

  function observaPrimero(l:in lista) return elemento;
  --

  function observaUltimo(l:in lista) return elemento;
  --

  function long(l:in lista) return natural;
  --

  function esta(e:in elemento; l:in lista) return boolean;
  --

  function esVacia(l:in lista) return boolean;
  --

  procedure inser(l:in out lista; i:in positive; e:in elemento);
  --

  procedure borra(l:in out lista; i:in positive);
  --

  procedure modif(l:in out lista; i:in positive; e:in elemento);
  -- 

  function observa(l:in lista; i:in positive) return elemento;
  --

  function pos(e:in elemento; l:in lista) return positive;
  --Devuelve la posicion del elemento (tramo) en la lista

  procedure asignar(nueva:out lista; vieja:in lista);
  --

  procedure liberar(l:in out lista);
  --

  ------------------------------------------
  --function inicializaActual(l: in out lista; pos: in positive)return elemento;
  -- pone al puntero actual apuntando al dato de posicion pos y dev el dato.
  procedure inicializaActual(l: in out lista; pos: in positive; e: out elemento);


  --function dameActual(l: in out lista) return elemento;
  -- devuelve el elemento correspondiente al dato apuntado por actual
  procedure dameActual(l: in out lista; e: out elemento);


  --function siguienteActual (l: in out lista) return elemento;
  -- puntero actual apunta al dato siguiente y dev el dato.
  procedure siguienteActual(l: in out lista; e: out elemento);

  function observaUltCar(l: in lista) return natural;
  --Devuelve el campo UltCar de la cada una de las listas = lineas
  -------------------------------------------

private

  type unDato;
  type ptUnDato is access unDato;
  type unDato is record
                   dato:elemento;
                   sig:ptUnDato;
                 end record;
  type lista is record
                  prim, ult, actual:ptUnDato;
				  -- prim apunta al primer dato de la lista, ult al ultimo y
				  -- acual se inicializa en una posicion y cada vez que se utilice
				  -- pasa a la posicion siguiente. Por defecto apunta al primero.
                  n: natural; --numero de elementos de la lista.
		  ultCar: natural; --columna del ultimo caracter de la lista
                end record;
end listaGenerica;
