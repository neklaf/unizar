with  Ada.text_io, Ada.integer_text_io,Ada.Numerics.Discrete_Random;
use Ada.text_io, Ada.integer_text_io;

-- FALTA TODAVIA POR HACER LA PARTE ALEATORIA

package body calculo is

-- VARIABLES:

dato: tpDato;



-------------------------------------------------------------

function usaRandom(numMax: in integer) return integer is 
-- Esta funcion es una random que devuelve un numero del 1 al 40. 

subtype tp_rango is positive range 1..numMax; 
package random_numMax is new Ada.Numerics.Discrete_Random(tp_rango); 
g: random_numMax.Generator; 
begin 
random_numMax.Reset(g); 
return (random_numMax.Random(g)); 

end usaRandom; 

-------------------------------------------------------------

procedure guardaValoresDato is
-- Guarda en el fichero de datos los valores actuales de todas
-- las estructuras.
-- Se usara tras la opcion -g.
-- Guarda nueva estructura borrando la que ya hubiese.
fich:F_Calc.file_type;
begin

-- open(fich,out_file,"calculo.dat"); 
begin
	open(fich,out_file,file); 
exception 
when F_Calc.name_error => create(fich,out_file,file); 
end;

write(fich, dato);

-- added
close(fich);

end guardaValoresDato;

-------------------------------------------------------------

function cargaValoresDato return boolean is
-- Carga, al empezar el programa, los valores guardados en 
-- el fichero de datos, en las estructuras.
-- IMP: si no hay ningun dato en el fichero para guardar o no existe fichero, entonces
--      devuelve true (hay error), si no false.
fich:F_Calc.file_type;
begin

--open(fich,in_file,"calculo.dat"); 
begin
	open(fich,in_file,file); 
exception 
when F_Calc.name_error => return false; 
end;

if end_of_file(fich) then
   close(fich);
   return false;
else
   read(fich,dato);
   close(fich);
   return true;
end if;

end cargaValoresDato;

-------------------------------------------------------------

procedure generaAleatorioDato is
begin

put_line("hola");

end generaAleatorioDato;

-------------------------------------------------------------

procedure introduce (tipoDato: in tpTipo; valor, pos: in integer) is

begin
   case tipoDato is
      when mochila => dato.mochila(pos):= valor;
      when datoN => dato.N := valor;
      when datoW => dato.W := valor;
      when datoWinv => dato.Winv := valor;
   end case;

end introduce;

---------------------------------------------------------

procedure verDato is

begin

	put_line("Este es el valor de la mochila:");
	put("{ ");
	for i in 1 .. MAX loop
		put(dato.mochila(i), 0); put(",");
	end loop;
	put(" }"); new_line;

	put_line("Este es el valor de la tupla:");
	put("{ ");
	for i in 1 .. MAX loop
		put(dato.tuplaA(i), 0); put(",");
	end loop;
	put(" }");new_line;

	put("El valor de la N: ");put(dato.N,0);new_line;
	put("El valor de la W: ");put(dato.W,0);new_line;
	put("El valor de la Winv: ");put(dato.Winv,0);new_line;

end verDato;

---------------------------------------------------------

procedure limpiaDato is

begin

	for i in 1 .. MAX loop
		dato.mochila(i) := 0;
	end loop;

	for i in 1 .. MAX loop
		dato.tuplaA(i) := 0;
	end loop;

	dato.N := 0;
	dato.W := 0;
	dato.Winv := 0;

	put_line("El dato esta limpio");
end limpiaDato;

---------------------------------------------------------

function crea_tupla return tpTupla is

begin

	for i in 1 ..MAX loop
		dato.tuplaA(i):= (dato.W * dato.mochila(i) )mod dato.N;
	end loop;

      return dato.tuplaA;

end crea_tupla;

-------------------------------------------------------------

function probMochila (pesoMax: in integer) return tpVectorBit is

vector: tpVectorBit;
capacidad: integer:=pesoMax;

begin

        for i in reverse 1..MAX loop
                if dato.mochila(i) <= capacidad then
                        vector(i):=1;
                        capacidad:= capacidad - dato.mochila(i);
                else
                        vector(i):=0;
                end if;
        end loop;

        return vector;

end probMochila;

-------------------------------------------------------------

function traduce(c: in character) return tpVectorBit is

num: integer;
vector: tpVectorBit;

begin

        num := character'pos(c);
        for i in reverse 1..MAX loop
           vector(i):= num mod 2;
	   num := num / 2;
        end loop;

        return vector;

end traduce;

-------------------------------------------------------------

function traduceInv(vector: in tpVectorBit) return character is

num: integer;
j:integer:=MAX-1;
car: character;

begin

        num := 0;
        for i in 1..MAX loop
                num:= num + (vector(i) * (2**j));
                j:=j-1;
        end loop;

	car := character'val(num);

        return car;

end traduceInv;

-------------------------------------------------------------

procedure codifica(nombre:in String15.Bounded_string)  is
fichSec:F_Princ.file_type;
fichText: Ada.text_IO.File_type;
car: character;
vecBit: tpVectorBit;
resulC:integer;
nombreSec:String15.Bounded_string;

begin
        open(fichText,in_file,to_string(nombre));
        nombreSec:= nombre & ".meh";
        create(fichSec,out_file,to_string(nombreSec));
	put_line("MENSAJE CIFRADO:");
        while not end_of_file(fichText) loop
                get(fichText,car);
                vecBit:=traduce(car);
                resulC:=0;
                for i in 1..MAX loop
                        resulC := resulC + vecBit(i) * dato.tuplaA(i);
                end loop;
                write(fichSec,resulC);
		put(car);
        end loop;
	new_line;
        close(fichText);
        close(fichSec);

end codifica;

-------------------------------------------------------------

procedure descifrar (nombre:in String15.Bounded_string) is
fichSec:F_Princ.file_type;
num: integer;
vecBit: tpVectorBit;
resulV:integer;
nombreSec:String15.Bounded_string;
car:character;

begin

        put("MENSAJE DESCIFRADO:");new_line;

        nombreSec:= nombre & ".meh";
        open(fichSec,in_file,to_string(nombreSec));
        --exception 
        --when F_Princ.name_error => put("ERROR EN EL NOMBRE"); 
        while not end_of_file(fichSec) loop
                read(fichSec,num); 
		resulV := (dato.Winv * num) mod dato.N;
		vecBit:= probMochila(resulV);
		car := traduceInv(vecBit);
		put(car); 
        end loop;
	new_line;
        close(fichSec);

end descifrar;

-------------------------------------------------------------



end calculo;
