with ada.text_io, Ada.Command_Line, Ada.Strings.Bounded;
use ada.text_io, Ada.Command_Line, Ada.Strings.Bounded;

with listagenerica;

package ficheros is

package String15 is new Ada.Strings.Bounded.Generic_bounded_length(15);
use String15;

-- Definicion de constantes

NEGRO: constant character := '*';
BLANCO: constant character := '.';
MAX: constant integer := 5;
imagen: constant String := "figura";

-- Definicion de tipos

type Tramo is private;

type tpFigura is limited private;

--package listaTramos is new listagenerica(Tramo);
--use listaTramos;



--Definicion de procedimientos

--procedure tpFigura2fich(fig: in tpFigura; nombre:in String15.Bounded_string);
--Pasara la informacion que hay en la estructura tpFigura a un fichero

--procedure fich2tpFigura(nombre: in String15.Bounded_string; fig: out tpFigura);
--Pasa el contenido de un fichero de texto a una variable de tipo tpFigura

procedure visualizaFigura(fig: in tpFigura);
--Saca por pantalla todos los tramos de puntos negros de cada linea de la figura

procedure inicializaFigura(fig: out tpFigura); 
--Incializa los valores de los campos de cada una de las linea que constituyen la figura

procedure visualizaFichero(nombre: in String15.Bounded_string);
--Saca por pantalla el contenido de un fichero caracter a caracter de manera correcta

--procedure insertaTramo(lin: integer; t:ptTramo);
--Incluye el tramo que le pasamos como parametro a la linea indicada

--procedure limpiaFigura;
--Resetea el contenido de la figura para que no interfiera es ejecuciones consecutivas



private

--Definicion de un puntero en Ada
type ptTramo is access Tramo; 

type Tramo is record
--Registro que representa el tramo de puntos negros
--Tenemos que obviar el campo ptSig porque ya ha sido definido dentro del paquete listagenerica
	pos: integer;
	ini: integer; --Punto de inicio del tramo
	long: integer;
	--ptSig: ptTramo;
end record;

package listaTramos is new listagenerica(Tramo);
--use listaTramos;


type tpFigura is array (1..MAX) of listaTramos.lista;


end ficheros;
