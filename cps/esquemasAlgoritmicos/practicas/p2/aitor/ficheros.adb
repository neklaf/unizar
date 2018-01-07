with  Ada.text_io, Ada.integer_text_io, Ada.Characters.Latin_1, Ada.IO_Exceptions, Ada.Strings.Bounded;
use Ada.text_io, Ada.integer_text_io, Ada.Characters.Latin_1, Ada.IO_Exceptions, Ada.Strings.Bounded;

--Esto es algo que todavia no comprendo
with listagenerica;
use listaTramos;

package body ficheros is

--Definicion de variables
----------------------------------------
--f: tpFigura;
----------------------------------------

--Definicion de los procedimientos
----------------------------------------


--NOTA: para que no pierdas tanto el tiempo manana lo que tienes que pensar es que la figura tiene un numero 
--de esas que hemos definido. Tenemos que utilizar el interfaz que me han proporcionado para hacer lo mismo
--que tenia pero hecho a lo cutre

--Estamos utilizando una cosa como el patron Iterator, me parece un poco matar moscas a canonazos


--Este fichero ya ha sido cambiado solamente quedara compilarlo y probar la correccion del mismo

--procedure tpFigura2fich(fig: in tpFigura; nombre: String15.Bounded_string) is
--fichText: File_Type;
----aux: ptTramo;
--auxTramo: Tramo;
--col, final: integer;
--
--begin
--
----Trabajamos bajo la suposicion de que el fichero puede ser rectangular
--
--	--open(fichText, out_file, to_string(nombre));
--
--	--Para crear un fichero tendremos que utilizar el procedimiento 'create'!!
--	--Este procedimiento tiene el modo a out_file por defecto
--	--Cuando dejamos el valor de un parametro por defecto tendremos que dejar referenciado a que parametro
--	--se le asignan los valores siguientes en la llamada
--	--El procedimiento create crea y abre el fichero en cuestion

--	create(fichText, out_file, to_string(nombre));
--
--	for i in 1 .. MAX loop
--		if long(fig(i)) /= 0 then --comprobamos si la linea esta vacia
--			aux := observaPrimero(fig(i));
--			col := 1;
--			for j in 1 .. long(fig(i)) loop
--				--final := aux.ini;
--				auxTramo := iniciaActual(fig(i), 1);
--				final := auxTramo.ini;
--				while col < final loop --Dibujamos el tramo de blancos antes del tramo de negros
--					put(fichText, '.');
--					col := col + 1;
--				end loop;
--				for t in 1 .. auxTramo.long loop
--					put(fichText, '*');
--				end loop;
--				col := col + 1; --Refrescamos la columna actual despues de dibujar el tramo negro
--
--				--aux := aux.ptSig; --Pasamos al siguiente tramo
--				auxTramo := siguienteActual(fig(i));
--			end loop;
--			auxTramo := observaUltimo(fig(i));
--			if (auxTramo.ini + auxTramo.long) < observaUltCar(fig(i)) then
--				for j in (auxTramo.ini + auxTramo.long) .. observaUltCar(fig(i)) loop
--					put(fichText,'.');
--				end loop;
--			end if;
--		else
--			--la linea no tiene ningun tramo de puntos negros
--			--put_line(fichText, "................"); --Esto representara una linea en blanco
--			for j in 1 .. observaUltCar(f(i)) loop
--				put(fichText,'.');
--			end loop;
--		end if;
--			new_line(fichText);
--	end loop;
--
--	close(fichText);
--
--end tpFigura2fich;

----------------------------------------

--Este procedimiento se va a quedar completamente obsoleto cuando pongamos la libreria de la lista
--Este procedimiento queda totalmente obsoleto

--procedure insertaTramo(lin: integer; t:ptTramo) is

--begin
--	f(lin).numElem:=f(lin).numElem+1; --Actualizamos el numero de tramos
--	if (f(lin).ultElem = null) then --lista vacia
--		f(lin).primElem := t;
--	else
--		f(lin).ultElem.ptSig:= t;
--	end if;
--	f(lin).ultElem:=t;
--end insertaTramo;

----------------------------------------

--NOTA: Creo que es una buena idea que le pasemos la figura
--TENEMOS QUE PASAR COMO PARAMETRO DE SALIDA EL NUMERO DE LINEAS DE LA FIGURA!!!

--TENEMOS QUE PROBAR TAMBIEN EL CORRECTO FUNCIONAMIENTO DE ESTE PROCEDIMIENTO

--procedure fich2tpFigura(nombre: in String15.Bounded_string; fig: out tpFigura; lin: out positive) is

--fichText: File_Type;
--col, linea, long, ini, numTramo: integer;
--car: character;
----aux: ptTramo;
--auxTramo: Tramo;
--inTramo: boolean;

--begin
--       open(fichText,in_file,to_string(nombre));

