-- Técnicas Avanzadas de Programación - Práctica 4
-- Implementación del módulo para una implementación de arbol auto-organizativo (splay tree) para el TAD diccionario.
-- Autores: Aitor Acedo & Santiago Faci 
--
-- Implementación tomada de los apuntes de la asignatura (a su vez tomada, "más o menos", de [Wei99]), ligeramente
-- modificada para adaptarla a un TAD diccionario, tal y como pide el enunciado.
--
-- Ligera modificación para soportar la actualización de definiciones de palabras que ya se encontraban insertadas.

with text_io; use text_io;
with unchecked_deallocation;

package body splay_tree is

    function "="(palabra1, palabra2: tipo_palabra) return boolean is
    begin
        return not(palabra2 < palabra1) and then not (palabra1 < palabra2);
    end "=";

    procedure rotar_con_hijo_izq(p2: in out ptNodo) is
        p1: ptNodo := p2.izq;
    begin
        p2.izq := p1.dch;
        p1.dch := p2;
        p2 := p1;
    end rotar_con_hijo_izq;
        

    procedure rotar_con_hijo_dch(p1: in out ptNodo) is
        p2: ptNodo := p1.dch;
    begin
        p1.dch := p2.izq;
        p2.izq := p1;
        p1 := p2;
    end rotar_con_hijo_dch;

    procedure liberar is new unchecked_deallocation(nodo, ptNodo);

    cabeza: ptNodo := new nodo;

    procedure splays(palabra: in tipo_palabra; p: in out ptNodo; elNodoNulo: in out ptNodo) is
        max_hijo_izq: ptNodo := cabeza;
        min_hijo_dch: ptNodo := cabeza;
    begin
        cabeza.izq := elNodoNulo;
        cabeza.dch := elNodoNulo;
        elNodoNulo.palabra := palabra;
        
        loop
            if palabra < p.palabra then
                if palabra < p.izq.palabra then
                    rotar_con_hijo_izq(p);
                end if;
                exit when p.izq = elNodoNulo;
                min_hijo_dch.izq := p;
                min_hijo_dch := p;
                p := p.izq;
            elsif p.palabra < palabra then
                if p.dch.palabra < palabra then
                    rotar_con_hijo_dch(p);
                end if;
                exit when p.dch = elNodoNulo;
                max_hijo_izq.dch := p;
                max_hijo_izq := p;
                p := p.dch;
            else
                exit;
            end if;
        end loop;
     
        max_hijo_izq.dch := p.izq;
        min_hijo_dch.izq := p.dch;
        p.izq := cabeza.dch;
        p.dch := cabeza.izq;

    end splays;

    procedure crear(s: in out splay) is
    begin
        s.nodoNulo := new nodo;
        s.nodoNulo.izq := s.nodoNulo;
        s.nodoNulo.dch := s.nodoNulo;
        s.raiz := s.nodoNulo;
    end crear;

    procedure buscar(palabra: in tipo_palabra; s: in out splay; encontrado: out boolean) is
    begin
        splays(palabra, s.raiz, s.nodoNulo);
        encontrado := (s.raiz.palabra = palabra);
    end buscar;

    procedure insertar(palabra: in tipo_palabra; definicion: in tipo_definicion; s: in out splay) is
        nuevoNodo: ptNodo;
    begin
        if nuevoNodo = null then
            nuevoNodo := new nodo'(palabra, definicion, null, null);
        else
            nuevoNodo.palabra := palabra;
            nuevoNodo.definicion := definicion;
            nuevoNodo.izq := null;
            nuevoNodo.dch := null;
        end if;

        if s.raiz = s.nodoNulo then
            nuevoNodo.izq := s.nodoNulo;
            nuevoNodo.dch := s.nodoNulo;
            s.raiz := nuevoNodo;
        else
            splays(palabra, s.raiz, s.nodoNulo);
            if palabra < s.raiz.palabra then
                nuevoNodo.izq := s.raiz.izq;
                nuevoNodo.dch := s.raiz;
                s.raiz.izq := s.nodoNulo;
                s.raiz := nuevoNodo;
            elsif s.raiz.palabra < palabra then
                nuevoNodo.dch := s.raiz.dch;
                nuevoNodo.izq := s.raiz;
                s.raiz.dch := s.nodoNulo;
                s.raiz := nuevoNodo;
            else
                -- La palabra ya existe en el árbol, se actualiza la definición 
                s.raiz.definicion := definicion;
                --put_line("Nodo actualizado");
                --return;   
            end if;
        end if;

        nuevoNodo := null;
        --put_line("Nodo insertado");

    end insertar;

    procedure borrar(palabra: in tipo_palabra; s: in out splay) is
        nuevoArbol: ptNodo;
        encontrado: boolean;
    begin
        buscar(palabra, s, encontrado);         -- splay de d a la raíz

        if s.raiz.izq = s.nodoNulo then
            nuevoArbol := s.raiz.dch;
        else
            -- buscar el máximo en el subárbol izquierdo y hacerle splay a la raíz y añadirle el hijo derecho
            nuevoArbol := s.raiz.izq;
            splays(palabra, nuevoArbol, s.nodoNulo);
            nuevoArbol.dch := s.raiz.dch;
        end if;
        liberar(s.raiz);
        s.raiz := nuevoArbol;
    end borrar;

    procedure dibujar(s: splay) is
        aux: ptNodo := null;
    begin
        put("raiz -> "); put_palabra(s.raiz.palabra); put("-"); put_definicion(s.raiz.definicion); put_line("");
        
        aux := s.raiz.izq;
        while aux /= null loop
            put("->"); put_palabra(aux.palabra); put("-"); put_definicion(aux.definicion); put_line("");
            aux := aux.izq;

            if aux = s.nodoNulo then
                exit;
            end if;
        end loop;

        put_line("---");
        
        aux := s.raiz.dch;
        while aux /= null loop
            put("->"); put_palabra(aux.palabra); put("-"); put_definicion(aux.definicion); put_line("");
            aux := aux.dch;

            if aux = s.nodoNulo then
                exit;
            end if;
        end loop;
    end dibujar;

end splay_tree;
