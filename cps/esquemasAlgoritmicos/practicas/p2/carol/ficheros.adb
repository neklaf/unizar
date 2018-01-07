with  Ada.text_io, Ada.integer_text_io, Ada.Characters.Latin_1, Ada.IO_Exceptions, Ada.Strings.Bounded;
use Ada.text_io, Ada.integer_text_io, Ada.Characters.Latin_1, Ada.IO_Exceptions, Ada.Strings.Bounded;

package body ficheros is

--Definicion de variables
----------------------------------------
f: tpFigura;
----------------------------------------

--Definicion de los procedimientos
----------------------------------------

--NOTA IMPORTANTE: se puede solucionar el problema de la representacion correcta de las lineas para ficheros rectangulares
--anadiendo un campo que indique en que columna se encuentra el ultimo caracter de la linea, creo que es la mejor solucion
--por lo menos la que implica menos cambios en la estructura de datos inicial

procedure tpFigura2fich(nombre: String15.Bounded_string) is
fichText: File_Type;
aux: ptTramo;
col, final: integer;

begin

--Trabajamos bajo la suposicion de que el fichero puede ser rectangular

	--open(fichText, out_file, to_string(nombre));

	--Para crear un fichero tendremos que utilizar el procedimiento 'create'!!
	--Este procedimiento tiene el modo a out_file por defecto
	--Cuando dejamos el valor de un parametro por defecto tendremos que dejar referenciado a que parametro
	--se le asignan los valores siguientes en la llamada
	--El procedimiento create crea y abre el fichero en cuestion

	create(fichText, out_file, to_string(nombre));

	for i in 1 .. MAX loop
		if f(i).numElem /= 0 then --comprobamos si la linea esta vacia
			aux := f(i).primElem;
			col := 1;
			for j in 1 .. f(i).numElem loop
				final := aux.ini;
				while col < final loop --Dibujamos el tramo de blancos antes del tramo de negros
					put(fichText, '.');
					col := col + 1;
				end loop;
				for t in 1 .. aux.long loop
					put(fichText, '*');
				end loop;
				col := col + 1; --Refrescamos la columna actual despues de dibujar el tramo negro

				aux := aux.ptSig; --Pasamos al siguiente tramo
			end loop;
			if (f(i).ultElem.ini + f(i).ultElem.long) < f(i).ultCar then
				for j in (f(i).ultElem.ini + f(i).ultElem.long) .. f(i).ultCar loop
					put(fichText,'.');
				end loop;
			end if;
		else
			--la linea no tiene ningun tramo de puntos negros
			--put_line(fichText, "................"); --Esto representara una linea en blanco
			for j in 1 .. f(i).ultCar loop
				put(fichText,'.');
			end loop;
		end if;
			new_line(fichText);
	end loop;

	close(fichText);

end tpFigura2fich;

----------------------------------------

--Definicion de un puntero en Ada
--type ptTramo is access Tramo;

--type Tramo is record
--Registro que representa el tramo de puntos negros
--        pos: integer;
--        ini: integer;
--        long: integer;
--        ptSig: ptTramo;
--end record;

--type tpLista is record
--Registro que representa una linea del dibujo
--       numElem: integer;
--        primElem: ptTramo;
--        ultElem: ptTramo;
--end record;

--type tpFigura is array (1..MAX) of tpLista;

procedure insertaTramo(lin: integer; t:ptTramo) is

begin
	f(lin).numElem:=f(lin).numElem+1; --Actualizamos el numero de tramos
	if (f(lin).ultElem = null) then --lista vacia
		f(lin).primElem := t;
	else
		f(lin).ultElem.ptSig:= t;
	end if;
	f(lin).ultElem:=t;
end insertaTramo;

----------------------------------------
--ESTE ES UNA VERSION PEOR QUE LA VERSION fich2tpFigura3!!

procedure fich2tpFigura2(nombre: in String15.Bounded_string) is
--Nota para que este procedimiento funcione bien, al igual que el visualizaFichero tenemos que anadir al final de los
--caracteres '.' o '*' un espacio porque si estan pegados al final de linea no los coge NO ENTIENDO PORQUE

fichText: Ada.text_IO.File_type;
col, linea, long, ini: integer;
car: character;
aux: ptTramo;
inTramo: boolean;

begin
	open(fichText,in_file,to_string(nombre));

	inTramo := false;
	col := 0;
	linea := 1; --llevamos el numero de lineas
	ini := 0; --almacenamos el punto en el que comienza el tramo
	while not end_of_file(fichText) loop
		get(fichText,car);
		if not end_of_line(fichText) then
			put(car);
			if car = NEGRO then
				if inTramo then
					long := long + 1;
				else
					long := 1;
					inTramo := true;
					ini := col + 1;
				end if;
			elsif car = BLANCO then
				if inTramo then
					inTramo := false;
					aux := new Tramo;
					aux.ini := ini;
					aux.long := long;
					aux.pos := 0; --No se que es lo que tiene que tener
					aux.ptSig := null;
					insertaTramo(linea, aux);
				end if;
			end if;
		col := col + 1;
		else
		new_line;
			if inTramo then
				inTramo := false;
				aux := new Tramo;
				aux.ini := ini;
				aux.long := long;
				aux.pos := 0; --No se que es lo que tiene que tener
				aux.ptSig := null;
				insertaTramo(linea, aux);
			end if;
			col := 0;
			linea := linea + 1;
		end if;
	end loop;
