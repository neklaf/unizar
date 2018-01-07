-- Implementacion del modulo para el TAD de arboles rojinegros para practica 1 de TAP
-- Fecha:
-- Autores: Aitor Acedo & Santiago Faci
-- Basado en la especificacion en pseudocodigo de "Introduction to Algorithms" de Thomas H. Cormen, Charles E. Leiserson
-- y Ronald L. Rivest.

-- REGLAS 
-- 1. Los nodos deben ser de un color, que podr� ser rojo o negro.
-- 2. Ning�n nodo rojo puede tener hijos rojos.
-- 3. Todos los caminos posibles que hacen recorrer el �rbol desde su ra�z (arriba) hasta abajo deber�n contener siempre
-- el mismo n�mero de nodos negros.

with Unchecked_Deallocation;
with text_io; use text_io;

package body rbtree is
    
    -- Vacia el arbol completo
    procedure vacio(t: out tree) is
    begin
        t:=null;
    end vacio;

    -- Insertar un nodo en un �rbol de b�squeda binario. Se usara desde el procedimiento "insertar" para �rboles
    -- rojinegros y se encargar� simplemente de insertar el nodo en el �rbol.
    procedure insertar_en_arbol(t: in out tree; z: in out tree) is
        x, y: tree;
    begin
        y:=null;
        x:=t;

        -- Se recorre el �rbol en busca de la posici�n correcta para el nodo que se quiere insertar. Esta posici�n
        -- depender� del valor de dicho nodo.
        while x/=null loop
            y:=x;
            if z.palabra < x.palabra then
                x:=x.izq;
            else
                x:=x.dch;
            end if;
        end loop;

        -- Una vez encontrada la posici�n, debemos insertar el nodo en el �rbol.
        z.padre:=y;     -- y ser� el padre del nuevo nodo que se inserta (z)
        if y=null then
        -- Si el padre del nuevo nodo es null, significa que es el primer nodo que insertamos. Por lo tanto �ste ser� el
        -- nodo ra�z de un nuevo �rbol.
            t:=z;       
        -- Sino, si es menor ser� hijo izquierda de y, si es mayor ser� hijo derecho de y
        elsif z.palabra < y.palabra then
            y.izq:=z;
        else
            y.dch:=z;
        end if;

        -- No se comprueba si existe ya la palabra en el arbol. Deberia dejarse asi como esta ya que el enunciado
        -- especifica que si la palabra ya existe se actualice su valor.
    end insertar_en_arbol;

    -- Insertar un nodo en el arbol rojinegro
    procedure insertar(t: in out tree; palabra: in tipo_palabra; definicion: in tipo_definicion) is
        --y:tree;
        x:tree;
    begin
        -- Creamos el nodo que va a ser insertado con la palabra y definici�n dadas, y color rojo por defecto. De esta
        -- manera siempre se satisfar� la regla de que debe haber siempre el mismo n�mero de nodos negros sea cual sea
        -- el camino que se toma para ir desde la ra�z del �rbol hasta cualquiera de sus ramas de m�s abajo. Por otra
        -- parte, otra de las reglas puede llegar a incumplirse en el caso de que el padre de este nodo sea rojo.
        x:= new nodo'(palabra, definicion, null, null, null, rojo);
        -- Primero se inserta el nodo como si de un �rbol de b�squeda binario se tratase, m�s adelante se llevan a cabo las
        -- operaciones propias de los �rboles rojinegros
        insertar_en_arbol(t, x);
        -- Una vez ha sido insertado el nodo en el �rbol, habr� que ver si es necesario rebalancear el mismo
        
        -- REBALANCEADO DEL ARBOL -- 
        -- Por lo visto en los �rboles rojinegros nunca es necesario rebalancear m�s de 3
        -- veces. Cosa que no ocurre con los AVL (ventaja de los RB frente a AVL)
        
    end insertar;

    procedure rotacion_izq(t: in out tree; x: in out tree) is
        y:tree;
    begin
        y:=x.dch;
        x.dch:=y.izq;
        
        if y.izq/=null then
            y.izq.padre:=x;
        end if;

        y.padre:=x.padre;

        if x.padre=null then
            t:=y;
        elsif x=x.padre.izq then
            x.padre.izq:=y;
        else
            x.padre.dch:=y;
        end if;

        y.izq:=x;
        x.padre:=y;
        
    end rotacion_izq;

    procedure rotacion_dch(t: in out tree; x: in out tree) is
        y:tree;
    begin
        y:=x.izq;
        x.dch:=y.dch;
        
        if y.dch/=null then
            y.dch.padre:=x;
        end if;

        y.padre:=x.padre;

        if x.padre=null then
            t:=y;
        elsif x=x.padre.dch then
            x.padre.dch:=y;
        else
            x.padre.izq:=y;
        end if;

        y.dch:=x;
        x.padre:=y;
    end rotacion_dch;

    -- Busca una palabra en todos los nodos del �rbol
    procedure buscar(t: in tree; palabra: in tipo_palabra; encontrada: out boolean; el_nodo: out tree) is
    begin
        el_nodo := t;
        encontrada := false;
        -- Se recorrer� el �rbol como si de un �rbol binario de b�squeda se tratara
        -- Una vez que la palabra haya sido encontrada no se seguir� recorriendo el �rbol
        while not encontrada and el_nodo /= null loop
            if palabra < el_nodo.palabra then
                el_nodo := el_nodo.izq;
            elsif el_nodo.palabra < palabra then
                el_nodo := el_nodo.dch;
            else
                -- Se ha encontrado la palabra (no es ni menor ni mayor)
                encontrada := true;
            end if;
        end loop;
    end buscar;

    -- Libera un nodo
    procedure liberar is new Unchecked_Deallocation(nodo,tree);
    
    -- Calcular el nodo sucesor
    procedure sucesor(nodo: in tree; siguiente: out tree) is
    begin
        if nodo.dch /= null then
            siguiente := nodo.dch;
            while siguiente.izq /= null loop
                siguiente := siguiente.izq;
            end loop;
        else
            siguiente := nodo.padre;
            while siguiente.padre /= null and then siguiente = siguiente.padre.dch loop
                siguiente := siguiente.padre;
            end loop;
            siguiente := siguiente.padre;
        end if;
    end sucesor;
    
    -- Borrar el nodo que contiene la palabra, realiza el posterior equilibrado del �rbol (si procede)
    procedure borrar(t: in out tree; palabra: in tipo_palabra) is
        x, y: tree;
        el_nodo: tree;          -- El nodo a borrar
        encontrado: boolean;    -- Indicar� si el nodo estaba para borrar o no borrar
    begin
        -- Primero buscaremos el nodo invocando a nuestro procedimiento buscar(..).
        -- Adem�s, en el_nodo apuntar� al nodo encontrado
        buscar(t, palabra, encontrado, el_nodo);

        -- Comprobamos que el nodo est� para borrarlo, sino no se borra y se finaliza el procedimiento ya que no hemos
        -- encontrado el nodo
        if encontrado then
            -- Se ha encontrado el nodo, est� apuntado por el_nodo, toca borrarlo del �rbol
            if el_nodo.izq = null or el_nodo.dch = null then
                y := el_nodo;
            else
                sucesor(el_nodo, y);
            end if;

            if y.izq /= null then
                x := y.izq;
            else
                x := y.dch;
            end if;

            if x /= null then
                x.padre := y.padre;
            end if;

            if y.padre = null then
                t := x;
            else
                if y = y.padre.izq then
                    y.padre.izq := x;
                else
                    y.padre.dch := x;
                end if;
            end if;

            if y /= el_nodo then
                el_nodo.palabra := y.palabra;
            end if;

            liberar(y);
        end if;
        -- No hay caso else, ya que no hay que hacer nada. Corresponde con que el nodo no existe, por lo tanto no se
        -- borra
    end borrar;
    
    procedure dibujar(t: in tree) is
    begin
        if t=null then
            put("null");
        else
            put_palabra(t.palabra);
            dibujar(t.izq);
            dibujar(t.dch);
        end if;
    end dibujar;
end rbtree;
