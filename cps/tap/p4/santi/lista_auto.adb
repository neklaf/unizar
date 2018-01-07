-- Técnicas Avanzadas de Programación - Práctica 4
-- Implementación del módulo para una implementación de lista lineal auto-organizativa para el TAD diccionario
-- Autores: Aitor Acedo & Santiago Faci
--
-- Se ha escogido la implementación mediante una heurística de "contar frecuencias" de entre todas las que se planteaban
-- en los apuntes de la asignatura.
-- Siguiendo esta heurística, la lista se encuentra ordenada por el número de accesos que se han efectuado a sus nodos,
-- siendo el primero el que mas "visitas" ha recibido. Para llevar a cabo esto, tras cada inserción, se invoca a un
-- procedimiento que se encarga de ordenar la lista por si hubiera habido cambios y algún nodo debiera modificar su
-- posición.
--

with text_io; use text_io;
with ada.integer_text_io; use ada.integer_text_io;

package body lista_auto is

    -- Crea una lista vacía
    procedure crear(l: out lista) is
    begin
        l:= null;
    end crear;

    -- Inserta la palabra en la lista. Si ésta ya existe, se actualiza su definición
    procedure insertar(palabra: in tipo_palabra; definicion: in tipo_definicion; l: in out lista) is
        aux, node: lista;
        insertado: boolean;
    begin
        insertado := false;
        node := new nodo'(palabra, definicion, 0, null);
        
        if l = null then
        -- La lista esta vacía, estamos insertando el primer elemento
            --put_line("La lista esta vacía, se inserta el primer elemento");
            l := node;
        else
            aux := l;

            if aux.palabra = palabra then
            -- El elemento existe y es el primero, lo actualizamos
                --put_line("La palabra ya existe en el diccionario, se actualizará su valor");
                node.siguiente := l;
                l := node;
            else
                -- Mediante este bucle se va recorriendo la lista en busca del nodo por si éste ya hubiera sido
                -- insertado. Si es asi se actualiza su valor. Sino, se insertará al final de la lista ya que su
                -- contador de accesos estará a 0 por ser un nuevo nodo.
                while aux.siguiente/=null and not insertado loop
                    if aux.siguiente.palabra = palabra then
                    -- El elemento está en la lista, se actualiza
                        --put_line("La palabra ya existe en el diccionario, se actualizará su valor");
                        node.siguiente := aux.siguiente.siguiente;
                        aux.siguiente := node; 
                        insertado := true;
                    end if;

                    aux := aux.siguiente;
                end loop;

                if not insertado then
                -- Se ha llegado al final de la lista y no se ha insertado, es un elemento nuevo, se inserta al final
                    aux.siguiente := node;
                end if;
            end if;
        end if;
    end insertar;

    -- Busca una palabra en la lista y actualiza el contador de accesos a la misma. Posteriormente invoca al
    -- procedimiento encargado de ordenar la lista por la frecuencia de accesos a los nodos.
    procedure buscar(palabra: in tipo_palabra; l: in out lista) is
        aux: lista;
        encontrado: boolean;
    begin
        encontrado := false;

        if l = null then
        -- La lista esta vacía
            put_line("La lista está vacía");
        else
            aux := l;

            while aux/=null and not encontrado loop
                if aux.palabra = palabra then
                    --put_line("Palabra encontrada");
                    encontrado := true;
                    -- Incrementa el contador de accesos al nodo
                    aux.frecuencia := aux.frecuencia +1;
                    -- Para reordenar, primero elimino el nodo que debe ser reordenado y luego reordeno
                    --node := new nodo'(aux.palabra, aux.definicion, aux.frecuencia, null);
                    borrar(aux.palabra, l);
                    ordena(aux, l);
                end if;
                aux := aux.siguiente;
            end loop;
        end if;

        --if not encontrado then
            -- No existe la palabra en el diccionario, búsqueda fallida
            --put_line("No se ha encontrado la palabra en el diccionario");
        --end if;
    end buscar;

    -- Ordena la lista en función de la frecuencia de los nodos. Es invocado cada vez que se accede a un nodo para
    -- reordenar ese nodo, si procede. 
    -- Coloca el nodo que se le pasa como parámetro en la lista en función de su contador de frecuencias. Dicho nodo ha
    -- sido previamente eliminado de ésta para evitar repeticiones
    procedure ordena(node: in out lista; l: in out lista) is
        aux: lista;
    begin
        aux := l;

        if aux.frecuencia < node.frecuencia then
            node.siguiente := l;
            l := node;
        else
            -- Recorre la lista en busca de la posición correcta del nodo
            while aux.siguiente /= null loop
                if node.frecuencia < aux.siguiente.frecuencia then
                    aux := aux.siguiente;
                else
                    exit;
                end if;
            end loop;

            --if aux.siguiente /= null then
            -- Si se ha encontrado el nodo
            
            -- Se inserta el nodo ya sea entre dos nodos de mayor y menor frecuencia, o al final de la lista si éste es
            -- el que menos accesos ha recibido
                node.siguiente := aux.siguiente;
                aux.siguiente := node;

            --else
            --    aux.siguiente := node;
            --end if;
        end if;

            
    end ordena;

    -- Elimina un nodo de la lista. Será el nodo cuya palabra sea la que se pasa como parámetro al procedimiento
    procedure borrar(palabra: in tipo_palabra; l: in out lista) is
        aux: lista;
        borrado: boolean;
    begin
        aux := l;
        borrado := false;

        if aux = null then
            put_line("La lista está vacía. No se puede borrar nada");
        else
            if aux.palabra = palabra then
            -- El nodo que hay que eliminar es el primero. Simplemente "nos lo saltamos"
                l := l.siguiente;
                borrado := true;
            else
                while aux.siguiente /= null and then aux.siguiente.palabra /= palabra loop
                    aux := aux.siguiente;
                end loop;

                if aux.siguiente /= null then
                -- Se ha encontrado el nodo que hay que borrar, "nos lo saltamos"
                    aux.siguiente := aux.siguiente.siguiente;
                    borrado := true;
                end if;
            end if;
        end if;
    end borrar;

    -- Dibuja la lista 
    procedure dibujar(l: in lista) is
        aux: lista;
    begin
        aux := l;
        
        put_line("--LISTA--");
        if aux = null then
            put_line("La lista está vacía");
        else
            while aux/=null loop
                put_palabra(aux.palabra);
                put("-");
                put_definicion(aux.definicion);
                put("-");
                put(aux.frecuencia);
                put_line("");

                aux := aux.siguiente;
            end loop;
        end if;
    end dibujar;

end lista_auto;
