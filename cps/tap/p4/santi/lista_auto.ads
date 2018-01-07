-- T�cnicas Avanzadas de Programaci�n - Pr�ctica 4
-- Especificaci�n del m�dulo para una implementaci�n de lista lineal auto-organizativa para el TAD diccionario
-- Autores: Aitor Acedo & Santiago Faci
-- 
-- Se ha escogido la implementaci�n mediante una heur�stica de "contar frecuencias" de entre todas las que se planteaban
-- en los apuntes de la asignatura.
-- Siguiendo esta heur�stica, la lista se encuentra ordenada por el n�mero de accesos que se han efectuado a sus nodos,
-- siendo el primero el que mas "visitas" ha recibido. Para llevar a cabo esto, tras cada inserci�n, se invoca a un
-- procedimiento que se encarga de ordenar la lista por si hubiera habido cambios y alg�n nodo debiera modificar su
-- posici�n.

generic

    type tipo_palabra is private;
    type tipo_definicion is private;
    with function "<" (izq, dch: in tipo_palabra) return boolean;
    with procedure put_palabra(palabra: in tipo_palabra);
    with procedure put_definicion(definicion: in tipo_definicion);

package lista_auto is

    type lista is private;

    -- Crea una lista vac�a
    procedure crear(l: out lista);
    -- Buscar una determinada palabra en toda la lista. Si la encuentra, incrementar� en uno el n�mero de accesos
    -- (frecuencia) a esa palabra
    procedure buscar(palabra: in tipo_palabra; l: in out lista);
    -- Inserta una palabra en la lista. Si �sta ya existe actualiza su definici�n
    procedure insertar(palabra: in tipo_palabra; definicion: in tipo_definicion; l: in out lista);
    -- Ordena la lista. Tras cada inserci�n, se invoca a este procedimiento para que mantenga la lista ordenada por el
    -- n�mero de accesos (frecuencia) que se dan en cada nodo.
    procedure ordena(node: in out lista; l: in out lista);
    -- Elimina, si existe, la palabra y su definici�n de la lista
    procedure borrar(palabra: in tipo_palabra; l: in out lista);
    -- Dibuja, a modo de ayuda, la lista auto-organizativa del primero al �ltimo elemento.
    procedure dibujar(l: in lista);

    private 
        type nodo;
        type lista is access nodo;
        type nodo is
            record
            palabra: tipo_palabra;              -- Palabra que representa el nodo
            definicion: tipo_definicion;        -- Definici�n de la palabra
            frecuencia: Integer;                -- Contador de n�mero de accesos a un determinado nodo
            siguiente: lista;                   -- Puntero al siguiente nodo de la lista
            end record;

end lista_auto;
    
