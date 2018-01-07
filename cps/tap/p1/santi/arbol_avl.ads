--
-- MODULO DE DECLARACION DEL TAD 'diccionario',
-- ALMACENADO EN UN ARBOL AVL
--
-- Version: 1.0
-- Fecha: 10-XI-99
-- Autor: Javier Campos Laclaustra (jcampos@posta.unizar.es)
--
-- Este m�dulo gen�rico implementa el TAD "tabla" (o "diccionario") tal
-- como est� especificado en la lecci�n 23 de los apuntes de
-- "Estructuras de Datos y Algoritmos". Por tanto, no se incluye aqu�
-- su especificaci�n algebraica.
--
-- En esencia, permite almacenar un conjunto de parejas formadas por
-- una "clave" y un "valor" (cuyos tipos son par�metros del m�dulo),
-- y realizar las operaciones b�sicas de inserci�n (teniendo en cuenta
-- que, puesto que la "clave" es �nica, la inserci�n de una "clave" ya 
-- existente supone la modificaci�n del "valor" asociado a la "clave";
-- borrado y b�squeda del "valor" asociado a una "clave".
--
-- La estructura de datos utilizada para almacenar el diccionario es
-- un �rbol AVL (es decir, un �rbol binario de b�squeda 1-equilibrado).
--
generic
  type tp_clave is private; --tipo de la "clave"
  type tp_valor is private; --tipo del "valor"
  with function "<"(izq,dch:tp_clave) return boolean; --relaci�n de orden en tp_clave
  with procedure put_clave(clave:in tp_clave); --escribe una "clave" en pantalla
  with procedure put_valor(valor:in tp_valor); --escribe un "valor" en pantalla
package arbol_avl is
  type avl is limited private; --avl es el nombre dado al TAD diccionario
  procedure vacio(a:out avl);
  -- devuelve un diccionario vac�o
  procedure modificar(a:in out avl; clave:in tp_clave; valor:in tp_valor);
  -- inserta una nueva "clave" con su "valor" en el diccionario;
  -- si la "clave" ya estaba, actualiza su "valor";
  procedure borrar(a:in out avl; clave:in tp_clave);
  -- borra la "clave" (y su "valor") del diccionario;
  -- si la "clave" no estaba en el diccionario, lo deja igual
  procedure buscar(a:in avl; clave:in tp_clave; exito:out boolean; valor:out tp_valor);
  -- si la "clave" est� en el diccionario devuelve su "valor" (y "exito" es true);
  -- en caso contrario, "exito" toma el valor false
  function es_vacio(a:avl) return boolean;
  -- devuelve verdad si y s�lo si el diccionario est� vac�o
  procedure put_inorden(a:in avl);
  -- escribe en pantalla las parejas <"clave": "valor"> (una en cada l�nea)
  -- ordenadas seg�n la relaci�n "<" definida para el tipo tp_clave
  
  
  -- las operaciones que siguen, se implementaron en la fase de depuraci�n
  procedure pintar_arbol(a:in avl);
  -- escribe en pantalla las "claves" del �rbol de manera que se puede ver
  -- de forma aproximada la forma que tiene el �rbol
  function altura(a:avl) return natural;
  -- devuelve la altura de un �rbol NO VACIO
  function equilibrado(a:avl) return boolean;
  -- devuelve true si y s�lo si el �rbol es 1-equilibrado
  function test_factores_equilibrio(a:avl) return boolean;
  -- devuelve true si y s�lo si el campo "equilibrio" es correcto en 
  -- todos los nodos del �rbol
private
  type nodo;
  type avl is access nodo;
  type factor_equilibrio is (pesado_izq, equilibrado, pesado_dch);
  type nodo is
    record
      -- Almacena la palabra 
      clave:tp_clave;
      -- Almacena la definici�n de la palabra
      valor:tp_valor;
      -- Indica el equilibrado del nodo
      equilibrio:factor_equilibrio;
      -- Hijos izquierdo y derecho del nodo
      izq,dch:avl;
    end record;
end arbol_avl;
