--|:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
--| MODULO DE DECLARACION DEL TAD 'lista generica con acceso por posicion'
--|:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::

generic
  type elemento is private;
package listaGenerica is

  type lista is limited private;

  
  procedure creaVacia(l:out lista);
  procedure aniadePrincipio(e:in elemento; l:in out lista);
  procedure concatena(l1:in out lista; l2:in lista);
  procedure creaUnitaria(e:in elemento; l:out lista);
  procedure aniadeFinal(l:in out lista; e:in elemento);
  procedure eliminaPrimero(l:in out lista);
  procedure eliminaUltimo(l:in out lista);
  function observaPrimero(l:in lista) return elemento;
  --observaPrimero quiere decir que devuelve el primer elemento de la lista
  function observaUltimo(l:in lista) return elemento;
  function long(l:in lista) return natural;
  function esta(e:in elemento; l:in lista) return boolean;
  function esVacia(l:in lista) return boolean;
  procedure inser(l:in out lista; i:in positive; e:in elemento);
  procedure borra(l:in out lista; i:in positive);
  procedure modif(l:in out lista; i:in positive; e:in elemento);
  function observa(l:in lista; i:in positive) return elemento;
  function pos(e:in elemento; l:in lista) return positive;
  procedure asignar(nueva:out lista; vieja:in lista);
  procedure liberar(l:in out lista);
  ------------------------------------------
  --Utilizados por Carol y creo que por mi tambien
  function inicializaActual(l: in out lista; pos: in positive)return elemento;
  -- pone al puntero actual apuntando al dato de posicion pos y dev el dato.
  function dameActual(l: in out lista) return elemento;
  -- devuelve el elemento correspondiente al dato apuntado por actual
 function siguienteActual (l: in out lista) return elemento;
 -- puntero actual apunta al dato siguiente y dev el dato.
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
                end record;
end listaGenerica;
