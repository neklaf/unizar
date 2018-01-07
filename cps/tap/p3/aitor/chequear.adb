--
-- PROGRAMA DE VERIFICACION DE LA IMPLEMENTACION DE PATRICIA
--
-- Version: 1.0
-- Fecha: Agosto 2004
-- Autores: Acedo Legarre, Aitor y Faci Miguel, Santiago
-- Descripcion: Este programa realiza una lectura del fichero ospd.txt e inserta todas las palabras 
--				en un arbol patricia, y posteriormente busca las palabras contenidas en el fichero
--				entrada.txt generando el fichero salida.txt, en el que indica las palabras 
--				del fichero de entrada que no se encuentran en el diccionario.
--

with patricia, Ada.Text_IO, ustrings, Ada.Strings.Unbounded, ristras_bits; 
use patricia, Ada.Text_IO, ustrings, Ada.Strings.Unbounded, ristras_bits;


procedure chequear is
	fich_text: File_Type;
	fich_sal: File_Type;
	p: ustring; --Esta es la palabra que vamos a leer del fichero de entrada (diccionario)
	
	c: conj; --Conjunto implementado con Patricia

	--Constantes para definir los nombres de los ficheros que tenemos que manejar
	f1: constant String := "ospd.txt";
	f2: constant String := "entrada.txt";
	f3: constant String := "salida.txt";

	linea: integer := 0; --variable que lleva la cuenta de las lineas leidas del fichero de entrada

	item: tp_item;
	ris: ristra_bits;

	package ent_io is new integer_io(integer);
	use ent_io; --Sin esta implementacion no podremos escribir un entero en un fichero con los caracteres
	--justos, es decir, no podremos llamar a put con 3 parametros

begin

	c.raiz := crea_conj; --Inicializamos la raiz del Patricia

	--En el fichero ospd.txt tenemos una palabra en cada fila del fichero
	open(fich_text, in_file, f1);

	--Leemos el diccionario y lo almacenamos en el Patricia
	while not end_of_file(fich_text) loop
		get_line(fich_text, p);
		item.clave := string2ristra(trata_cadena(S(p))); --Rellenamos el item a insertar
		item.p := p;
		insertar(c.raiz, item); 
	end loop;

	close(fich_text);
	
	pintar_conjunto(c.raiz);
	--Cuando tenemos el fichero ospd.txt como entrada no conviene descomentar la linea anterior

	put_line("Ha terminado la inserción del diccionario en el Patricia.");
	
	open(fich_text, in_file, f2);
	create(fich_sal, out_file, f3);
	
	--Leemos el fichero de entrada
	while not end_of_file(fich_text) loop
			get_line(fich_text, p);
			linea := linea + 1;
			ris := string2ristra(trata_cadena(S(p))); --Rellenamos el item a buscar
			if (buscar(c.raiz, ris) = NULL_ITEM) then
				put(fich_sal, p); --escribimos la palabra no encontrada
				put(fich_sal, "   "); --escribimos un hueco de separacion
				put(fich_sal, linea, 0); --escribimos la linea en la que se encuentra la palabra
				put_line(fich_sal, ""); --saltamos de linea dentro del fichero para poder escribir la siguiente
										--palabra
			end if;
	end loop;
	put_line("Ha terminado la busqueda del fichero de entrada.");
	close(fich_text);
	close(fich_sal);
end chequear;
