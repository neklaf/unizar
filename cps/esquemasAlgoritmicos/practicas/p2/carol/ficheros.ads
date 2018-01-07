with ada.text_io, Ada.Command_Line, Ada.Strings.Bounded;
use ada.text_io, Ada.Command_Line, Ada.Strings.Bounded;

package ficheros is

package String15 is new Ada.Strings.Bounded.Generic_bounded_length(15);
use String15;

-- Definicion de constantes

NEGRO: constant character := '*';
BLANCO: constant character := '.';
MAX: constant integer := 5;
imagen: constant String := "figura";

-- Definicion de tipos

type Tramo;

--Definicion de un puntero en Ada
type ptTramo is access Tramo; 

type Tramo is record
--Registro que representa el tramo de puntos negros
	pos: integer;
	ini: integer; --Punto de inicio del tramo
	long: integer;
	ptSig: ptTramo;
end record;

type tpLista is record
--Registro que representa una linea del dibujo
	numElem: integer;
	ultCar: integer; --Lo utilizaremos para indicar en que columna se encuentra el ultimo caracter de la linea
	primElem: ptTramo;
	ultElem: ptTramo;
end record;

type tpFigura is array (1..MAX) of tpLista;


--Definicion de procedimientos

procedure tpFigura2fich(nombre:in String15.Bounded_string);
--Pasara la informacion que hay en la estructura tpFigura a un fichero

--procedure fich2tpFigura(nombre:in String15.Bounded_string);
--El siguiente procedimiento es de prueba

procedure fich2tpFigura2(nombre: in String15.Bounded_string);
--Pasa el contenido de un fichero con un espacio al final de cada linea a una variable de tipo tpFigura

procedure fich2tpFigura3(nombre: in String15.Bounded_string);
--Pasa 'BIEN' el contenido de un fichero de texto a una variable de tipo tpFigura

procedure visualizaFigura;
--Saca por pantalla todos los tramos de puntos negros de cada linea de la figura

procedure inicializaFigura; 
--Incializa los valores de los campos de cada una de las linea que constituyen la figura

procedure visualizaFichero(nombre:in String15.Bounded_string);
--Saca por pantalla el contenido de un fichero caracter a caracter cuando el fichero tiene un espacio al final de cada linea

procedure visualizaFichero2(nombre: in String15.Bounded_string);
--Saca por pantalla el contenido de un fichero caracter a caracter de manera correcta

procedure insertaTramo(lin: integer; t:ptTramo);
--Incluye el tramo que le pasamos como parametro a la linea indicada

procedure limpiaFigura;
--Resetea el contenido de la figura para que no interfiera es ejecuciones consecutivas

end ficheros;