--      inTramo := false; --iniciamos la posicion sin estar dentro de un tramo
--     col := 0; --Tenemos que tener en cuenta que los caracteres empiezan de la columna 1
--        linea := 1; --llevamos el numero de lineas
--        ini := 0; --almacenamos el punto en el que comienza el tramo
--        while not end_of_file(fichText) loop
--		numTramo := 0; --este es el numero de tramos que hay en la fila en cuestion
--		while not end_of_line(fichText) loop
--                	get(fichText,car);
--			put(car);
--			if car = NEGRO then
--                                if inTramo then
--                                        long := long + 1;
--                                else
--                                        long := 1;
--                                        inTramo := true;
--                                        ini := col + 1;
--                                end if;
--                        elsif car = BLANCO then
--                                if inTramo then
--                                        inTramo := false;
--					numTramo := numTramo + 1;
--                                        --aux := new Tramo;
--                                        auxTramo.ini := ini;
--                                        auxTramo.long := long;
--                                        auxTramo.pos := numTramo; 
--                                        --aux.ptSig := null;
--                                        --insertaTramo(linea, aux);
--					aniadeFinal(fig, auxTramo);
--                                end if;
--			end if;
--			col := col + 1;
--		end loop;
--		skip_line(fichText); --Movemos el puntero del buffer de entrada para saltar EOL
--		new_line; --Escribimos EOL en la pantalla
--		f(linea).ultCar := col; --NUEVO
--		--IGUAL TENEMOS QUE HACER UN PROCEDIMIENTO PARA ESCRIBIR EL ULTCAR DE LA LINEA
--
--		--TAMBIEN TENEMOS QUE RELLENAR EN LA LISTA CUANTOS NODOS HAY EN TOTAL
--		--fig(linea).n := numTramos;
--
--		if inTramo then -- tratamiento para cuando leemos un fin de linea
--			inTramo := false;
--			--aux := new Tramo;
--			auxTramo.ini := ini;
--			auxTramo.long := long;
--			auxTramo.pos := numTramo; --No se que es lo que tiene que tener
--			--aux.ptSig := null;
--			--insertaTramo(linea, aux);
--			aniadeFinal(fig, auxTramo);
--			--TENEMOS QUE VER EN EL CODIGO QUE ANIADEFINAL, CREA UN PUNTERO AL QUE LE ASIGNARA LOS VALORES 
--			--DE LOS CAMPOS DEL TRAMO QUE LE PASAMOS COMO PARAMETRO
--		end if;
--		col := 0;
--		linea := linea + 1;
--	end loop;
--	lin := linea; --Esto es nuevo, comprobar si saca correctamente el numero de lineas de la figura creada
--	close(fichText);
--
--end fich2tpFigura;

----------------------------------------

--PROBAR!!

procedure visualizaFigura(fig: tpFigura) is
--	aux: ptTramo;
	aux: Tramo;
	tramo: integer;
begin
--para representar el caracter de control 'tabulador horizontal' tenemos que poner ascii.ht (pagina 106 libro dani)
--Esto es una manera obsoleta de hacerlo y tenemos que incluir el paquete Ada.Characters.Latin_1 y escribir 'ht'

for i in 1 .. MAX loop
	tramo := 1;
	put("Numero de linea: "); put(i,0); new_line;
	put(ht & "Numero de tramos: "); put(f(i).n,0); new_line;
	if f(i).n /= 0 then
		inicializaActual(fig,1); --ponemos el puntero 'actual' en la primera posicion e iteramos
		dameActual(fig, aux);

		for j in 1 .. f(i).n loop
			put(ht & ht & "Tramo numero: "); put(tramo,0); new_line;
			put(ht & ht & "Posicion: "); put(aux.pos, 0); new_line;
			put(ht & ht & "Inicial: "); put(aux.ini,0); new_line;
			put(ht & ht & "Longitud: "); put(aux.long,0); new_line;
			put_line(ht & ht & "*******************************");
			siguienteActual(fig, aux);
		end loop;
		
		--ESTE ES EL BUCLE QUE ESTA MAL
--		while aux /= null loop
--			put(ht & ht & "Tramo numero: "); put(tramo,0); new_line;
--			put(ht & ht & "Posicion: "); put(aux.pos, 0); new_line;
--			put(ht & ht & "Inicial: "); put(aux.ini,0); new_line;
--			put(ht & ht & "Longitud: "); put(aux.long,0); new_line;
--			put_line(ht & ascii.ht & "*******************************");
--			tramo:=tramo+1;
--			aux:=aux.ptSig;
--		end loop;
	end if;
end loop;

end visualizaFigura;

----------------------------------------

--procedure limpiaFigura is
--begin
--	for i in 1 .. MAX loop
--		--Creo que con esto simplemente no bastara ya que no se si Ada tiene recolector de basura??
--		f(i).primElem := null;
--		f(i).ultElem := null;
--	end loop;
--	new_line;
--	put_line("Figura limpiada");
--end limpiaFigura;

----------------------------------------

procedure visualizaFichero(nombre: in String15.Bounded_string) is
-- Me baso en la implementacion de Ryan Stansifer
fichText: File_Type;
car: character;

begin

	open(fichText, in_file, to_string(nombre));

	new_line;
	while not end_of_file(fichText)  loop
		while not end_of_line(fichText) loop
			get(fichText, car);
			put(car);
		end loop;
		skip_line(fichText); --Mover el puntero en el buffer de entrada pasando el EOL
		new_line; --Escribir EOL en la salida
	end loop;
	close(fichText); --Cuidado con no cerrar el fichero!!
	
--	exception
	
--	when Name_Error =>
--		put_line("Imposible abrir el fichero" & to_string(nombre) & " .");
	
--	when others =>
--		close(fichText);
	
end visualizaFichero;

----------------------------------------

--PROBAR
procedure inicializaFigura(fig: out tpFigura) is

begin

--No tenemos que inicializar ningun tramo porque no existe ninguno
	for i in 1 .. MAX loop
		creaVacia(f(i));
		--f(i).numElem := 0; --La linea no tiene al principio ningun tramo
		--f(i).ultCar := 0; --El menor numero posible si hay algun caracter en la linea es 1
		--f(i).primElem := null;
		--f(i).ultElem := null;
	end loop;

end inicializaFigura;

----------------------------------------

end ficheros;
