with ada.text_io, Ada.Command_Line, Ada.Strings.Bounded, ada.integer_text_io, ada.float_text_io, listaGenerica; 
use ada.text_io, Ada.Command_Line, Ada.Strings.Bounded, ada.integer_text_io, ada.float_text_io;
with ada.unchecked_deallocation;




package calculo is



type Tramo is private;

type Pareja is private;



package listaTramos is new listaGenerica(Tramo);
	use listaTramos;
	
package listaParejas is new listaGenerica(Pareja);
	use listaParejas;


--Definicion de constantes
MAX:constant integer:=30;
POND: constant float:=0.6;




--Definicion de funciones

function calculaCosteSimple (ini1,ini2,long1,long2 : integer) return float;


procedure calculaMatrizCosteS( num: in integer);



--function costeDivision (orig, d1, d2: ptTramo) return float;

--function costeMezcla (o1, o2, dest: ptTramo) return float;
procedure borrarFiguras (numFilas: in integer);
procedure visualizarMatriz (numFila: in integer);
procedure inicializaFiguras;
procedure escribe;
procedure calculaMatrizCosteA (maxFila, maxColum: in integer; fila: in integer);
procedure calculaEmparejamientos(fila: in integer; vecParejas: in out tpVecParejas);









private

-- Definicion de tipos


type Tramo;

--Definicion de un puntero en Ada
type ptTramo is access Tramo; 

type Tramo is record
--Registro que representa el tramo de puntos negros
	pos: integer;	--indica la posicion del tramo en la lista
	ini: integer; 	--indica la posicion inicial del tramo
	long: integer; 	--indica la longitud del tramo
end record;

--type tpLista is record
----Registro que representa una linea del dibujo
--	numElem: integer;
--	primElem: ptTramo;
--	ultElem: ptTramo;
--end record;

			
type tpFigura is array (1..MAX) of listaTramos;
--vector de listas de tramos. Cada elemento del vector representa
--la lista de tramos correspondiente a una fila de la figura.

type Pareja;

type ptPareja is access Pareja;

type Pareja is record
	refIni: integer; -- guarda pto inicial del tramo origen, se toma como referencia para
					 -- calcular pto inicial del tramo en un paso intermedio.
	refFin: integer; -- guarda pto final del tramo origen, se toma como referencia para
					 -- calcular pto final del tramo en un paso intermedio.
	longIni: integer; -- guarda la longitud q habra q sumar a refIni para calcular 
					  -- el pto inical del tramo en el paso p.
					  -- Esto es: Ini= longIni*(p-1) + refIni
	
	longFin: integer; -- guarda la longitud q habra q sumar a refFin para calcular 
					  -- el pto final del tramo en el paso p.
					  -- Esto es: Ini= longFin*(p-1) + refFin
end record;

type tpVecParejas is array (1..MAX) of listaParejas;
-- Cada elemento de este vector indica una lista de emparejamientos.
-- Cada una de estas listas se corresponde con una fila de la figura.




end calculo;
