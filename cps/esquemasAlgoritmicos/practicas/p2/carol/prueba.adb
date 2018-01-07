with Ada.text_io, ficheros; 
use Ada.text_io, ficheros;
--Apunte importante solamente tenemos que poner 'use String15' para que se entere de la funcion to_bounded_string

procedure prueba is
--package String15 is new Ada.Strings.Bounded.Generic_bounded_length(15);
use String15; --Lo que implica que simplemente tiene que usar la implementacion del paquete este de los cojones
f: tpFigura;

begin

	put_line("PROGRAMA DE PRUEBA!!");

	--Los casos que se pueden durante la ejecucion del visualizaFichero son:
		--no existe el fichero
		--el fichero existe pero esta vacio
		--el fichero es normal
	--visualizaFichero(to_bounded_string(imagen));
	--visualizaFichero(to_bounded_string("vacio")); --No hay problema con el fichero vacio
	--visualizaFichero(to_bounded_string("no_existo")); --Preguntar a Carol como se manejan las excepciones para ficheros de texto

	--Prueba de una de los procedimientos importantes
	--put_line("antes de ejecutar fich2tpFigura");
	--fich2tpFigura(to_bounded_string(imagen));
	--put_line("He acabado de ejecutar fich2tpFigura");

	fich2tpFigura3(to_bounded_string(imagen));
	visualizaFigura;
	tpFigura2fich(to_bounded_string("figura2"));
	visualizaFichero2(to_bounded_string("figura2"));
	limpiaFigura;
	--visualizaFichero2(to_bounded_string(imagen));
end prueba;
