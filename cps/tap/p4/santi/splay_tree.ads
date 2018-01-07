-- Técnicas Avanzadas de Programación - Práctica 4
-- Especificación del módulo para una implementación de arbol auto-organizativo (splay tree) para el TAD diccionario
-- Autores: Aitor Acedo & Santiago Faci
--
-- Implementación tomada de los apuntes de la asignatura (a su vez tomada, "más o menos", de [Wei99]), ligeramente
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

    -- Crea un splay tree vacío
    procedure crear(s: in out splay);
    -- Realiza la reorganización del árbol cada vez que se inserta un nuevo nodo o se accede a uno ya existente
    --procedure splay(palabra: in tipo_palabra; p: in out ptNodo; elNodoNulo: in out ptNodo);
    -- Busca una palabra en todo el árbol
    procedure buscar(palabra: in tipo_palabra; s: in out splay; encontrado: out boolean);
    -- Inserta un nuevo nodo en el árbol, si éste ya existe se actualiza su definición
    procedure insertar(palabra: in tipo_palabra; definicion: in tipo_definicion; s: in out splay);
    -- Borrar la palabra y la definición de ésta
    procedure borrar(palabra: in tipo_palabra; s: in out splay);
    -- Dibuja un boceto del árbol y el contenido del mismo
    procedure dibujar(s: in splay);

    private 
        type nodo;
        type ptNodo is access nodo;
        type splay is
            record
            -- Nodo raíz del árbol
            raiz: ptNodo;
            -- Nodo centinela
            nodoNulo: ptNodo;
            end record;
        type nodo is
            record
            -- Palabra del nodo
            palabra: tipo_palabra;
            -- Definición de la palabra
            definicion: tipo_definicion;
            -- Nodos hijo izquierda y derecha
            izq, dch: ptNodo;
            end record;

end splay_tree;
    
