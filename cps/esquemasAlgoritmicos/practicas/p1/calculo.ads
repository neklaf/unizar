with  Ada.text_io, Ada.integer_text_io, Ada.Sequential_IO,Ada.Strings.Bounded;
use Ada.text_io, Ada.integer_text_io,Ada.Strings.Bounded;

-- ESTE MSDULO SSLO REALIZA OPERACIONES DE CALCULO SOBRE DATOS DEL
-- TIPO TPDATO. ?MSDULO O TAD?

package calculo is

--Lo ponemos para que las cadenas sean de tamano variable y que no tengamos 
--que utilizar unicamente el tamano que hemos definido de la cadena
package String15 is new Ada.Strings.Bounded.Generic_bounded_length(15);
use String15;

--Constante que indicara el numero de bits utilizados para representar el
--el codigo ASCII de las letras
MAX:constant integer:= 8;

-- Definimos el nombre del fichero donde se guarda la info como cte
--file:constant String15:="calculo.dat";
file:constant String:="calculo.dat";
file2:constant String:="mensaje";

--Este tipo es una enumeracion para realizar el interface para cambiar
--el valor de los campos del registro
type tpTipo is (mochila, datoN, datoW, datoWinv);
-- En una enumeracion podemos asignar cualquiera de los valores que tenemos 
-- dentro de los posibles!!


--Este tipo ya lo teniamos en el ejercicio anterior
type tpTupla is array(1..MAX) of integer;

--Este es el tipo que encapsula los datos que vamos a tener que serializar
--El tipo lo hemos definido como private por lo que desde los paquetes en los
--que hayamos importado este paquete no tendran la capacidad de cambiar el 
--valor de los campos de este tipo
type tpDato is private;   


-- LOS PROCEDIMIENTOS


procedure guardaValoresDato;
-- Guarda en el fichero de datos los valores actuales de todas
-- las estructuras.
-- Se usara tras la opcion -g.

function cargaValoresDato return boolean;
-- Carga, al empezar el programa, los valores guardados en 
-- el fichero de datos, en las estructuras.

procedure generaAleatorioDato;
-- Carga en las estructuras valores aleatorios.

procedure introduce (tipoDato: in tpTipo; valor, pos: in integer);
-- Almacena los valores en el campo del dato

procedure verDato;
-- Saca por pantalla el valor de los campos de la variable dato

procedure limpiaDato;
-- Borrara los valores de los campos de la variable dato

function crea_tupla return tpTupla;
procedure codifica(nombre:in String15.Bounded_string);
procedure descifrar(nombre:in String15.Bounded_string);



---------------------------------------------------------
private

subtype tpBit is integer range 0..1;

type tpVectorBit is array(Integer  range 1..MAX) of tpBit;


--Definicion del tipo tpDato
type tpDato is record
   mochila: tpTupla;
   tuplaA: tpTupla;
   N: integer; --numero que indica el modulo
   W: integer; --numero que tiene que introducir el usuario
   Winv: integer; --inverso de W modulo N.
end record;

--Implementacion para que podamos serializar el tpDato en un fichero secuencial
package F_Calc is new Ada.sequential_IO(tpDato);
use F_Calc;

--Lo mismo que lo anterior unicamente que lo tenemos que hacer con el tipo
--integer
package F_Princ is new Ada.sequential_IO(integer);
use F_Princ;

end calculo;
