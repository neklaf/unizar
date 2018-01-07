--
-- PROGRAMA DE PRUEBA DEL FUNCIONAMIENTO DE LA IMPLEMENTACION DE LA PRACTICA 2ª
--
-- Version: 1.0
-- Fecha: Julio 2004
-- Autores: Acedo Legarre, Aitor y Faci Miguel, Santiago
-- Descripcion: Test simple de prueba para la practica 2
--

with conj_dm; use conj_dm;
with Ada.text_io; use Ada.text_io;
with Ada.Numerics.Discrete_Random; 
with Ada.Command_line; use Ada.Command_line;

procedure test_conj_dm is
 	set: conj;
	num_dat, num_iter, l: integer;
	nat: natural;
	op1, op2, op3: integer; --no se pueden definir como naturales porque pueden llegar a valer 0

	subtype numeros is integer range 1 .. MAX_NUM;

	subtype op is integer range 1 .. 3;
	package op_aleatorias is new Ada.Numerics.Discrete_Random(op);
	use op_aleatorias;
	gen2: op_aleatorias.Generator;

 	package aleatorios is new Ada.Numerics.Discrete_Random(numeros);
    use aleatorios;
	gen: aleatorios.Generator;

    package numeros_io is new integer_io(numeros);
    --use ent_io;
	
	package enteros_io is new integer_io(integer);
	--use enteros_io;

--Si ponemos los use de las 2 implementaciones automáticamente no utiliza la implementacion mas adecuada
--por lo que se producen fallos en ejecucion! Asi que es mas util hacerlo seleccionar la implementacion
--mas adecuada directamente.

--PROCEDIMIENTOS AUXILIARES PARA LA REALIZACION DE LA PRUEBA--
    procedure inicializa is
    --Crea el conjunto y resetea el generador de numeros aleatorios
    begin
        put_line("Inicio del test:");
        crear(set);
		--inicializacion de los numeros aleatorios
        Reset(gen); 
        Reset(gen2);
		--Contadores de las operaciones que realizamos
		op1 := 0;
		op2 := 0;
		op3 := 0;
    end inicializa;

	procedure crea_fich is
	--Programa que crea un fichero de texto con 10000 datos para tener unos datos 
	--de inicializacion en un fichero
		fich_text: File_type;
		nombre: constant String := "datos.txt";

		MAX: constant integer := 10000;
		subtype num is integer range 1 .. MAX;
		package alea is new Ada.Numerics.Discrete_Random(num);
		use alea;
		gen: alea.Generator;
	begin
		create(fich_text, out_file, nombre);

		Reset(gen);

		for i in 1 .. MAX loop
			numeros_io.put(fich_text, Random(gen), 0);
			new_line(fich_text);
		end loop;

		close(fich_text);

		put_line("Fichero creado.");		
	end crea_fich;


--------------------------------------------------------------

begin

    if Argument_count /= 2 then
        put_line("Uso: ./test_conj_dm <num_dat> <num_iter>");
		put_line("<num_dat> sera el numero de datos que introduciremos en un arbol para inicializarlo");
		put_line("<num_iter> sera el numero de veces que ejecutara al azar una de las operaciones en el arbol");
    else
        inicializa;
        
		--Recepcion de los argumentos
        enteros_io.get(argument(1), num_dat, l);
        enteros_io.get(argument(2), num_iter, l);

		--Inicializamos el conjunto sobre el que vamos a realizar las operaciones
		for i in 1 .. num_dat loop
			insertar(set, Random(gen));
		end loop;

		put("Realizadas "); enteros_io.put(num_dat, 0); put(" inserciones."); new_line;

		--Pintamos el conjunto
		pintar_conj(set);
	
		--Realizamos al azar las operaciones mas interesantes
		for j in 1 .. num_iter loop
			case Random(gen2) is
				when 1 => --borrar
						nat := Random(gen);
						if buscar(set, nat) then 
							borrar(set, nat);
						end if;
						op1 := op1 + 1;
				when 2 => --insertar
						insertar(set, Random(gen));
						op2 := op2 + 1;
				when 3 => --dm
						nat := dm(set);
						op3 := op3 + 1;
						--put("La distancia minima es: "); put(nat, 0); new_line;
				when others => put_line("No existe la operacion a realizar.");
			end case;

		end loop;
			put_line("Se han realizado: ");
			put("Se han intentado "); 
			enteros_io.put(op1, 0); 
			put(" operaciones de borrado."); new_line;
			enteros_io.put(op2, 0); 
			put(" operaciones de insercion."); new_line;
			enteros_io.put(op3, 0); 
			put(" operaciones de distancia minima.");
	end if;
end test_conj_dm;
