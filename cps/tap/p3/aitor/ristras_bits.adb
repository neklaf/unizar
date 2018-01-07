--
-- MODULO DE IMPLEMENTACION DEL TAD "RISTRAS DE BITS"
--
-- Version: 1.0
-- Fecha: Agosto 2004
-- Autores: Acedo Legarre, Aitor y Faci Miguel, Santiago
-- Descripcion: En este fichero realizamos la implementacion
--				del tad ristras de bits, definido en ristras_bits.ads
--
-- Nota:Sabemos que los caracteres en Ada necesitan 8 bits para codificar su codigo ASCII,
--		por lo que si utilizamos un vector de 256 posiciones cada palabra podra tener 
--		como mucho una longitud de 32 letras.
--

with Ada.Strings.Unbounded, ustrings, Ada.Text_IO;
use Ada.Strings.Unbounded, ustrings, Ada.Text_IO;

package body ristras_bits is
  
  package ent_io is new integer_io(bit);
  use ent_io;


  function cambia_numero (num: integer) return ristra_bits is
  --Codifica el numero entero pasado como parametro a bits
  	aux: ristra_bits;
	asc: integer;
  begin
  	asc := num;
     for j in reverse 0..7 loop
       aux.ind := aux.ind + 1;
       if (asc / 2**j) = 1 then
          asc := asc - 2**j;
          aux.ristra(aux.ind):=1;
        end if;
      end loop;
	  return aux;
  end cambia_numero;
 
 
  procedure pinta_numero(s: in ristra_bits) is
  --Pintamos en la pantalla el contenido de la ristra de bits
  begin
	for i in 1..s.ind loop
		put(s.ristra(i), 0);
	end loop;
	new_line;
  end pinta_numero;
  
  
  procedure pinta_ristra (s: in ristra_bits) is
  --pintamos en la pantalla que es lo que hay en la secuencia de bits
  begin
	for i in 1..s.ristra'Length loop
		put(s.ristra(i), 0);
	end loop;
	new_line;
  end pinta_ristra;
  

  function string2ristra (s: String) return ristra_bits is
  --Transforma una cadena de caracteres en una ristra de bits a partir del numero
  --ascii que representa ese caracter
    aux: ristra_bits;
    num: integer;
  begin
    for i in 1..s'Length loop
      num := Character'Pos(s(i)); --Devolvemos el numero del caracter en la tabla ascii 
      for j in reverse 0..7 loop
        aux.ind := aux.ind + 1;
        if (num / 2**j) = 1 then
          num:=num - 2**j;
          aux.ristra(aux.ind):=1;
        end if;
      end loop;
    end loop;
    return aux;
  end string2ristra;
  


  function diferencia (a, b: ristra_bits) return integer is
  --Devuelve la posicion del primer bit en el que difieren dos ristras de bits
  begin
    for i in 1..a.ind loop
      if a.ristra(i)/=b.ristra(i) then return i;
      end if;
    end loop;
	return ERROR;
  end diferencia;
  


  function trata_cadena (str: String) return String is
  --Manipula la cadena para que se ajuste a las condiciones de nuestra implementacion
  --de Patricia, convierte los caracteres a mayúscula y concatena el caracter fin
  --de cadena
    aux1: String:=str;
    aux2: ustring;
  begin
	for i in 1..str'Length loop
      if aux1(i) in 'a'..'z' then
        aux1(i):=Character'Val(Character'Pos(aux1(i))-32);
      end if;
    end loop;
    aux2:=U(aux1) & "$";
    return S(aux2);
  end trata_cadena;
  


  function ristra2string (r: ristra_bits) return String is
  --Transforma una ristra de caracteres a una cadena
    aux: ustring;
  begin
    aux:=U("");
    for i in 1..r.ind loop
      if r.ristra(i)=0 then aux:=aux&'0'; else aux:=aux&'1'; end if;
    end loop;
    return S(aux);
  end ristra2string;
  
end ristras_bits;
