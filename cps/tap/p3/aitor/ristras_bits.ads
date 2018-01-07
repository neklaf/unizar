--
-- MODULO DE DEFINICION DEL TAD "RISTRAS DE BITS"
--
-- Version: 1.0
-- Fecha: Agosto 2004
-- Autores: Acedo Legarre, Aitor y Faci Miguel, Santiago
-- Descripcion: Definicion del tipo de dato ristra de bits
--				y de las operaciones que hemos necesitado
--				en la implementacion del Patricia
--

package ristras_bits is
	
  ERROR: constant integer := -1;

  subtype bit is integer range 0..1;

  type array_bits is array (1..256) of bit; -- Palabras de hasta 32 letras

  --Definicion del tipo ristra de bits
  type ristra_bits is record
    ristra: array_bits := (others => 0); --Inicializamos todas las posiciones del vector a 0
    ind: integer := 0; --Indice de la secuencia de bits hasta donde hay bits significativos
  end record;
  
  function string2ristra (s: String) return ristra_bits;
  --Esta funcion nos devolvera la codificacion en binario de la cadena 
  --de caracteres que le pasemos como parametro
  
  function diferencia (a, b: ristra_bits) return integer;
  --Esta funcion devolvera la posicion del primer bit en el cual difieran las ristras
  --pasadas como parametros, si no existiera ningun bit de diferencia se devolveria
  --ERROR
 
  function trata_cadena (str: String) return String;
  --Nos devuelve una cadena pasada a mayusculas y con el caracter de fin de cadena concatenado
  
  function ristra2string (r: ristra_bits) return String;
  --Esta funcion realiza la mision inversa a la que hemos visto arriba (string2ristra)


  --PROCEDIMIENTOS DE DEBUG--
  procedure pinta_ristra (s: in ristra_bits);
  --Pintamos en la pantalla el contenido de la ristra de bits completa

  procedure pinta_numero(s: in ristra_bits);
  --Pintamos en la pantalla el contenido de la ristra de bits hasta el indice actual

  function cambia_numero (num: integer) return ristra_bits;
  --Codifica el numero entero pasado como parametro a bits
  
end ristras_bits;
