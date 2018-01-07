-- Especificacion del modulo para el TAD de arboles rojinegros para practica 1 de TAP
-- Fecha:
-- Autores: Aitor Acedo & Santiago Faci
-- Basado en la especificacion en pseudocodigo de "Introduction to Algorithms" de Thomas H. Cormen, Charles E. Leiserson
-- y Ronald L. Rivest.

generic
    type tipo_palabra is private;
    type tipo_definicion is private;
    with function "<" (izq, dch: in tipo_palabra) return boolean;
    --with function ">" (izq, dch: in tipo_palabra) return boolean;
    with procedure put_palabra (palabra: in tipo_palabra);
    with procedure put_definicion (palabra: in tipo_palabra);
package rbtree is
    type tree is private;

    -- Crea un �rbol Rojinegro vac�o
    procedure vacio (t: out tree);
    -- Inserta una palabra con su definici�n en el �rbol, si �sta palabra ya existe se actualiza su definici�n
    procedure insertar (t: in out tree; palabra: in tipo_palabra; definicion: in tipo_definicion);
    -- Borra el nodo que almacena la palabra que se pasa como par�metro
    procedure borrar (t: in out tree; palabra: in tipo_palabra);
    -- Busca el nodo que contiene a la palabra. Adem�s, dicho nodo es apuntado por el par�metro de salida "el_nodo"
    procedure buscar (t: in tree; palabra: in tipo_palabra; encontrada: out boolean; el_nodo: out tree);
    -- Rota a izquierdas el �rbol Rojinegro
    procedure rotacion_dch (t: in out tree; x: in out tree);
    -- Rota a derechas el �rbol Rojinegro
    procedure rotacion_izq (t: in out tree; x: in out tree);
    -- Realiza un boceto de la estructura del �rbol y su contenido
    procedure dibujar (t: in tree);

    private
        type nodo;
        type tree is access nodo;
        type colores is (rojo, negro);
        type nodo is
            record
                -- Palabra del nodo
                palabra: tipo_palabra;
                -- Definici�n
                definicion: tipo_definicion;
                -- Nodos hijo izquierda, derecha y nodo padre
                izq, dch, padre: tree;
                -- Campo que indica el color del nodo
                color: colores;
            end record;
end rbtree;
