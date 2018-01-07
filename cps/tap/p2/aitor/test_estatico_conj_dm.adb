--
-- PROGRAMA DE PRUEBA DEL FUNCIONAMIENTO DE LA IMPLEMENTACION DE LA PRACTICA 2ª
--
-- Version: 1.0
-- Fecha: Julio 2004
-- Autores: Acedo Legarre, Aitor y Faci Miguel, Santi
-- Descripcion: Este programa inserta 10 numeros aleatorios en un arbol y despues
--				los borra en orden inverso a como han sido generados

with conj_dm; use conj_dm;
with Ada.text_io; use Ada.text_io;
with Ada.Numerics.Discrete_Random; 
with Ada.Command_line; use Ada.Command_line;


procedure test_estatico_conj_dm is
	set: conj;
	nombre: constant String := "datos.txt";
	
	-- Implementacion de los Discrete_Random para generar aleatorios
	MAX: constant integer := 10;
	subtype num is integer range 1 .. MAX;
	package aleatorios is new Ada.Numerics.Discrete_Random(num);
	use aleatorios;
	generador: aleatorios.Generator;

	package ent_io is new integer_io(natural);
	use ent_io;


	procedure inicializa is
	--Crea el conjunto y resetea el generador de numeros aleatorios
	begin
		put_line("Inicio del test:");
		crear(set);
		Reset(generador);
	end inicializa;


	procedure lee_fich is
	--Lee el fichero de datos de entrada e inserta los valores en el conjunto

		fich_text: File_Type;
		n: num;
	begin
	    open(fich_text, in_file, nombre);
		while not end_of_file(fich_text) loop
		 	if  not end_of_line(fich_text) then
				get(fich_text, n);
				insertar(set, n);
			else
				skip_line(fich_text);
			end if;
		end loop;
		pintar_conj(set);
		new_line;
	
		close(fich_text);
	end lee_fich;


	procedure test is
		v: array (integer range 1 .. MAX) of num;
	begin

		put_line("Secuencia a insertar en el arbol:");
		for i in 1 .. MAX loop
			v(i) := Random(generador);
			put(v(i), 0); 
			if i /= MAX then
				put(", ");
			else
				put(".");
			end if;
			insertar(set, v(i));
		end loop;
		new_line;
		new_line;

		put_line("Contenido del arbol despues de la insercion: ");
		pintar_conj(set);

		new_line;
		for i in reverse 1 .. MAX loop
			put("Borramos: "); put(v(i), 0); new_line; new_line;
			borrar(set, v(i));
			pintar_inorden(set);
			--listar_predecesores(set);
			put_line("************************************");
		end loop;

--		pintar_conj(set);
--		put_line("En inorden:");
--		pintar_inorden(set);
		put("La distancia minima es: "); put(dm(set), 0); new_line;
--		put_line("En postorden:");
--		listar_predecesores(set);
		
end test;

begin
	
		inicializa;

		test;

		vaciar(set);

		put_line("Fin del test.");
	
end test_estatico_conj_dm;