end fich2tpFigura2;

----------------------------------------

procedure fich2tpFigura3(nombre: in String15.Bounded_string) is
--Este procedimiento evita el problema de los fin de linea

fichText: File_Type;
col, linea, long, ini: integer;
car: character;
aux: ptTramo;
inTramo: boolean;

begin
        open(fichText,in_file,to_string(nombre));

        inTramo := false; --iniciamos la posicion sin estar dentro de un tramo
        col := 0; --Tenemos que tener en cuenta que los caracteres empiezan de la columna 1
        linea := 1; --llevamos el numero de lineas
        ini := 0; --almacenamos el punto en el que comienza el tramo
        while not end_of_file(fichText) loop
		while not end_of_line(fichText) loop
                	get(fichText,car);
			put(car);
			if car = NEGRO then
                                if inTramo then
                                        long := long + 1;
                                else
                                        long := 1;
                                        inTramo := true;
                                        ini := col + 1;
                                end if;
                        elsif car = BLANCO then
                                if inTramo then
                                        inTramo := false;
                                        aux := new Tramo;
                                        aux.ini := ini;
                                        aux.long := long;
                                        aux.pos := 0; --No se que es lo que tiene que tener
                                        aux.ptSig := null;
                                        insertaTramo(linea, aux);
                                end if;
			end if;
			col := col + 1;
		end loop;
		skip_line(fichText); --Movemos el puntero del buffer de entrada para saltar EOL
		new_line; --Escribimos EOL en la pantalla
		f(linea).ultCar := col; --NUEVO
		if inTramo then -- tratamiento para cuando leemos un fin de linea
			inTramo := false;
			aux := new Tramo;
			aux.ini := ini;
			aux.long := long;
			aux.pos := 0; --No se que es lo que tiene que tener
			aux.ptSig := null;
			insertaTramo(linea, aux);
		end if;
		col := 0;
		linea := linea + 1;
	end loop;
	close(fichText);

end fich2tpFigura3;

----------------------------------------

procedure visualizaFigura is
	aux: ptTramo;
	tramo: integer;
begin
--para representar el caracter de control 'tabulador horizontal' tenemos que poner ascii.ht (pagina 106 libro dani)
--Esto es una manera obsoleta de hacerlo y tenemos que incluir el paquete Ada.Characters.Latin_1 y escribir 'ht'
for i in 1 .. MAX loop
	tramo := 1;
	put("Numero de linea: "); put(i,0); new_line;
	put(ht & "Numero de tramos: "); put(f(i).numElem,0); new_line;
	if f(i).numElem /= 0 then
		aux:=f(i).primElem;
		while aux /= null loop
			put(ht & ht & "Tramo numero: "); put(tramo,0); new_line;
			put(ht & ht & "Posicion: "); put(aux.pos, 0); new_line;
			put(ht & ht & "Inicial: "); put(aux.ini,0); new_line;
			put(ht & ht & "Longitud: "); put(aux.long,0); new_line;
			put_line(ht & ascii.ht & "*******************************");
			tramo:=tramo+1;
			aux:=aux.ptSig;
		end loop;
	end if;
end loop;

end visualizaFigura;

----------------------------------------

procedure limpiaFigura is
begin
	for i in 1 .. MAX loop
		--Creo que con esto simplemente no bastara ya que no se si Ada tiene recolector de basura??
		f(i).primElem := null;
		f(i).ultElem := null;
	end loop;
	new_line;
	put_line("Figura limpiada");
end limpiaFigura;

----------------------------------------

procedure visualizaFichero(nombre:in String15.Bounded_string) is
fichText: Ada.text_IO.File_type;
car: character;

begin
--Para usar este tenemos que poner el put despues del get porque lo que hace es al leer el ultimo caracter
--detecta el fin de linea y no trata el ultimo caracter

	open(fichText,in_file,to_string(nombre));

	while not end_of_file(fichText) loop 
		get(fichText, car);
		if not end_of_line(fichText) then 
			put(car);
		else
			new_line;
		end if;
	end loop;


end visualizaFichero;

----------------------------------------

procedure visualizaFichero2(nombre: in String15.Bounded_string) is
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
	
end visualizaFichero2;

----------------------------------------

procedure inicializaFigura is

begin

--No tenemos que inicializar ningun tramo porque no existe ninguno
	for i in 1 .. MAX loop
		f(i).numElem := 0; --La linea no tiene al principio ningun tramo
		f(i).ultCar := 0; --El menor numero posible si hay algun caracter en la linea es 1
		f(i).primElem := null;
		f(i).ultElem := null;
	end loop;

end inicializaFigura;

----------------------------------------

end ficheros;
