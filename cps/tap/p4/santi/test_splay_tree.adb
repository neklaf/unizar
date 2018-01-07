-- Programa de prueba para el m�dulo de �rbol auto-organizativo (splay tree)
-- Autores: Aitor Acedo & Santiago Faci

with ada.text_io; use ada.text_io;
with ada.Integer_text_io; use ada.Integer_text_io;
with ada.strings.unbounded; use ada.strings.unbounded;
with ustrings; use ustrings;
with ada.command_line; use ada.command_line;
with ada.real_time; use ada.real_time;
with Ada.Numerics.Discrete_Random; 
with splay_tree;

procedure test_splay_tree is

    package diccionario is new splay_tree(ustring, ustring, "<", put, put);
    use diccionario;

    numeroLimite: constant integer := 200000; -- N�mero l�mite de elementos a insertar
    subtype limiteAlea is Integer range 0..numeroLimite; --Limite para los n�meros aleatorios
    package Aleatorios is new Ada.Numerics.Discrete_Random(limiteAlea);
    use Aleatorios;
    alea: generator;

    s: splay;
    palabra, definicion, busqueda, nombreFicheroPalabras, nombreFicheroDefiniciones: ustring;
    ficheroPalabras, ficheroDefiniciones: file_type;
    longitud, i: integer;
    tInicio, tFin: Time;
    tComputo: Time_Span;
    tiempo, n: integer;
    type vector is array(1..numeroLimite) of ustring;
    palabras: vector;
    nBusquedas: integer; --N�mero de b�squedas que se realizar�n sobre la estructura de datos
    nBorrados: integer; -- N�mero de borrados
    encontrado: boolean := true; -- Para 'buscar' de splay_tree
    fichero_salida: file_type;  -- Fichero de resultados

begin

    put_line("Practica 4 - Tecnicas Avanzadas de Programacion");
    put_line("Programa de prueba para el m�dulo de �RBOL AUTO-ORGANIZATIVO (SPLAY TREE)");
    put_line("Aitor Acedo & Santiago Faci");

    if argument_count < 3 then
        -- Se indica al usuario como debe ejecutarse el comando
        put("Uso: test_splay_tree <nombre_fichero_palabras> <nombre_fichero_definiciones> <inserciones> <busquedas> <borrados>");
    else
        -- Se leen lo que son el fichero de palabras y el de definiciones
        --nombreFicheroPalabras := U(argument(1));        -- Fichero de palabras
        --nombreFicheroDefiniciones := U(argument(2));    -- Fichero de definiciones
        get(argument(3), longitud, i);                  -- N�mero de elementos que se van a insertar
        get(argument(4), nBusquedas, i);                  -- N�mero de b�squedas que se realizar�n
        get(argument(5), nBorrados, i);                  -- N�mero de borrados
    end if;
    -- Se carga en la lista el diccionario completo, palabras y definiciones
    open(ficheroPalabras, in_file, argument(1));        -- Se carga el fichero de palabras
    open(ficheroDefiniciones, in_file, argument(2));    -- Se carga el fichero de definiciones

    -- Se abre el fichero de resultados y se prepara la cabecera de este test
    open(fichero_salida, append_file, "resultados_practica4.txt");
    put(fichero_salida,"TEST SPLAY | Tama�o:"); put(fichero_salida, longitud); put(fichero_salida, ", B�squedas:"); 
    put(fichero_salida, nBusquedas);  put(fichero_salida,", Borrados:"); put(fichero_salida, nBorrados); put_line(fichero_salida, "");
    
    -- INICIALIZAMOS LA ESTRUCTURA
    crear(s);
    -- INSERCI�N DE DATOS EN LA LISTA
    -- Se recorren ambos ficheros y se cargan los datos en la lista auto-organizativa
    i := 1;
    put_line("Insertando elementos en la lista . . .");
    tInicio := Clock;
    while not end_of_file(ficheroPalabras) and i < longitud loop
        get_line(ficheroPalabras, palabra);
        get_line(ficheroDefiniciones, definicion);
        --put_line(palabra);
        palabras(i) := palabra;
        insertar(palabra, definicion, s);
        i := i+1;
    end loop;
    tFin := Clock;
    tComputo := tFin - tInicio;
    put("Inserci�n completada con �xito -> tiempo: ");
    tiempo := tComputo/Microseconds(1);
    put(tiempo); put_line("");

    -- Se a�aden los resultados al fichero de test
    put(fichero_salida,"Inserci�n | Tiempo:"); put(fichero_salida, tiempo); put_line(fichero_salida, "");

    -- Se cierran los ficheros
    close(ficheroPalabras);
    close(ficheroDefiniciones);

    -- B�SQUEDA EN LA LISTA
    -- Se realizan un n�mero determinado de b�squedas sobre la misma lista
    Reset(alea);
    tInicio := Clock;
    for i in 1..nBusquedas loop
        n := random(alea) mod longitud +1;
        palabra := palabras(n);
        --put_line(palabra);
        buscar(palabra, s, encontrado);
    end loop;
    tFin := Clock;
    tComputo := tFin - tInicio;
    put(nBusquedas); put(" b�squedas realizadas -> tiempo: ");
    tiempo := tComputo/Microseconds(1);
    put(tiempo); put_line("");

    -- Se a�aden los resultados al fichero de test
    put(fichero_salida,"B�squeda | Tiempo:"); put(fichero_salida, tiempo); put_line(fichero_salida, "");

    -- BORRADO
    -- Se realizan un n�mero determinado de b�squedas sobre la misma lista
    Reset(alea);
    tInicio := Clock;
    for i in 1..nBorrados loop
        n := random(alea) mod longitud +1;
        palabra := palabras(n);
        borrar(palabra, s);
    end loop;
    tFin := Clock;
    tComputo := tFin - tInicio;
    put(nBorrados); put(" borrados -> tiempo: ");
    tiempo := tComputo/Microseconds(1);
    put(tiempo); put_line("");

    -- Se a�aden los resultados al fichero de test
    put(fichero_salida,"Borrado | Tiempo:"); put(fichero_salida, tiempo); put_line(fichero_salida, "");
    put_line(fichero_salida, "");


    --dibujar(s);
    
    -- Se cierra el fichero de resultados
    close(fichero_salida);

end test_splay_tree;    
