with ada.text_io, Ada.Command_Line, Ada.Strings.Bounded, ada.integer_text_io, ada.float_text_io; 
use ada.text_io, Ada.Command_Line, Ada.Strings.Bounded,ada.integer_text_io, ada.float_text_io;
with ada.unchecked_deallocation;


package calculo is

--Definicion de constantes
MAX:constant integer:=30;
POND: constant float:=0.6;


-- Definicion de tipos

type Tramo;

--Definicion de un puntero en Ada
type ptTramo is access Tramo; 

type Tramo is record
--Registro que representa el tramo de puntos negros
	pos: integer;
	ini: float;
	long: float;
	ptSig: ptTramo;
end record;

type tpLista is record
--Registro que representa una linea del dibujo
	numElem: integer;
	primElem: ptTramo;
	ultElem: ptTramo;
end record;

type tpFigura is array (1..MAX) of tpLista;


--Definicion de funciones

function calculaCosteSimple (ini1,ini2,long1,long2 : float) return float;


procedure calculaMatrizCosteS( num: in integer);



--function costeDivision (orig, d1, d2: ptTramo) return float;

--function costeMezcla (o1, o2, dest: ptTramo) return float;
procedure borrarFiguras (numFilas: in integer);
procedure visualizarMatriz (numFila: in integer);
procedure inicializaFiguras;
procedure escribe;
procedure calculaMatrizCosteA (maxFila, maxColum: in integer);

end calculo;
