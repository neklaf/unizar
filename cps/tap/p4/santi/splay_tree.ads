-- T�cnicas Avanzadas de Programaci�n - Pr�ctica 4
-- Especificaci�n del m�dulo para una implementaci�n de arbol auto-organizativo (splay tree) para el TAD diccionario
-- Autores: Aitor Acedo & Santiago Faci
--
-- Implementaci�n tomada de los apuntes de la asignatura (a su vez tomada, "m�s o menos", de [Wei99]), ligeramente
-- modificada para adaptarla a un TAD diccionario, tal y como pide el enunciado.
--

generic
    
    type tipo_palabra is private;
    type tipo_definicion is private;
    with function "<" (izq, dch: in tipo_palabra) return boolean;
    with procedure put_palabra(palabra: in tipo_palabra);
    with procedure put_definicion(definicion: in tipo_definicion);

package splay_tree is

    type splay is private;

    -- Crea un splay tree vac�o
    procedure crear(s: in out splay);
    -- Realiza la reorganizaci�n del �rbol cada vez que se inserta un nuevo nodo o se accede a uno ya existente
    --procedure splay(palabra: in tipo_palabra; p: in out ptNodo; elNodoNulo: in out ptNodo);
    -- Busca una palabra en todo el �rbol
    procedure buscar(palabra: in tipo_palabra; s: in out splay; encontrado: out boolean);
    -- Inserta un nuevo nodo en el �rbol, si �ste ya existe se actualiza su definici�n
    procedure insertar(palabra: in tipo_palabra; definicion: in tipo_definicion; s: in out splay);
    -- Borrar la palabra y la definici�n de �sta
    procedure borrar(palabra: in tipo_palabra; s: in out splay);
    -- Dibuja un boceto del �rbol y el contenido del mismo
    procedure dibujar(s: in splay);

    private 
        type nodo;
        type ptNodo is access nodo;
        type splay is
            record
            -- Nodo ra�z del �rbol
            raiz: ptNodo;
            -- Nodo centinela
            nodoNulo: ptNodo;
            end record;
        type nodo is
            record
            -- Palabra del nodo
            palabra: tipo_palabra;
            -- Definici�n de la palabra
            definicion: tipo_definicion;
            -- Nodos hijo izquierda y derecha
            izq, dch: ptNodo;
            end record;

end splay_tree;
    
